package com.kylelieber.trimlight.edge.client;

import com.kylelieber.trimlight.edge.client.config.TrimlightClientHeadersFactory;
import com.kylelieber.trimlight.edge.client.models.DeviceDetailsRequest;
import com.kylelieber.trimlight.edge.client.models.DeviceDetailsResponse;
import com.kylelieber.trimlight.edge.client.models.DeviceSwitchStateRequest;
import com.kylelieber.trimlight.edge.client.models.DeviceSwitchStateResponse;
import com.kylelieber.trimlight.edge.client.models.DevicesRequest;
import com.kylelieber.trimlight.edge.client.models.DevicesResponse;
import com.kylelieber.trimlight.edge.client.models.OverlayEffectsRequest;
import com.kylelieber.trimlight.edge.client.models.OverlayEffectsResponse;
import com.kylelieber.trimlight.edge.client.models.PreviewEffectRequest;
import com.kylelieber.trimlight.edge.client.models.PreviewEffectResponse;
import com.kylelieber.trimlight.edge.client.models.UpdateCombinedEffectRequest;
import com.kylelieber.trimlight.edge.client.models.UpdateCombinedEffectResponse;
import com.kylelieber.trimlight.edge.client.models.ViewEffectRequest;
import com.kylelieber.trimlight.edge.client.models.ViewEffectResponse;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import org.eclipse.microprofile.rest.client.annotation.RegisterClientHeaders;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@RegisterRestClient(baseUri = "https://trimlight.ledhue.com/trimlight")
@RegisterClientHeaders(TrimlightClientHeadersFactory.class)
@Path("/v1/oauth/resources")
public interface TrimlightEdgeClient {
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
