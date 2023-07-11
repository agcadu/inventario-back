package com.agcadu.inventory.controller;

import com.agcadu.inventory.model.Product;
import com.agcadu.inventory.response.CategoryResponseRest;
import com.agcadu.inventory.response.ProductResponseRest;
import com.agcadu.inventory.services.IProductService;
import com.agcadu.inventory.util.CategoryExcelExporter;
import com.agcadu.inventory.util.ProductExcelExport;
import com.agcadu.inventory.util.Util;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@CrossOrigin(origins= {"http://localhost:4200"})
@RestController
@RequestMapping("/api/v1")
public class ProductRestController {

    private IProductService productService;
    public ProductRestController(IProductService productService) {
        this.productService = productService;
    }



    @PostMapping("/products")
    public ResponseEntity<ProductResponseRest> save(
            @RequestParam("picture")MultipartFile picture,
            @RequestParam("name")String name,
            @RequestParam("price")int price,
            @RequestParam("stock")int stock,
            @RequestParam("categoryId")Long categoryId) throws IOException
    {
        Product product = new Product();
        product.setName(name);
        product.setPrice(price);
        product.setStock(stock);
        product.setPicture(Util.compressZLib(picture.getBytes()));

        ResponseEntity<ProductResponseRest> response = productService.save(product, categoryId);


        return response;
    }

    @GetMapping("/products/{id}")
    public ResponseEntity<ProductResponseRest> searchById(@PathVariable Long id){
        ResponseEntity<ProductResponseRest> response = productService.searchById(id);
        return response;
    }

    @GetMapping("/products/filter/{name}")
    public ResponseEntity<ProductResponseRest> searchByName(@PathVariable String name){
        ResponseEntity<ProductResponseRest> response = productService.searchByName(name);
        return response;
    }

    @DeleteMapping("/products/{id}")
    public ResponseEntity<ProductResponseRest> deleteById(@PathVariable Long id){
        ResponseEntity<ProductResponseRest> response = productService.deleteById(id);
        return response;
    }

    @GetMapping("/products")
    public ResponseEntity<ProductResponseRest> search(){
        ResponseEntity<ProductResponseRest> response = productService.search();
        return response;
    }

    @PutMapping("/products/{id}")
    public ResponseEntity<ProductResponseRest> update(
            @PathVariable Long id,
            @RequestParam("categoryId") Long categoryId,
            @RequestParam("picture")MultipartFile picture,
            @RequestParam("name")String name,
            @RequestParam("price")int price,
            @RequestParam("stock")int stock) throws IOException
    {
        Product product = new Product();
        product.setName(name);
        product.setPrice(price);
        product.setStock(stock);
        product.setPicture(Util.compressZLib(picture.getBytes()));

        ResponseEntity<ProductResponseRest> response = productService.update(id, categoryId, product);

        return response;
    }

    @GetMapping("/products/export/excel")
    public void exportToExcel(HttpServletResponse response) throws IOException {
        response.setContentType("application/octet-stream");
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=productos.xlsx";
        response.setHeader(headerKey, headerValue);

        ResponseEntity<ProductResponseRest> productsResponse = productService.search();
        ProductExcelExport excelExporter = new ProductExcelExport(productsResponse.getBody().getProduct().getProducts());
        excelExporter.export(response);

    }
}
