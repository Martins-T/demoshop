package com.example.demo.service;

import com.example.demo.dto.ProductPatchRequest;
import com.example.demo.exception.NotFoundException;
import com.example.demo.model.Product;
import com.example.demo.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository repository;

    public List<Product> getAllProducts() {
        return repository.findAll();
    }

    public Product getProductById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Produkts ar id " + id + " nav atrasts."));
    }

    public Integer getStock(Long id) {
        return repository.findById(id)
                .map(Product::getStock)
                .orElseThrow(() -> new NotFoundException("Produkts ar id " + id + " nav atrasts."));
    }

    public Product saveProduct(Product product) {
        return repository.save(product);
    }

    public Product patchProduct(Long id, ProductPatchRequest patch) {
        return repository.findById(id).map(existingProduct -> {
            if (patch.getName() != null) {
                existingProduct.setName(patch.getName());
            }
            if (patch.getDescription() != null) {
                existingProduct.setDescription(patch.getDescription());
            }
            if (patch.getPrice() != null) {
                existingProduct.setPrice(patch.getPrice());
            }
            if (patch.getStock() != null) {
                existingProduct.setStock(patch.getStock());
            }
            return repository.save(existingProduct);
        }).orElseThrow(() -> new NotFoundException("Produkts ar id " + id + " nav atrasts."));
    }

    public void deleteAllProducts() {
        repository.deleteAll();
    }

    public void deleteProduct(Long id) {
        if (!repository.existsById(id)) {
            throw new NotFoundException("Produkts ar id " + id + " nav atrasts.");
        }
        repository.deleteById(id);
    }
}
