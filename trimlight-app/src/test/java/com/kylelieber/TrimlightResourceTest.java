package com.kylelieber;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.kylelieber.trimlight.edge.client.TrimlightEdgeClient;
import com.kylelieber.trimlight.edge.client.models.DeviceSummary;
import com.kylelieber.trimlight.edge.client.models.DevicesPayload;
import com.kylelieber.trimlight.edge.client.models.DevicesRequest;
import com.kylelieber.trimlight.edge.client.models.DevicesResponse;
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
    DevicesResponse devicesResponse = DevicesResponse
      .builder()
      .setCode(0)
      .setDesc("success")
      .setPayload(
        DevicesPayload
          .builder()
          .setTotal(1)
          .setCurrent(1)
          .addData(Instancio.create(DeviceSummary.class))
          .build()
      )
      .build();

    when(trimlightClient.getDevices(any(DevicesRequest.class)))
      .thenReturn(devicesResponse);

    DevicesResponse actual = given()
      .when()
      .get("/trimlight/devices")
      .then()
      .statusCode(200)
      .extract()
      .response()
      .as(DevicesResponse.class);

    assertThat(actual).isEqualTo(devicesResponse);
  }
}
