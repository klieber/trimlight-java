package com.kylelieber.trimlight.data.models;

import com.google.common.collect.ImmutableMap;
import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

public enum DeviceStatus {
  OFF(0),
  ON(1),
  TIMER(2);

  private static Map<Integer, DeviceStatus> DEVICE_STATUS_BY_CODE = Arrays
    .stream(DeviceStatus.values())
    .collect(
      ImmutableMap.toImmutableMap(DeviceStatus::getCode, Function.identity())
    );

  private final int code;

  DeviceStatus(int code) {
    this.code = code;
  }

  public int getCode() {
    return this.code;
  }

  public static Optional<DeviceStatus> fromCode(int code) {
    return Optional.ofNullable(DEVICE_STATUS_BY_CODE.get(code));
  }
}
