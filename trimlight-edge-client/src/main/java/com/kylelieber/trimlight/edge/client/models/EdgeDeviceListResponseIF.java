package com.kylelieber.trimlight.edge.client.models;

import com.kylelieber.trimlight.style.BuilderStyle;
import java.util.Optional;
import org.immutables.value.Value.Immutable;

@Immutable
@BuilderStyle
public interface EdgeDeviceListResponseIF extends EdgeBaseResponse {
  Optional<EdgeDeviceList> getPayload();
}
