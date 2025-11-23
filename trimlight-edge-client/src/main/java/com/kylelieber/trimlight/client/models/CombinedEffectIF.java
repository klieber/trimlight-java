package com.kylelieber.trimlight.client.models;

import com.kylelieber.trimlight.client.style.BuilderStyle;
import org.immutables.value.Value.Immutable;

import java.util.List;

@Immutable
@BuilderStyle
public interface CombinedEffectIF {
  List<Integer> getEffectIds();

  int getInterval();
}
