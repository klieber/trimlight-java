package com.kylelieber.trimlight.client.models;

import com.kylelieber.trimlight.client.models.DeviceDetailsPayload;
import com.kylelieber.trimlight.client.style.BuilderStyle;
import org.immutables.value.Value.Immutable;

@Immutable
@BuilderStyle
public interface DeviceDetailsResponseIF
  extends BasePayloadResponse<DeviceDetailsPayload> {}
