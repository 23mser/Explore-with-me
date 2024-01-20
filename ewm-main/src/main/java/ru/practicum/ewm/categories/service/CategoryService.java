package ru.practicum.ewm.categories.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.categories.dto.CategoryDto;
import ru.practicum.ewm.categories.dto.CategoryMapper;
import ru.practicum.ewm.categories.model.Category;
import ru.practicum.ewm.categories.repostiory.CategoryRepository;
import ru.practicum.ewm.exceptions.NotFoundException;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryDto createCategory(CategoryDto categoryDto) {
        Category savedCategory = categoryRepository.save(CategoryMapper.toCategory(categoryDto));
        return CategoryMapper.toCategoryDto(savedCategory);
    }

    @Transactional
    public CategoryDto updateCategoryById(Long categoryId, CategoryDto request) {
        Category category = getCategoryByIdIfExist(categoryId);
        category.setName(request.getName());
        return CategoryMapper.toCategoryDto(categoryRepository.save(category));
    }

    public void deleteCategoryById(Long categoryId) {
        Category category = getCategoryByIdIfExist(categoryId);
        categoryRepository.delete(category);
    }

    public List<CategoryDto> getCategories(Integer from, Integer size) {
        List<Category> categories = categoryRepository.findAll(PageRequest.of(from, size)).getContent();
        return CategoryMapper.toCategoryDtosList(categories);
    }

    public CategoryDto getCategoryById(Long categoryId) {
        Category category = getCategoryByIdIfExist(categoryId);
        return CategoryMapper.toCategoryDto(category);
    }

    public Category getCategoryByIdIfExist(Long categoryId) {
        return categoryRepository.findById(categoryId).orElseThrow(() -> new NotFoundException("Категория не найдена"));
    }
}