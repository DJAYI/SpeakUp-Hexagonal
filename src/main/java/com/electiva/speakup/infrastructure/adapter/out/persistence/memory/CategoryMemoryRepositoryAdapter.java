package com.electiva.speakup.infrastructure.adapter.out.persistence.memory;

import com.electiva.speakup.application.port.out.CategoryRepositoryPort;
import com.electiva.speakup.domain.model.Category;
import com.electiva.speakup.infrastructure.adapter.out.persistence.memory.stubs.CategoryMemoryStub;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class CategoryMemoryRepositoryAdapter implements CategoryRepositoryPort {

    private final CategoryMemoryStub categoryMemoryStub;

    public CategoryMemoryRepositoryAdapter() {
        this.categoryMemoryStub = new CategoryMemoryStub();
    }

    @Override
    public Category save(Category category) {

        return categoryMemoryStub.add(category);
    }

    @Override
    public Optional<Category> findById(Long id) {

        return categoryMemoryStub.findOne(id);
    }

    @Override
    public List<Category> findAll() {
        return new ArrayList<>(categoryMemoryStub.readAll());
    }

    @Override
    public Optional<Category> findByName(String name) {
        return categoryMemoryStub.readAll().stream().filter(category -> category.getName().equals(name)).findFirst();
    }
}
