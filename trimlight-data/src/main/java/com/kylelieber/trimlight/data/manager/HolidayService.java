package com.kylelieber.trimlight.data.manager;

import com.kylelieber.trimlight.data.models.Location;
import de.focus_shift.jollyday.core.Holiday;
import de.focus_shift.jollyday.core.HolidayManager;
import de.focus_shift.jollyday.core.ManagerParameters;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.time.LocalDate;
import java.time.Year;
import java.util.Optional;
import java.util.Set;

@ApplicationScoped
public class HolidayService {

  private final HolidayManager holidayManager;

  @Inject
  public HolidayService() {
    this.holidayManager = HolidayManager.getInstance(
      ManagerParameters.create(
        HolidayService.class
          .getResource("/holidays/us-custom.xml")
      )
    );
  }

  public Optional<LocalDate> getHolidayDate(
    Location location,
    String holidayName
  ) {
    Set<Holiday> holidays = holidayManager.getHolidays(Year.now(location.getTimezone()));

    return holidays
      .stream()
      .filter(h -> h.getPropertiesKey().equalsIgnoreCase(holidayName))
      .map(Holiday::getDate)
      .findFirst();
  }
}
