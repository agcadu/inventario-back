package com.agcadu.inventory.response;

import com.agcadu.inventory.model.Category;
import lombok.Data;

import java.util.List;

@Data
public class CategoryResponse {

    private List<Category> category;

}
