package com.kylelieber.trimlight.client.models;

import com.kylelieber.trimlight.client.models.Calendar;
import com.kylelieber.trimlight.client.models.CombinedEffect;
import com.kylelieber.trimlight.client.models.CurrentDateTime;
import com.kylelieber.trimlight.client.models.CurrentEffect;
import com.kylelieber.trimlight.client.models.DailySchedule;
import com.kylelieber.trimlight.client.models.Effect;
import com.kylelieber.trimlight.client.models.OverlayEffect;
import com.kylelieber.trimlight.client.models.Port;
import com.kylelieber.trimlight.client.style.BuilderStyle;
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
