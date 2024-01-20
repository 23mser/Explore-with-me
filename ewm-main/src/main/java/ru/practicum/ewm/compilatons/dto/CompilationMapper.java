package ru.practicum.ewm.compilatons.dto;

import lombok.experimental.UtilityClass;
import ru.practicum.ewm.compilatons.model.Compilation;
import ru.practicum.ewm.events.dto.EventMapper;

import java.util.List;
import java.util.stream.Collectors;

@UtilityClass
public class CompilationMapper {
    public CompilationDto toCompilationDto(Compilation compilation) {
        return CompilationDto.builder()
                .id(compilation.getId())
                .events(EventMapper.toEventShortDtoList(compilation.getEvents()))
                .pinned(compilation.getPinned())
                .title(compilation.getTitle())
                .build();
    }

    public Compilation toCompilation(NewCompilationDto dto) {
        return Compilation.builder()
                .pinned(dto.getPinned())
                .title(dto.getTitle())
                .events(List.of())
                .build();
    }

    public List<CompilationDto> toCompilationDtoList(List<Compilation> compilations) {
        return compilations.stream()
                .map(CompilationMapper::toCompilationDto)
                .collect(Collectors.toList());
    }
}