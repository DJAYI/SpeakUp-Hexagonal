package com.electiva.speakup.application.service;

import com.electiva.speakup.application.port.in.CreateCategoryUseCase;
import com.electiva.speakup.application.port.in.ListCategoriesUseCase;
import com.electiva.speakup.application.port.out.CategoryRepositoryPort;
import com.electiva.speakup.domain.model.Category;
import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class CategoryService implements CreateCategoryUseCase, ListCategoriesUseCase {
    private final CategoryRepositoryPort categoryRepositoryPort;

    public CategoryService(CategoryRepositoryPort categoryRepositoryPort) {
        this.categoryRepositoryPort = categoryRepositoryPort;
    }

    @Override
    public void create(String name, String description) {
        if (name == null || name.isEmpty() || description == null || description.isEmpty()) {
            throw new IllegalArgumentException("Name and description cannot be null or empty");
        }

        if (categoryRepositoryPort.findByName(name).isPresent()) {
            throw new IllegalArgumentException("Category already exists");
        }

        Category category = Category.builder()
                .name(name)
                .description(description)
                .build();

        var saved = categoryRepositoryPort.save(category);

        log.info("Category created: {}", saved);
    }

    @Override
    public List<Category> list() {
        return categoryRepositoryPort.findAll();
    }
}
