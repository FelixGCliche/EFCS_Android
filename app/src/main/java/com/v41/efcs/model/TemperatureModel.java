package com.v41.efcs.model;

public interface TemperatureModel {
  double getLowLimit();

  double getHighLimit();

  void setLowLimit(double lowLimit);

  void setHighLimit(double highLimit);
}
