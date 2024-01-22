package ru.practicum.ewm.categories.dto;

import lombok.experimental.UtilityClass;
import ru.practicum.ewm.categories.model.Category;
import ru.practicum.ewm.exceptions.IncorrectRequestException;

import java.util.List;
import java.util.stream.Collectors;

@UtilityClass
public class CategoryMapper {

    public Category toCategory(CategoryDto categoryDto) {
        if (categoryDto.getName() == null) {
            throw new IncorrectRequestException("Имя не может быть пустым.");
        }
        return Category.builder()
                .name(categoryDto.getName())
                .build();
    }

    public CategoryDto toCategoryDto(Category category) {
        if (category.getName() == null || category.getId() == null) {
            throw new IncorrectRequestException("Имя/id не может быть пустым.");
        }
        return CategoryDto.builder()
                .id(category.getId())
                .name(category.getName())
                .build();
    }

    public List<CategoryDto> toCategoryDtosList(List<Category> categories) {
        return categories.stream()
                .map(CategoryMapper::toCategoryDto)
                .collect(Collectors.toList());
    }
}
