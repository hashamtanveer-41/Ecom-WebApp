package org.hashlearning.ecomwebapp.service;

import jakarta.transaction.Transactional;
import org.hashlearning.ecomwebapp.model.Product;
import org.hashlearning.ecomwebapp.repo.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepo repo;
    public List<Product> getAllProducts() {
        return repo.findAll();
    }

    public Product getProductById(int id) {
        return repo.findById(id).orElse(new Product(-1));
    }

    public Product addOrUpdateProduct(Product data, MultipartFile image) throws IOException {
        data.setImageName(image.getOriginalFilename());
        data.setImageType(image.getContentType());
            data.setImageData(image.getBytes());

        return repo.save(data);
    }

    public Product getImageDataById(int imageId) {
        return repo.findImageDataById(imageId);
    }

    public Object deleteProductById(int imageId) {
        repo.deleteById(imageId);
        return repo;
    }

    public List<Product> getAllProductsByKeyword(String keyword) {
        return repo.searchProduct(keyword);
    }
}
