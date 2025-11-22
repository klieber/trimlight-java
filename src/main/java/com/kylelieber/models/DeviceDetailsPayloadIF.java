package com.kylelieber.models;

import com.kylelieber.models.style.BuilderStyle;
import java.util.List;
import org.immutables.value.Value.Immutable;

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
