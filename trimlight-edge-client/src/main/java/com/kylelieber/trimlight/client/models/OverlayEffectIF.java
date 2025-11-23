package com.kylelieber.trimlight.client.models;

import com.kylelieber.trimlight.client.style.BuilderStyle;
import org.immutables.value.Value.Immutable;
import org.immutables.value.Value.Parameter;

@Immutable
@BuilderStyle
public interface OverlayEffectIF {
  @Parameter(order = 1)
  int getOverlayType();

  @Parameter(order = 2)
  int getTargetEffect();
}
