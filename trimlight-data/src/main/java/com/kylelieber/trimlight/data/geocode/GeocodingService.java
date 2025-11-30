package com.kylelieber.trimlight.data.geocode;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class GeocodingService {

  private NominatimClient nominatimClient;

  @Inject
  public GeocodingService(@RestClient NominatimClient nominatimClient) {
    this.nominatimClient = nominatimClient;
  }

  /**
   * Geocodes a postal code to get latitude/longitude coordinates.
   *
   * @param postalCode The postal code to geocode
   * @param country    Optional country code (e.g., "US", "CA") for better accuracy
   * @return GeoLocation with coordinates, or null if not found
   */
  public Optional<GeoLocation> geocodePostalCode(String postalCode, String country) {
    String query = postalCode;
    if (country != null && !country.isEmpty()) {
      query = postalCode + ", " + country;
    }

    List<NominatimResponse> results = nominatimClient.search(
      query,
      "json",
      1,
      1
    );

    if (results == null || results.isEmpty()) {
      return Optional.empty();
    }

    return results.stream().findFirst().map(result -> GeoLocation
      .builder()
      .setLatitude(Double.parseDouble(result.getLatitude()))
      .setLongitude(Double.parseDouble(result.getLongitude()))
      .setDisplayName(result.getDisplayName())
      .build());
  }

  /**
   * Geocodes a postal code (assumes US by default).
   */
  public Optional<GeoLocation> geocodePostalCode(String postalCode) {
    return geocodePostalCode(postalCode, "US");
  }
}
