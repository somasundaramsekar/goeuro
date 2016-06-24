package de.goeuro.recruit.application;

import de.goeuro.recruit.read.model.Location;
import de.goeuro.recruit.read.model.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by somasundaram.s on 24-06-2016.
 */
@Component
public class LocationApplicationService {

    private final LocationService locationService;

    @Autowired
    public LocationApplicationService(LocationService locationService) {
        this.locationService = locationService;
    }

    public List<Location> locationInfoByCityName(String cityName) {
        return locationService.cityInfo(cityName);
    }

}
