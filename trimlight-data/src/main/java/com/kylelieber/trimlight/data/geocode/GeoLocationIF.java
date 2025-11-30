package com.kylelieber.trimlight.data.geocode;

import com.kylelieber.trimlight.style.BuilderStyle;
import org.immutables.value.Value.Immutable;

@Immutable
@BuilderStyle
public interface GeoLocationIF {
  double getLatitude();

  double getLongitude();

  String getDisplayName();
}
