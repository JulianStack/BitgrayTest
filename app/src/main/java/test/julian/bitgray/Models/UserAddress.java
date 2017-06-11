package test.julian.bitgray.Models;

import android.location.Location;

import java.util.HashMap;

/**
 * Created by JulianStack on 09/06/2017.
 */

public class UserAddress {

    String Street;
    String Suite;
    String City;
    String Zipcode;
    Location Geo;

    public UserAddress(String street, String suite, String city, String zipcode, Location geo) {
        Street = street;
        Suite = suite;
        City = city;
        Zipcode = zipcode;
        Geo = geo;
    }

    public String getStreet() {
        return Street;
    }

    public void setStreet(String street) {
        Street = street;
    }

    public String getSuite() {
        return Suite;
    }

    public void setSuite(String suite) {
        Suite = suite;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        City = city;
    }

    public String getZipcode() {
        return Zipcode;
    }

    public void setZipcode(String zipcode) {
        Zipcode = zipcode;
    }

    public Location getGeo() {
        return Geo;
    }

    public void setGeo(Location geo) {
        Geo = geo;
    }
}
