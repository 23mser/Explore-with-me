package ru.practicum.ewm.compilatons.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.compilatons.dto.CompilationDto;
import ru.practicum.ewm.compilatons.dto.CompilationUpdateRequest;
import ru.practicum.ewm.compilatons.dto.NewCompilationDto;
import ru.practicum.ewm.compilatons.service.CompilationService;

import javax.validation.Valid;
import javax.validation.constraints.Positive;

@RestController
@RequestMapping("/admin/compilations")
@RequiredArgsConstructor
public class CompilationControllerAdmin {
    private final CompilationService compilationService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CompilationDto createCompilation(@RequestBody @Valid NewCompilationDto request) {
        return compilationService.createCompilation(request);
    }

    @PatchMapping("/{compId}")
    public CompilationDto updateCompilation(@PathVariable(name = "compId") @Positive Long compilationId,
                                            @RequestBody @Valid CompilationUpdateRequest request) {
        return compilationService.updateCompilationById(compilationId, request);
    }

    @DeleteMapping("/{compId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCompilation(@PathVariable(name = "compId") @Positive Long compilationId) {
        compilationService.deleteCompilationById(compilationId);
    }
}