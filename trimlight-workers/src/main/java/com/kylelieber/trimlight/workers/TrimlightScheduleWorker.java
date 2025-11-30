package com.kylelieber.trimlight.workers;

import com.kylelieber.trimlight.data.manager.CalculatedScheduleManager;
import com.kylelieber.trimlight.data.manager.LocationManager;
import com.kylelieber.trimlight.data.manager.TrimlightManager;
import com.kylelieber.trimlight.data.models.CalculatedSchedule;
import com.kylelieber.trimlight.data.models.DetailedDevice;
import com.kylelieber.trimlight.data.models.Location;
import io.quarkus.scheduler.Scheduled;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Clock;
import java.time.LocalDateTime;

@ApplicationScoped
public class TrimlightScheduleWorker {

  private static final Logger LOG = LoggerFactory.getLogger(
    TrimlightScheduleWorker.class
  );

  private final TrimlightManager trimlightManager;
  private final CalculatedScheduleManager scheduleManager;
  private final LocationManager locationManager;
  private final Clock clock;

  @Inject
  public TrimlightScheduleWorker(
    TrimlightManager trimlightManager,
    CalculatedScheduleManager scheduleManager,
    LocationManager locationManager,
    Clock clock
  ) {
    this.trimlightManager = trimlightManager;
    this.scheduleManager = scheduleManager;
    this.locationManager = locationManager;
    this.clock = clock;
  }

  @Scheduled(every = "60s")
  // Runs every 10 seconds
  public void syncTrimlightSchedule() {
    LOG.info("Syncing trimlight schedules");
    trimlightManager
      .getDevices()
      .stream()
      .findFirst()
      .flatMap(device -> trimlightManager.getDeviceById(device.getDeviceId()))
      .ifPresent(this::syncDevice);
  }

  private void syncDevice(DetailedDevice device) {
    LOG.info(
      "Syncing device: {}",
      device
    );
    Location location = locationManager.getLocation(device.getDeviceId());

    scheduleManager
      .getActiveSchedule(device.getDeviceId(), LocalDateTime.now(location.getTimezone()))
      .ifPresentOrElse(
        schedule -> activateSchedule(device, schedule),
        () -> turnOffDevice(device)
      );
  }

  private void activateSchedule(DetailedDevice device, CalculatedSchedule schedule) {
    LOG.info("Active Schedule: {}", schedule);
    device
      .getCurrentEffect()
      .ifPresent(effect -> {
        if (effect.getEffectId() != schedule.getEffectId()) {
          LOG.info(
            "Changing device {} ({}) effect to {}",
            device.getName(),
            device.getDeviceId(),
            schedule.getEffectId()
          );
          trimlightManager.changeEffect(
            device.getDeviceId(),
            schedule.getEffectId()
          );
        }
      });

    if (device.isOff()) {
      LOG.info(
        "Turning on device {} ({})",
        device.getName(),
        device.getDeviceId()
      );
      trimlightManager.on(device.getDeviceId());
    }
  }

  private void turnOffDevice(DetailedDevice device) {
    if (device.isOn()) {
      LOG.info(
        "Turning off device {} ({})",
        device.getName(),
        device.getDeviceId()
      );
      trimlightManager.off(device.getDeviceId());
    }
  }
}
