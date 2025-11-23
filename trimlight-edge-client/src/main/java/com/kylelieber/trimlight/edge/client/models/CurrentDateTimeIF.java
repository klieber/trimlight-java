package com.kylelieber.trimlight.edge.client.models;

import com.kylelieber.trimlight.edge.client.style.BuilderStyle;
import org.immutables.value.Value.Immutable;

@Immutable
@BuilderStyle
public interface CurrentDateTimeIF {
  int getYear();

  int getMonth();

  int getDay();

  int getWeekday();

  int getHours();

  int getMinutes();

  int getSeconds();
}
