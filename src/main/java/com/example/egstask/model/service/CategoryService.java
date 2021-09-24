package com.example.egstask.model.service;

import com.example.egstask.exception.BadRequestException;
import com.example.egstask.exception.NotFoundException;
import com.example.egstask.model.entity.Category;
import com.example.egstask.model.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.example.egstask.common.ErrorMessages.*;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public Category save(Category category) {
        return categoryRepository.save(category);
    }

    public Category findById(Long id) {
        Optional<Category> optional = categoryRepository.findById(id);
        if (optional.isPresent())
            return optional.get();
        throw new NotFoundException(NOT_FOUND);
    }

    public Category update(Long id, Category categoryReq) {
        Category category = findById(id);
        category.setName(categoryReq.getName());
        return categoryRepository.save(category);
    }

    public void delete(Long id) {
        Category category = findById(id);
        if (category.getProducts() == null || category.getProducts().size() == 0)
            categoryRepository.deleteById(id);
        throw new BadRequestException(CATEGORY_NOT_EMPTY);
    }

}
