package com.kylelieber.trimlight.data.models;

import com.kylelieber.trimlight.style.BuilderStyle;
import org.immutables.value.Value.Immutable;

import java.time.LocalDate;

@Immutable
@BuilderStyle
public interface CalculatedScheduleDateRangeIF {
  LocalDate getStartDate();

  LocalDate getEndDate();
}
