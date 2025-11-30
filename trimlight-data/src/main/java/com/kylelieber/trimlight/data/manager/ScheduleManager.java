package com.kylelieber.trimlight.data.manager;

import com.google.common.collect.ImmutableList;
import com.kylelieber.trimlight.data.entity.ScheduleEntity;
import com.kylelieber.trimlight.data.models.Schedule;
import com.kylelieber.trimlight.data.models.ScheduleDateRange;
import com.kylelieber.trimlight.data.models.ScheduleDateType;
import com.kylelieber.trimlight.data.models.ScheduleEgg;
import com.kylelieber.trimlight.data.models.ScheduleTime;
import com.kylelieber.trimlight.data.repository.ScheduleRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.time.Duration;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class ScheduleManager {

  private final ScheduleRepository scheduleRepository;

  @Inject
  public ScheduleManager(ScheduleRepository scheduleRepository) {
    this.scheduleRepository = scheduleRepository;
  }

  @Transactional
  public Schedule createSchedule(ScheduleEgg newSchedule) {
    //    boolean hasConflicts = getAllSchedules()
    //      .stream()
    //      .anyMatch(newSchedule::hasConflict);
    //
    //    if (hasConflicts) {
    //      throw new TrimlightException(
    //        "Schedule conflicts with existing schedules"
    //      );
    //    }

    ScheduleEntity entity = new ScheduleEntity();
    entity.deviceId = newSchedule.getDeviceId();
    entity.startType = newSchedule.getStartTime().getType();
    entity.endType = newSchedule.getEndTime().getType();
    newSchedule
      .getStartTime()
      .getTime()
      .ifPresent(startTime -> entity.startTime = startTime);
    newSchedule
      .getEndTime()
      .getTime()
      .ifPresent(endTime -> entity.endTime = endTime);
    entity.relativeStartTimeInMillis =
      newSchedule.getStartTime().getRelativeTime().toMillis();
    entity.relativeEndTimeInMillis =
      newSchedule.getEndTime().getRelativeTime().toMillis();
    entity.effectId = newSchedule.getEffectId();
    entity.enabled = newSchedule.isEnabled();

    newSchedule
      .getDateRange()
      .ifPresent(dateRange -> {
        entity.dateType = dateRange.getDateType();
        dateRange
          .getStartDate()
          .ifPresent(startDate -> entity.startDate = startDate);
        dateRange
          .getEndDate()
          .ifPresent(endDate -> entity.endDate = endDate);
        dateRange.getHoliday().ifPresent(holiday -> entity.holiday = holiday);
        entity.relativeStartDateInDays = dateRange.getRelativeStartDays();
        entity.relativeEndDateInDays = dateRange.getRelativeEndDays();
      });
    scheduleRepository.persist(entity);

    return Schedule
      .builder()
      .from(newSchedule)
      .setScheduleId(entity.id)
      .build();
  }

  public List<Schedule> getAllSchedules() {
    return scheduleRepository
      .findAll()
      .list()
      .stream()
      .map(this::toSchedule)
      .collect(ImmutableList.toImmutableList());
  }

  public List<Schedule> getSchedulesByDeviceId(String deviceId) {
    return scheduleRepository
      .findByDeviceId(deviceId)
      .stream()
      .map(this::toSchedule)
      .collect(ImmutableList.toImmutableList());
  }

  public Optional<Schedule> getScheduleById(long scheduleId) {
    return Optional
      .ofNullable(scheduleRepository.findById(scheduleId))
      .map(this::toSchedule);
  }

  private Schedule toSchedule(ScheduleEntity entity) {
    ScheduleTime startTime;
    if (entity.startTime == null) {
      startTime =
        ScheduleTime
          .builder()
          .setType(entity.startType)
          .setRelativeTime(Duration.ofMillis(entity.relativeStartTimeInMillis))
          .build();
    } else {
      startTime =
        ScheduleTime
          .builder()
          .setType(entity.startType)
          .setTime(entity.startTime)
          .build();
    }

    ScheduleTime endTime;
    if (entity.endTime == null) {
      endTime =
        ScheduleTime
          .builder()
          .setType(entity.endType)
          .setRelativeTime(Duration.ofMillis(entity.relativeEndTimeInMillis))
          .build();
    } else {
      endTime =
        ScheduleTime
          .builder()
          .setType(entity.endType)
          .setTime(entity.endTime)
          .build();
    }
    Optional<ScheduleDateRange> dateRange = Optional.empty();

    if (entity.dateType == ScheduleDateType.SPECIFIC_RANGE) {
      dateRange =
        Optional.of(
          ScheduleDateRange
            .builder()
            .setDateType(entity.dateType)
            .setStartDate(entity.startDate)
            .setEndDate(entity.endDate)
            .build()
        );
    } else if (entity.dateType == ScheduleDateType.HOLIDAY_RANGE) {
      dateRange =
        Optional.of(
          ScheduleDateRange
            .builder()
            .setDateType(entity.dateType)
            .setHoliday(entity.holiday)
            .setRelativeStartDays(entity.relativeStartDateInDays)
            .setRelativeEndDays(entity.relativeEndDateInDays)
            .build()
        );
    }
    return Schedule
      .builder()
      .setScheduleId(entity.id)
      .setDeviceId(entity.deviceId)
      .setScheduleId(entity.id)
      .setDateRange(dateRange)
      .setStartTime(startTime)
      .setEndTime(endTime)
      .setEffectId(entity.effectId)
      .setEnabled(entity.enabled)
      .build();
  }
}
