package com.kylelieber.trimlight.data.repository;

import com.kylelieber.trimlight.data.entity.ScheduleEntity;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ScheduleRepository implements PanacheRepository<ScheduleEntity> {}
