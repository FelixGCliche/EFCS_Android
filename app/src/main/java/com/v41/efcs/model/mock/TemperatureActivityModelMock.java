package com.v41.efcs.model.mock;

import com.v41.efcs.model.IModel;
import com.v41.efcs.model.TemperatureModel;

public class TemperatureActivityModelMock implements IModel, TemperatureModel {

  private double lowLimit;
  private double highLimit;

  public TemperatureActivityModelMock(double _lowLimit, double _highLimit) {
    lowLimit = _lowLimit;
    highLimit = _highLimit;
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
}
