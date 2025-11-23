package com.kylelieber.trimlight.client.config;

import jakarta.inject.Inject;
import jakarta.ws.rs.core.MultivaluedHashMap;
import jakarta.ws.rs.core.MultivaluedMap;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.rest.client.ext.ClientHeadersFactory;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class TrimlightClientHeadersFactory implements ClientHeadersFactory {

  private final String clientId;
  private final String clientSecret;

  @Inject
  public TrimlightClientHeadersFactory(
    @ConfigProperty(name = "trimlight.client.id") String clientId,
    @ConfigProperty(name = "trimlight.client.secret") String clientSecret
  ) {
    this.clientId = clientId;
    this.clientSecret = clientSecret;
  }

  @Override
  public MultivaluedMap<String, String> update(
    MultivaluedMap<String, String> incomingHeaders,
    MultivaluedMap<String, String> outgoingHeaders
  ) {
    long timestamp = System.currentTimeMillis();
    String accessToken = calculateAccessToken(
      clientId,
      clientSecret,
      timestamp
    );

    MultivaluedMap<String, String> result = new MultivaluedHashMap<>();
    result.add("Accept", "application/json");
    result.add("authorization", accessToken);
    result.add("S-ClientId", clientId);
    result.add("S-Timestamp", String.valueOf(timestamp));
    return result;
  }

  private String calculateAccessToken(
    String clientId,
    String clientSecret,
    long timestamp
  ) {
    try {
      String data = "Trimlight|%s|%s".formatted(clientId, timestamp);
      Mac mac = Mac.getInstance("HmacSHA256");
      SecretKeySpec secretKeySpec = new SecretKeySpec(
        clientSecret.getBytes(),
        "HmacSHA256"
      );
      mac.init(secretKeySpec);
      byte[] bytes = mac.doFinal(data.getBytes());
      Base64.Encoder encoder = Base64.getEncoder();
      return encoder.encodeToString(bytes);
    } catch (NoSuchAlgorithmException | InvalidKeyException e) {
      throw new RuntimeException(e);
    }
  }
}
