package com.kylelieber.trimlight.edge.client.models;

import com.kylelieber.trimlight.edge.client.style.BuilderStyle;
import org.immutables.value.Value.Immutable;

import java.util.List;

@Immutable
@BuilderStyle
public interface DeviceDetailsPayloadIF {
  String getName();

  int getSwitchState();

  int getConnectivity();

  int getState();

  int getColorOrder();

  int getIc();

  List<Port> getPorts();

  String getFwVersionName();

  List<Effect> getEffects();

  CombinedEffect getCombinedEffect();

  List<DailySchedule> getDaily();

  List<Calendar> getCalendar();

  CurrentEffect getCurrentEffect();

  List<OverlayEffect> getOverlayEffects();

  CurrentDateTime getCurrentDatetime();
}
