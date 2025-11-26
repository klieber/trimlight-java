package com.kylelieber.trimlight.data.models;

import com.kylelieber.trimlight.style.BuilderStyle;
import java.util.Optional;
import org.immutables.value.Value.Immutable;

@Immutable
@BuilderStyle
public interface DetailedDeviceIF {
  String getDeviceId();

  String getName();

  DeviceStatus getStatus();

  Optional<Effect> getCurrentEffect();
}
