package com.example.faq.persistences;

import com.example.faq.dto.ProductCreateDto;
import com.example.faq.dto.ProductEditDto;
import com.example.faq.models.Product;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public interface ProductDao {
    Product findProductById(Integer id);

    ArrayList<Product> findProducts();

    Integer insertProduct(ProductCreateDto productCreateDto);

    Integer updateProduct(ProductEditDto productEditDto);

    Integer deleteProduct(Integer productId);
}
