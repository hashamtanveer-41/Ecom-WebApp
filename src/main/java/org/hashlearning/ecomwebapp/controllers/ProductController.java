package org.hashlearning.ecomwebapp.controllers;

import org.hashlearning.ecomwebapp.model.Product;
import org.hashlearning.ecomwebapp.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class ProductController {

    @Autowired
    private ProductService service;

    @GetMapping("/products")
    public ResponseEntity<List<Product>> getProducts(){
        return new ResponseEntity<>(service.getAllProducts(), HttpStatus.OK);
    }

    @GetMapping("/product/{id}")
    public  ResponseEntity<Product> getProduct(@PathVariable int id){
        Product product= service.getProductById(id);
        if (product.getId()>0){
            return new ResponseEntity<>(product, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/product")
    public ResponseEntity<?> addProduct(@RequestPart Product product, @RequestPart MultipartFile imageFile){
        Product product1 = null;
        try {
            product1 = service.addOrUpdateProduct(product, imageFile);
            return new ResponseEntity<>(product1, HttpStatus.CREATED);
        } catch (IOException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/product/{imageId}/image")
    public ResponseEntity<byte[]> getImageDataById(@PathVariable int imageId){
        Product product = service.getImageDataById(imageId);
        return new ResponseEntity<>(product.getImageData(), HttpStatus.OK);
    }

    @PutMapping("/product/{imageId}")
    public ResponseEntity<?> updateProductById(@PathVariable int imageId, @RequestPart Product product, @RequestPart MultipartFile imageFile){
        Product product1 = null;
        try {
            product1 = service.addOrUpdateProduct(product, imageFile);
            return new ResponseEntity<>(product1, HttpStatus.CREATED);
        } catch (IOException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/product/{imageId}")
    public  ResponseEntity<String> deleteProductById(@PathVariable int imageId){
        Product product = service.getProductById(imageId);
        if (product!=null){
            service.deleteProductById(imageId);
            return new ResponseEntity<>("Deleted", HttpStatus.OK);
        }else return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/product/search")
    public ResponseEntity<List<Product>> searchByKeyWord(@RequestParam String keyword){
        List<Product> products = service.getAllProductsByKeyword(keyword);
        System.out.println("Searching with "+ keyword);
        return new ResponseEntity<>(products, HttpStatus.FOUND);
    }

}
