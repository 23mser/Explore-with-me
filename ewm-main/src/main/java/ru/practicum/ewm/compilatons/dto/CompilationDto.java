package ru.practicum.ewm.compilatons.dto;

import lombok.*;
import ru.practicum.ewm.events.dto.EventShortDto;

import java.util.List;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CompilationDto {
    private List<EventShortDto> events;
    private Long id;
    private Boolean pinned;
    private String title;
}