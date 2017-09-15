package com.game.zillionaire.view;

import com.game.zillionaire.activity.ZActivity;
import com.game.zillionaire.util.ConstantUtil;
import com.game.zillionaire.util.PicManager;
import com.game.zillionaire.util.ScreenTransUtil;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Message;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class GameMenuView extends SurfaceView implements SurfaceHolder.Callback
{
	ZActivity activity;//activity的引用
	public DrawThread drawThread;//刷帧的线程
	static Bitmap mainBitmap;//菜单主图
	static Bitmap backBitmap;//返回图
	static Bitmap button1Bitmap;//开始新游戏按钮
	static Bitmap button2Bitmap;//继续游戏按钮
	static Bitmap button3Bitmap;//读取进度按钮
	static Bitmap button4Bitmap;//商店按钮
	public GameMenuView(ZActivity activity) 
	{
		super(activity);
		getHolder().addCallback(this);
		this.activity = activity;//activity的引用
		this.drawThread = new DrawThread(getHolder(), this);//初始化刷帧线程
	}
	//方法:绘制屏幕
	public void onDraw(Canvas canvas)//自己写的绘制方法
	{
		if(canvas==null)
		{
			return;
		}
		mainBitmap=PicManager.getPic("menu",activity.getResources());//获取菜单主图
		backBitmap=PicManager.getPic("back",activity.getResources());
		button1Bitmap=PicManager.getPic("start",activity.getResources());
		button2Bitmap=PicManager.getPic("jix",activity.getResources());
		button3Bitmap=PicManager.getPic("read",activity.getResources());
		button4Bitmap=PicManager.getPic("shop1",activity.getResources());
		canvas.translate(ConstantUtil.LOX, ConstantUtil.LOY);
		canvas.scale(ConstantUtil.RADIO, ConstantUtil.RADIO);
		canvas.clipRect(0,0,1280,720);
		
		canvas.drawBitmap(mainBitmap, 0, 0, null);
		canvas.drawBitmap(backBitmap, 63,0, null);//返回按钮
		canvas.drawBitmap(button1Bitmap, 193,120, null);//开始新游戏按钮
		canvas.drawBitmap(button2Bitmap, 467,120, null);//继续游戏按钮
		canvas.drawBitmap(button3Bitmap, 203,286, null);//读取进度按钮
		canvas.drawBitmap(button4Bitmap, 469,286, null);//商店按钮
	}
	@Override
	public boolean onTouchEvent(MotionEvent event)
	{
		int x=ScreenTransUtil.xFromRealToNorm((int)event.getX());
		int y=ScreenTransUtil.yFromRealToNorm((int)event.getY());
		if(x>=ConstantUtil.GameMenuView_RetunMainMenu_Left_X&&x<=ConstantUtil.GameMenuView_RetunMainMenu_Right_X
				&&y>=ConstantUtil.GameMenuView_RetunMainMenu_Up_Y&&y<=ConstantUtil.GameMenuView_RetunMainMenu_Down_Y)//返回主菜单界面
		{
			Message msg1 = this.activity.myHandler.obtainMessage(3);
			this.activity.myHandler.sendMessage(msg1);//向主activity发送Handler消息
		}else if(x>=ConstantUtil.GameMenuView_StartGame_Left_X&&x<=ConstantUtil.GameMenuView_StartGame_Right_X
				&&y>=ConstantUtil.GameMenuView_StartGame_Up_Y&&y<=ConstantUtil.GameMenuView_StartGame_Down_Y)//开始新游戏-->人物选择
		{
			Message msg1 = this.activity.myHandler.obtainMessage(6);
			this.activity.myHandler.sendMessage(msg1);//向主activity发送Handler消息
		}else if(x>=ConstantUtil.GameMenuView_ContinueGame_Left_X&&x<=ConstantUtil.GameMenuView_ContinueGame_Right_X
				&&y>=ConstantUtil.GameMenuView_ContinueGame_Up_Y&&y<=ConstantUtil.GameMenuView_ContinueGame_Down_Y)//继续游戏-->开始游戏
		{
			Message msg1 = this.activity.myHandler.obtainMessage(9);
			this.activity.myHandler.sendMessage(msg1);//向主activity发送Handler消息
		}else if(x>=ConstantUtil.GameMenuView_ReadShedule_Left_X&&x<=ConstantUtil.GameMenuView_ReadShedule_Right_X
				&&y>=ConstantUtil.GameMenuView_ReadShedule_Up_Y&&y<=ConstantUtil.GameMenuView_ReadShedule_Down_Y)//读取进度
		{
			Message msg1 = this.activity.myHandler.obtainMessage(11);
			this.activity.myHandler.sendMessage(msg1);//向主activity发送Handler消息
		}
		return true;		
	}
	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		this.drawThread.setFlag(true);
		drawThread.setIsViewOn(true);
        if(! drawThread.isAlive())//如果后台重绘线程没起来,就启动它
        {
        	try
        	{
        		drawThread.start();
        	}
        	catch(Exception e)
        	{
        		e.printStackTrace();
        	}        	
        }
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		this.drawThread.setIsViewOn(false);
	}
	public class DrawThread extends Thread{//刷帧线程

		private int sleepSpan =100;//睡眠的毫秒数 
		private SurfaceHolder surfaceHolder;
		private GameMenuView menuView;
		private boolean isViewOn = false;
		private boolean flag = true;
        public DrawThread(SurfaceHolder surfaceHolder, GameMenuView menuView) //构造器
        {
        	super.setName("==GameView.DrawThread");
            this.surfaceHolder = surfaceHolder;
            this.menuView = menuView;
        }
        public void setFlag(boolean flag)//设置循环标记位
        {
        	this.flag = flag;
        }
        public void setIsViewOn(boolean isViewOn)
        {
        	this.isViewOn = isViewOn;
        }
		public void run() 
		{
			Canvas c;
			while(flag)
			{
	            while (isViewOn) 
	            {
	                c = null;
	                try 
	                {
	                	// 锁定整个画布，在内存要求比较高的情况下，建议参数不要为null
	                    c = this.surfaceHolder.lockCanvas(null);
	                    synchronized (this.surfaceHolder) 
	                    {
	                    	menuView.onDraw(c);
	                    }
	                } finally 
	                {
	                    if (c != null) 
	                    {
	                    	//更新屏幕显示内容
	                        this.surfaceHolder.unlockCanvasAndPost(c);
	                    }
	                }
	                try
	                {
	                	Thread.sleep(sleepSpan);//睡眠指定毫秒数
	                }
	                catch(Exception e)
	                {
	                	e.printStackTrace();
	                }
	            }
	            try
	            {
	            	Thread.sleep(1500);//睡眠指定毫秒数
	            }
	            catch(Exception e)
	            {
	            	e.printStackTrace();
	            }
			}
		}
	}
}
