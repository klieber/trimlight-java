package com.kylelieber.trimlight.edge.client.models;

import com.kylelieber.trimlight.style.BuilderStyle;
import java.util.List;
import org.immutables.value.Value.Immutable;

@Immutable
@BuilderStyle
public interface EdgeDeviceIF {
  String getName();

  int getSwitchState();

  int getConnectivity();

  int getState();

  int getColorOrder();

  int getIc();

  List<EdgePort> getPorts();

  String getFwVersionName();

  List<EdgeEffect> getEffects();

  EdgeCombinedEffect getCombinedEffect();

  List<EdgeDailySchedule> getDaily();

  List<EdgeCalendar> getCalendar();

  EdgeCurrentEffect getCurrentEffect();

  List<EdgeOverlayEffect> getOverlayEffects();

  EdgeCurrentDateTime getCurrentDatetime();
}
