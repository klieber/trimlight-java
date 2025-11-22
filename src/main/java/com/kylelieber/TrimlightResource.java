package com.kylelieber;

import com.kylelieber.client.TrimlightClient;
import com.kylelieber.models.CombinedEffect;
import com.kylelieber.models.CurrentDateTime;
import com.kylelieber.models.DeviceDetailsRequest;
import com.kylelieber.models.DeviceDetailsResponse;
import com.kylelieber.models.DeviceSwitchState;
import com.kylelieber.models.DeviceSwitchStateRequest;
import com.kylelieber.models.DeviceSwitchStateResponse;
import com.kylelieber.models.DevicesRequest;
import com.kylelieber.models.DevicesResponse;
import com.kylelieber.models.OverlayEffects;
import com.kylelieber.models.OverlayEffectsRequest;
import com.kylelieber.models.OverlayEffectsResponse;
import com.kylelieber.models.PreviewEffect;
import com.kylelieber.models.PreviewEffectRequest;
import com.kylelieber.models.PreviewEffectResponse;
import com.kylelieber.models.UpdateCombinedEffectRequest;
import com.kylelieber.models.UpdateCombinedEffectResponse;
import com.kylelieber.models.ViewEffect;
import com.kylelieber.models.ViewEffectRequest;
import com.kylelieber.models.ViewEffectResponse;
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

@Path("/trimlight")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class TrimlightResource {

  private final TrimlightClient trimlightClient;

  @Inject
  public TrimlightResource(@RestClient TrimlightClient trimlightClient) {
    this.trimlightClient = trimlightClient;
  }

  @GET
  @Path("/devices")
  public DevicesResponse getDevices(
    @QueryParam("page") @DefaultValue("1") int page
  ) {
    return trimlightClient.getDevices(DevicesRequest.of(page));
  }

  @GET
  @Path("/devices/{deviceId}")
  public DeviceDetailsResponse getDevices(
    @PathParam("deviceId") String deviceId
  ) {
    LocalDateTime now = LocalDateTime.now();
    CurrentDateTime currentDateTime = CurrentDateTime
      .builder()
      .setDay(now.getDayOfMonth())
      .setMonth(now.getMonth().getValue())
      .setYear(now.getYear())
      .setHours(now.getHour())
      .setMinutes(now.getMinute())
      .setSeconds(now.getSecond())
      .setWeekday(now.getDayOfWeek().getValue())
      .build();

    return trimlightClient.getDevice(
      DeviceDetailsRequest.of(deviceId, currentDateTime)
    );
  }

  @POST
  @Path("/devices/{deviceId}/state")
  public DeviceSwitchStateResponse setDeviceState(
    @PathParam("deviceId") String deviceId,
    @QueryParam("state") int state
  ) {
    return trimlightClient.setDeviceSwitchState(
      DeviceSwitchStateRequest.of(deviceId, DeviceSwitchState.of(state))
    );
  }

  @POST
  @Path("/devices/{deviceId}/effect/preview")
  public PreviewEffectResponse previewEffect(
    @PathParam("deviceId") String deviceId,
    PreviewEffect effect
  ) {
    return trimlightClient.previewEffect(
      PreviewEffectRequest.of(deviceId, effect)
    );
  }

  @POST
  @Path("/devices/{deviceId}/effect/view/{effectId}")
  public ViewEffectResponse viewEffect(
    @PathParam("deviceId") String deviceId,
    @PathParam("effectId") int effectId
  ) {
    return trimlightClient.viewEffect(
      ViewEffectRequest.of(deviceId, ViewEffect.of(effectId))
    );
  }

  @POST
  @Path("/devices/{deviceId}/combined-effect")
  public UpdateCombinedEffectResponse updateCombinedEffect(
    @PathParam("deviceId") String deviceId,
    CombinedEffect combinedEffect
  ) {
    return trimlightClient.updateCombinedEffect(
      UpdateCombinedEffectRequest.of(deviceId, combinedEffect)
    );
  }

  @POST
  @Path("/devices/{deviceId}/effect/overlay")
  public OverlayEffectsResponse addOverlayEffects(
    @PathParam("deviceId") String deviceId,
    OverlayEffects overlayEffects
  ) {
    return trimlightClient.addOverlayEffects(
      OverlayEffectsRequest.of(deviceId, overlayEffects)
    );
  }
}
