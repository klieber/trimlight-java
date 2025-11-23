package com.kylelieber.trimlight.edge.client.models;

import java.util.Optional;

public interface BasePayloadResponse<E> {
  int getCode();

  String getDesc();

  Optional<E> getPayload();
}
