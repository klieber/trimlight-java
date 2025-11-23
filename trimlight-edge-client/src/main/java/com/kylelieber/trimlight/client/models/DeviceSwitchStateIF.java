package com.kylelieber.trimlight.client.models;

import com.kylelieber.trimlight.client.style.BuilderStyle;
import org.immutables.value.Value.Immutable;
import org.immutables.value.Value.Parameter;

@Immutable
@BuilderStyle
public interface DeviceSwitchStateIF {
  @Parameter
  int getSwitchState();
}
