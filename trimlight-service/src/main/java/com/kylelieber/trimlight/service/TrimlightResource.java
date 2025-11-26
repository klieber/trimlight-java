package com.kylelieber.trimlight.service;

import com.kylelieber.trimlight.data.manager.TrimlightManager;
import com.kylelieber.trimlight.data.models.DetailedDevice;
import com.kylelieber.trimlight.data.models.Device;
import com.kylelieber.trimlight.data.models.Effect;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DefaultValue;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import java.util.List;
import java.util.Optional;

@Path("/trimlight")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class TrimlightResource {

  private final TrimlightManager trimlightManager;

  @Inject
  public TrimlightResource(TrimlightManager trimlightManager) {
    this.trimlightManager = trimlightManager;
  }

  @GET
  @Path("/devices")
  public List<Device> getDevices(
    @QueryParam("page") @DefaultValue("1") int page
  ) {
    return trimlightManager.getDevices();
  }

  @GET
  @Path("/device/{deviceId}")
  public Optional<DetailedDevice> getDeviceById(
    @PathParam("deviceId") String deviceId
  ) {
    return trimlightManager.getDeviceById(deviceId);
  }

  @POST
  @Path("/device/{deviceId}/on")
  public void setDeviceOn(@PathParam("deviceId") String deviceId) {
    trimlightManager.on(deviceId);
  }

  @POST
  @Path("/device/{deviceId}/off")
  public void setDeviceOff(@PathParam("deviceId") String deviceId) {
    trimlightManager.off(deviceId);
  }

  @GET
  @Path("/device/{deviceId}/current-effect")
  public Optional<Effect> getCurrentEffect(
    @PathParam("deviceId") String deviceId
  ) {
    return trimlightManager.getCurrentEffect(deviceId);
  }

  @POST
  @Path("/device/{deviceId}/effect/{effectId}")
  public Effect getCurrentEffect(
    @PathParam("deviceId") String deviceId,
    @PathParam("effectId") int effectId
  ) {
    return trimlightManager.changeEffect(deviceId, effectId);
  }
}
