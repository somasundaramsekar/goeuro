package de.goeuro.recruit.read.model;

import java.util.Objects;

/**
 * Created by somasundaram.s on 23-06-2016.
 */
public class Location {

    private String _id;
    private String name;
    private String type;
    private Coordinate coordinate;

    public Location(String _id, String name, String type, Coordinate coordinate) {
        this._id = _id;
        this.name = name;
        this.type = type;
        this.coordinate = coordinate;
    }

    public String get_id() {
        return _id;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Location location = (Location) o;
        return Objects.equals(name, location.name) &&
                Objects.equals(type, location.type) &&
                Objects.equals(coordinate, location.coordinate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, type, coordinate);
    }
}
