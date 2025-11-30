package com.kylelieber.trimlight.data.manager;

import com.github.rholder.retry.RetryException;
import com.github.rholder.retry.Retryer;
import com.github.rholder.retry.RetryerBuilder;
import com.github.rholder.retry.StopStrategies;
import com.github.rholder.retry.WaitStrategies;
import com.google.common.collect.ImmutableList;
import com.kylelieber.trimlight.data.TrimlightException;
import com.kylelieber.trimlight.data.models.DetailedDevice;
import com.kylelieber.trimlight.data.models.Device;
import com.kylelieber.trimlight.data.models.DeviceStatus;
import com.kylelieber.trimlight.data.models.Effect;
import com.kylelieber.trimlight.edge.client.TrimlightEdgeClient;
import com.kylelieber.trimlight.edge.client.models.EdgeBaseResponse;
import com.kylelieber.trimlight.edge.client.models.EdgeCurrentDateTime;
import com.kylelieber.trimlight.edge.client.models.EdgeCurrentEffect;
import com.kylelieber.trimlight.edge.client.models.EdgeDevice;
import com.kylelieber.trimlight.edge.client.models.EdgeDeviceListRequest;
import com.kylelieber.trimlight.edge.client.models.EdgeDeviceRequest;
import com.kylelieber.trimlight.edge.client.models.EdgeDeviceSwitchState;
import com.kylelieber.trimlight.edge.client.models.EdgeDeviceSwitchStateRequest;
import com.kylelieber.trimlight.edge.client.models.EdgeEffect;
import com.kylelieber.trimlight.edge.client.models.EdgeViewEffect;
import com.kylelieber.trimlight.edge.client.models.EdgeViewEffectRequest;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Clock;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

@Singleton
public class TrimlightManager {

  private static final Logger LOG = LoggerFactory.getLogger(
    TrimlightManager.class
  );

  private static final int DEVICE_SWITCH_STATE_OFF = 0;
  private static final int DEVICE_SWITCH_STATE_MANUAL = 1;

  private final TrimlightEdgeClient client;
  private final Clock clock;

  @Inject
  public TrimlightManager(@RestClient TrimlightEdgeClient client, Clock clock) {
    this.client = client;
    this.clock = clock;
  }

  public List<Device> getDevices() {
    return handleResponse(client.getDevices(EdgeDeviceListRequest.of()))
      .getPayload()
      .map(payload ->
        payload
          .getData()
          .stream()
          .map(deviceSummary ->
            Device
              .builder()
              .setDeviceId(deviceSummary.getDeviceId())
              .setName(deviceSummary.getName())
              .build()
          )
          .collect(ImmutableList.toImmutableList())
      )
      .orElseGet(ImmutableList::of);
  }

  public Optional<DetailedDevice> getDeviceById(String deviceId) {
    LocalDateTime now = LocalDateTime.now(clock);
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

    return handleResponse(
      client.getDevice(EdgeDeviceRequest.of(deviceId, currentDateTime))
    )
      .getPayload()
      .map(deviceDetails ->
        DetailedDevice
          .builder()
          .setDeviceId(deviceId)
          .setName(deviceDetails.getName())
          .setStatus(
            DeviceStatus
              .fromCode(deviceDetails.getSwitchState())
              .orElse(DeviceStatus.OFF)
          )
          .setCurrentEffect(getDeviceCurrentEffect(deviceDetails))
          .build()
      );
  }

  public void on(String deviceId) {
    setSwitchState(deviceId, DEVICE_SWITCH_STATE_MANUAL);
  }

  public void off(String deviceId) {
    setSwitchState(deviceId, DEVICE_SWITCH_STATE_OFF);
  }

  public Optional<Effect> getCurrentEffect(String deviceId) {
    LocalDateTime now = LocalDateTime.now(clock);
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

    return handleResponse(
      client.getDevice(EdgeDeviceRequest.of(deviceId, currentDateTime))
    )
      .getPayload()
      .flatMap(this::getDeviceCurrentEffect);
  }

  public Effect changeEffect(String deviceId, int effectId) {
    handleResponse(
      client.viewEffect(
        EdgeViewEffectRequest.of(deviceId, EdgeViewEffect.of(effectId))
      )
    );


    Retryer<Optional<Effect>> retryer = RetryerBuilder
      .<Optional<Effect>>newBuilder()
      .retryIfResult(result ->
        result.filter(effect -> effect.getEffectId() == effectId).isEmpty()
      )
      .retryIfException()
      .withWaitStrategy(WaitStrategies.fixedWait(5, TimeUnit.SECONDS))
      .withStopStrategy(StopStrategies.stopAfterAttempt(2))
      .build();

    try {
      Thread.sleep(Duration.ofSeconds(5));
      return retryer.call(() -> getCurrentEffect(deviceId)).orElseThrow();
    } catch (ExecutionException | RetryException | InterruptedException e) {
      throw new TrimlightException("Unable to confirm effect change.", e);
    }
  }

  private void setSwitchState(String deviceId, int state) {
    handleResponse(
      client.setDeviceSwitchState(
        EdgeDeviceSwitchStateRequest.of(
          deviceId,
          EdgeDeviceSwitchState.of(state)
        )
      )
    );
  }

  private Optional<Effect> getDeviceCurrentEffect(EdgeDevice payload) {
    LOG.info("Getting current effect");
    Optional<Effect> currentEffect = payload
      .getEffects()
      .stream()
      .filter(effect -> isSameEffect(payload.getCurrentEffect(), effect))
      .map(effect ->
        Effect
          .builder()
          .setEffectId(effect.getId())
          .setEffectName(effect.getName())
          .build()
      )
      .findFirst();
    LOG.info("Found current effect: {}", currentEffect);
    return currentEffect;
  }

  private boolean isSameEffect(
    EdgeCurrentEffect currentEffect,
    EdgeEffect effect
  ) {
    return currentEffect.equals(
      EdgeCurrentEffect.builder().from(effect).build()
    );
  }

  private <E extends EdgeBaseResponse> E handleResponse(E response) {
    if (response.getCode() != 0) {
      throw new TrimlightException(
        "%s (code = %s)".formatted(response.getDesc(), response.getCode())
      );
    }
    return response;
  }
}
