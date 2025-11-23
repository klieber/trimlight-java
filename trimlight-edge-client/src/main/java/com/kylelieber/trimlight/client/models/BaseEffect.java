package com.kylelieber.trimlight.client.models;

import com.kylelieber.trimlight.client.models.Pixel;

import java.util.List;
import java.util.Optional;

public interface BaseEffect {
  int getCategory();

  int getMode();

  int getSpeed();

  int getBrightness();

  Optional<Integer> getPixelLen();

  Optional<Boolean> getReverse();

  List<Pixel> getPixels();
}
