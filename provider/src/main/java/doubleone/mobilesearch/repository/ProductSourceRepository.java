package doubleone.mobilesearch.repository;

import org.springframework.stereotype.Repository;

import doubleone.mobilesearch.entity.ProductSource;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
@Repository
public interface ProductSourceRepository extends CrudRepository<ProductSource, Integer>{

    List<ProductSource> findByCreateTimeBetween(Long start, Long end);
}