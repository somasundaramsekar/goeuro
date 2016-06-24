package de.goeuro.recruit.read.model;



import java.text.DecimalFormat;


public class Coordinate implements Comparable<Coordinate>{

    // declare private class level variables
    private float latitude;
    private float longitude;
    private DecimalFormat format;


    public Coordinate(float latitude, float longitude) {

        if(CoordinateManager.isValidLatitude(latitude) == true && CoordinateManager.isValidLongitude(longitude) == true) {
            this.latitude = latitude;
            this.longitude = longitude;
        } else {
            throw new IllegalArgumentException("The parameters did not pass validation as defined by the CoordinateManager class");
        }

        this.format = new DecimalFormat("##.######");
    }

    /*
     * get and set methods
     */
    public float getLatitude() {
        return latitude;
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLatitude(float latitude) {
        if(CoordinateManager.isValidLatitude(latitude) == true) {
            this.latitude = latitude;
        } else {
            throw new IllegalArgumentException("The parameter did not pass validation as defined by the CoordinateManager class");
        }
    }

    public void setLongitude(float longitude) {
        if(CoordinateManager.isValidLongitude(longitude) == true) {
            this.longitude = longitude;
        } else {
            throw new IllegalArgumentException("The parameter did not pass validation as defined by the CoordinateManager class");
        }
    }

    public String getLatitudeAsString() {

        return format.format(latitude);
    }

    public String getLongitudeAsString() {
        return format.format(longitude);
    }

    public String toString() {
        return format.format(latitude) + ", " + format.format(longitude);
    }


    public boolean equals(Object o) {
        // check to make sure the object is an event
        if ((o instanceof Coordinate) == false) {
            // o is not an event object
            return false;
        }

        // compare these two events
        Coordinate c = (Coordinate)o;

        // build items for comparison
        String me  = this.getLatitudeAsString() + this.getLongitudeAsString();
        String you = c.getLatitudeAsString() + c.getLongitudeAsString();

        return me.equals(you);

    }
    public int hashCode() {

        String me = this.getLatitudeAsString() + this.getLongitudeAsString();
        return 31*me.hashCode();
    }

    public int compareTo(Coordinate c) {

        String me  = this.getLatitudeAsString() + this.getLongitudeAsString();
        String you = c.getLatitudeAsString() + c.getLongitudeAsString();

        Double meDbl  = Double.valueOf(me);
        Double youDbl = Double.valueOf(you);

        if(meDbl == youDbl) {
            return 0;
        } else {
            Double tmp = Math.floor(meDbl - youDbl);
            return tmp.intValue();
        }

    }
}

class CoordinateManager {

    public static float MIN_LATITUDE = Float.valueOf("-90.0000");

    public static float MAX_LATITUDE = Float.valueOf("90.0000");

    public static float MIN_LONGITUDE = Float.valueOf("-180.0000");

    public static float MAX_LONGITUDE = Float.valueOf("180.0000");

    public static float EARTH_DIAMETER = Float.valueOf("12756.274");

    public static boolean isValidLatitude(float latitude) {
        if(latitude >= MIN_LATITUDE && latitude <= MAX_LATITUDE) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean isValidLongitude(float longitude) {
        if(longitude >= MIN_LONGITUDE && longitude <= MAX_LONGITUDE) {
            return true;
        } else {
            return false;
        }
    }

    public static double latitudeConstant() {
        return EARTH_DIAMETER * (Math.PI / Float.valueOf("360"));
    }

    public static double longitudeConstant(float latitude) {

        return EARTH_DIAMETER * Math.PI * Math.abs(Math.cos(Math.abs(latitude))) / Float.valueOf("360");

    }

    public static Coordinate addDistanceNorth(float latitude, float longitude, int distance) {

        if(isValidLatitude(latitude) == false || isValidLongitude(longitude) == false || distance <= 0) {
            throw new IllegalArgumentException("All parameters are required and must be valid");
        }

        float kilometers = distance / new Float(1000);

        double newLat = latitude + (kilometers / latitudeConstant());

        return new Coordinate(new Float(newLat).floatValue(), longitude);

    }

    public static Coordinate addDistanceSouth(float latitude, float longitude, int distance) {

        if(isValidLatitude(latitude) == false || isValidLongitude(longitude) == false || distance <= 0) {
            throw new IllegalArgumentException("All parameters are required and must be valid");
        }

        float kilometers = distance / new Float(1000);

        double newLat = latitude - (kilometers / latitudeConstant());

        return new Coordinate(new Float(newLat).floatValue(), longitude);

    }

    public static Coordinate addDistanceEast(float latitude, float longitude, int distance) {

        if(isValidLatitude(latitude) == false || isValidLongitude(longitude) == false || distance <= 0) {
            throw new IllegalArgumentException("All parameters are required and must be valid");
        }

        float kilometers = distance / 1000;

        double newLng = longitude + (distance / longitudeConstant(latitude));

        return new Coordinate(latitude, new Float(newLng).floatValue());
    }

    public static Coordinate addDistanceWest(float latitude, float longitude, int distance) {

        if(isValidLatitude(latitude) == false || isValidLongitude(longitude) == false || distance <= 0) {
            throw new IllegalArgumentException("All parameters are required and must be valid");
        }

        float kilometers = distance / 1000;

        double newLng = longitude - (distance / longitudeConstant(latitude));

        return new Coordinate(latitude, new Float(newLng).floatValue());
    }

    public static java.util.HashMap<String, Coordinate> getBoundingBox(float latitude, float longitude, int distance) {

        if(isValidLatitude(latitude) == false || isValidLongitude(longitude) == false || distance <= 0) {
            throw new IllegalArgumentException("All parameters are required and must be valid");
        }

        float kilometers = distance / 1000;

        java.util.HashMap<String, Coordinate> boundingBox = new java.util.HashMap<String, Coordinate>();

        Coordinate north = addDistanceNorth(latitude, longitude, distance);
        Coordinate south = addDistanceSouth(latitude, longitude, distance);
        Coordinate east  = addDistanceEast(latitude, longitude, distance);
        Coordinate west  = addDistanceWest(latitude, longitude, distance);

        boundingBox.put("NE", new Coordinate(north.getLatitude(), east.getLongitude()));
        boundingBox.put("SE", new Coordinate(south.getLatitude(), east.getLongitude()));
        boundingBox.put("SW", new Coordinate(south.getLatitude(), west.getLongitude()));
        boundingBox.put("NW", new Coordinate(north.getLatitude(), west.getLongitude()));

        return boundingBox;
    }
}