package com.kylelieber.trimlight.data.models;

import com.kylelieber.trimlight.style.BuilderStyle;
import org.immutables.value.Value.Immutable;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Optional;

@Immutable
@BuilderStyle
public interface CalculatedScheduleIF {
  long getScheduleId();

  String getDeviceId();

  boolean isEnabled();

  Optional<CalculatedScheduleDateRange> getDateRange();

  LocalTime getStartTime();

  LocalTime getEndTime();

  int getEffectId();

  default boolean isActiveAt(LocalDateTime localDateTime) {
    return isActiveAt(localDateTime.toLocalDate()) && isActiveAt(localDateTime.toLocalTime());
  }

  default boolean isActiveAt(LocalDate localDate) {
    return getDateRange()
      .map(dateRange ->
        (
          localDate.isEqual(dateRange.getStartDate()) ||
            localDate.isAfter(dateRange.getStartDate())
        ) &&
          (
            localDate.isEqual(dateRange.getEndDate()) ||
              localDate.isBefore(dateRange.getEndDate())
          )
      ).orElse(true);
  }

  default boolean isActiveAt(LocalTime localTime) {
    return (
      (localTime.isAfter(getStartTime()) && localTime.isBefore(getEndTime())) ||
        (
          getStartTime().isAfter(getEndTime()) &&
            (localTime.isAfter(getStartTime()) || localTime.isBefore(getEndTime()))
        )
    );
  }

  default boolean hasConflict(CalculatedSchedule other) {
    return (
      isActiveAt(other.getStartTime()) ||
        isActiveAt(other.getEndTime()) ||
        other.isActiveAt(getStartTime()) ||
        other.isActiveAt(getEndTime())
    );
  }
}
