package com.kylelieber.trimlight.edge.client.models;

import com.kylelieber.trimlight.style.BuilderStyle;
import org.immutables.value.Value;
import org.immutables.value.Value.Immutable;

@Immutable(singleton = true)
@BuilderStyle
public interface EdgeDeviceListRequestIF {
  @Value.Default
  @Value.Parameter
  default int getPage() {
    return 1;
  }
}
