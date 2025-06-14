
package com.pelyvasbalazs.weather.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class City {
  @Id @GeneratedValue
  private Long id;
  private String name;

  protected City(){}
  public City(String name){ this.name = name; }

  public Long getId(){ return id; }
  public String getName(){ return name; }
}
