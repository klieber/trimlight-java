package com.kylelieber.models;

import com.kylelieber.models.style.BuilderStyle;
import java.util.List;
import org.immutables.value.Value.Immutable;

@Immutable
@BuilderStyle
public interface CombinedEffectIF {
  List<Integer> getEffectIds();

  int getInterval();
}
