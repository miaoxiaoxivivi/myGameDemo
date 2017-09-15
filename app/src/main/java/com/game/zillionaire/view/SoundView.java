package com.game.zillionaire.view;

import com.game.zillionaire.activity.ZActivity;
import com.game.zillionaire.util.ConstantUtil;
import com.game.zillionaire.util.PicManager;
import com.game.zillionaire.util.ScreenTransUtil;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Message;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class SoundView extends SurfaceView implements SurfaceHolder.Callback
{
	ZActivity activity;
	Paint paint;
	static Bitmap soundBackBitmap;//开启声音界面的背景图
	public SoundView(ZActivity activity) {
		super(activity);
		this.activity = activity;
		getHolder().addCallback(this);
		paint = new Paint();
		paint.setColor(Color.WHITE);
		paint.setAntiAlias(true);//抗锯齿
		paint.setARGB(255, 42, 48, 103);//设置字体颜色
		paint.setTextSize(24);//设置文字大小
	}
	public void onDraw(Canvas canvas) {
		if(canvas==null)
		{
			return;
		}
		
		soundBackBitmap=PicManager.getPic("sound",activity.getResources());
		canvas.save();
		canvas.translate(ConstantUtil.LOX, ConstantUtil.LOY);
		canvas.scale(ConstantUtil.RADIO, ConstantUtil.RADIO);
		canvas.clipRect(0,0,1280,720);
		
		canvas.drawBitmap(soundBackBitmap, 0, 0, null);
		canvas.restore();
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) 
	{
		int x=ScreenTransUtil.xFromRealToNorm((int)event.getX());
		int y=ScreenTransUtil.yFromRealToNorm((int)event.getY());
		if(event.getAction() == MotionEvent.ACTION_DOWN)
		{
			if(x>=ConstantUtil.SoundView_YesButton_Left_X&&x<=ConstantUtil.SoundView_YesButton_Right_X
					&&y>=ConstantUtil.SoundView_Button_Up_Y&&y<=ConstantUtil.SoundView_Button_Down_Y){//点击是按钮
				activity.isBackSound = true;
				activity.soundCount=0;
				Message msg1 = this.activity.myHandler.obtainMessage(5);
				this.activity.myHandler.sendMessage(msg1);
			}
			else if(x>=ConstantUtil.SoundView_NoButton_Left_X&&x<=ConstantUtil.SoundView_NoButton_Right_X
					&& y>=ConstantUtil.SoundView_Button_Up_Y&& y<=ConstantUtil.SoundView_Button_Down_Y){//点击否按钮
				activity.isBackSound = false;
				Message msg1 = this.activity.myHandler.obtainMessage(5);
				this.activity.myHandler.sendMessage(msg1);
			}
		}
		return super.onTouchEvent(event);
	}
	public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {
	}
	public void surfaceCreated(SurfaceHolder holder) {
		new Thread(){
			public void run(){
				Canvas c = SoundView.this.getHolder().lockCanvas(null);
		        synchronized (SoundView.this.getHolder()) {
		        	onDraw(c);
		        }
		        SoundView.this.getHolder().unlockCanvasAndPost(c);			
			}
		}.start();
	}
	public void surfaceDestroyed(SurfaceHolder holder) {
	}
}
