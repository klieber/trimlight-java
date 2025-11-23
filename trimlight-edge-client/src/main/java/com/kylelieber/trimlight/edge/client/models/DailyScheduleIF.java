package com.kylelieber.trimlight.edge.client.models;

import com.kylelieber.trimlight.edge.client.style.BuilderStyle;
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
