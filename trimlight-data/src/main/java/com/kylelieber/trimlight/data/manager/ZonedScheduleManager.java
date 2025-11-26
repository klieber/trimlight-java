package com.kylelieber.trimlight.data.manager;

import com.google.common.collect.ImmutableList;
import com.kylelieber.trimlight.data.models.Schedule;
import com.kylelieber.trimlight.data.models.ScheduleEgg;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.time.Clock;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Optional;
import org.eclipse.microprofile.config.inject.ConfigProperty;

@ApplicationScoped
public class ZonedScheduleManager {

  private final ScheduleManager scheduleManager;
  private final Clock clock;
  private final ZoneId zoneId;

  @Inject
  public ZonedScheduleManager(
    ScheduleManager scheduleManager,
    Clock clock,
    @ConfigProperty(name = "trimlight.timezone") ZoneId zoneId
  ) {
    this.scheduleManager = scheduleManager;
    this.clock = clock;
    this.zoneId = zoneId;
  }

  public Schedule createSchedule(ScheduleEgg newZonedSchedule) {
    return convertToUserTimezone(
      scheduleManager.createSchedule(convertToSystemTimezone(newZonedSchedule))
    );
  }

  public List<Schedule> getAllSchedules() {
    return scheduleManager
      .getAllSchedules()
      .stream()
      .map(this::convertToUserTimezone)
      .collect(ImmutableList.toImmutableList());
  }

  public Optional<Schedule> getActiveSchedule(LocalTime time) {
    return scheduleManager
      .getActiveSchedule(convertToSystemTimeZone(time))
      .map(this::convertToUserTimezone);
  }

  public Optional<Schedule> getScheduleById(long scheduleId) {
    return scheduleManager
      .getScheduleById(scheduleId)
      .map(this::convertToUserTimezone);
  }

  private ScheduleEgg convertToSystemTimezone(ScheduleEgg scheduleEgg) {
    ZoneOffset userOffset = zoneId.getRules().getOffset(clock.instant());
    ZoneOffset systemOffset = clock
      .getZone()
      .getRules()
      .getOffset(clock.instant());
    return scheduleEgg
      .withStartTime(
        convertToTimeZone(scheduleEgg.getStartTime(), userOffset, systemOffset)
      )
      .withEndTime(
        convertToTimeZone(scheduleEgg.getEndTime(), userOffset, systemOffset)
      );
  }

  private Schedule convertToUserTimezone(Schedule schedule) {
    ZoneOffset userOffset = zoneId.getRules().getOffset(clock.instant());
    ZoneOffset systemOffset = clock
      .getZone()
      .getRules()
      .getOffset(clock.instant());
    return schedule
      .withStartTime(
        convertToTimeZone(schedule.getStartTime(), systemOffset, userOffset)
      )
      .withEndTime(
        convertToTimeZone(schedule.getEndTime(), systemOffset, userOffset)
      );
  }

  private ScheduleEgg convertToTimezone(
    ScheduleEgg scheduleEgg,
    ZoneOffset fromOffset,
    ZoneOffset toOffset
  ) {
    return scheduleEgg
      .withStartTime(
        convertToTimeZone(scheduleEgg.getStartTime(), fromOffset, toOffset)
      )
      .withEndTime(
        convertToTimeZone(scheduleEgg.getEndTime(), fromOffset, toOffset)
      );
  }

  private LocalTime convertToSystemTimeZone(LocalTime time) {
    ZoneOffset userOffset = zoneId.getRules().getOffset(clock.instant());
    ZoneOffset systemOffset = clock
      .getZone()
      .getRules()
      .getOffset(clock.instant());
    return convertToTimeZone(time, userOffset, systemOffset);
  }

  private LocalTime convertToTimeZone(
    LocalTime time,
    ZoneOffset fromOffset,
    ZoneOffset toOffset
  ) {
    return time
      .atOffset(fromOffset)
      .withOffsetSameInstant(toOffset)
      .toLocalTime();
  }
}
