package com.kylelieber.data.dao;

import com.kylelieber.data.models.ScheduleEntity;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ScheduleRepository implements PanacheRepository<ScheduleEntity> {

}
