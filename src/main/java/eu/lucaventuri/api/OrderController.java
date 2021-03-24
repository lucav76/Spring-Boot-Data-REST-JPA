package eu.lucaventuri.api;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import eu.lucaventuri.api.entities.Order;
import eu.lucaventuri.api.entities.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/orders")
public class OrderController {
    // TODO: when Java 16 is available, switch to records
    @Data
    @AllArgsConstructor
    public static class OrderLine {
        @JsonUnwrapped
        private Product product;
        private int quantity;

        public double getLineTotal() {
            return product.getPriceInUsd() * quantity;
        }
    }

    // TODO: when Java 16 is available, switch to records
    @Data
    public static class FullOrder {
        private long orderId;
        private String user;
        private Date creationDate = new Date();
        private String state = "OPEN";
        private Set<OrderLine> lines = new HashSet<>();

        public double getTotal() {
            return lines.stream().mapToDouble(OrderLine::getLineTotal).sum();
        }

        public static FullOrder from(Order order) {
            var full = new FullOrder();

            full.setOrderId(order.getOrderId());
            full.setUser(order.getUser());
            full.setCreationDate(order.getCreationDate());
            full.setState(order.getState());

            full.lines = order.getOrderItems().stream().
                    map(item -> new OrderLine(item.getProduct(), item.getQuantity())).collect(Collectors.toSet());

            return full;
        }
    }

    @Autowired
    private OrderService orderService;

    @Value("${hostName}")
    private String hostName;
    @Value("${server.port}")
    private String port;

    @PostMapping()
    public ResponseEntity<Void> createOrder() throws URISyntaxException {
        var order = orderService.createOrder();

        return ResponseEntity.created(new URI("http://" + hostName + ":" + port + "/orders/" + order.getOrderId()))
                .build();
    }

    @GetMapping()
    public List<FullOrder> getOrders(HttpServletRequest request) {
        if (request.isUserInRole("ADMIN"))
            return orderService.getAllOrders().stream().map(FullOrder::from).collect(Collectors.toList());
        else
            return orderService.getOrders(request.getRemoteUser()).stream().map(FullOrder::from).collect(Collectors.toList());
    }

    @GetMapping("/{orderId}")
    public ResponseEntity getOrder(@PathVariable long orderId, HttpServletRequest request) {
        var orderOptional = orderService.getOrder(orderId);

        return orderOptional.map(order -> {
                    if (request.isUserInRole("ADMIN") || request.getRemoteUser().equals(order.getUser()))
                        return ResponseEntity.ok().body(FullOrder.from(order));
                    return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
                }
        ).orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{orderId}/product/{productId}/quantity/{quantity}")
    public void addProductToOrder(@PathVariable long orderId, @PathVariable long productId, @PathVariable int quantity) {
        orderService.addProductToOrder(orderId, productId, quantity);
    }
}
