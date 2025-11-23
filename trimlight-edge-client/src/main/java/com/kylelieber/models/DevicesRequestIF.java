package com.kylelieber.models;

import com.kylelieber.models.style.BuilderStyle;
import org.immutables.value.Value;
import org.immutables.value.Value.Immutable;

@Immutable(singleton = true)
@BuilderStyle
public interface DevicesRequestIF {
  @Value.Default
  @Value.Parameter
  default int getPage() {
    return 1;
  }
}
