package com.kylelieber.trimlight.edge.client;

import com.kylelieber.trimlight.edge.client.config.EdgeClientHeadersFactory;
import com.kylelieber.trimlight.edge.client.models.EdgeCombinedEffectRequest;
import com.kylelieber.trimlight.edge.client.models.EdgeDeviceListRequest;
import com.kylelieber.trimlight.edge.client.models.EdgeDeviceListResponse;
import com.kylelieber.trimlight.edge.client.models.EdgeDeviceRequest;
import com.kylelieber.trimlight.edge.client.models.EdgeDeviceResponse;
import com.kylelieber.trimlight.edge.client.models.EdgeDeviceSwitchStateRequest;
import com.kylelieber.trimlight.edge.client.models.EdgeOverlayEffectsRequest;
import com.kylelieber.trimlight.edge.client.models.EdgePreviewEffectRequest;
import com.kylelieber.trimlight.edge.client.models.EdgeResponse;
import com.kylelieber.trimlight.edge.client.models.EdgeViewEffectRequest;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import org.eclipse.microprofile.rest.client.annotation.RegisterClientHeaders;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@RegisterRestClient(baseUri = "https://trimlight.ledhue.com/trimlight")
@RegisterClientHeaders(EdgeClientHeadersFactory.class)
@Path("/v1/oauth/resources")
public interface TrimlightEdgeClient {
  @GET
  @Path("/devices")
  EdgeDeviceListResponse getDevices(EdgeDeviceListRequest request);

  @POST
  @Path("/device/get")
  EdgeDeviceResponse getDevice(EdgeDeviceRequest request);

  @POST
  @Path("/device/update")
  EdgeResponse setDeviceSwitchState(EdgeDeviceSwitchStateRequest request);

  @POST
  @Path("/device/effect/preview")
  EdgeResponse previewEffect(EdgePreviewEffectRequest request);

  @POST
  @Path("/device/effect/view")
  EdgeResponse viewEffect(EdgeViewEffectRequest request);

  @POST
  @Path("/device/combined-effect/save")
  EdgeResponse updateCombinedEffect(EdgeCombinedEffectRequest request);

  @POST
  @Path("/device/effect/overlay")
  EdgeResponse addOverlayEffects(EdgeOverlayEffectsRequest request);
}
