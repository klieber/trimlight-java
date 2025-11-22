package com.kylelieber.models;

import com.kylelieber.models.style.BuilderStyle;
import org.immutables.value.Value.Immutable;
import org.immutables.value.Value.Parameter;

@Immutable
@BuilderStyle
public interface OverlayEffectsRequestIF {
  @Parameter(order = 1)
  String getDeviceId();

  @Parameter(order = 2)
  OverlayEffects getPayload();
}
