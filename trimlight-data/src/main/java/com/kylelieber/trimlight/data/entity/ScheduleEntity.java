package com.kylelieber.trimlight.data.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import java.time.LocalTime;

@Entity
@Table(name = "schedule")
public class ScheduleEntity extends PanacheEntity {

  public boolean enabled;
  public LocalTime startTime;
  public LocalTime endTime;
  public int effectId;
}
