package com.testeiteam.saw_products_api.config;

import com.testeiteam.saw_products_api.model.Category;
import com.testeiteam.saw_products_api.model.User;
import com.testeiteam.saw_products_api.repository.CategoryRepository;
import com.testeiteam.saw_products_api.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.OffsetDateTime;
import java.util.Arrays;
import java.util.List;

@Configuration
public class DataSeeder {

    @Bean
    public CommandLineRunner seedData(CategoryRepository categoryRepository,
                                      UserRepository userRepository,
                                      PasswordEncoder passwordEncoder) {
        return args -> {
            if (categoryRepository.count() == 0) {
                List<Category> categories = Arrays.asList(
                        new Category(null, "Electronics", OffsetDateTime.now(), OffsetDateTime.now()),
                        new Category(null, "Clothes", OffsetDateTime.now(), OffsetDateTime.now()),
                        new Category(null, "Food", OffsetDateTime.now(), OffsetDateTime.now())
                );
                categoryRepository.saveAll(categories);
                System.out.println("Seeded Categories: " + categories);
            }

            if (userRepository.count() == 0) {
                User admin = new User();
                admin.setName("Admin User");
                admin.setEmail("admin@domain.com");
                admin.setPassword(passwordEncoder.encode("adminpassword"));
                admin.setRole("admin");
                admin.setIsActive(true);
                admin.setCreatedAt(OffsetDateTime.now());
                admin.setUpdatedAt(OffsetDateTime.now());

                User normalUser = new User();
                normalUser.setName("Normal User");
                normalUser.setEmail("user@domain.com");
                normalUser.setPassword(passwordEncoder.encode("userpassword"));
                normalUser.setRole("user");
                normalUser.setIsActive(true);
                normalUser.setCreatedAt(OffsetDateTime.now());
                normalUser.setUpdatedAt(OffsetDateTime.now());

                userRepository.saveAll(Arrays.asList(admin, normalUser));
                System.out.println("Seeded Users: " + Arrays.asList(admin, normalUser));
            }
        };
    }
}
