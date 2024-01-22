package ru.practicum.ewm.requests.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.requests.dto.RequestDto;
import ru.practicum.ewm.requests.service.RequestService;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class RequestController {
    private final RequestService requestService;

    @PostMapping("/{userId}/requests")
    @ResponseStatus(HttpStatus.CREATED)
    public RequestDto createRequest(@PathVariable Long userId,
                                    @RequestParam Long eventId) {
        return requestService.createRequest(userId, eventId);
    }

    @GetMapping("/{userId}/requests")
    public List<RequestDto> getAllRequests(@PathVariable(name = "userId") Long userId) {
        return requestService.getAllRequestsByUserId(userId);
    }

    @PatchMapping("/{userId}/requests/{requestId}/cancel")
    public RequestDto cancelledRequest(@PathVariable(name = "userId") Long userId,
                                       @PathVariable(name = "requestId") Long requestId) {
        return requestService.cancelledRequestById(userId, requestId);
    }
}