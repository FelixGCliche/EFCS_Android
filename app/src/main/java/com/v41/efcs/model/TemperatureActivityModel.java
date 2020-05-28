package com.v41.efcs.model;

public class TemperatureActivityModel implements IModel, TemperatureModel {

  private double lowLimit;
  private double highLimit;

  public TemperatureActivityModel() {
    lowLimit = 15;
    highLimit = 30;
  }

  @Override
  public double getLowLimit() {
    return lowLimit;
  }

  @Override
  public void setLowLimit(double lowLimit) {
    this.lowLimit = lowLimit;
  }

  @Override
  public double getHighLimit() {
    return highLimit;
  }

  @Override
  public void setHighLimit(double highLimit) {
    this.highLimit = highLimit;
  }
}
