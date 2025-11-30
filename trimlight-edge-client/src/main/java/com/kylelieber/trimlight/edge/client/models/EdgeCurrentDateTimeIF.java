package com.kylelieber.trimlight.edge.client.models;

import com.kylelieber.trimlight.style.BuilderStyle;
import org.immutables.value.Value.Immutable;

@Immutable
@BuilderStyle
public interface EdgeCurrentDateTimeIF {
  int getYear();

  int getMonth();

  int getDay();

  int getWeekday();

  int getHours();

  int getMinutes();

  int getSeconds();
}
