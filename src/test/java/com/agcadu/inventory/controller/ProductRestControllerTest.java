package com.agcadu.inventory.controller;

import com.agcadu.inventory.model.Product;
import com.agcadu.inventory.response.ProductResponseRest;
import com.agcadu.inventory.services.IProductService;
import com.agcadu.inventory.util.Util;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class ProductRestControllerTest {

    @InjectMocks
    ProductRestController productRestController;

    @Mock
    private IProductService productService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void save() throws IOException {

        MultipartFile picture = new MockMultipartFile("picture", "picture.jpg", "image/jpeg", "picture".getBytes());
        Product product = new Product();
        product.setName("product");
        product.setPrice(100);
        product.setStock(10);
        product.setPicture(Util.compressZLib(picture.getBytes()));

        ResponseEntity<ProductResponseRest> response = new ResponseEntity<>(HttpStatus.OK);
        when(productService.save(eq(product), eq(1L))).thenReturn(response);

        ResponseEntity<ProductResponseRest> responseEntity = productRestController.save(picture, "product", 100, 10, 1L);
        assertEquals(responseEntity, response);
    }


    @Test
    void searchByIdTest() {

        Long productId = 1L;
        ProductResponseRest expectedResponse = new ProductResponseRest();

        when(productService.searchById(productId)).thenReturn(new ResponseEntity<>(expectedResponse, HttpStatus.OK));

        ResponseEntity<ProductResponseRest> response = productRestController.searchById(productId);

        assertSame(HttpStatus.OK, response.getStatusCode());
        assertSame(expectedResponse, response.getBody());


    }

    @Test
    void searchByName() {

        String productName = "product";
        ProductResponseRest expectedResponse = new ProductResponseRest();

        when(productService.searchByName(productName)).thenReturn(new ResponseEntity<>(expectedResponse, HttpStatus.OK));

        ResponseEntity<ProductResponseRest> response = productRestController.searchByName(productName);

        assertSame(HttpStatus.OK, response.getStatusCode());
        assertSame(expectedResponse, response.getBody());
    }

    @Test
    void deleteById() {

        Long productId = 1L;
        ProductResponseRest expectedResponse = new ProductResponseRest();

        when(productService.deleteById(productId)).thenReturn(new ResponseEntity<>(expectedResponse, HttpStatus.OK));

        ResponseEntity<ProductResponseRest> response = productRestController.deleteById(productId);

        assertSame(HttpStatus.OK, response.getStatusCode());
        assertSame(expectedResponse, response.getBody());
    }

    @Test
    void search() {

        ProductResponseRest expectedResponse = new ProductResponseRest();

        when(productService.search()).thenReturn(new ResponseEntity<>(expectedResponse, HttpStatus.OK));

        ResponseEntity<ProductResponseRest> response = productRestController.search();

        assertSame(HttpStatus.OK, response.getStatusCode());
        assertSame(expectedResponse, response.getBody());
    }

    @Test
    void updateTest() throws IOException {

        MultipartFile picture = new MockMultipartFile("picture", "picture.jpg", "image/jpeg", "picture".getBytes());

        Product product = new Product();
        product.setName("product");
        product.setPrice(100);
        product.setStock(10);
        product.setPicture(Util.compressZLib(picture.getBytes()));

        ResponseEntity<ProductResponseRest> response = new ResponseEntity<>(HttpStatus.OK);

        when(productService.update(eq(1L), eq(1L), eq(product))).thenReturn(response);

        ResponseEntity<ProductResponseRest> responseEntity = productRestController.update(1L, 1L, picture, "product", 100, 10);

        assertEquals(responseEntity, response);
    }

}