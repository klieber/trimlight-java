package com.kylelieber.trimlight.data.manager;

import com.google.common.collect.ImmutableList;
import com.kylelieber.trimlight.data.entity.ScheduleEntity;
import com.kylelieber.trimlight.data.models.Schedule;
import com.kylelieber.trimlight.data.models.ScheduleEgg;
import com.kylelieber.trimlight.data.models.ScheduleFields;
import com.kylelieber.trimlight.data.repository.ScheduleRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import java.time.Clock;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ApplicationScoped
public class ScheduleManager {

  private static final Logger LOG = LoggerFactory.getLogger(
    ScheduleManager.class
  );

  private final ScheduleRepository scheduleRepository;
  private final Clock clock;
  private final ZoneId zoneId;

  @Inject
  public ScheduleManager(
    ScheduleRepository scheduleRepository,
    Clock clock,
    @ConfigProperty(name = "trimlight.timezone") ZoneId zoneId
  ) {
    LOG.info("ZoneId: {}", zoneId);
    this.scheduleRepository = scheduleRepository;
    this.clock = clock;
    this.zoneId = zoneId;
  }

  @Transactional
  public Schedule createSchedule(ScheduleEgg newSchedule) {
    // 12 - 16
    // 10 - 14 - conflict
    // 13 - 15 - conflict
    // 14 - 20 - conflict
    // 10 - 11 - ok
    // 17 - 20 - ok

    //ZoneId zoneId = ZoneId.of("America/Chicago");

    // 2. Define an Instant (a specific point in time)
    // This is crucial because ZoneOffset can vary depending on DST rules.
    //    Instant instant = Instant.now(); // Using the current instant
    //
    //    // 3. Get the ZoneOffset for the given ZoneId and Instant
    //    ZoneOffset zoneOffset = zoneId.getRules().getOffset(instant);
    //    OffsetTime offsetTime = newSchedule.getStartTime().atOffset(zoneOffset);
    //
    //    LocalTime time = offsetTime.withOffsetSameInstant(ZoneOffset.UTC).toLocalTime();
    boolean hasConflicts = getAllSchedules()
      .stream()
      .anyMatch(s ->
        isWithinSchedule(s, newSchedule.getStartTime()) ||
        isWithinSchedule(s, newSchedule.getEndTime()) ||
        isWithinSchedule(newSchedule, s.getStartTime()) ||
        isWithinSchedule(newSchedule, s.getEndTime())
      );

    if (hasConflicts) {
      throw new RuntimeException("Schedule conflicts with existing schedules");
    }
    ScheduleEntity entity = new ScheduleEntity();
    entity.startTime = newSchedule.getStartTime();
    entity.endTime = newSchedule.getEndTime();
    entity.effectId = newSchedule.getEffectId();
    entity.enabled = newSchedule.isEnabled();
    scheduleRepository.persist(entity);
    return toSchedule(entity);
  }

  public List<Schedule> getAllSchedules() {
    return scheduleRepository
      .findAll()
      .stream()
      .map(this::toSchedule)
      .collect(ImmutableList.toImmutableList());
  }

  public Optional<Schedule> getActiveSchedule(LocalTime time) {
    return scheduleRepository
      .findAll()
      .stream()
      .map(this::toSchedule)
      .filter(Schedule::isEnabled)
      .filter(schedule -> isWithinSchedule(schedule, time))
      .findFirst();
  }

  public Optional<Schedule> getScheduleById(long scheduleId) {
    return Optional
      .ofNullable(scheduleRepository.findById(scheduleId))
      .map(this::toSchedule);
  }

  private Schedule toSchedule(ScheduleEntity entity) {
    return Schedule
      .builder()
      .setScheduleId(entity.id)
      .setStartTime(entity.startTime)
      .setEndTime(entity.endTime)
      .setEffectId(entity.effectId)
      .setEnabled(entity.enabled)
      .build();
  }

  private boolean isWithinSchedule(ScheduleFields schedule, LocalTime time) {
    return (
      time.isAfter(schedule.getStartTime()) &&
      time.isBefore(schedule.getEndTime())
    );
  }
}
