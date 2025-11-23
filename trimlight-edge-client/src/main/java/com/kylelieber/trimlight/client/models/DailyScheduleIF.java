package com.kylelieber.trimlight.client.models;

import com.kylelieber.trimlight.client.models.Time;
import com.kylelieber.trimlight.client.style.BuilderStyle;
import org.immutables.value.Value.Immutable;

@Immutable
@BuilderStyle
public interface DailyScheduleIF {
  int getId();

  boolean isEnable();

  int getEffectId();

  int getRepetition();

  Time getStartTime();

  Time getEndTime();
}
