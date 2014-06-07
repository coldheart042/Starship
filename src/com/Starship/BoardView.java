package com.Starship;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;

import java.util.ArrayList;

/**
 * Created by Richard on 6/4/14.
 */
public class BoardView extends ImageView {
  private static Paint board;
  private static Paint line;
  public ArrayList<Ship> ships = null;
  public String[] letters = {"", "a", "b", "c", "d", "e", "f", "g", "h", "i", "j"};
  public String[] numbers = {"", "1","2","3","4","5","6","7","8","9","10"};

  public BoardView(Context context, AttributeSet attrs) {
    super(context, attrs);
    board = new Paint();
    board.setStrokeWidth(10);
    board.setColor(Color.BLUE);
    board.setStyle(Paint.Style.FILL_AND_STROKE);
    line = new Paint();
    line.setStrokeWidth(2);
    line.setColor(Color.WHITE);
  }

  @Override
  protected void onDraw(Canvas canvas){
    super.onDraw(canvas);
    //Draw the Defend Board:
    int size = this.getMeasuredWidth() / 2;
    int cellsize = size / 10;

    canvas.drawRect(30, 30, size + 18, size + 18, board);
    for (int i = 0; i < 11; i++){
      int a = 26 + (cellsize * i);
      canvas.drawLine(28, a, size + 20, a, line );//Across
      canvas.drawLine(a, 28, a, size + 20, line);//Down
      canvas.drawText(numbers[i], a -25, 20, line);
      canvas.drawText(letters[i], 5, a - 10, line);
    }
    if (ships != null){
      for (Ship ship : ships){
        int x = (ship.xCoord * cellsize) + (cellsize + 5);
        int y = (ship.yCoord * cellsize) + (cellsize + 5) ;
        int spaces = (ship.size - 1) * cellsize;
        line.setColor(Color.LTGRAY);
        if (ship.direction == 0){
            canvas.drawCircle(x, y, 10, line);
            canvas.drawRect(x - 10, y - spaces, x + 10, y, line);
            canvas.drawCircle(x, y - spaces, 10, line);
        }
        if (ship.direction == 2){
            canvas.drawCircle(x, y, 10, line);
            canvas.drawRect(x, y - 10, x + spaces, y + 10, line);
            canvas.drawCircle(x + spaces, y, 10, line);
        }
        if (ship.direction == 4){
          canvas.drawCircle(x, y, 10, line);
          canvas.drawRect(x - 10, y, x + 10, y + spaces, line );
          canvas.drawCircle(x, y + spaces, 10, line);
        }
        if (ship.direction == 6){
          canvas.drawCircle(x, y, 10, line);
          canvas.drawRect(x - spaces, y - 10, x, y + 10, line);
          canvas.drawCircle(x - spaces, y, 10, line);
        }
      }
    }
    //TODO: Add hit markers (Similar Method as above...)
  }
}
