package com.agcadu.inventory.services;

import com.agcadu.inventory.dao.ICategoryDao;
import com.agcadu.inventory.model.Category;
import com.agcadu.inventory.response.CategoryResponseRest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    @Override
    @Transactional(readOnly = true)
    public ResponseEntity<CategoryResponseRest> searchById(Long id) {

        CategoryResponseRest response = new CategoryResponseRest();
        List<Category> list = new ArrayList<>();

        try{

            Optional<Category> category = categoryDao.findById(id);

            if (category.isPresent()) {

                list.add(category.get());
                response.getCategoryResponse().setCategory(list);
                response.setMetadata("Respuesta ok", "00", "Respuesta exitosa");

            } else {

                response.setMetadata("Respuesta nok", "-1", "No se encontro el id");
                return new ResponseEntity<CategoryResponseRest>(response, HttpStatus.NOT_FOUND);

            }


        } catch (Exception e) {

            response.setMetadata("Respuesta nok", "-1", "Error al consultar por id");
            e.getStackTrace();
            return new ResponseEntity<CategoryResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR);

        }

        return new ResponseEntity<CategoryResponseRest>(response, HttpStatus.OK);
        }

    @Override
    @Transactional
    public ResponseEntity<CategoryResponseRest> save(Category category) {

        CategoryResponseRest response = new CategoryResponseRest();
        List<Category> list = new ArrayList<>();

        try{

            Category categorySave = categoryDao.save(category);

            if (categorySave != null) {

                list.add(categorySave);
                response.getCategoryResponse().setCategory(list);
                response.setMetadata("Respuesta ok", "00", "Respuesta exitosa");

            } else {

                response.setMetadata("Respuesta nok", "-1", "No se pudo grabar");
                return new ResponseEntity<CategoryResponseRest>(response, HttpStatus.BAD_REQUEST);

            }


        } catch (Exception e) {

            response.setMetadata("Respuesta nok", "-1", "Error al grabar por categoria");
            e.getStackTrace();
            return new ResponseEntity<CategoryResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR);

        }

        return new ResponseEntity<CategoryResponseRest>(response, HttpStatus.OK);
    }

    @Override
    @Transactional
    public ResponseEntity<CategoryResponseRest> update(Category category, Long id) {

            CategoryResponseRest response = new CategoryResponseRest();
            List<Category> list = new ArrayList<>();

            try{

                Optional<Category> categorySearch = categoryDao.findById(id);

                if (categorySearch.isPresent()) {

                    categorySearch.get().setName(category.getName());
                    categorySearch.get().setDescription(category.getDescription());
                    Category categoryUpdate = categoryDao.save(categorySearch.get());

                    if (categoryUpdate != null) {

                        list.add(categoryUpdate);
                        response.getCategoryResponse().setCategory(list);
                        response.setMetadata("Respuesta ok", "00", "categoria actualizada");

                    } else {

                        response.setMetadata("Respuesta nok", "-1", "categoria no actualizada");
                        return new ResponseEntity<CategoryResponseRest>(response, HttpStatus.BAD_REQUEST);

                    }

                } else {

                    response.setMetadata("Respuesta nok", "-1", "Categoria no encontrada");
                    return new ResponseEntity<CategoryResponseRest>(response, HttpStatus.NOT_FOUND);

                }
    }

    catch (Exception e) {

        response.setMetadata("Respuesta nok", "-1", "Error al grabar por categoria");
        e.getStackTrace();
        return new ResponseEntity<CategoryResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR);

    }

            return new ResponseEntity<CategoryResponseRest>(response, HttpStatus.OK);
    }

}

