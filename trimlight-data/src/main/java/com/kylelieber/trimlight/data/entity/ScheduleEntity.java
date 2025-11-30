package com.kylelieber.trimlight.data.entity;

import com.kylelieber.trimlight.data.models.ScheduleDateType;
import com.kylelieber.trimlight.data.models.ScheduleTimeType;
import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "schedule")
public class ScheduleEntity extends PanacheEntity {

  public String deviceId;
  public boolean enabled;
  public ScheduleTimeType startType;
  public ScheduleTimeType endType;
  @Column(nullable = true)
  public LocalTime startTime;
  @Column(nullable = true)
  public LocalTime endTime;
  public long relativeStartTimeInMillis = 0L;
  public long relativeEndTimeInMillis = 0L;
  public ScheduleDateType dateType = ScheduleDateType.DAILY;
  @Column(nullable = true)
  public LocalDate startDate;
  @Column(nullable = true)
  public LocalDate endDate;
  public String holiday;
  public long relativeStartDateInDays = 0L;
  public long relativeEndDateInDays = 0L;
  public int effectId;
}
