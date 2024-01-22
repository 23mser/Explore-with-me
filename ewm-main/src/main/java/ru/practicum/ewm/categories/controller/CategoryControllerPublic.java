package ru.practicum.ewm.categories.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.categories.dto.CategoryDto;
import ru.practicum.ewm.categories.service.CategoryService;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
public class CategoryControllerPublic {
    private final CategoryService categoryService;

    @GetMapping
    public List<CategoryDto> getCategories(@RequestParam(name = "from", defaultValue = "0") @PositiveOrZero Integer from,
                                           @RequestParam(name = "size", defaultValue = "10") @Positive Integer size) {
        return categoryService.getCategories(from, size);
    }

    @GetMapping("/{catId}")
    public CategoryDto getCategoryById(@PathVariable(name = "catId") @Positive Long categoryId) {
        return categoryService.getCategoryById(categoryId);
    }
}
