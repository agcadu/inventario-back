package com.agcadu.inventory.services;

import com.agcadu.inventory.model.Product;
import com.agcadu.inventory.response.ProductResponseRest;
import org.springframework.http.ResponseEntity;

public interface IProductService {

    public ResponseEntity<ProductResponseRest> save(Product product, Long categoryId);
    public ResponseEntity<ProductResponseRest> searchById(Long id);
    public ResponseEntity<ProductResponseRest> searchByName(String name);
    public ResponseEntity<ProductResponseRest> deleteById(Long id);
    public ResponseEntity<ProductResponseRest> search();
    public ResponseEntity<ProductResponseRest> update(Long id,Long categoryId, Product product);




}
