package ru.practicum.ewm.events.dto;

import lombok.*;
import ru.practicum.ewm.requests.dto.RequestDto;

import java.util.List;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EventStatusUpdateResponse {
    private List<RequestDto> confirmedRequests;
    private List<RequestDto> rejectedRequests;
}