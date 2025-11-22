package com.kylelieber.models;

import com.kylelieber.models.style.BuilderStyle;
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
