package com.v41.efcs.model.entity;

import android.annotation.SuppressLint;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

public class SensorData implements Parcelable {
  public static final int TEMPERATURE_ID = 1;
  public static final int HUMIDITY_ID = 2;

  @JsonProperty("id")
  long id = 0;

  @JsonProperty("datas")
  SensorValue[] values;


  public SensorData(Parcel parcel)
  {
    this(parcel.readLong(), parcel.createTypedArray(SensorValue.CREATOR));
  }

  @JsonCreator
  public SensorData(@JsonProperty("id") long id,
                    @JsonProperty("datas") SensorValue[] values)
  {
    this.values= values;
    this.id= id;
  }

  @JsonProperty("datas")
  public SensorValue[] getValues()
  {
    return values;
  }
  @JsonProperty("id")
  public long getId()
  {
    return id;
  }

  @Override
  public int describeContents() {
    return 0;
  }

  @Override
  public void writeToParcel(Parcel parcel, int i)
  {
    parcel.writeLong(id);
    parcel.writeTypedArray(values,i);
  }

  public static final Parcelable.Creator<SensorData> CREATOR
          = new Parcelable.Creator<SensorData>() {
    public SensorData createFromParcel(Parcel in) {
      return new SensorData(in);
    }

    public SensorData[] newArray(int size) {
      return new SensorData[size];
    }
  };
}