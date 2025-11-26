package com.kylelieber.trimlight.data.models;

import com.kylelieber.trimlight.style.BuilderStyle;
import org.immutables.value.Value.Immutable;

@Immutable
@BuilderStyle
public interface EffectIF {
  int getEffectId();

  String getEffectName();
}
