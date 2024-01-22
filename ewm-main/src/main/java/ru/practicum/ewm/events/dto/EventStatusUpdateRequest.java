package ru.practicum.ewm.events.dto;

import lombok.*;
import ru.practicum.ewm.requests.model.RequestStatus;

import java.util.Set;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EventStatusUpdateRequest {
    private Set<Long> requestIds;
    private RequestStatus status;
}