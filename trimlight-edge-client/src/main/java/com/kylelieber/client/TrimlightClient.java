package com.kylelieber.client;

import com.kylelieber.models.DeviceDetailsRequest;
import com.kylelieber.models.DeviceDetailsResponse;
import com.kylelieber.models.DeviceSwitchStateRequest;
import com.kylelieber.models.DeviceSwitchStateResponse;
import com.kylelieber.models.DevicesRequest;
import com.kylelieber.models.DevicesResponse;
import com.kylelieber.models.OverlayEffectsRequest;
import com.kylelieber.models.OverlayEffectsResponse;
import com.kylelieber.models.PreviewEffectRequest;
import com.kylelieber.models.PreviewEffectResponse;
import com.kylelieber.models.UpdateCombinedEffectRequest;
import com.kylelieber.models.UpdateCombinedEffectResponse;
import com.kylelieber.models.ViewEffectRequest;
import com.kylelieber.models.ViewEffectResponse;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import org.eclipse.microprofile.rest.client.annotation.RegisterClientHeaders;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@RegisterRestClient(baseUri = "https://trimlight.ledhue.com/trimlight")
@RegisterClientHeaders(TrimlightClientHeadersFactory.class)
@Path("/v1/oauth/resources")
public interface TrimlightClient {
  @GET
  @Path("/devices")
  DevicesResponse getDevices(DevicesRequest request);

  @POST
  @Path("/device/get")
  DeviceDetailsResponse getDevice(DeviceDetailsRequest request);

  @POST
  @Path("/device/update")
  DeviceSwitchStateResponse setDeviceSwitchState(
    DeviceSwitchStateRequest request
  );

  @POST
  @Path("/device/effect/preview")
  PreviewEffectResponse previewEffect(PreviewEffectRequest request);

  @POST
  @Path("/device/effect/view")
  ViewEffectResponse viewEffect(ViewEffectRequest request);

  @POST
  @Path("/device/combined-effect/save")
  UpdateCombinedEffectResponse updateCombinedEffect(
    UpdateCombinedEffectRequest request
  );

  @POST
  @Path("/device/effect/overlay")
  OverlayEffectsResponse addOverlayEffects(OverlayEffectsRequest request);
}
