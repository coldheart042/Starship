package com.Starship;

/**
 * Created by Richard on 6/6/14.
 */
public class Ship {
  public int xCoord, yCoord, size, direction;
  public Ship(int x, int y, int spaces, int dir){
    super();
    xCoord = x -1;
    yCoord = y -1;
    size = spaces;
    direction = dir; //"north":0,"east":2,"south":4,"west":6
  }
}
