package com.agcadu.inventory.services;
import com.agcadu.inventory.dao.ICategoryDao;
import com.agcadu.inventory.dao.IProductDao;
import com.agcadu.inventory.model.Category;
import com.agcadu.inventory.model.Product;
import com.agcadu.inventory.response.ProductResponseRest;

import com.agcadu.inventory.util.Util;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements IProductService{

    private ICategoryDao categoryDao;

    private IProductDao productDao;

    public ProductServiceImpl(ICategoryDao categoryDao, IProductDao productDao) {
        this.categoryDao = categoryDao;
        this.productDao = productDao;
    }

    @Override
    @Transactional
    public ResponseEntity<ProductResponseRest> save(Product product, Long categoryId) {

        ProductResponseRest response = new ProductResponseRest();
        List<Product> list = new ArrayList<>();

        try {
            //buscar categoria por id
            Optional<Category> category = categoryDao.findById(categoryId);

            if(category.isPresent()){
                product.setCategory(category.get());
            }else {
                response.setMetadata("Respuesta nok", "-1", "Categoria no encontrada");
                return new ResponseEntity<ProductResponseRest>(response, HttpStatus.NOT_FOUND);
            }

            //Salvamos producto
            Product productSave = productDao.save(product);

            if(productSave != null) {
                list.add(productSave);
                response.getProduct().setProducts(list);
                response.setMetadata("Respuesta ok", "00", "Producto guardado");
            }else {
                response.setMetadata("Respuesta nok", "-1", "Producto no guardado");
                return new ResponseEntity<ProductResponseRest>(response, HttpStatus.BAD_REQUEST);
            }

        } catch (Exception e) {
            e.printStackTrace();
            response.setMetadata("Respuesta nok", "-1", "Error al guardar producto");
            return new ResponseEntity<ProductResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<ProductResponseRest>(response, HttpStatus.OK);
    }

    @Override
    @Transactional(readOnly = true)
    public ResponseEntity<ProductResponseRest> searchById(Long id) {

        ProductResponseRest response = new ProductResponseRest();
        List<Product> list = new ArrayList<>();

        try {
            //buscar producto por id
            Optional<Product> product = productDao.findById(id);

            if(product.isPresent()){
                byte[] imageDecompress = Util.decompressZLib(product.get().getPicture());
                product.get().setPicture(imageDecompress);
                list.add(product.get());
                response.getProduct().setProducts(list);
                response.setMetadata("Respuesta ok", "00", "Producto encontrado");

            }else {
                response.setMetadata("Respuesta nok", "-1", "Producto no encontrado");
                return new ResponseEntity<ProductResponseRest>(response, HttpStatus.NOT_FOUND);
            }



        } catch (Exception e) {
            e.printStackTrace();
            response.setMetadata("Respuesta nok", "-1", "Error al buscar producto");
            return new ResponseEntity<ProductResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<ProductResponseRest>(response, HttpStatus.OK);
    }

    @Override
    @Transactional(readOnly = true)
    public ResponseEntity<ProductResponseRest> searchByName(String name) {
        ProductResponseRest response = new ProductResponseRest();
        List<Product> list = new ArrayList<>();
        List<Product> listAux = new ArrayList<>();

        try {
            //buscar producto por name
            listAux = productDao.findByNameContainingIgnoreCase(name);

            if(listAux.size() > 0){
                for (Product product : listAux) {
                    byte[] imageDecompress = Util.decompressZLib(product.getPicture());
                    product.setPicture(imageDecompress);
                    list.add(product);
                }

                response.getProduct().setProducts(list);
                response.setMetadata("Respuesta ok", "00", "Producto encontrado");

            }else {
                response.setMetadata("Respuesta nok", "-1", "Producto no encontrado");
                return new ResponseEntity<ProductResponseRest>(response, HttpStatus.NOT_FOUND);
            }



        } catch (Exception e) {
            e.printStackTrace();
            response.setMetadata("Respuesta nok", "-1", "Error al buscar producto");
            return new ResponseEntity<ProductResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<ProductResponseRest>(response, HttpStatus.OK);
    }

    @Override
    @Transactional
    public ResponseEntity<ProductResponseRest> deletyeById(Long id) {
        ProductResponseRest response = new ProductResponseRest();


        try {
            //borrar producto por id
            productDao.deleteById(id);
            response.setMetadata("Respuesta ok", "00", "Producto eliminado");


        } catch (Exception e) {
            e.printStackTrace();
            response.setMetadata("Respuesta nok", "-1", "Error al eliminar producto");
            return new ResponseEntity<ProductResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<ProductResponseRest>(response, HttpStatus.OK);
    }
}
