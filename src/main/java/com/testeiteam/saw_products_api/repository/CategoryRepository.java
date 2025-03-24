package com.testeiteam.saw_products_api.repository;

import com.testeiteam.saw_products_api.model.Category;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CategoryRepository extends MongoRepository<Category, String> {
}
