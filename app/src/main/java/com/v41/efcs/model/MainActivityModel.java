package com.v41.efcs.model;

import com.v41.efcs.model.entity.SensorData;
import com.v41.efcs.service.AsyncNetworkCall;
import com.v41.efcs.service.Repository;
import com.v41.efcs.service.OnEventListener;

/**
 * Modèle de MainActivity
 */
public class MainActivityModel implements IModel {
  private Repository<SensorData> sensorRepository;

  public MainActivityModel() {
  }

  /**
   * Permet de créer une repository de Sensor depuis un appel réseau async
   * @param url URL de l'appel réseau
   */
  public void initRepository(String url) {
    AsyncNetworkCall asyncNetworkCall = new AsyncNetworkCall(new OnEventListener<Repository<SensorData>>() {
      @Override
      public void onSuccess(Repository<SensorData> result) {
        sensorRepository = result;
      }

      @Override
      public void onFailure(Exception e) {
        e.printStackTrace();
        sensorRepository = null;
      }
    });
    asyncNetworkCall.execute(url);
  }

  public Repository<SensorData> getRepository() {
    return sensorRepository;
  }
}
