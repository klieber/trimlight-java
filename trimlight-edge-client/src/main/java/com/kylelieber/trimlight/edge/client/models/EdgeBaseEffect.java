package com.kylelieber.trimlight.edge.client.models;

import java.util.List;
import java.util.Optional;

public interface EdgeBaseEffect {
  int getCategory();

  int getMode();

  int getSpeed();

  int getBrightness();

  Optional<Integer> getPixelLen();

  Optional<Boolean> getReverse();

  List<EdgePixel> getPixels();
}
