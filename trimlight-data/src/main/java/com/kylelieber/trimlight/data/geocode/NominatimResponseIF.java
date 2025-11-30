package com.kylelieber.trimlight.data.geocode;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.kylelieber.trimlight.style.BuilderStyle;
import org.immutables.value.Value.Immutable;

@Immutable
@BuilderStyle
public interface NominatimResponseIF {
  @JsonProperty("lat")
  String getLatitude();

  @JsonProperty("lon")
  String getLongitude();

  @JsonProperty("display_name")
  String getDisplayName();

  Address getAddress();
}
