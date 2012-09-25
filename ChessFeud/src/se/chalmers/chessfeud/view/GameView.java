
package se.chalmers.chessfeud.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

public class GameView extends View{

	public GameView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		int chessSquareHeight = this.getHeight()/8;
		int chessSquareWidth = this.getWidth()/8;
		Paint pW = new Paint();
		pW.setColor(25);
		Paint pB = new Paint();
		//En till loop
		for(int i = 0; i < 8; i++){
			Rect r = new Rect(i*chessSquareWidth,i*chessSquareHeight,(i+1)*chessSquareWidth,(i+1)*chessSquareWidth);
			
			
			canvas.drawRect(r, pW);
		}
	}

}
