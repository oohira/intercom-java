package com.github.oohira.intercom.model;

/**
 * Class representing a location data of {@link User}.
 *
 * @author oohira
 */
public class LocationData {
    private String cityName;
    private String continentCode;
    private String countryName;
    private Double latitude;
    private Double longitude;
    private String postalCode;
    private String regionName;
    private String timezone;
    private String countryCode;

    public LocationData() {
    }

    public String getCityName() {
        return this.cityName;
    }

    public String getContinentCode() {
        return this.continentCode;
    }

    public String getCountryName() {
        return this.countryName;
    }

    public Double getLatitude() {
        return this.latitude;
    }

    public Double getLongitude() {
        return this.longitude;
    }

    public String getPostalCode() {
        return this.postalCode;
    }

    public String getRegionName() {
        return this.regionName;
    }

    public String getTimezone() {
        return this.timezone;
    }

    public String getCountryCode() {
        return this.countryCode;
    }
}
