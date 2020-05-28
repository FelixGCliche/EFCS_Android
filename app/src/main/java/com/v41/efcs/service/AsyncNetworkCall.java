package com.v41.efcs.service;

import android.os.AsyncTask;

import com.v41.efcs.model.entity.SensorData;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Appel réseau async à un API REST
 * @see "https://gist.github.com/cesarferreira/ef70baa8d64f9753b4da"
 */
public class AsyncNetworkCall extends AsyncTask<String, Void, Repository<SensorData>> {

  private OnEventListener<Repository<SensorData>> callback;
  private Exception exception;

  public AsyncNetworkCall(OnEventListener<Repository<SensorData>> _callback) {
    callback = _callback;
  }

  /**
   * Génère une repoistory à partir de données JSON
   * @param strings URL de l'appel réseau
   * @return Repository des données JSON
   */
  @Override
  protected Repository<SensorData> doInBackground(String... strings) {
    String url = strings[0];
    Repository<SensorData> repository = null;

    OkHttpClient client = new OkHttpClient();
    Request request = new Request.Builder()
            .url(url)
            .build();
    Response response = null;
    try {
      response = client.newCall(request).execute();
    } catch (IOException e) {
      exception = e;
    }

    if (response.isSuccessful()) {
      try {
        repository = new SensorRepository(response.body().string());
      } catch (IOException e) {
        exception = e;
      }
    }
    response.close();
    return repository;
  }

  /**
   * Gestion du callback OnEventListener
   * @param result Repository des données JSON
   */
  @Override
  protected void onPostExecute(Repository<SensorData> result) {
    if (callback != null) {
      if (exception == null) {
        callback.onSuccess(result);
      } else {
        callback.onFailure(exception);
      }
    }
  }
}
