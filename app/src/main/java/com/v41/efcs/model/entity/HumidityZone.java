package com.v41.efcs.model.entity;

import com.v41.efcs.model.Zone;

import java.util.ArrayList;

public class HumidityZone {
  private Zone id;
  private double lowLimit;
  private double highLimit;
  private ArrayList<SensorValue> datas;

  public HumidityZone(Zone _id) {
    id = _id;
    datas = new ArrayList<SensorValue>();
  }

  /**
   * Ajoute une donnée du senseur dans la zone
   * @param value
   */
  public void addSensorValue(SensorValue value) {
    datas.add(value);
  }


  public Zone getId() {
    return id;
  }

  public double getLowLimit() {
    return lowLimit;
  }
  public void setLowLimit(double lowLimit) {
    this.lowLimit = lowLimit;
  }

  public double getHighLimit() {
    return highLimit;
  }
  public void setHighLimit(double highLimit) {
    this.highLimit = highLimit;
  }

  public int getCount() {
    return datas.size();
  }

  /**
   * Vide les données de la zone
   */
  public void clearData() {
    datas.clear();
  }


}
