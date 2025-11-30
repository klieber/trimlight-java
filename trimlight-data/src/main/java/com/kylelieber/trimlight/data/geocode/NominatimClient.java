package com.kylelieber.trimlight.data.geocode;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import java.util.List;

@Path("/search")
@RegisterRestClient(configKey = "nominatim")
public interface NominatimClient {

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  List<NominatimResponse> search(
    @QueryParam("q") String query,
    @QueryParam("format") String format,
    @QueryParam("limit") int limit,
    @QueryParam("addressdetails") int addressDetails
  );
}
