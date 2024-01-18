package ru.practicum.ewm.compilatons.model;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class CombinedCompilationEventKeyId implements Serializable {
    private Long compilationId;
    private Long eventId;
}
