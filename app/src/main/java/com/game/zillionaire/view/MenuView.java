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

public class MenuView extends SurfaceView implements SurfaceHolder.Callback
{
	ZActivity activity;//activity的引用
	public DrawThread drawThread;//刷帧的线程
	static Bitmap mainBackBitmap;//菜单主图
	static Bitmap aboutBitmap;//关于图
	static Bitmap musicBitmap;//音乐的开关图
	public boolean drawAbout=false;//绘制关于小图片的判断标志位
	public MenuView(ZActivity activity) {
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
		mainBackBitmap=PicManager.getPic("welcome",activity.getResources());//获取菜单主图
		aboutBitmap=PicManager.getPic("about",activity.getResources());//获取关于图
		musicBitmap=PicManager.getPic("music",activity.getResources());//获取音乐的开关图
		canvas.save();
		canvas.translate(ConstantUtil.LOX, ConstantUtil.LOY);
		canvas.scale(ConstantUtil.RADIO, ConstantUtil.RADIO);
		canvas.clipRect(0,0,1280,720);
		
		canvas.drawBitmap(mainBackBitmap, 0, 0, null);
		if(drawAbout)//绘制关于小图片
		{
			canvas.drawBitmap(aboutBitmap, 136, 90, null);
		}
		if(activity.isBackSound)//绘制音乐的开关图
		{
			canvas.drawBitmap(musicBitmap, 745,388, null);
		}
		canvas.restore();
	}
	@Override
	public boolean onTouchEvent(MotionEvent event)
	{
		int x=ScreenTransUtil.xFromRealToNorm((int)event.getX());
		int y=ScreenTransUtil.yFromRealToNorm((int)event.getY());
		if(event.getAction()==MotionEvent.ACTION_DOWN)
		{
			if(!drawAbout)
			{
				if(x>=ConstantUtil.MenuView_EnterGame_Left_X&&x<=ConstantUtil.MenuView_EnterGame_Right_X
						&&y>=ConstantUtil.MenuView_EnterGame_Up_Y&&y<=ConstantUtil.MenuView_EnterGame_Down_Y)//进入游戏菜单
				{
					Message msg1 = this.activity.myHandler.obtainMessage(0);
					this.activity.myHandler.sendMessage(msg1);//向主activity发送Handler消息
				}else if(x>=ConstantUtil.MenuView_ExitGame_Left_X&&x<=ConstantUtil.MenuView_ExitGame_Right_X
						&&y>=ConstantUtil.MenuView_ExitGame_Up_Y&&y<=ConstantUtil.MenuView_ExitGame_Down_Y)//退出游戏
				{
					this.drawThread.setFlag(false);
					this.activity.finish();
				}else if(x>=ConstantUtil.MenuView_About_Left_X&&x<=ConstantUtil.MenuView_About_Right_X
						&&y>=ConstantUtil.MenuView_About_Up_Y&&y<=ConstantUtil.MenuView_About_Down_Y)//关于
				{
					drawAbout=true;//绘制关于小图片
				}else if(x>=ConstantUtil.MenuView_Set_Left_X&&x<=ConstantUtil.MenuView_Set_Right_X
						&&y>=ConstantUtil.MenuView_Set_Up_Y&&y<=ConstantUtil.MenuView_Set_Down_Y)//设置
				{
					Message msg1 = this.activity.myHandler.obtainMessage(4);
					this.activity.myHandler.sendMessage(msg1);//向主activity发送Handler消息
				}else if(x>ConstantUtil.MenuView_Help_Left_X&&x<=ConstantUtil.MenuView_Help_Right_X
						&&y>=ConstantUtil.MenuView_Help_Up_Y&&y<=ConstantUtil.MenuView_Help_Down_Y)//帮助
				{
					Message msg1 = this.activity.myHandler.obtainMessage(2);
					this.activity.myHandler.sendMessage(msg1);//向主activity发送Handler消息
				}else if(x>ConstantUtil.MenuView_Music_Left_X&&x<=ConstantUtil.MenuView_Music_Right_X
						&&y>=ConstantUtil.MenuView_Music_Up_Y&&y<=ConstantUtil.MenuView_Music_Down_Y)//音乐
				{
					activity.isBackSound=!activity.isBackSound;
				}
			}else if(x>ConstantUtil.MenuView_SmallAbout_Left_X&&x<=ConstantUtil.MenuView_SmallAbout_Right_X
					&&y>=ConstantUtil.MenuView_SmallAbout_Up_Y&&y<=ConstantUtil.MenuView_SmallAbout_Down_Y)//点了关于小图片
			{
				drawAbout=false;//不绘制关于小图片
			}
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
		private MenuView welcomeView;
		private boolean isViewOn = false;
		private boolean flag = true;
        public DrawThread(SurfaceHolder surfaceHolder, MenuView welcomView) //构造器
        {
        	super.setName("==GameView.DrawThread");
            this.surfaceHolder = surfaceHolder;
            this.welcomeView=welcomView;
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
	                    	welcomeView.onDraw(c);
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
