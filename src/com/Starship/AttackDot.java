package com.Starship;

/**
 * Created by Richard on 6/9/14.
 */
public class AttackDot {
  public int xCoord, yCoord;
  public Boolean isAHit;
  public AttackDot(int x, int y, Boolean hit){
    super();
    xCoord = x;
    yCoord = y -1;
    isAHit = hit;
  }
}
