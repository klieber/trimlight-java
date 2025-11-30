package com.kylelieber.trimlight.data.repository;

import com.kylelieber.trimlight.data.entity.ScheduleEntity;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ScheduleRepository implements PanacheRepository<ScheduleEntity> {

  public PanacheQuery<ScheduleEntity> findByDeviceId(String deviceId) {
    return find("deviceId", deviceId);
  }
}
