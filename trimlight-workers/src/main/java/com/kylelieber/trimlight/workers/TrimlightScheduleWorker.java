package com.kylelieber.trimlight.workers;

import com.kylelieber.trimlight.data.manager.ScheduleManager;
import com.kylelieber.trimlight.data.manager.TrimlightManager;
import com.kylelieber.trimlight.data.models.DetailedDevice;
import com.kylelieber.trimlight.data.models.DeviceStatus;
import io.quarkus.scheduler.Scheduled;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.time.Clock;
import java.time.LocalTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ApplicationScoped
public class TrimlightScheduleWorker {

  private static final Logger LOG = LoggerFactory.getLogger(
    TrimlightScheduleWorker.class
  );

  private final TrimlightManager trimlightManager;
  private final ScheduleManager scheduleManager;
  private final Clock clock;

  @Inject
  public TrimlightScheduleWorker(
    TrimlightManager trimlightManager,
    ScheduleManager scheduleManager,
    Clock clock
  ) {
    this.trimlightManager = trimlightManager;
    this.scheduleManager = scheduleManager;
    this.clock = clock;
  }

  @Scheduled(every = "10s")
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
      "Syncing device: {} ({}) {}",
      device.getName(),
      device.getDeviceId(),
      device.getStatus()
    );
    LOG.info("Clock: {}", LocalTime.now(clock));
    scheduleManager
      .getActiveSchedule(LocalTime.now(clock))
      .ifPresentOrElse(
        schedule -> {
          LOG.info("Found active schedule: {}", schedule);
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

          if (device.getStatus() == DeviceStatus.OFF) {
            LOG.info(
              "Turning on device {} ({})",
              device.getName(),
              device.getDeviceId()
            );
            trimlightManager.on(device.getDeviceId());
          }
        },
        () -> {
          if (device.getStatus() == DeviceStatus.ON) {
            LOG.info(
              "Turning off device {} ({})",
              device.getName(),
              device.getDeviceId()
            );
            trimlightManager.off(device.getDeviceId());
          }
        }
      );
  }
}
