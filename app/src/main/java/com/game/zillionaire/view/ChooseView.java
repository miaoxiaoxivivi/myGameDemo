package com.game.zillionaire.view;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Message;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import com.game.zillionaire.activity.ZActivity;
import com.game.zillionaire.util.ConstantUtil;
import com.game.zillionaire.util.PicManager;
import com.game.zillionaire.util.ScreenTransUtil;

public class ChooseView extends SurfaceView implements SurfaceHolder.Callback
{
	ZActivity activity;//activity的引用
	public DrawThread drawThread;//刷帧的线程
	static Bitmap people0;//人物0头像图
	static Bitmap people1;//人物1头像图
	static Bitmap people2;//人物2头像图
	static Bitmap[] peoples=new Bitmap[3];//人物卡片图组
	static Bitmap chooseBackBitmap;//选择人物界面背景图
	private boolean drawAll=false;//绘制所有人物
	private int count=0;//绘制所有人物时的绘制次数
	public ChooseView(ZActivity activity) {
		super(activity);
		getHolder().addCallback(this);
		this.activity = activity;//activity的引用
		this.drawThread = new DrawThread(getHolder(), this);//初始化刷帧线程
	}
	public void onDraw(Canvas canvas)//绘制方法
	{
		if(canvas==null)
		{
			return;
		}
		chooseBackBitmap=PicManager.getPic("choose",activity.getResources());//获取选择人物界面背景图
		people0=PicManager.getPic("people0_1",activity.getResources());//人物0的头像图
		people1=PicManager.getPic("people2_1",activity.getResources());//人物1的头像图
		people2=PicManager.getPic("people1_1",activity.getResources());//人物2的头像图
		peoples[0]=PicManager.getPic("people0",activity.getResources());//人物0的卡片图
		peoples[1]=PicManager.getPic("people2",activity.getResources());//人物1的卡片图
		peoples[2]=PicManager.getPic("people1",activity.getResources());//人物2的卡片图
		if(count>=10)
		{
			drawAll=false;
			count=0;
			Message msg1 = this.activity.myHandler.obtainMessage(1);
			this.activity.myHandler.sendMessage(msg1);//向主activity发送Handler消息
		}
		canvas.translate(ConstantUtil.LOX, ConstantUtil.LOY);
		canvas.scale(ConstantUtil.RADIO, ConstantUtil.RADIO);
		canvas.clipRect(0,0,1280,720);
		
		canvas.drawBitmap(chooseBackBitmap, 0, 0, null);
		canvas.drawBitmap(peoples[activity.peopleindext], 120, 120, null);//绘制人物
		if(drawAll)
		{
			for(int i=0,j=0;i<3;i++)
			{
				if(i!=activity.peopleindext)
				{
					canvas.drawBitmap(peoples[i], 340+j*155+j*70,120, null);//绘制人物
				    j++;
				}
			}
			count++;
		}
		//绘制头像
		canvas.drawBitmap(people0, 107,366, null);
		canvas.drawBitmap(people1, 259,376, null);
		canvas.drawBitmap(people2,429,369, null);
	}
	@Override
	public boolean onTouchEvent(MotionEvent event)
	{
		if(event.getAction()==MotionEvent.ACTION_DOWN)
		{
			int x=ScreenTransUtil.xFromRealToNorm((int)event.getX());
			int y=ScreenTransUtil.yFromRealToNorm((int)event.getY());
			if(x>=ConstantUtil.ChooseView_ReturnMenu_Left_X&&x<=ConstantUtil.ChooseView_ReturnMenu_Right_X
					&&y>=ConstantUtil.ChooseView_ReturnMenu_Up_Y&&y<=ConstantUtil.ChooseView_ReturnMenu_Down_Y)//返回游戏菜单界面
			{
				Message msg1 = this.activity.myHandler.obtainMessage(0);
				this.activity.myHandler.sendMessage(msg1);//向主activity发送Handler消息
			}else if(x>=ConstantUtil.ChooseView_EnterGame_Left_X&&x<=ConstantUtil.ChooseView_EnterGame_Right_X
					&&y>=ConstantUtil.ChooseView_EnterGame_Up_Y&&y<=ConstantUtil.ChooseView_EnterGame_Down_Y)//进入游戏界面
			{
				drawAll=true;
			}else if(x>=ConstantUtil.ChooseView_ChooseFigure1_Left_X&&x<=ConstantUtil.ChooseView_ChooseFigure1_Right_X
					&&y>=ConstantUtil.ChooseView_ChooseFigure1_Up_Y&&y<=ConstantUtil.ChooseView_ChooseFigure1_Down_Y)//选择人物0
			{
				activity.peopleindext=0;
			}
			else if(x>=ConstantUtil.ChooseView_ChooseFigure2_Left_X&&x<=ConstantUtil.ChooseView_ChooseFigure2_Right_X
					&&y>=ConstantUtil.ChooseView_ChooseFigure2_Up_Y&&y<=ConstantUtil.ChooseView_ChooseFigure2_Down_Y)//选择人物1
			{
				activity.peopleindext=1;
			}
			else if(x>=ConstantUtil.ChooseView_ChooseFigure3_Left_X&&x<=ConstantUtil.ChooseView_ChooseFigure3_Right_X
					&&y>=ConstantUtil.ChooseView_ChooseFigure3_Up_Y&&y<=ConstantUtil.ChooseView_ChooseFigure3_Down_Y)//选择人物2
			{
				activity.peopleindext=2;
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
		private ChooseView chooseView;
		private boolean isViewOn = false;
		private boolean flag = true;
        public DrawThread(SurfaceHolder surfaceHolder, ChooseView chooseView) //构造器
        {
        	super.setName("==GameView.DrawThread");
            this.surfaceHolder = surfaceHolder;
            this.chooseView=chooseView;
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
	                    	chooseView.onDraw(c);
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
