package com.kylelieber.trimlight.edge.client.models;

import com.kylelieber.trimlight.edge.client.style.BuilderStyle;
import org.immutables.value.Value.Immutable;

@Immutable
@BuilderStyle
public interface PixelIF {
  int getIndex();

  int getCount();

  int getColor();

  boolean isDisable();
}
