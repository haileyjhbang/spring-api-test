package com.boot.api.practice;

public class Singleton {

  private Singleton() {
  }

  public static class SingletonHelper {
    private static final Singleton singleton = new Singleton();
  }

  public static Singleton getInstance() {
    return SingletonHelper.singleton;
  }

}
