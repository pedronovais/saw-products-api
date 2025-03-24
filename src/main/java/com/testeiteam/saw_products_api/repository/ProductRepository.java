package com.testeiteam.saw_products_api.repository;

import com.testeiteam.saw_products_api.model.Product;
import com.testeiteam.saw_products_api.repository.custom.ProductRepositoryCustom;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProductRepository extends MongoRepository<Product, String>, ProductRepositoryCustom {

}
