package com.kylelieber.trimlight.edge.client.models;

import com.kylelieber.trimlight.style.BuilderStyle;
import org.immutables.value.Value.Immutable;
import org.immutables.value.Value.Parameter;

@Immutable
@BuilderStyle
public interface EdgeViewEffectRequestIF {
  @Parameter(order = 1)
  String getDeviceId();

  @Parameter(order = 2)
  EdgeViewEffect getPayload();
}
