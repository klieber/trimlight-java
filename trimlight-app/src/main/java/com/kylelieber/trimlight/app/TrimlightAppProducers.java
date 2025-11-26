package com.kylelieber.trimlight.app;

import jakarta.enterprise.inject.Produces;
import jakarta.inject.Singleton;
import java.time.Clock;

public class TrimlightAppProducers {

  @Produces
  @Singleton
  Clock clock() {
    return Clock.systemUTC();
  }
}
