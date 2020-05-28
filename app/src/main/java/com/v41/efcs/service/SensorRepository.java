package com.v41.efcs.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.v41.efcs.model.entity.SensorData;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Implémentation de repository de Sensor à partir de données JSON
 * @see SensorData
 */
public class SensorRepository implements Repository<SensorData> {
  private String json;

  public SensorRepository(String input) {
    json = input;
  }

  private ArrayList<SensorData> getAll() {
    ObjectMapper mapper = new ObjectMapper();
    ArrayList<SensorData> results = null;

    try {
      Class<SensorData[]> arrayofResults = (Class<SensorData[]>) Class.forName("[L" + SensorData.class.getName() + ";");
      SensorData[] resultsAsArray = mapper.readValue(json, arrayofResults);
      results = new ArrayList<>(Arrays.asList(resultsAsArray));
    } catch (ClassNotFoundException | IOException e) {
      e.printStackTrace();
    }
    return results;
  }

  @Override
  public boolean insert(SensorData data) {
    return false;
  }

  @Override
  public SensorData find(long id) {
    for (SensorData sensorData : getAll()) {
      if (sensorData.getId() == id)
        return sensorData;
    }
    return null;
  }

  @Override
  public SensorData findLast() {
    return null;
  }

  @Override
  public boolean save(SensorData obj) {
    return false;
  }

  @Override
  public boolean delete(SensorData obj) {
    return false;
  }

  @Override
  public boolean delete(long id) {
    return false;
  }
}
