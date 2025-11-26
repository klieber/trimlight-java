package com.kylelieber;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.kylelieber.trimlight.edge.client.TrimlightEdgeClient;
import com.kylelieber.trimlight.edge.client.models.EdgeDeviceList;
import com.kylelieber.trimlight.edge.client.models.EdgeDeviceListRequest;
import com.kylelieber.trimlight.edge.client.models.EdgeDeviceListResponse;
import com.kylelieber.trimlight.edge.client.models.EdgeDeviceSummary;
import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.instancio.Instancio;
import org.junit.jupiter.api.Test;

@QuarkusTest
class TrimlightResourceTest {

  @InjectMock
  @RestClient
  private TrimlightEdgeClient trimlightClient;

  @Test
  void testGetDevices() {
    EdgeDeviceListResponse devicesResponse = EdgeDeviceListResponse
      .builder()
      .setCode(0)
      .setDesc("success")
      .setPayload(
        EdgeDeviceList
          .builder()
          .setTotal(1)
          .setCurrent(1)
          .addData(Instancio.create(EdgeDeviceSummary.class))
          .build()
      )
      .build();

    when(trimlightClient.getDevices(any(EdgeDeviceListRequest.class)))
      .thenReturn(devicesResponse);

    EdgeDeviceListResponse actual = given()
      .when()
      .get("/trimlight/edge/devices")
      .then()
      .statusCode(200)
      .extract()
      .response()
      .as(EdgeDeviceListResponse.class);

    assertThat(actual).isEqualTo(devicesResponse);
  }
}
