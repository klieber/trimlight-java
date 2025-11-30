package com.kylelieber.trimlight.data.manager;

import com.kylelieber.trimlight.data.entity.LocationEntity;
import com.kylelieber.trimlight.data.geocode.GeoLocation;
import com.kylelieber.trimlight.data.geocode.GeocodingService;
import com.kylelieber.trimlight.data.models.Location;
import com.kylelieber.trimlight.data.repository.LocationRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.shredzone.commons.suncalc.SunTimes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Objects;

@ApplicationScoped
public class LocationManager {

  private static final Logger LOG = LoggerFactory.getLogger(
    LocationManager.class
  );

  private final LocationRepository locationRepository;
  private final GeocodingService geocodingService;

  @Inject
  public LocationManager(
    LocationRepository locationRepository,
    GeocodingService geocodingService
  ) {
    this.locationRepository = locationRepository;
    this.geocodingService = geocodingService;
  }

  @Transactional
  public Location saveLocation(
    String deviceId,
    String postalCode,
    String country
  ) {
    return locationRepository
      .findByDeviceId(deviceId)
      .map(location -> {
        LOG.info("Found existing location: {}", location);
        if (
          !location.postalCode.equals(postalCode) ||
            !location.country.equals(country)
        ) {
          GeoLocation geoLocation = geocodingService
            .geocodePostalCode(postalCode, country)
            .orElseThrow();

          LOG.info(
            "Found geolocation for {}, {}: {} ",
            postalCode,
            country,
            geoLocation
          );

          location.postalCode = postalCode;
          location.country = country;
          location.latitude = geoLocation.getLatitude();
          location.longitude = geoLocation.getLongitude();
          location.timezone = "America/Chicago"; // Need to also look this up.
          locationRepository.persistAndFlush(location);
        }
        return toLocation(location);
      })
      .orElseGet(() -> {
        GeoLocation geoLocation = geocodingService
          .geocodePostalCode(postalCode, country)
          .orElseThrow();

        LOG.info(
          "Found geolocation for {}, {}: {} ",
          postalCode,
          country,
          geoLocation
        );

        LocationEntity location = new LocationEntity();
        location.deviceId = deviceId;
        location.postalCode = postalCode;
        location.country = country;
        location.latitude = geoLocation.getLatitude();
        location.longitude = geoLocation.getLongitude();
        location.timezone = "America/Chicago"; // Need to also look this up.
        locationRepository.persistAndFlush(location);
        return toLocation(location);
      });
  }

  public Location getLocation(String deviceId) {
    return locationRepository
      .findByDeviceId(deviceId)
      .map(this::toLocation)
      .orElseGet(() -> getDefaultLocation(deviceId));
  }

  private Location toLocation(LocationEntity entity) {
    SunTimes sunTimes = SunTimes
      .compute()
      .on(LocalDate.now().atStartOfDay(ZoneId.of(entity.timezone)))
      .at(entity.latitude, entity.longitude)
      .execute();

    return Location
      .builder()
      .setDeviceId(entity.deviceId)
      .setPostalCode(entity.postalCode)
      .setCountry(entity.country)
      .setLatitude(entity.latitude)
      .setLongitude(entity.longitude)
      .setTimezone(ZoneId.of(entity.timezone))
      .setSunrise(Objects.requireNonNull(sunTimes.getRise()).toLocalTime())
      .setSunset(Objects.requireNonNull(sunTimes.getSet()).toLocalTime())
      .build();
  }

  private Location getDefaultLocation(String deviceId) {
    double latitude = 42.0004613;
    double longitude = -88.0232808;
    String timezone = "America/Chicago";

    SunTimes sunTimes = SunTimes
      .compute()
      .on(LocalDate.now().atStartOfDay(ZoneId.of(timezone)))
      .at(latitude, longitude)
      .execute();

    return Location
      .builder()
      .setDeviceId(deviceId)
      .setPostalCode("60007")
      .setCountry("US")
      .setLatitude(latitude)
      .setLongitude(longitude)
      .setTimezone(ZoneId.of(timezone))
      .setSunrise(Objects.requireNonNull(sunTimes.getRise()).toLocalTime())
      .setSunset(Objects.requireNonNull(sunTimes.getSet()).toLocalTime())
      .build();
  }
}
