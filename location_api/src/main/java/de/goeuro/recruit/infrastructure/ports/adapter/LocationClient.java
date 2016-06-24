package de.goeuro.recruit.infrastructure.ports.adapter;

import com.google.gson.Gson;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import org.springframework.util.StringUtils;

/**
 * Created by somasundaram.s on 23-06-2016.
 */

public enum LocationClient {

    INSTANCE;

    private static final String POSITION_END_POINT = "http://api.goeuro.com/api/v2/position/suggest/en/%s";
    private final Client client;
    private final Gson gson;


    LocationClient() {
        client = Client.create();
        gson = new Gson();
    }

    public LocationResponse[] positionWithCityName(String cityName) {

        WebResource webResource = client.resource(String.format(POSITION_END_POINT, cityName));

        return gson.fromJson(jsonResponseAfterInvocation(webResource), LocationResponse[].class);
    }

    private String jsonResponseAfterInvocation(WebResource webResource) {

        ClientResponse response = webResource.accept("application/json").get(ClientResponse.class);
        if (response.getStatus() != 200) {
            throw new RuntimeException("Failed : HTTP error code : " + response.getStatus());
        }

        String location_array = response.getEntity(String.class);
        if (StringUtils.isEmpty(location_array))
            throw new IllegalArgumentException("Specified city name is incorrect");

        return location_array;
    }
}
