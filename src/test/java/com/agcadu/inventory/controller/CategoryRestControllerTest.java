package com.agcadu.inventory.controller;

import com.agcadu.inventory.model.Category;
import com.agcadu.inventory.response.CategoryResponseRest;
import com.agcadu.inventory.services.ICategoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class CategoryRestControllerTest {

    @InjectMocks
    CategoryRestController categoryRestController;

    @Mock
    private ICategoryService categoryService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void searchCategories() {
        CategoryResponseRest expectedResponse = new CategoryResponseRest();

        when(categoryService.search()).thenReturn(new ResponseEntity<>(expectedResponse, HttpStatus.OK));

        ResponseEntity<CategoryResponseRest> response = categoryRestController.searchCategories();

        assertSame(HttpStatus.OK, response.getStatusCode());
        assertSame(expectedResponse, response.getBody());
    }


    @Test
    void searchCategoriesById() {
        Long categoryId = 1L;
        CategoryResponseRest expectedResponse = new CategoryResponseRest();

        when(categoryService.searchById(categoryId)).thenReturn(new ResponseEntity<>(expectedResponse, HttpStatus.OK));

        ResponseEntity<CategoryResponseRest> response = categoryRestController.searchCategoriesById(categoryId);

        assertSame(HttpStatus.OK, response.getStatusCode());
        assertSame(expectedResponse, response.getBody());
    }


    @Test
    void save() {

        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        Category category = new Category();
        category.setId(Long.valueOf(1));
        category.setName("Lacteos");
        category.setDescription("Productos lacteos");

        when(categoryService.save(any(Category.class))).thenReturn(new ResponseEntity< CategoryResponseRest >(HttpStatus.OK));

        ResponseEntity< CategoryResponseRest > response = categoryRestController.save(category);

        assert(response.getStatusCodeValue() == 200);

    }

    @Test
    void updateCategory() {
        Long categoryId = 1L;
        Category category = new Category();
        when(categoryService.update(eq(category), eq(categoryId)))
                .thenReturn(new ResponseEntity<>(HttpStatus.OK));

        ResponseEntity<CategoryResponseRest> response = categoryRestController.update(category, categoryId);

        assertSame(HttpStatus.OK, response.getStatusCode());
    }


    @Test
    void deleteCategory() {
        Long categoryId = 1L;
        when(categoryService.deleteById(categoryId))
                .thenReturn(new ResponseEntity<>(HttpStatus.OK));

        ResponseEntity<CategoryResponseRest> response = categoryRestController.delete(categoryId);

        assertSame(HttpStatus.OK, response.getStatusCode());
    }


}