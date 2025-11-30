package com.kylelieber.trimlight.edge.client.models;

import com.kylelieber.trimlight.style.BuilderStyle;
import org.immutables.value.Value.Immutable;

@Immutable
@BuilderStyle
public interface EdgePixelIF {
  int getIndex();

  int getCount();

  int getColor();

  boolean isDisable();
}
