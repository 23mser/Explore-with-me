package ru.practicum.ewm.compilatons.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.compilatons.dto.CompilationDto;
import ru.practicum.ewm.compilatons.dto.NewCompilationDto;
import ru.practicum.ewm.compilatons.service.CompilationService;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class CompilationControllerAdmin {

    private final CompilationService compilationService;

    @PostMapping("/admin/compilations")
    @ResponseStatus(HttpStatus.CREATED)
    public CompilationDto create(@RequestBody @Valid NewCompilationDto newCompilationDto) {
        return compilationService.create(newCompilationDto);
    }

    @PatchMapping("/admin/compilations/{compId}")
    public CompilationDto update(@PathVariable("compId") Long compId,
                                 @RequestBody NewCompilationDto newCompilationDto) {
        return compilationService.update(compId, newCompilationDto);
    }

    @DeleteMapping("/admin/compilations/{compId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("compId") Long compId) {
        compilationService.delete(compId);
    }
}
