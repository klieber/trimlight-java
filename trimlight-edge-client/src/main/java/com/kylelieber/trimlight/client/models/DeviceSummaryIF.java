package com.kylelieber.trimlight.client.models;

import com.kylelieber.trimlight.client.style.BuilderStyle;
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
