package com.agcadu.inventory.response;

import com.agcadu.inventory.model.Product;
import lombok.Data;

import java.util.List;

@Data
public class ProductResponse {

    List<Product> products;

}
