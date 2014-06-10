package com.Starship;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.ImageView;

import java.util.ArrayList;

/**
 * Created by Richard on 6/4/14.
 */
public class BoardView extends ImageView {
  private static Paint board;
  private static Paint line;
  private static Paint hit;
  public ArrayList<Ship> ships;
  public ArrayList<AttackDot> attackDots;
  public String[] letters = {"", "a", "b", "c", "d", "e", "f", "g", "h", "i", "j"};
  public String[] numbers = {"", "1","2","3","4","5","6","7","8","9","10"};

  public BoardView(Context context, AttributeSet attrs) {
    super(context, attrs);
    ships = new ArrayList<Ship>();
    attackDots = new ArrayList<AttackDot>();
    board = new Paint();
    board.setStrokeWidth(10);
    board.setColor(Color.BLUE);
    board.setStyle(Paint.Style.FILL_AND_STROKE);
    line = new Paint();
    line.setStrokeWidth(2);
    line.setColor(Color.WHITE);
    hit = new Paint();
    hit.setColor(Color.RED);
    hit.setStyle(Paint.Style.FILL_AND_STROKE);
    hit.setStrokeWidth(2);
  }

  @Override
  protected void onDraw(Canvas canvas){
    super.onDraw(canvas);
    //Draw the Defend Board:
    int totalWidth = this.getMeasuredWidth();
    int margin = totalWidth/4;
    int boardWidth = totalWidth/2;
    int cellSize = boardWidth / 11;

    // Board
    canvas.drawRect(margin, cellSize, margin + (boardWidth - cellSize), boardWidth, board);
    for(int i = 0; i < letters.length; i++){
      canvas.drawText(letters[i], margin - cellSize, (cellSize / 2) + (cellSize * i), line); // Letters and horiz lines
      canvas.drawLine(margin, cellSize + (cellSize * i), margin + (boardWidth - cellSize)-6, cellSize + (cellSize * i), line);

      canvas.drawText(numbers[i], margin - cellSize + (cellSize * i), cellSize / 2, line);
      canvas.drawLine(margin + (cellSize * i), cellSize, margin + (cellSize * i), cellSize + (boardWidth - cellSize)-6, line);
    }

    // Ships
    for(Ship ship : ships){
      int rawX = ship.xCoord;
      int rawY = ship.yCoord;
      int shipSize = ship.size;
      hit.setColor(Color.LTGRAY);
      if (ship.direction == 0){ //North
        canvas.drawCircle((margin - (cellSize / 2)) + (cellSize * rawX), (cellSize + (cellSize / 2)) + (cellSize * rawY), cellSize / 2, hit);
        canvas.drawRect((margin - cellSize) + (cellSize * rawX), (cellSize + (cellSize / 2)) + ((cellSize * rawY) - (cellSize * (shipSize - 1))), ((margin - cellSize) + (cellSize * rawX) + cellSize), (cellSize + (cellSize / 2)) + (cellSize * rawY), hit);
        canvas.drawCircle((margin - (cellSize / 2)) + (cellSize * rawX), (cellSize + (cellSize / 2)) + ((cellSize * rawY) - (cellSize * (shipSize - 1))), cellSize / 2, hit);
      }
      if (ship.direction == 2){ //East
        canvas.drawCircle((margin - (cellSize / 2)) + (cellSize * rawX), (cellSize + (cellSize / 2)) + (cellSize * rawY), cellSize / 2, hit);
        canvas.drawRect(((margin - cellSize) + (cellSize / 2) + (cellSize * rawX)), (cellSize * rawY) + cellSize, (margin - cellSize) - (cellSize / 2) + (cellSize * rawX) + (cellSize * shipSize), ((cellSize * rawY) + (cellSize * 2)), hit);
        canvas.drawCircle((margin - (cellSize / 2)) + (cellSize * rawX) + (cellSize * (shipSize - 1)), (cellSize + (cellSize / 2)) + (cellSize * rawY), cellSize / 2, hit);
      }
      if (ship.direction == 4){ //South
        canvas.drawCircle((margin - (cellSize / 2)) + (cellSize * rawX), (cellSize + (cellSize / 2)) + (cellSize * rawY), cellSize / 2, hit);
        canvas.drawRect((margin - cellSize) + (cellSize * rawX), (cellSize + (cellSize / 2)) + ((cellSize * rawY)), ((margin - cellSize) + (cellSize * rawX) + cellSize), (cellSize + (cellSize / 2)) + (cellSize * rawY) + (cellSize * (shipSize - 1)), hit);
        canvas.drawCircle((margin - (cellSize / 2)) + (cellSize * rawX), (cellSize + (cellSize / 2)) + (cellSize * rawY) + (cellSize * (shipSize - 1)), cellSize / 2, hit);
      }
      if (ship.direction == 6){ //West
        canvas.drawCircle((margin - (cellSize / 2)) + (cellSize * rawX), (cellSize + (cellSize / 2)) + (cellSize * rawY), cellSize / 2, hit);
        canvas.drawRect(((margin - (cellSize / 2)) + (cellSize * rawX) - (cellSize * (shipSize - 1))), (cellSize * (rawY + 1)), ((margin - (cellSize / 2)) + (cellSize * rawX)), (cellSize * (rawY + 2)), hit);
        canvas.drawCircle((margin - (cellSize / 2)) + (cellSize * rawX) - (cellSize * (shipSize - 1)), (cellSize + (cellSize / 2)) + (cellSize * rawY), cellSize / 2, hit);
      }
      hit.setColor(Color.RED);
    }

    // Hit markers
    for(AttackDot attackDot : attackDots){
      int rawX = attackDot.xCoord;
      int rawY = attackDot.yCoord;
      Boolean isAHit = attackDot.isAHit;
      if (isAHit){
        canvas.drawCircle((margin - (cellSize / 2)) + (cellSize * rawX), (cellSize + (cellSize / 2)) + (cellSize * rawY), cellSize / 4, hit);
      }
    }
  }
}