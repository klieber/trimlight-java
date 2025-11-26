package com.kylelieber.trimlight.data.models;

import java.time.LocalTime;

public interface ScheduleFields {
  boolean isEnabled();
  LocalTime getStartTime();
  LocalTime getEndTime();
  int getEffectId();
}
