package eu.lucaventuri.api;

import eu.lucaventuri.api.entities.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepo;
    @Autowired
    private OrderItemRepository orderItemRepo;
    @Autowired
    private ProductRepository productRepository;

    public Order createOrder() {
        var order = new Order();

        order.setUser("TestUser");

        return orderRepo.save(order);
    }

    public Optional<Order> getOrder(long orderId) {
        return orderRepo.findById(orderId);
    }

    public void addProductToOrder(long orderId, long productId, int quantity) {
        // FIXME: inefficient
        var order = orderRepo.findById(orderId).orElseThrow();
        var product = productRepository.findById(productId).orElseThrow();
        var id = new OrderItemId(orderId, productId);
        var orderItem = new OrderItem(id, product, order, quantity);
        order.getOrderItems().removeIf(item -> item.getId().equals(id));
        order.getOrderItems().add(orderItem);

        orderRepo.save(order);
    }

    public List<Order> getOrders(String user) {
        var list = new ArrayList<Order>();

        for (var order : orderRepo.findByUser(user))
            list.add(order);

        return list;
    }

    public List<Order> getAllOrders() {
        var list = new ArrayList<Order>();

        for (var order : orderRepo.findAll())
            list.add(order);

        return list;
    }
}
