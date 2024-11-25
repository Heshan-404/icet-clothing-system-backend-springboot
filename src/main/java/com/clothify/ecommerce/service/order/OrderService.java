package com.clothify.ecommerce.service.order;

import com.clothify.ecommerce.dto.order.OrderDTO;
import com.clothify.ecommerce.dto.order.OrderDetailsDTO;
import com.clothify.ecommerce.dto.product.ProductDTO;
import com.clothify.ecommerce.dto.product.ProductWithoutImageData;
import com.clothify.ecommerce.dto.product.UpdateProductDTO;
import com.clothify.ecommerce.dto.user.UserDTO;
import com.clothify.ecommerce.entity.order.OrderEntity;
import com.clothify.ecommerce.entity.order.orderitem.OrderItemEntity;
import com.clothify.ecommerce.entity.product.ProductEntity;
import com.clothify.ecommerce.entity.user.UserEntity;
import com.clothify.ecommerce.exception.order.StockLimitExceededException;
import com.clothify.ecommerce.exception.product.ProductNotFoundException;
import com.clothify.ecommerce.repository.order.OrderDAO;
import com.clothify.ecommerce.repository.order.orderitem.OrderItemDAO;
import com.clothify.ecommerce.repository.product.ProductDAO;
import com.clothify.ecommerce.repository.user.UserDAO;
import com.clothify.ecommerce.security.filter.JWTFilter;
import com.clothify.ecommerce.security.service.JWTService;
import com.clothify.ecommerce.service.product.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderDAO orderDAO;
    private final ObjectMapper mapper;

    private final JWTService jwtService;
    private final ProductDAO productDAO;
    private final OrderItemDAO orderItemDAO;
    private final UserDAO userDAO;
    private final ProductService productService;

    public OrderDTO placeOrder(OrderDTO order) {
        try {
            String email = jwtService.getAuthenticatedUserEmail();

            UserEntity userEntity = userDAO.findById(email).orElse(null);
            OrderEntity orderEntity = new OrderEntity();
            orderEntity.setUser(userEntity);
            orderEntity.setTotalCost(order.getTotalCost());
            orderEntity.setStatus(order.getStatus());
            orderEntity.setCreateDate(order.getCreateDate());

            List<OrderItemEntity> orderItemEntityList = new ArrayList<>();

            order.getOrderItems().forEach(orderItemEntity -> {
                OrderItemEntity orderItem = new OrderItemEntity();
                ProductEntity product = productDAO.findById(orderItemEntity.getProduct().getId()).orElse(null);
                orderItem.setProduct(product);
                if (product != null) {
                    List<Integer> stockQty = product.getStockQty();
                    if (stockQty.get(orderItemEntity.getSizeId()) > orderItemEntity.getQty()) {
                        stockQty.set(orderItemEntity.getSizeId(),
                                (stockQty.get(orderItemEntity.getSizeId()) - orderItemEntity.getQty()));
                        product.setStockQty(stockQty);
                    } else {
                        throw new StockLimitExceededException("stock limit exceeded");
                    }
                }
                productService.update(mapper.convertValue(product, UpdateProductDTO.class));
                orderItem.setQty(orderItemEntity.getQty());
                orderItem.setPrice(orderItemEntity.getPrice());
                orderItem.setSizeId(orderItemEntity.getSizeId());
                orderItemEntityList.add(orderItem);
            });
            orderEntity.setOrderItems(orderItemEntityList);
            OrderEntity save = orderDAO.save(orderEntity);
            return mapper.convertValue(save, OrderDTO.class);
        } catch (Exception e) {
            log.info(e.getMessage());
            return null;
        }
    }


    public OrderDTO getById(Integer orderId) {
        return mapper.convertValue(orderDAO.findById(orderId), OrderDTO.class);
    }


    public List<OrderDetailsDTO> getAllOrders() {
        // Fetch all orders sorted by creation date (ascending order)
        List<Object[]> allOrdersSortedByCreateDate = orderDAO.findAllOrdersSortedByCreateDate();
        List<OrderDetailsDTO> orderDetailsList = new ArrayList<>();

        // Group orders by orderId
        Map<Integer, OrderDetailsDTO> orderMap = new HashMap<>();

        for (Object[] row : allOrdersSortedByCreateDate) {
            Integer orderId = (Integer) row[0];  // orderId is the first element

            // Check if the order already exists in the map
            OrderDetailsDTO orderDetails = orderMap.get(orderId);
            if (orderDetails == null) {
                orderDetails = new OrderDetailsDTO();
                orderDetails.setId(orderId);
                orderDetails.setCreateDate((Date) row[1]);
                orderDetails.setStatus((String) row[2]);
                orderDetails.setTotalCost((Long) row[3]);
                orderDetails.setUserEmail((String) row[4]);

                // Initialize the orderItems list
                orderDetails.setOrderItems(new ArrayList<>());

                // Add the order to the map
                orderMap.put(orderId, orderDetails);
            }

            // Now map the order item (row) and add it to the current order's orderItems list
            OrderDetailsDTO.OrderItemDTO item = new OrderDetailsDTO.OrderItemDTO();
            item.setPrice((Double) row[6]);

            OrderDetailsDTO.OrderItemDTO.ProductDTO product = new OrderDetailsDTO.OrderItemDTO.ProductDTO();
            product.setId((Integer) row[7]);
            product.setQty((Integer) row[5]);
            product.setSizeId((Integer) row[8]);
            item.setProduct(product);

            // Add the item to the order's orderItems list
            orderDetails.getOrderItems().add(item);
        }

        // Add all orders to the result list
        orderDetailsList.addAll(orderMap.values());
        Collections.reverse(orderDetailsList);
        return orderDetailsList;
    }


    public OrderDetailsDTO getByOrderId(Integer orderId) {
        List<Object[]> rows = orderDAO.findOrderDetailsById(orderId);

        OrderDetailsDTO orderDetails = new OrderDetailsDTO();
        List<OrderDetailsDTO.OrderItemDTO> items = new ArrayList<>();

        for (Object[] row : rows) {
            if (orderDetails.getId() == null) {
                orderDetails.setId((Integer) row[0]);
                orderDetails.setCreateDate((Date) row[1]);
                orderDetails.setStatus((String) row[2]);
                orderDetails.setTotalCost((Long) row[3]);
                orderDetails.setUserEmail((String) row[4]);
            }

            OrderDetailsDTO.OrderItemDTO item = new OrderDetailsDTO.OrderItemDTO();
            item.setPrice((Double) row[6]);

            OrderDetailsDTO.OrderItemDTO.ProductDTO product = new OrderDetailsDTO.OrderItemDTO.ProductDTO();
            product.setId((Integer) row[7]);
            product.setQty((Integer) row[5]);
            product.setSizeId((Integer) row[8]);
            item.setProduct(product);

            items.add(item);
        }

        orderDetails.setOrderItems(items);
        return orderDetails;
    }

    public void changeStatus(Integer orderId, String status) {
        OrderEntity orderEntity = orderDAO.findById(orderId).orElseThrow();
        orderEntity.setStatus(status);
        orderDAO.save(orderEntity);
    }
}
