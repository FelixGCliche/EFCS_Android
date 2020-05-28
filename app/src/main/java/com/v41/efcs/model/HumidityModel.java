package com.v41.efcs.model;

import com.v41.efcs.model.entity.HumidityZone;
import com.v41.efcs.model.exception.InvalidHighLimitException;

public interface HumidityModel {
  HumidityZone getHumidityZone(Zone zone);
  Zone getSelectedZone();
  int getSelectedZoneIndex();
  void setSelectedZone(Zone zone);
  double getUpperLimit(Zone zone);
  double getLowerLimit(Zone zone);
  int getCountInZone(Zone zone);
  void setCurrentZoneHighLimit(double newLimit) throws InvalidHighLimitException;
  void setCurrentZoneLowLimit(double newLimit);
  double getHighestValue();
}
