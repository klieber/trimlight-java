package com.kylelieber.trimlight.data.models;

import com.kylelieber.trimlight.style.BuilderStyle;
import org.immutables.value.Value.Default;
import org.immutables.value.Value.Immutable;

@Immutable
@BuilderStyle
public interface ScheduleEggIF extends ScheduleFields {
  @Default
  default boolean isEnabled() {
    return true;
  }
}
