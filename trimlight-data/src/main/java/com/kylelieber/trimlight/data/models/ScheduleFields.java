package com.kylelieber.trimlight.data.models;

import org.immutables.value.Value.Default;

import java.util.Optional;

public interface ScheduleFields {
  String getDeviceId();

  @Default
  default boolean isEnabled() {
    return true;
  }

  Optional<ScheduleDateRange> getDateRange();

  ScheduleTime getStartTime();

  ScheduleTime getEndTime();

  int getEffectId();
}
