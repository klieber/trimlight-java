package com.kylelieber.models;

import com.kylelieber.models.style.BuilderStyle;
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
