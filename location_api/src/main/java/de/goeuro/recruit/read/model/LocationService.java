package de.goeuro.recruit.read.model;

import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by somasundaram.s on 24-06-2016.
 */
@Service
public interface LocationService {

    List<Location> cityInfo(String cityName);
}
