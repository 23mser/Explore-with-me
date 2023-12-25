package ru.practicum.server.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.dto.CreatedHitDto;
import ru.practicum.dto.HitDto;
import ru.practicum.dto.StatDto;
import ru.practicum.server.service.StatService;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class StatController {

    private final StatService statService;

    @PostMapping("/hit")
    public CreatedHitDto createHitDto(@RequestBody @Valid HitDto hitDto) {
        return statService.createHitDto(hitDto);
    }

    @GetMapping("/stats")
    public List<StatDto> getStats(@RequestParam("start") LocalDateTime start,
                                  @RequestParam("end") LocalDateTime end,
                                  @RequestParam(value = "uris", required = false, defaultValue = "") List<String> uris,
                                  @RequestParam(value = "unique", required = false, defaultValue = "false") Boolean unique) {
        return statService.getStats(start, end, uris, unique);
    }
}