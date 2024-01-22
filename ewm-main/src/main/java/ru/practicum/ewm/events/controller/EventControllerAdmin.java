package ru.practicum.ewm.events.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.events.dto.EventFullDto;
import ru.practicum.ewm.events.dto.EventUpdateAdminRequest;
import ru.practicum.ewm.events.service.EventService;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/admin/events")
@RequiredArgsConstructor
public class EventControllerAdmin {
    private final EventService eventService;

    @GetMapping
    public List<EventFullDto> getEvents(@RequestParam(name = "users", required = false) List<Long> users,
                                        @RequestParam(name = "states", required = false) List<String> states,
                                        @RequestParam(name = "categories", required = false) List<Long> categories,
                                        @RequestParam(name = "rangeStart", required = false)
                                        @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime startDate,
                                        @RequestParam(name = "rangeEnd", required = false)
                                        @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime endDate,
                                        @RequestParam(name = "from", defaultValue = "0") @PositiveOrZero Integer from,
                                        @RequestParam(name = "size", defaultValue = "10") @Positive Integer size) {
        return eventService.getAllEvents(users, states, categories, startDate, endDate, from, size);
    }

    @PatchMapping("/{eventId}")
    public EventFullDto updateEvent(@PathVariable(name = "eventId") Long eventId,
                                    @RequestBody @Valid EventUpdateAdminRequest request) {
        return eventService.updateEventById(eventId, request);
    }
}