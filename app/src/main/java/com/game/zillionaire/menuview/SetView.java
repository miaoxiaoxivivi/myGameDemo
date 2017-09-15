package com.game.zillionaire.menuview;


import com.game.zillionaire.activity.ZActivity;
import com.game.zillionaire.util.ConstantUtil;
import com.game.zillionaire.util.PicManager;
import com.game.zillionaire.util.ScreenTransUtil;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.media.AudioManager;
import android.os.Message;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class SetView extends SurfaceView implements SurfaceHolder.Callback
{
	ZActivity activity;//activity的引用
	public DrawThread drawThread;//刷帧的线程
    static Bitmap setBitmap;//设置背景图
    static Bitmap sliderBitmap;//滑块图
    private String[] musicName={"1.与我同行","2.悠游人生","3.旋转木马","4.欢乐庙会","5.开心茶馆"};//歌曲名称
    private String showString=musicName[0];//要绘制的歌曲名称
    private int[][] coordinate={{525,163},{525,273},{525,378}};//各个滑块的绘制坐标
    
	public SetView(ZActivity activity) {
		super(activity);
		getHolder().addCallback(this);
		this.activity = activity;//activity的引用
		this.drawThread = new DrawThread(getHolder(), this);//初始化刷帧线程
		if(activity.isBackSound)
		{
			activity.sm.playBackMusic(activity.soundName);
		}
	}
	public void onDraw(Canvas canvas)//绘制方法
	{
		setBitmap=PicManager.getPic("set",activity.getResources());//获取设置界面的背景图
		sliderBitmap=PicManager.getPic("slider",activity.getResources());//获取设置界面的滑块图
		canvas.translate(ConstantUtil.LOX, ConstantUtil.LOY);
		canvas.scale(ConstantUtil.RADIO, ConstantUtil.RADIO);
		canvas.clipRect(0,0,1280,720);
		
		canvas.drawBitmap(setBitmap, 0, 0, null);//绘制设置背景图
		for(int i=0;i<1;i++)//绘制滑块
		{
			canvas.drawBitmap(sliderBitmap, coordinate[i][0], coordinate[i][1], null);
		}
		Paint paint = new Paint();
		paint.setARGB(255, 42, 48, 103);//设置字体颜色
		paint.setAntiAlias(true);//抗锯齿
		paint.setFakeBoldText(true);//字体加粗
		paint.setTextSize(24);//设置文字大小
		canvas.drawText(showString,150,198, paint);//绘制歌曲名
		canvas.drawText("简体中文",210,304, paint);//绘制歌曲名
		canvas.restore();
	}
	@Override
	public boolean onTouchEvent(MotionEvent event)
	{
		int x=ScreenTransUtil.xFromRealToNorm((int)event.getX());
		int y=ScreenTransUtil.yFromRealToNorm((int)event.getY());
		if(event.getAction()==MotionEvent.ACTION_DOWN||event.getAction()==MotionEvent.ACTION_MOVE)
		{
			if(x>ConstantUtil.SetView_MusicVolume_Left_X&&x<=ConstantUtil.SetView_MusicVolume_Right_X
					&&y>=ConstantUtil.SetView_MusicVolume_Up_Y&&y<=ConstantUtil.SetView_MusicVolume_Down_Y)//音乐音量
			{
				int tempx=coordinate[0][0];
				coordinate[0][0]=x-30;
				if(x<560)
				{
					coordinate[0][0]=525;
				}
				if(x>630)
				{
					coordinate[0][0]=595;
				}
				if(tempx>coordinate[0][0])//声音减小
				{
					if(coordinate[0][0]==525)
					{
						setSound(0);
					}else
					{
						setSound(-2);
					}
				}else
				{
					if(coordinate[0][0]==595)
					{
						setSound(0);
					}else
					{
						setSound(2);
					}
					setSound(2);
				}
			}else if(x>ConstantUtil.SetView_SetSoundEffect_Left_X&&x<=ConstantUtil.SetView_SetSoundEffect_Right_X
					&&y>=ConstantUtil.SetView_SetSoundEffect_Up_Y&&y<=ConstantUtil.SetView_SetSoundEffect_Down_Y)//音效设置
			{
				coordinate[1][0]=x-30;
				if(x<560)
				{
					coordinate[1][0]=525;
				}
				if(x>630)
				{
					coordinate[1][0]=595;
				}
			}else if(x>ConstantUtil.SetView_SetSpeed_Left_X&&x<=ConstantUtil.SetView_SetSpeed_Right_X
					&&y>=ConstantUtil.SetView_SetSpeed_Up_Y&&y<=ConstantUtil.SetView_SetSpeed_Down_Y)//速度设置
			{
				coordinate[2][0]=x-30;
				if(x<560)
				{
					coordinate[2][0]=525;
				}
				if(x>630)
				{
					coordinate[2][0]=595;
				}
			}else if(x>ConstantUtil.SetView_Reduce_Left_X&&x<=ConstantUtil.SetView_Reduce_Right_X
					&&y>=ConstantUtil.SetView_MusicVolumeReduce_Up_Y&&y<=ConstantUtil.SetView_MusicVolumeReduce_Down_Y)//音乐音量 减 按键
			{
				coordinate[0][0]=coordinate[0][0]-2;
				if(coordinate[0][0]<525)
				{
					coordinate[0][0]=525;
				}
				setSound(-2);
			}else if(x>ConstantUtil.SetView_Reduce_Left_X&&x<=ConstantUtil.SetView_Reduce_Right_X
					&&y>=ConstantUtil.SetView_SetSoundEffectReduce_Up_Y&&y<=ConstantUtil.SetView_SetSoundEffectReduce_Down_Y)//音效设置 减 按键
			{
				coordinate[1][0]=coordinate[1][0]-2;
				if(coordinate[1][0]<525)
				{
					coordinate[1][0]=525;
				}
			}else if(x>ConstantUtil.SetView_Reduce_Left_X&&x<=ConstantUtil.SetView_Reduce_Right_X
					&&y>=ConstantUtil.SetView_SetSpeedReduce_Up_Y&&y<=ConstantUtil.SetView_SetSpeedReduce_Down_Y)//速度设置 减 按键
			{
				coordinate[2][0]=coordinate[2][0]-2;
				if(coordinate[2][0]<525)
				{
					coordinate[2][0]=525;
				}
			}else if(x>ConstantUtil.SetView_Add_Left_X&&x<=ConstantUtil.SetView_Add_Right_X
					&&y>=ConstantUtil.SetView_MusicVolumeAdd_Up_Y&&y<=ConstantUtil.SetView_MusicVolumeAdd_Down_Y)//音乐音量 加 按键||(y>=270&&y<=310)||(y>=375&&y<=415)))//加 按键
			{
				coordinate[0][0]=coordinate[0][0]+2;
				if(coordinate[0][0]>595)
				{
					coordinate[0][0]=595;
				}
				setSound(2);
			}else if(x>ConstantUtil.SetView_Add_Left_X&&x<=ConstantUtil.SetView_Add_Right_X
					&&y>=ConstantUtil.SetView_SetSoundEffectAdd_Up_Y&&y<=ConstantUtil.SetView_SetSoundEffectAdd_Down_Y)//音效设置加 按键
			{
				coordinate[1][0]=coordinate[1][0]+2;
				if(coordinate[1][0]>595)
				{
					coordinate[1][0]=595;
				}
				
			}else if(x>ConstantUtil.SetView_Add_Left_X&&x<=ConstantUtil.SetView_Add_Right_X
					&&y>=ConstantUtil.SetView_SetSpeedAdd_Up_Y&&y<=ConstantUtil.SetView_SetSpeedAdd_Down_Y)//速度设置加 按键
			{
				coordinate[2][0]=coordinate[2][0]+2;
				if(coordinate[2][0]>595)
				{
					coordinate[2][0]=595;
				}
			}
		}
		else if(event.getAction()==MotionEvent.ACTION_UP)
		{
			if(x>=ConstantUtil.SetView_ReturnMainMenu_Left_X&&x<=ConstantUtil.SetView_ReturnMainMenu_Right_X
					&&y>=ConstantUtil.SetView_ReturnMainMenu_Up_Y&&y<=ConstantUtil.SetView_ReturnMainMenu_Down_Y)//返回菜单界面
			{
				Message msg1 = this.activity.myHandler.obtainMessage(3);
				this.activity.myHandler.sendMessage(msg1);//向主activity发送Handler消息
				if(activity.sm.mp.isPlaying())//退出音乐设置界面--关闭停止播放音乐
				{
					activity.sm.mp.stop();
					activity.sm.mp.release();
				}
				
			}else if(x>=ConstantUtil.SetView_ChooseSongGo_Left_X&&x<=ConstantUtil.SetView_ChooseSongGo_Right_X
					&&y>=ConstantUtil.SetView_ChooseSong_Up_Y&&y<=ConstantUtil.SetView_ChooseSong_Down_Y)//向前选取歌曲
			{
				if(activity.soundCount>0)
				{
					activity.soundCount--;
				}
				showString=musicName[activity.soundCount];
				activity.soundName=activity.musicPath[activity.soundCount];
				if(activity.isBackSound)
				{
					activity.sm.playBackMusic(activity.soundName);//播放背景音乐
				}
			}else if(x>=ConstantUtil.SetView_ChooseSongBack_Left_X&&x<=ConstantUtil.SetView_ChooseSongBack_Right_X
					&&y>=ConstantUtil.SetView_ChooseSong_Up_Y&&y<=ConstantUtil.SetView_ChooseSong_Down_Y)//向后选取歌曲
			{
				if(activity.soundCount<musicName.length-1)
				{
					activity.soundCount++;
				}
				showString=musicName[activity.soundCount];
				activity.soundName=activity.musicPath[activity.soundCount];
				if(activity.isBackSound)
				{
					activity.sm.playBackMusic(activity.soundName);//播放背景音乐
				}
			}
		}
		return true;		
	}
	public void setSound(int flags)
	{
		int maxVolume = activity.sm.am.getStreamMaxVolume(AudioManager.STREAM_MUSIC);//音乐声音最大值
		int currentVolume=activity.sm.am.getStreamVolume(AudioManager.STREAM_RING);//音乐声音当前值
		int nextVolume=currentVolume+flags;
		if(nextVolume<=0||nextVolume>=maxVolume)
		{
			flags=0;
		}
		int direction=0;
		if(flags >= 0){
		    direction = AudioManager.ADJUST_RAISE;
		}else{
			flags = -flags;
		    direction = AudioManager.ADJUST_LOWER;
		}                               
		activity.sm.am.adjustStreamVolume(AudioManager.STREAM_MUSIC, direction,0);
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
		private SetView setView;
		private boolean isViewOn = false;
		private boolean flag = true;
        public DrawThread(SurfaceHolder surfaceHolder, SetView setView) //构造器
        {
        	super.setName("==GameView.DrawThread");
            this.surfaceHolder = surfaceHolder;
            this.setView=setView;
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
	                    	setView.onDraw(c);
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
