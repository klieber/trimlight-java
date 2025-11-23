package com.kylelieber.models;

import com.kylelieber.models.style.BuilderStyle;
import java.util.List;
import org.immutables.value.Value.Immutable;
import org.immutables.value.Value.Parameter;

@Immutable
@BuilderStyle
public interface OverlayEffectsIF {
  @Parameter
  List<OverlayEffect> getOverlayEffects();
}
