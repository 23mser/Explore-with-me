package ru.practicum.ewm.locations.dto;

import ru.practicum.ewm.locations.model.Location;

public class LocationMapper {

    private LocationMapper() {
    }

    public static Location toLocation(NewLocationDto newLocationDto) {
        Location location = new Location();

        location.setLat(newLocationDto.getLat());
        location.setLon(newLocationDto.getLon());

        return location;
    }

    public static LocationDto toCreatedLocationDto(Location location) {
        return new LocationDto(location.getId(), location.getLat(), location.getLon());
    }
}
