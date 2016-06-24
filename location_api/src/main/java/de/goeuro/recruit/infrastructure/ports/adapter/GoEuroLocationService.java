package de.goeuro.recruit.infrastructure.ports.adapter;

import com.sun.jersey.api.client.WebResource;
import de.goeuro.recruit.read.model.Location;
import de.goeuro.recruit.read.model.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by somasundaram.s on 24-06-2016.
 */
@Service
public class GoEuroLocationService implements LocationService {

    @Override
    public List<Location> cityInfo(String cityName) {
        if (StringUtils.isEmpty(cityName))
            throw new IllegalArgumentException("Specified city name is incorrect");


        return Stream.of(LocationClient.INSTANCE.positionWithCityName(cityName))
                .map(
                        LocationApiAdapter::fromLocationResponse
                )
                .collect(Collectors.toList());
    }
}
