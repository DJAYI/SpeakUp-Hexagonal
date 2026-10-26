package com.electiva.speakup.infrastructure.adapter.out.persistence.memory.stubs;

import com.electiva.speakup.domain.model.Category;

import java.util.*;

public class CategoryMemoryStub {
    private final List<Category> categories = new ArrayList<>();

    public CategoryMemoryStub() {
    }

    public Category add(Category category) {
        category.setId((long) categories.size() + 1);
        categories.add(category);

        return categories.getLast();
    }

    public void remove(Category category) {
        categories.remove(category);
    }

    public void update(Long id, Category updatedCategory) {
        categories.stream()
                .filter(category -> category.getId().equals(id))
                .findFirst()
                .ifPresent(category -> {
                    category.setName(updatedCategory.getName());
                    category.setDescription(updatedCategory.getDescription());
                });
    }

    public void clear() {
        categories.clear();
    }

    public Optional<Category> findOne(Long id) {
        return categories.stream()
                .filter(category -> category.getId().equals(id))
                .findFirst();
    }

    public Collection<Category> readAll() {
        return Collections.unmodifiableCollection(categories);
    }
}
