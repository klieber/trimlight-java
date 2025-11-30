package com.kylelieber.trimlight.data.models;

import com.kylelieber.trimlight.style.BuilderStyle;
import org.immutables.value.Value.Default;
import org.immutables.value.Value.Immutable;

import java.time.LocalDate;
import java.util.Optional;

@Immutable
@BuilderStyle
public interface ScheduleDateRangeIF {
  @Default
  default ScheduleDateType getDateType() {
    return ScheduleDateType.DAILY;
  }

  Optional<LocalDate> getStartDate();

  Optional<LocalDate> getEndDate();

  Optional<String> getHoliday();

  @Default
  default long getRelativeStartDays() {
    return 0L;
  }

  @Default
  default long getRelativeEndDays() {
    return 0L;
  }
}
