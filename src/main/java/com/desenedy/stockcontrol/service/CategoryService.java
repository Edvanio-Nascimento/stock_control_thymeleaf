package com.desenedy.stockcontrol.service;

import com.desenedy.stockcontrol.domain.dto.category.CategoryRequest;
import com.desenedy.stockcontrol.domain.dto.category.CategoryResponse;
import com.desenedy.stockcontrol.domain.dto.category.CategoryUpdate;
import com.desenedy.stockcontrol.domain.entity.Category;
import com.desenedy.stockcontrol.domain.mapper.CategoryMapper;
import com.desenedy.stockcontrol.repository.CategoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class CategoryService {

    private final CategoryRepository repository;

    public CategoryService(CategoryRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public void create(CategoryRequest request) {

        if (repository.existsByNameIgnoreCase(request.name())) {
            throw new RuntimeException("Category with this name: " + request.name() + " already exists.");
        }

        Category category = CategoryMapper.toEntity(request);
        category.setActive(Boolean.TRUE);
        repository.save(category);

    }

    @Transactional(readOnly = true)
    public List<CategoryResponse> getAll(String name, boolean active) {

        List<Category> categories;

        if (StringUtils.hasText(name)) {

            categories = active
                    ? repository.findByNameContainingIgnoreCaseAndActiveTrue(name)
                    : repository.findByNameContainingIgnoreCaseAndActiveFalse(name);

        } else {

            categories = active
                    ? repository.findByActiveTrue()
                    : repository.findByActiveFalse();
        }

        return categories
                .stream()
                .map(CategoryMapper::toResponse)
                .toList();

    }

    @Transactional(readOnly = true)
    public CategoryResponse getById(Long id) {

        Category category = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found with that id: " + id));

        return CategoryMapper.toResponse(category);

    }

    @Transactional
    public void delete(Long id) {

        if (!repository.existsById(id)) {
            throw new RuntimeException("Category not found with that id: " + id);
        }

        repository.deleteById(id);

    }

    @Transactional
    public void update(Long id, CategoryUpdate update) {

        Category category = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found with that id: " + id));

        if (!category.getActive()) {
            throw new RuntimeException("The category cannot be edited because it is inactive");
        }

        category.setName(update.name());
        category.setDescription(update.description());

        repository.save(category);

    }

    @Transactional
    public void changeStatus(Long id, boolean active) {

        Category category = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found with that id: " + id));


        category.setActive(active);

        repository.save(category);

    }

}
