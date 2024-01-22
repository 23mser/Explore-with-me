package ru.practicum.server.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.dto.HitDto;
import ru.practicum.dto.StatDto;
import ru.practicum.server.service.StatService;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class StatController {
    private final StatService service;

    @PostMapping("/hit")
    @ResponseStatus(HttpStatus.CREATED)
    public void addHit(@RequestBody HitDto hitDto) {
        service.saveRecord(hitDto);
    }

    @GetMapping("/stats")
    public List<StatDto> getStats(@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime start,
                                  @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime end,
                                  @RequestParam(required = false) List<String> uris,
                                  @RequestParam(defaultValue = "false") boolean unique) {

        return service.getAllStats(start, end, uris, unique);
    }
}