package ru.practicum.ewm.locations.dto;

import lombok.experimental.UtilityClass;
import ru.practicum.ewm.locations.model.Location;

@UtilityClass
public class LocationMapper {

    public Location toLocation(LocationDto locationDto) {
        return Location.builder()
                .lat(locationDto.getLat())
                .lon(locationDto.getLon())
                .build();
    }
}