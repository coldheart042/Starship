package com.Starship;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;

/**
 * Created by Richard on 6/4/14.
 */
public class BoardView extends ImageView {
  private static Paint board;
  private static Paint line;
  private static int cellsize;

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
    cellsize = size / 10; //This is the cell size
    String[] letters = {"", "a", "b", "c", "d", "e", "f", "g", "h", "i", "j"};
    String[] numbers = {"", "1","2","3","4","5","6","7","8","9","10"};
    canvas.drawRect(30, 30, size + 18, size + 18, board);
    for (int i = 0; i < 11; i++){
      int a = 26 + (cellsize * i);
      canvas.drawLine(28, a, size + 20, a, line );//Across
      canvas.drawLine(a, 28, a, size + 20, line);//Down
      canvas.drawText(numbers[i], a -25, 20, line);
      canvas.drawText(letters[i], 5, a - 10, line);
    }
  }
  public void addShip(Canvas canvas, int x, int y, int type, int direction){
    //TODO: Add switches for type & direction, then drawing algorithms (Oval).
  }
  public void addHit(Canvas canvas, int x, int y){
    //TODO: Add red dot for hits.
  }
}
