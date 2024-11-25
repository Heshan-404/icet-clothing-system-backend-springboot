package com.clothify.ecommerce.controller.order;

import com.clothify.ecommerce.dto.order.OrderDTO;
import com.clothify.ecommerce.dto.order.OrderDetailsDTO;
import com.clothify.ecommerce.repository.order.OrderDAO;
import com.clothify.ecommerce.service.order.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/order")
public class OrderController {
    private final OrderService orderService;
    private final OrderDAO orderDAO;

    @PostMapping
    public ResponseEntity<Object> placeOrder(@RequestBody OrderDTO order) {
        OrderDTO orderDTO = orderService.placeOrder(order);
        if (orderDTO == null) {
            return ResponseEntity.internalServerError().build();
        } else {
            if (orderDTO.getId() > 0) {
                log.info("success");
                return ResponseEntity.ok().build();
            }
            return ResponseEntity.badRequest().build();
        }
    }


    @GetMapping("/{orderId}")
    public OrderDetailsDTO getOrderByOrderId(@PathVariable Integer orderId) {
        return orderService.getByOrderId(orderId);
    }

    @GetMapping("/all")
    public List<OrderDetailsDTO> getAllOrders() {
        return orderService.getAllOrders();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/complete/{orderId}")
    public ResponseEntity<Object> completeOrder(@PathVariable Integer orderId) {
        orderService.changeStatus(orderId, "COMPLETED");
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/cancel/{orderId}")
    public ResponseEntity<Object> cancel(@PathVariable Integer orderId) {
        orderService.changeStatus(orderId, "CANCELLED");
        return ResponseEntity.ok().build();
    }
}
