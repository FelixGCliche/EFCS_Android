package com.v41.efcs.model.entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class SensorValue implements Parcelable, Comparable<SensorValue> {
  @JsonProperty("timestamp")
  long timeStamp;

  @JsonProperty("value")
  double value;

  @JsonCreator
  public SensorValue(@JsonProperty("timestamp") long timestamp,
                     @JsonProperty("value") double value)
  {
    this.value= value;
    this.timeStamp = timestamp;
  }

  public SensorValue(Parcel parcel)
  {
    this(parcel.readLong(), parcel.readDouble());
  }

  @JsonProperty("timestamp")
  public long getTimeStamp()
  {
    return timeStamp;
  }
  @JsonProperty("value")
  public double getValue()
  {
    return value;
  }

  @Override
  public int describeContents() {
    return 0;
  }

  @Override
  public void writeToParcel(Parcel parcel, int i)
  {
    parcel.writeLong(timeStamp);
    parcel.writeDouble(value);
  }

  public static final Parcelable.Creator<SensorValue> CREATOR
          = new Parcelable.Creator<SensorValue>() {
    public SensorValue createFromParcel(Parcel in) {
      return new SensorValue(in);
    }

    public SensorValue[] newArray(int size) {
      return new SensorValue[size];
    }
  };

  @Override
  public int compareTo(SensorValue o) {
    if (value == o.value)
      return 0;
    else if (value > o.value)
      return 1;
    else
      return -1;
  }
}
