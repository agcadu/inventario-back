package com.agcadu.inventory.dao;

import com.agcadu.inventory.model.Category;
import org.springframework.data.repository.CrudRepository;

public interface ICategoryDao extends CrudRepository<Category, Long> {
}
