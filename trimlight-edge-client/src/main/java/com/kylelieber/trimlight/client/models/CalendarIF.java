package com.kylelieber.trimlight.client.models;

import com.kylelieber.trimlight.client.models.MonthAndDay;
import com.kylelieber.trimlight.client.models.Time;
import com.kylelieber.trimlight.client.style.BuilderStyle;
import org.immutables.value.Value.Immutable;

@Immutable
@BuilderStyle
public interface CalendarIF {
  int getId();

  int getEffectId();

  MonthAndDay getStartDate();

  MonthAndDay getEndDate();

  Time getStartTime();

  Time getEndTime();
}
