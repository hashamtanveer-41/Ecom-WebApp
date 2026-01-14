package org.hashlearning.ecomwebapp.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import org.hashlearning.ecomwebapp.model.Product;
import java.util.List;

@Repository
public interface ProductRepo extends JpaRepository<Product, Integer>{
    Product findImageDataById(int imageId);


    @Query("SELECT p FROM Product p WHERE " +
            "(:keyword IS NOT NULL AND :keyword <> '' AND (" +
            "LOWER(p.name) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(p.description) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(p.brand) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(p.category) LIKE LOWER(CONCAT('%', :keyword, '%'))" +
            "))")
    List<Product> searchProduct(@Param("keyword")String keyword);
}
