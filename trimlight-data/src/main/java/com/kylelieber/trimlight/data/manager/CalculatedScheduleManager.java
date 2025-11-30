package com.kylelieber.trimlight.data.manager;

import com.google.common.collect.ImmutableList;
import com.kylelieber.trimlight.data.models.CalculatedSchedule;
import com.kylelieber.trimlight.data.models.CalculatedScheduleDateRange;
import com.kylelieber.trimlight.data.models.Location;
import com.kylelieber.trimlight.data.models.Schedule;
import com.kylelieber.trimlight.data.models.ScheduleTime;
import de.focus_shift.jollyday.core.Holiday;
import de.focus_shift.jollyday.core.HolidayCalendar;
import de.focus_shift.jollyday.core.HolidayManager;
import de.focus_shift.jollyday.core.ManagerParameters;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Year;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@ApplicationScoped
public class CalculatedScheduleManager {

  private final ScheduleManager scheduleManager;
  private final LocationManager locationManager;

  @Inject
  public CalculatedScheduleManager(
    ScheduleManager scheduleManager,
    LocationManager locationManager
  ) {
    this.scheduleManager = scheduleManager;
    this.locationManager = locationManager;
  }

  public List<CalculatedSchedule> getAllSchedules() {
    return scheduleManager
      .getAllSchedules()
      .stream()
      .map(schedule ->
        calculate(schedule, locationManager.getLocation(schedule.getDeviceId()))
      )
      .collect(ImmutableList.toImmutableList());
  }

  public List<CalculatedSchedule> getSchedulesByDeviceId(String deviceId) {
    Location location = locationManager.getLocation(deviceId);

    return scheduleManager
      .getSchedulesByDeviceId(deviceId)
      .stream()
      .map(schedule -> calculate(schedule, location))
      .collect(ImmutableList.toImmutableList());
  }

  public Optional<CalculatedSchedule> getActiveSchedule(
    String deviceId,
    LocalDateTime time
  ) {
    return getSchedulesByDeviceId(deviceId)
      .stream()
      .filter(CalculatedSchedule::isEnabled)
      .filter(schedule -> schedule.isActiveAt(time))
      .min(Comparator.comparing(CalculatedSchedule::getStartTime));
  }

  public Optional<CalculatedSchedule> getScheduleById(long scheduleId) {
    return scheduleManager
      .getScheduleById(scheduleId)
      .map(entity ->
        calculate(entity, locationManager.getLocation(entity.getDeviceId()))
      );
  }

  private CalculatedSchedule calculate(Schedule schedule, Location location) {
    LocalTime startTime = calculateTime(schedule.getStartTime(), location);
    LocalTime endTime = calculateTime(schedule.getEndTime(), location);

    Optional<CalculatedScheduleDateRange> calculatedRange = calculateDateRange(schedule, location);

    return CalculatedSchedule
      .builder()
      .setDeviceId(location.getDeviceId())
      .setScheduleId(schedule.getScheduleId())
      .setStartTime(startTime)
      .setEndTime(endTime)
      .setEffectId(schedule.getEffectId())
      .setEnabled(schedule.isEnabled())
      .setDateRange(calculatedRange)
      .build();
  }

  private LocalTime calculateTime(
    ScheduleTime scheduleTime,
    Location location
  ) {
    return switch (scheduleTime.getType()) {
      case SPECIFIC_TIME -> scheduleTime.getTime().orElseThrow();
      case SUNRISE -> location
        .getSunrise()
        .plus(scheduleTime.getRelativeTime());
      case SUNSET -> location.getSunset().plus(scheduleTime.getRelativeTime());
    };
  }

  private Optional<CalculatedScheduleDateRange> calculateDateRange(Schedule schedule, Location location) {
    return schedule
      .getDateRange()
      .flatMap(range ->
        range
          .getHoliday()
          .flatMap(holidayName ->
            getHolidayRange(
              location,
              holidayName,
              range.getRelativeStartDays(),
              range.getRelativeEndDays()
            )
          )
          .or(() ->
            range
              .getStartDate()
              .flatMap(startDate ->
                range
                  .getEndDate()
                  .map(endDate ->
                    CalculatedScheduleDateRange
                      .builder()
                      .setStartDate(startDate)
                      .setEndDate(endDate)
                      .build()
                  )
              )
          )
      );
  }

  private Optional<CalculatedScheduleDateRange> getHolidayRange(
    Location location,
    String holidayName,
    long relativeStart,
    long relativeEnd
  ) {
    return getHoliday(location, holidayName)
      .map(holidayDate ->
        CalculatedScheduleDateRange
          .builder()
          .setStartDate(holidayDate.minusDays(relativeStart))
          .setEndDate(holidayDate.plusDays(relativeEnd))
          .build()
      );
  }

  private Optional<LocalDate> getHoliday(
    Location location,
    String holidayName
  ) {
    HolidayManager usHolidayManager = HolidayManager.getInstance(
      ManagerParameters.create(HolidayCalendar.UNITED_STATES)
    );

    Set<Holiday> holidays = usHolidayManager.getHolidays(Year.now(location.getTimezone()));

    return holidays
      .stream()
      .filter(h -> h.getPropertiesKey().equalsIgnoreCase(holidayName))
      .map(Holiday::getDate)
      .findFirst();
  }
}
