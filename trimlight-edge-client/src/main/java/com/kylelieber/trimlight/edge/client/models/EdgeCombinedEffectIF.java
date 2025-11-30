package com.kylelieber.trimlight.edge.client.models;

import com.kylelieber.trimlight.style.BuilderStyle;
import java.util.List;
import org.immutables.value.Value.Immutable;

@Immutable
@BuilderStyle
public interface EdgeCombinedEffectIF {
  List<Integer> getEffectIds();

  int getInterval();
}
