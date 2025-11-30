package com.kylelieber.trimlight.data.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.kylelieber.trimlight.style.BuilderStyle;
import org.immutables.value.Value.Auxiliary;
import org.immutables.value.Value.Derived;
import org.immutables.value.Value.Immutable;

import java.util.Optional;

@Immutable
@BuilderStyle
public interface DetailedDeviceIF {
  String getDeviceId();

  String getName();

  DeviceStatus getStatus();

  Optional<Effect> getCurrentEffect();

  @Derived
  @Auxiliary
  @JsonIgnore
  default boolean isOn() {
    return DeviceStatus.ON == getStatus();
  }

  @Derived
  @Auxiliary
  @JsonIgnore
  default boolean isOff() {
    return !isOn();
  }
}
