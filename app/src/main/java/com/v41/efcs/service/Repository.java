package com.v41.efcs.service;

import java.util.ArrayList;

/**
 * Interface de repository générique
 * @param <T> Type de données contenues dans la repository
 */
public interface Repository<T> {
  boolean insert(T obj);
  T find(long id);
  T findLast();
  boolean save(T obj);
  boolean delete(T obj);
  boolean delete(long id);
}
