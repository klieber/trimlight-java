package com.kylelieber.trimlight.client.models;

import com.kylelieber.trimlight.client.models.DevicesPayload;
import com.kylelieber.trimlight.client.style.BuilderStyle;
import org.immutables.value.Value.Immutable;

@Immutable
@BuilderStyle
public interface DevicesResponseIF
  extends BasePayloadResponse<DevicesPayload> {}
