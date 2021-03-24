package eu.lucaventuri.api.entities;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource(exported = false)
public interface OrderRepository extends PagingAndSortingRepository<Order, Long> {
    List<Order> findByUser(String user);
}
