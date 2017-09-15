package com.game.zillionaire.view;

import java.text.SimpleDateFormat;
import java.util.Date;
import com.game.zillionaire.activity.ZActivity;
import com.game.zillionaire.map.SerializableGame;
import com.game.zillionaire.util.ConstantUtil;
import com.game.zillionaire.util.PicManager;
import com.game.zillionaire.util.ScreenTransUtil;
import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Message;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class SheduleChooseView extends SurfaceView implements SurfaceHolder.Callback
{
	GameView gameView;//游戏主界面
	Bitmap island;
	Bitmap gamedate;
	Bitmap figure1Pic;
	Bitmap figure2Pic;
	Bitmap figure3Pic;
	Bitmap sheduleTip;
	boolean drawDialogFlag=false;
	int drawDialogOnTouchFlag=0;
	boolean fuGaiFlag=false;
	boolean saveFlag=true;
	public SheduleChooseView(ZActivity activity,GameView gameView) {
		super(activity);

		this.activity = activity;//activity的引用
		getHolder().addCallback(this);
        this.drawThread = new DrawThread(getHolder(), this);//初始化刷帧线程
        this.gameView=gameView;
	}
	ZActivity activity;//activity的引用
	public DrawThread drawThread;//刷帧的线程
	static Bitmap mainBackBitmap;//菜单主图
	public float angle=0;//初始旋转角度
	
	//方法:绘制屏幕
	public void onDraw(Canvas canvas)//自己写的绘制方法
	{
		if(ConstantUtil.MainMenuOrMenu==1){
			mainBackBitmap=PicManager.getPic("save1",activity.getResources());//获取菜单主图
		}else if(ConstantUtil.MainMenuOrMenu==0){
			mainBackBitmap=PicManager.getPic("load",activity.getResources());//获取菜单主图
		}
		island=PicManager.getPic("island",activity.getResources());
		gamedate=PicManager.getPic("gamedate",activity.getResources());
		figure1Pic=PicManager.getPic("figure0",activity.getResources());
		figure2Pic=PicManager.getPic("figure1",activity.getResources());
		figure3Pic=PicManager.getPic("figure2",activity.getResources());
		sheduleTip=PicManager.getPic("sheduletip",activity.getResources());
		if(canvas==null){
			return;
		}
		canvas.save();
		canvas.translate(ConstantUtil.LOX, ConstantUtil.LOY);
		canvas.scale(ConstantUtil.RADIO, ConstantUtil.RADIO);
		canvas.clipRect(0,0,1280,720);
		canvas.drawBitmap(mainBackBitmap, 0, 0,null);
		for(int i=0;i<3;i++)
		{
			if(!ConstantUtil.SaveString[i].equals("")){
				String[] list=ConstantUtil.SaveString[i].split("<#>");
				canvas.drawBitmap(island,140,130+105*i,null);//地址
				canvas.drawBitmap(gamedate,248,130+105*i,null);//日期
				drawString(canvas,list[1],252,190+105*i);//日期
				
				canvas.drawBitmap(figure1Pic,365,130+105*i,null);
				canvas.drawBitmap(figure2Pic,455,130+105*i,null);
				canvas.drawBitmap(figure3Pic,545,130+105*i,null);
			}
		}
		if(drawDialogFlag){
			canvas.drawBitmap(sheduleTip,80,150,null);
			drawDialogOnTouchFlag=1;
			saveFlag=false;
		}
	}
	public void drawString(Canvas canvas,String string,int location_x,int location_y)
	{
		Paint paint = new Paint();
		paint.setARGB(255, 42, 48, 103);//设置字体颜色
		paint.setAntiAlias(true);//抗锯齿
		paint.setTextSize(18);//设置文字大小
		canvas.drawText(string, location_x, location_y, paint);
	}
	@SuppressLint("SimpleDateFormat")
	@Override
	public boolean onTouchEvent(MotionEvent event)
	{
		int x=ScreenTransUtil.xFromRealToNorm((int)event.getX());
		int y=ScreenTransUtil.yFromRealToNorm((int)event.getY());
		if(event.getAction()==MotionEvent.ACTION_DOWN)
		{
			//读取进度
			if(ConstantUtil.MainMenuOrMenu==0&&saveFlag)
			{   
				//第一行
	            if(x>ConstantUtil.SheduleChooseView_ReadShedule_Left_X&&x<ConstantUtil.SheduleChooseView_ReadShedule_Right_X
	            		&&y>ConstantUtil.SheduleChooseView_ReadShedule1_Up_Y&&y<ConstantUtil.SheduleChooseView_ReadShedule1_Down_Y
	            		&&ConstantUtil.SheduleChooseViewSaved>=0)
	            {
	            	ConstantUtil.SheduleChooseViewSelected=0;
	            	Message msg1 = this.activity.myHandler.obtainMessage(9);
	    			this.activity.myHandler.sendMessage(msg1);//向主activity发送Handler消息
	            }//第二行
	            else if(x>ConstantUtil.SheduleChooseView_ReadShedule_Left_X&&x<ConstantUtil.SheduleChooseView_ReadShedule_Right_X
	            		&&y>ConstantUtil.SheduleChooseView_ReadShedule2_Up_Y&&y<ConstantUtil.SheduleChooseView_ReadShedule2_Down_Y
	            		&&ConstantUtil.SheduleChooseViewSaved>=1)
	            {
	            	ConstantUtil.SheduleChooseViewSelected=1;
	            	Message msg1 = this.activity.myHandler.obtainMessage(9);
	    			this.activity.myHandler.sendMessage(msg1);//向主activity发送Handler消息
	            }//第三行
	            else if(x>ConstantUtil.SheduleChooseView_ReadShedule_Left_X&&x<ConstantUtil.SheduleChooseView_ReadShedule_Right_X
	            		&&y>ConstantUtil.SheduleChooseView_ReadShedule3_Up_Y&&y<ConstantUtil.SheduleChooseView_ReadShedule3_Down_Y
	            		&&ConstantUtil.SheduleChooseViewSaved==2)
	            {
	            	ConstantUtil.SheduleChooseViewSelected=2;
	            	Message msg1 = this.activity.myHandler.obtainMessage(9);
	    			this.activity.myHandler.sendMessage(msg1);//向主activity发送Handler消息
	            }
			}
			//读取进度界面返回键
			if(ConstantUtil.MainMenuOrMenu==0&&saveFlag)
			{  
				if(x>ConstantUtil.SheduleChooseView_ReturnMainMenu_Left_X&&x<ConstantUtil.SheduleChooseView_ReturnMainMenu_Right_X
	            		&&y>ConstantUtil.SheduleChooseView_ReturnMainMenu_Up_Y&&y<ConstantUtil.SheduleChooseView_ReturnMainMenu_Down_Y)
	            {
	            	Message msg1 = this.activity.myHandler.obtainMessage(0);
	    			this.activity.myHandler.sendMessage(msg1);//向主activity发送Handler消息
	    			if(activity.sm.mp.isPlaying())//退出界面--关闭停止播放音乐
					{
						activity.sm.mp.stop();
						activity.sm.mp.release();
					}
	            }
			}
			//存储
            if(ConstantUtil.MainMenuOrMenu==1&&saveFlag)
            {
            	SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
            	String dateStr=formatter.format(new Date());//获得当前日期
            	//第一行
            	if(x>ConstantUtil.SheduleChooseView_ReadShedule_Left_X&&x<ConstantUtil.SheduleChooseView_ReadShedule_Right_X
	            		&&y>ConstantUtil.SheduleChooseView_ReadShedule1_Up_Y&&y<ConstantUtil.SheduleChooseView_ReadShedule1_Down_Y)
            	{
            		ConstantUtil.changeNum=0;
            		if(ConstantUtil.SheduleChooseViewSaved>=0)
            			drawDialogFlag=true;
            		if(ConstantUtil.SheduleChooseViewSaved==-1)
            			ConstantUtil.SheduleChooseViewSaved++;
            		ConstantUtil.SaveString[0]="神秘岛<#>"+dateStr+"<#>A<#>B<#>C";
            		
            		if(!drawDialogFlag||fuGaiFlag)
            		{
            			SerializableGame.saveGameStatus(gameView);
                		SerializableGame.saveSaveString(gameView);
            		}
            	}
            	//第二行
            	else if(x>ConstantUtil.SheduleChooseView_ReadShedule_Left_X&&x<ConstantUtil.SheduleChooseView_ReadShedule_Right_X
	            		&&y>ConstantUtil.SheduleChooseView_ReadShedule2_Up_Y&&y<ConstantUtil.SheduleChooseView_ReadShedule2_Down_Y)
            	{
            		ConstantUtil.changeNum=1;
            		if(ConstantUtil.SheduleChooseViewSaved>=1)
            			drawDialogFlag=true;
            		if(ConstantUtil.SheduleChooseViewSaved==0)
            			ConstantUtil.SheduleChooseViewSaved++;
            		ConstantUtil.SaveString[1]="神秘岛<#>"+dateStr+"<#>A<#>B<#>C";
            		
            		if(!drawDialogFlag||fuGaiFlag)
            		{
            			SerializableGame.saveGameStatus(gameView);
                		SerializableGame.saveSaveString(gameView);
            		}
            	}
            	//第三行
            	else if(x>ConstantUtil.SheduleChooseView_ReadShedule_Left_X&&x<ConstantUtil.SheduleChooseView_ReadShedule_Right_X
	            		&&y>ConstantUtil.SheduleChooseView_ReadShedule3_Up_Y&&y<ConstantUtil.SheduleChooseView_ReadShedule3_Down_Y)
            	{
            		ConstantUtil.changeNum=2;
            		if(ConstantUtil.SheduleChooseViewSaved==2)
            			drawDialogFlag=true;
            		if(ConstantUtil.SheduleChooseViewSaved==1)
            			ConstantUtil.SheduleChooseViewSaved++;
            		ConstantUtil.SaveString[2]="神秘岛<#>"+dateStr+"<#>A<#>B<#>C";
            		
            		if(!drawDialogFlag||fuGaiFlag)
            		{
            			SerializableGame.saveGameStatus(gameView);
                		SerializableGame.saveSaveString(gameView);
            		}
            	}
            }
            //存储界面返回键
            if(ConstantUtil.MainMenuOrMenu==1&&saveFlag)
            {
            	if(x>ConstantUtil.SheduleChooseView_ReturnMainMenu_Left_X&&x<ConstantUtil.SheduleChooseView_ReturnMainMenu_Right_X
	            		&&y>ConstantUtil.SheduleChooseView_ReturnMainMenu_Up_Y&&y<ConstantUtil.SheduleChooseView_ReturnMainMenu_Down_Y)
            	{
            		drawDialogFlag=false;
            		Message msg1 = this.activity.myHandler.obtainMessage(0);
	    			this.activity.myHandler.sendMessage(msg1);//向主activity发送Handler消息
	    			if(activity.sm.mp.isPlaying())//退出界面--关闭停止播放音乐
					{
						activity.sm.mp.stop();
						activity.sm.mp.release();
					}
            	}
            }
            
            
            //覆盖框
            if(drawDialogOnTouchFlag==1)
            {
            	//不覆盖
            	if(x>ConstantUtil.SheduleChooseView_NoFuGai_Left_X&&x<ConstantUtil.SheduleChooseView_NoFuGai_Right_X
            			&&y>ConstantUtil.SheduleChooseView_NoFuGai_Up_Y&&y<ConstantUtil.SheduleChooseView_NoFuGai_Down_Y)//no
            	{
            		drawDialogFlag=false;
            		drawDialogOnTouchFlag=0;
            		
            	}
            	//覆盖
            	else if(x>ConstantUtil.SheduleChooseView_YesFuGai_Left_X&&x<ConstantUtil.SheduleChooseView_YesFuGai_Right_X
            			&&y>ConstantUtil.SheduleChooseView_YesFuGai_Up_Y&&y<ConstantUtil.SheduleChooseView_YesFuGai_Down_Y)//yes
            	{
            		drawDialogFlag=false;
            		fuGaiFlag=true;
        			SerializableGame.saveGameStatus(gameView);
            		SerializableGame.saveSaveString(gameView);
            		
            	}
            	saveFlag=true;
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
        this.drawThread.start();//启动刷帧线程
	}
	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		 boolean retry = true;
	        drawThread.setFlag(false);
	        while (retry) {
	            try {
	                drawThread.join();
	                retry = false;
	            } 
	            catch (InterruptedException e) {//不断地循环，直到刷帧线程结束
	            }
	        }
	}
	public class DrawThread extends Thread{//刷帧线程
		private int sleepSpan =10;//睡眠的毫秒数 
		private SurfaceHolder surfaceHolder;
		private SheduleChooseView shedulechooseView;
		private boolean flag = false;
        public DrawThread(SurfaceHolder surfaceHolder, SheduleChooseView sheduleChooseView) {//构造器
        	super.setName("==LoadingView.DrawThrea");
            this.surfaceHolder = surfaceHolder;
            this.shedulechooseView = sheduleChooseView;
        }
        public void setFlag(boolean flag) {//设置循环标记位
        	this.flag = flag;
        }
		
		public void run() {
			Canvas c;
            while (this.flag) {
                c = null;
                try {
                	// 锁定整个画布，在内存要求比较高的情况下，建议参数不要为null
                    c = this.surfaceHolder.lockCanvas(null);
                    synchronized (this.surfaceHolder) {
                    	angle=angle+5;
                    	shedulechooseView.onDraw(c);
                    }
                } finally {
                    if (c != null) {
                    	//更新屏幕显示内容
                        this.surfaceHolder.unlockCanvasAndPost(c);
                    }
                }
                try{
                	Thread.sleep(sleepSpan);//睡眠指定毫秒数
                }
                catch(Exception e){
                	e.printStackTrace();
                }
            }
		}
	}
}

