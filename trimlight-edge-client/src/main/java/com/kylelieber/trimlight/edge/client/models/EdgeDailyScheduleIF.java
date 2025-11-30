package com.kylelieber.trimlight.edge.client.models;

import com.kylelieber.trimlight.style.BuilderStyle;
import org.immutables.value.Value.Immutable;

@Immutable
@BuilderStyle
public interface EdgeDailyScheduleIF {
  int getId();

  boolean isEnable();

  int getEffectId();

  int getRepetition();

  EdgeTime getStartTime();

  EdgeTime getEndTime();
}
