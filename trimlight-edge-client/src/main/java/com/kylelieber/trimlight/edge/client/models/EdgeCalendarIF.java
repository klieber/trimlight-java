package com.kylelieber.trimlight.edge.client.models;

import com.kylelieber.trimlight.style.BuilderStyle;
import org.immutables.value.Value.Immutable;

@Immutable
@BuilderStyle
public interface EdgeCalendarIF {
  int getId();

  int getEffectId();

  EdgeMonthAndDay getStartDate();

  EdgeMonthAndDay getEndDate();

  EdgeTime getStartTime();

  EdgeTime getEndTime();
}
