package com.kylelieber.trimlight.service;

import com.kylelieber.trimlight.data.manager.CalculatedScheduleManager;
import com.kylelieber.trimlight.data.models.CalculatedSchedule;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import java.util.List;
import java.util.Optional;

@Path("/trimlight")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class CalculatedScheduleResource {

  private final CalculatedScheduleManager scheduleManager;

  @Inject
  public CalculatedScheduleResource(CalculatedScheduleManager scheduleManager) {
    this.scheduleManager = scheduleManager;
  }

  @GET
  @Path("/schedule/calc")
  public List<CalculatedSchedule> getAllSchedules() {
    return scheduleManager.getAllSchedules();
  }

  @GET
  @Path("/schedule/calc/{scheduleId}")
  public Optional<CalculatedSchedule> getScheduleById(
    @PathParam("scheduleId") long scheduleId
  ) {
    return scheduleManager.getScheduleById(scheduleId);
  }

  @GET
  @Path("/schedule/calc/device/{deviceId}")
  public List<CalculatedSchedule> getScheduleByDeviceId(
    @PathParam("deviceId") String deviceId
  ) {
    return scheduleManager.getSchedulesByDeviceId(deviceId);
  }
}
