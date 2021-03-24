package eu.lucaventuri.api.entities;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(exported = false)
public interface OrderItemRepository extends PagingAndSortingRepository<OrderItem, Long> {
}
