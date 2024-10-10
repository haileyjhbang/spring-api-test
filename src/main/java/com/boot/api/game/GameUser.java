package com.boot.api.game;

public class GameUser {
  private String tag;
  private String username;
  private int id;
  private int win;
  private int lose;

  public GameUser() {
  }
  public String getTag() {
      return tag;
  }
  public String getUsername() {
      return username;
  }
  public int getId() {
    return id;
  }
  public int getWin() {
      return win;
  }
  public int getLose() {
      return lose;
  }
}
