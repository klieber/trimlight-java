package com.kylelieber.trimlight.data.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "location")
public class LocationEntity extends PanacheEntity {

  public String deviceId;
  public String postalCode;
  public String country;
  public double latitude;
  public double longitude;
  public String timezone;
}
