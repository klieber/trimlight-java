package com.kylelieber.trimlight.data.models;

import com.kylelieber.trimlight.style.BuilderStyle;
import org.immutables.value.Value.Immutable;

import java.time.LocalTime;
import java.time.ZoneId;

@Immutable
@BuilderStyle
public interface LocationIF {
  String getDeviceId();

  String getPostalCode();

  String getCountry();

  double getLatitude();

  double getLongitude();

  ZoneId getTimezone();

  LocalTime getSunrise();

  LocalTime getSunset();
}
