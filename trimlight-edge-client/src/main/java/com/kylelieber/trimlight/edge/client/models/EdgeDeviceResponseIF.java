package com.kylelieber.trimlight.edge.client.models;

import com.kylelieber.trimlight.style.BuilderStyle;
import java.util.Optional;
import org.immutables.value.Value.Immutable;

@Immutable
@BuilderStyle
public interface EdgeDeviceResponseIF extends EdgeBaseResponse {
  Optional<EdgeDevice> getPayload();
}
