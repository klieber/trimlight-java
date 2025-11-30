package com.kylelieber.trimlight.service;

import com.kylelieber.trimlight.data.manager.LocationManager;
import com.kylelieber.trimlight.data.models.Location;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;

@Path("/trimlight/location")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class LocationResource {

  private final LocationManager locationManager;

  @Inject
  public LocationResource(LocationManager locationManager) {
    this.locationManager = locationManager;
  }

  @GET
  @Path("/{deviceId}")
  public Location getLocationByDeviceId(
    @PathParam("deviceId") String deviceId
  ) {
    return locationManager.getLocation(deviceId);
  }

  @PUT
  @Path("/{deviceId}")
  public Location saveLocationByDeviceId(
    @PathParam("deviceId") String deviceId,
    @QueryParam("postalCode") String postalCode,
    @QueryParam("country") String country
  ) {
    return locationManager.saveLocation(deviceId, postalCode, country);
  }

}
