package com.kylelieber.trimlight.service;

import com.kylelieber.trimlight.data.manager.ZonedScheduleManager;
import com.kylelieber.trimlight.data.models.Schedule;
import com.kylelieber.trimlight.data.models.ScheduleEgg;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import java.util.List;
import java.util.Optional;

@Path("/trimlight")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ScheduleResource {

  private final ZonedScheduleManager scheduleManager;

  @Inject
  public ScheduleResource(ZonedScheduleManager scheduleManager) {
    this.scheduleManager = scheduleManager;
  }

  @GET
  @Path("/schedule")
  public List<Schedule> getAllSchedules() {
    return scheduleManager.getAllSchedules();
  }

  @PUT
  @Path("/schedule")
  public Schedule createSchedule(ScheduleEgg scheduleEgg) {
    return scheduleManager.createSchedule(scheduleEgg);
  }

  @GET
  @Path("/schedule/{scheduleId}")
  public Optional<Schedule> getScheduleById(
    @PathParam("scheduleId") long scheduleId
  ) {
    return scheduleManager.getScheduleById(scheduleId);
  }
}
