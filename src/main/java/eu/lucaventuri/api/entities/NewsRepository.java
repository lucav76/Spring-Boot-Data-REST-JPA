package eu.lucaventuri.api.entities;

import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface NewsRepository extends PagingAndSortingRepository<News, Long> {
    List<News> findByOrderByDateDesc();
}
