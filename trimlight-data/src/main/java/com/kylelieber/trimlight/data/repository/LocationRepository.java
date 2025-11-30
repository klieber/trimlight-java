package com.kylelieber.trimlight.data.repository;

import com.kylelieber.trimlight.data.entity.LocationEntity;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.Optional;

@ApplicationScoped
public class LocationRepository implements PanacheRepository<LocationEntity> {

  public Optional<LocationEntity> findByDeviceId(String deviceId) {
    return find("deviceId", deviceId).firstResultOptional();
  }
}
