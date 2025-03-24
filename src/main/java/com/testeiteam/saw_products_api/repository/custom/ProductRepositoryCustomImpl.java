package com.testeiteam.saw_products_api.repository.custom;

import com.testeiteam.saw_products_api.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.*;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public class ProductRepositoryCustomImpl implements ProductRepositoryCustom {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public Page<Product> findAllWithFilters(String category,
                                            BigDecimal minPrice,
                                            BigDecimal maxPrice,
                                            Pageable pageable) {

        Query query = new Query().with(pageable);

        if (category != null && !category.isBlank()) {
            query.addCriteria(Criteria.where("category").is(category));
        }

        if (minPrice != null) {
            query.addCriteria(Criteria.where("price").gte(minPrice));
        }

        if (maxPrice != null) {
            query.addCriteria(Criteria.where("price").lte(maxPrice));
        }

        List<Product> products = mongoTemplate.find(query, Product.class);

        long count = mongoTemplate.count(Query.of(query).limit(-1).skip(-1), Product.class);

        return new PageImpl<>(products, pageable, count);
    }
}
