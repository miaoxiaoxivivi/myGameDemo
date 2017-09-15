package com.game.zillionaire.view;

import com.game.zillionaire.figure.Figure;
import com.game.zillionaire.util.PicManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Message;
import android.view.MotionEvent;
import android.view.View;

public class WinView implements View.OnTouchListener{
	GameView gameView;
	static Bitmap win;
	
	Bitmap[] figureHead=new Bitmap[3];
	public WinView(GameView gameView)
	{
		this.gameView=gameView;
	}
	public void onDraw(Canvas canvas,Figure figure)
	{
		win=PicManager.getPic("win",gameView.activity.getResources());
		figureHead[0]=PicManager.getPic(gameView.figure.headBitmapName, gameView.activity.getResources());
		figureHead[1]=PicManager.getPic(gameView.figure1.headBitmapName, gameView.activity.getResources());
		figureHead[2]=PicManager.getPic(gameView.figure2.headBitmapName, gameView.activity.getResources());
		if(canvas==null)
		{
			return;
		}
		canvas.drawBitmap(win, 0, 0, null);
		for(int i=0,j=0;i<3;i++)
		{
			if(i!=figure.k)
			{
				if(i==0)
				{
					canvas.drawBitmap(figureHead[0], 40, 8+130*j, null);
				}else if(i==1)
				{
					canvas.drawBitmap(figureHead[1], 40, 8+130*j, null);
				}else if(i==2)
				{
					canvas.drawBitmap(figureHead[2], 40, 8+130*j, null);
				}
				j++;
			}
		}
		canvas.drawBitmap(figure.animationSegment.get(6)[1], 270, 195, null);
	}
	@Override
	public boolean onTouch(View view, MotionEvent event)
	{
		if(event.getAction()==MotionEvent.ACTION_DOWN)
		{
			Message msg1 = this.gameView.activity.myHandler.obtainMessage(1);
			this.gameView.activity.myHandler.sendMessage(msg1);//向主activity发送Handler消息
		}
		return true;
	}

}
