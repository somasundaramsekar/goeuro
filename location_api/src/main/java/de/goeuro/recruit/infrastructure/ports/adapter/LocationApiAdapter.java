package de.goeuro.recruit.infrastructure.ports.adapter;

import de.goeuro.recruit.read.model.Coordinate;
import de.goeuro.recruit.read.model.Location;

/**
 * Created by somasundaram.s on 24-06-2016.
 */
public class LocationApiAdapter {

    public static Location fromLocationResponse(LocationResponse locationResponse) {
        return new Location(String.valueOf(locationResponse.getId()), locationResponse.getName(), locationResponse.getType(),
                new Coordinate(
                        locationResponse.getGeoPosition().getLatitude(),
                        locationResponse.getGeoPosition().getLongitude()
                ));
    }
}
