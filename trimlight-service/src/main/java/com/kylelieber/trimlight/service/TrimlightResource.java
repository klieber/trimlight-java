package com.kylelieber.trimlight.service;

import com.kylelieber.trimlight.client.TrimlightClient;
import com.kylelieber.trimlight.data.repository.ScheduleRepository;
import com.kylelieber.trimlight.data.entity.ScheduleEntity;
import com.kylelieber.trimlight.client.models.CombinedEffect;
import com.kylelieber.trimlight.client.models.CurrentDateTime;
import com.kylelieber.trimlight.client.models.DeviceDetailsRequest;
import com.kylelieber.trimlight.client.models.DeviceDetailsResponse;
import com.kylelieber.trimlight.client.models.DeviceSwitchState;
import com.kylelieber.trimlight.client.models.DeviceSwitchStateRequest;
import com.kylelieber.trimlight.client.models.DeviceSwitchStateResponse;
import com.kylelieber.trimlight.client.models.DevicesRequest;
import com.kylelieber.trimlight.client.models.DevicesResponse;
import com.kylelieber.trimlight.client.models.OverlayEffects;
import com.kylelieber.trimlight.client.models.OverlayEffectsRequest;
import com.kylelieber.trimlight.client.models.OverlayEffectsResponse;
import com.kylelieber.trimlight.client.models.PreviewEffect;
import com.kylelieber.trimlight.client.models.PreviewEffectRequest;
import com.kylelieber.trimlight.client.models.PreviewEffectResponse;
import com.kylelieber.trimlight.client.models.UpdateCombinedEffectRequest;
import com.kylelieber.trimlight.client.models.UpdateCombinedEffectResponse;
import com.kylelieber.trimlight.client.models.ViewEffect;
import com.kylelieber.trimlight.client.models.ViewEffectRequest;
import com.kylelieber.trimlight.client.models.ViewEffectResponse;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DefaultValue;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Path("/trimlight")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class TrimlightResource {

  private final TrimlightClient trimlightClient;
  private final ScheduleRepository scheduleRepository;

  @Inject
  public TrimlightResource(@RestClient TrimlightClient trimlightClient, ScheduleRepository scheduleRepository) {
    this.trimlightClient = trimlightClient;
    this.scheduleRepository = scheduleRepository;
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

  @GET
  @Path("/schedule/{scheduleId}")
  public Optional<ScheduleEntity> getScheduleById(
    @PathParam("scheduleId") long scheduleId
  ) {
    return Optional.ofNullable(scheduleRepository.findById(scheduleId));
  }

  @GET
  @Path("/schedule")
  public List<ScheduleEntity> getSchedules() {
    return scheduleRepository.findAll().list();
  }

  @PUT
  @Path("/schedule")
  public ScheduleEntity getSchedules(ScheduleEntity scheduleEntity) {
    scheduleEntity.persist();
    return scheduleEntity;
  }
}
