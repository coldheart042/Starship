package com.Starship;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * Created by Richard on 6/4/14.
 */
public class AttackView extends ImageView {
  private static Paint board;
  private static Paint line;
  public AttackView(Context context, AttributeSet attrs) {
    super(context, attrs);
    board = new Paint();
    board.setStrokeWidth(10);
    board.setColor(Color.BLACK);
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
    int cellSize = size / 10;
    String[] letters = {"", "a", "b", "c", "d", "e", "f", "g", "h", "i", "j"};
    String[] numbers = {"", "1","2","3","4","5","6","7","8","9","10"};
    canvas.drawRect(30, 30, size + 18, size + 18, board);
    for (int i = 0; i < 11; i++){
      int a = 26 + (cellSize * i);
      canvas.drawLine(28, a, size + 20, a, line );//Across
      canvas.drawLine(a, 28, a, size + 20, line);//Down
      canvas.drawText(numbers[i], a -25, 20, line);
      canvas.drawText(letters[i], 5, a - 10, line);
    }
  }
  public void addHitMiss(Canvas canvas, int x, int y, int type){
    //TODO: Add switch for type, then drawing algorithms (Circle).
  }
}
