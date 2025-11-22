package com.kylelieber.models;

import com.kylelieber.models.style.BuilderStyle;
import org.immutables.value.Value.Immutable;

@Immutable
@BuilderStyle
public interface DeviceSummaryIF {
  String getDeviceId();

  String getName();

  int getConnectivity();

  int getState();

  String getFwVersionName();
}
