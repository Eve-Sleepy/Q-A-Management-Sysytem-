package com.example.faq.services;

import com.example.faq.dto.ProductCreateDto;
import com.example.faq.dto.ProductEditDto;
import com.example.faq.models.Product;
import com.example.faq.persistences.ProductDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class ProductService {
    @Autowired
    private ProductDao productDao;

    public Product findProductById(Integer productId){
        Product product = productDao.findProductById(productId);
        return product;
    }

    public ArrayList<Product> findProducts(){
        ArrayList<Product> products = productDao.findProducts();
        return products;
    }

    public Integer addProduct(ProductCreateDto productCreateDto){
        Integer state = productDao.insertProduct(productCreateDto);
        return state;
    }

    public Integer modifyProduct(ProductEditDto productEditDto){
        Integer state = productDao.updateProduct(productEditDto);
        return state;
    }

    public Integer deleteProduct(Integer id){
        return productDao.deleteProduct(id);
    }
}
