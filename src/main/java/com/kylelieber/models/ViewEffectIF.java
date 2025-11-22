package com.kylelieber.models;

import com.kylelieber.models.style.BuilderStyle;
import org.immutables.value.Value.Immutable;
import org.immutables.value.Value.Parameter;

@Immutable
@BuilderStyle
public interface ViewEffectIF {
  @Parameter
  int getId();
}
