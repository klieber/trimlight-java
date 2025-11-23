package com.kylelieber.trimlight.client.models;

import com.kylelieber.trimlight.client.models.OverlayEffect;
import com.kylelieber.trimlight.client.style.BuilderStyle;
import org.immutables.value.Value.Immutable;
import org.immutables.value.Value.Parameter;

import java.util.List;

@Immutable
@BuilderStyle
public interface OverlayEffectsIF {
  @Parameter
  List<OverlayEffect> getOverlayEffects();
}
