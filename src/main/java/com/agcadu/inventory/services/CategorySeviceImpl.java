package com.agcadu.inventory.services;

import com.agcadu.inventory.dao.ICategoryDao;
import com.agcadu.inventory.model.Category;
import com.agcadu.inventory.response.CategoryResponseRest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CategorySeviceImpl implements ICategoryService{

    @Autowired//Inyecta la dependencia de todos los metodos de la clase ICategoryDao como un new ICategoryDao()
    private ICategoryDao categoryDao;

    @Override
    @Transactional(readOnly = true)//Solo lectura de la base de datos
    public ResponseEntity<CategoryResponseRest> search() {

        CategoryResponseRest response = new CategoryResponseRest();

        try {

            List<Category> category = (List<Category>) categoryDao.findAll();

            response.getCategoryResponse().setCategory(category);
            response.setMetadata("Respuesta ok", "00", "Respuesta exitosa");


        } catch (Exception e) {

            response.setMetadata("Respuesta nok", "-1", "Error al consultar");
            e.getStackTrace();
            return new ResponseEntity<CategoryResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR);

        }

        return new ResponseEntity<CategoryResponseRest>(response, HttpStatus.OK);

    }
}
