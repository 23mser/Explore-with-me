package ru.practicum.ewm.events.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.events.dto.*;
import ru.practicum.ewm.events.service.EventUserService;
import ru.practicum.ewm.requests.dto.RequestDto;
import ru.practicum.ewm.requests.service.RequestService;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class EventControllerPrivate {
    private final EventUserService eventService;
    private final RequestService requestService;

    @PostMapping("/{userId}/events")
    @ResponseStatus(HttpStatus.CREATED)
    public EventFullDto createEvent(@PathVariable(name = "userId") @Positive Long userId,
                                    @RequestBody @Valid NewEventDto request) {
        return eventService.createEvent(request, userId);
    }

    @GetMapping("/{userId}/events")
    public List<EventShortDto> getEvents(@PathVariable(name = "userId") @Positive Long userId,
                                         @RequestParam(name = "from", defaultValue = "0") @PositiveOrZero Integer from,
                                         @RequestParam(name = "size", defaultValue = "10") @Positive Integer size) {
        return eventService.getEventsByUserId(userId, from, size);
    }


    @GetMapping("/{userId}/events/{eventId}")
    public EventFullDto getEventByUserAndEvent(@PathVariable(name = "userId") @Positive Long userId,
                                               @PathVariable(name = "eventId") @Positive Long eventId) {
        return eventService.getEventByUserIdAndEventId(userId, eventId);
    }

    @PatchMapping("/{userId}/events/{eventId}")
    public EventFullDto updateEventByOwner(@PathVariable(name = "userId") @Positive Long userId,
                                           @PathVariable(name = "eventId") @Positive Long eventId,
                                           @RequestBody @Valid EventUpdateUserRequest request) {
        return eventService.updateEventByUserIdAndEventId(userId, eventId, request);
    }

    @GetMapping("/{userId}/events/{eventId}/requests")
    @ResponseStatus(HttpStatus.OK)
    public List<RequestDto> getRequestByEvent(@PathVariable(name = "userId") Long userId,
                                              @PathVariable(name = "eventId") Long eventId) {
        return requestService.getAllParticipationRequestsByEventId(userId, eventId);
    }

    @PatchMapping("/{userId}/events/{eventId}/requests")
    @ResponseStatus(HttpStatus.OK)
    public EventStatusUpdateResponse updateStatusRequest(@PathVariable(name = "userId") @Positive Long userId,
                                                         @PathVariable(name = "eventId") @Positive Long eventId,
                                                         @RequestBody EventStatusUpdateRequest request) {
        return requestService.updateStatusRequestByEventId(userId, eventId, request);
    }
}