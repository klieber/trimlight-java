package com.kylelieber.trimlight.edge.client.models;

import com.kylelieber.trimlight.edge.client.style.BuilderStyle;
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
