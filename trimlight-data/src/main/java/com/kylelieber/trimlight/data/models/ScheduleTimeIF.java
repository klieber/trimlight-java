package com.kylelieber.trimlight.data.models;

import com.kylelieber.trimlight.data.TrimlightException;
import com.kylelieber.trimlight.style.BuilderStyle;
import org.immutables.value.Value.Check;
import org.immutables.value.Value.Default;
import org.immutables.value.Value.Immutable;

import java.time.Duration;
import java.time.LocalTime;
import java.util.Optional;

@Immutable
@BuilderStyle
public interface ScheduleTimeIF {
  ScheduleTimeType getType();

  Optional<LocalTime> getTime();

  @Default
  default Duration getRelativeTime() {
    return Duration.ZERO;
  }

  @Check
  default void check() {
    if (getType() == ScheduleTimeType.SPECIFIC_TIME && getTime().isEmpty()) {
      throw new TrimlightException("Specific time must be defined.");
    }
  }
}
