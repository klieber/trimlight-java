package com.kylelieber.trimlight.edge.client.models;

import com.kylelieber.trimlight.style.BuilderStyle;
import java.util.List;
import org.immutables.value.Value.Immutable;
import org.immutables.value.Value.Parameter;

@Immutable
@BuilderStyle
public interface EdgeOverlayEffectsIF {
  @Parameter
  List<EdgeOverlayEffect> getOverlayEffects();
}
