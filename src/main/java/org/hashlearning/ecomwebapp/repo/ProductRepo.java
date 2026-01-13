package org.hashlearning.ecomwebapp.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import org.hashlearning.ecomwebapp.model.Product;

@Repository
public interface ProductRepo extends JpaRepository<Product, Integer>{
}
