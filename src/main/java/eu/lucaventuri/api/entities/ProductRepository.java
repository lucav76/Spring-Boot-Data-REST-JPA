package eu.lucaventuri.api.entities;

import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface ProductRepository extends PagingAndSortingRepository<Product, Long> {
    List<Product> findByOrderByName();

    List<Product> findByNameContainingOrderByName(String name);

    List<Product> findByBrandContainingOrderByName(String brand);
}
