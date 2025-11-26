package com.kylelieber.trimlight.service;

import com.kylelieber.trimlight.edge.client.TrimlightEdgeClient;
import com.kylelieber.trimlight.edge.client.models.EdgeCombinedEffect;
import com.kylelieber.trimlight.edge.client.models.EdgeCombinedEffectRequest;
import com.kylelieber.trimlight.edge.client.models.EdgeCurrentDateTime;
import com.kylelieber.trimlight.edge.client.models.EdgeDeviceListRequest;
import com.kylelieber.trimlight.edge.client.models.EdgeDeviceListResponse;
import com.kylelieber.trimlight.edge.client.models.EdgeDeviceRequest;
import com.kylelieber.trimlight.edge.client.models.EdgeDeviceResponse;
import com.kylelieber.trimlight.edge.client.models.EdgeDeviceSwitchState;
import com.kylelieber.trimlight.edge.client.models.EdgeDeviceSwitchStateRequest;
import com.kylelieber.trimlight.edge.client.models.EdgeOverlayEffects;
import com.kylelieber.trimlight.edge.client.models.EdgeOverlayEffectsRequest;
import com.kylelieber.trimlight.edge.client.models.EdgePreviewEffect;
import com.kylelieber.trimlight.edge.client.models.EdgePreviewEffectRequest;
import com.kylelieber.trimlight.edge.client.models.EdgeResponse;
import com.kylelieber.trimlight.edge.client.models.EdgeViewEffect;
import com.kylelieber.trimlight.edge.client.models.EdgeViewEffectRequest;
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
import java.time.LocalDateTime;
import org.eclipse.microprofile.rest.client.inject.RestClient;

@Path("/trimlight/edge")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class TrimlightEdgeResource {

  private final TrimlightEdgeClient trimlightEdgeClient;

  @Inject
  public TrimlightEdgeResource(
    @RestClient TrimlightEdgeClient trimlightEdgeClient
  ) {
    this.trimlightEdgeClient = trimlightEdgeClient;
  }

  @GET
  @Path("/devices")
  public EdgeDeviceListResponse getDevices(
    @QueryParam("page") @DefaultValue("1") int page
  ) {
    return trimlightEdgeClient.getDevices(EdgeDeviceListRequest.of(page));
  }

  @GET
  @Path("/devices/{deviceId}")
  public EdgeDeviceResponse getDevices(@PathParam("deviceId") String deviceId) {
    LocalDateTime now = LocalDateTime.now();
    EdgeCurrentDateTime currentDateTime = EdgeCurrentDateTime
      .builder()
      .setDay(now.getDayOfMonth())
      .setMonth(now.getMonth().getValue())
      .setYear(now.getYear())
      .setHours(now.getHour())
      .setMinutes(now.getMinute())
      .setSeconds(now.getSecond())
      .setWeekday(now.getDayOfWeek().getValue())
      .build();

    return trimlightEdgeClient.getDevice(
      EdgeDeviceRequest.of(deviceId, currentDateTime)
    );
  }

  @POST
  @Path("/devices/{deviceId}/state")
  public EdgeResponse setDeviceState(
    @PathParam("deviceId") String deviceId,
    @QueryParam("state") int state
  ) {
    return trimlightEdgeClient.setDeviceSwitchState(
      EdgeDeviceSwitchStateRequest.of(deviceId, EdgeDeviceSwitchState.of(state))
    );
  }

  @POST
  @Path("/devices/{deviceId}/effect/preview")
  public EdgeResponse previewEffect(
    @PathParam("deviceId") String deviceId,
    EdgePreviewEffect effect
  ) {
    return trimlightEdgeClient.previewEffect(
      EdgePreviewEffectRequest.of(deviceId, effect)
    );
  }

  @POST
  @Path("/devices/{deviceId}/effect/view/{effectId}")
  public EdgeResponse viewEffect(
    @PathParam("deviceId") String deviceId,
    @PathParam("effectId") int effectId
  ) {
    return trimlightEdgeClient.viewEffect(
      EdgeViewEffectRequest.of(deviceId, EdgeViewEffect.of(effectId))
    );
  }

  @POST
  @Path("/devices/{deviceId}/combined-effect")
  public EdgeResponse updateCombinedEffect(
    @PathParam("deviceId") String deviceId,
    EdgeCombinedEffect combinedEffect
  ) {
    return trimlightEdgeClient.updateCombinedEffect(
      EdgeCombinedEffectRequest.of(deviceId, combinedEffect)
    );
  }

  @POST
  @Path("/devices/{deviceId}/effect/overlay")
  public EdgeResponse addOverlayEffects(
    @PathParam("deviceId") String deviceId,
    EdgeOverlayEffects overlayEffects
  ) {
    return trimlightEdgeClient.addOverlayEffects(
      EdgeOverlayEffectsRequest.of(deviceId, overlayEffects)
    );
  }
}
