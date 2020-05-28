package com.v41.efcs.model;

import androidx.annotation.NonNull;

import com.jjoe64.graphview.series.DataPoint;

public enum Zone {
  ZONE_1(0, "Zone 1"),
  ZONE_2(1, "Zone 2"),
  ZONE_3(2, "Zone 3"),
  ZONE_4(3, "Zone 4"),
  ZONE_5(4, "Zone 5");

  private String tag;
  private int index;

  Zone(int _index, String _tag) {
    index = _index;
    tag = _tag;
  }

  public int getIndex() {
    return index;
  }


  @NonNull
  @Override
  public String toString() {
    return tag;
  }
}
