package com.game.zillionaire.menuview;

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

public class HelpView extends SurfaceView implements SurfaceHolder.Callback
{
	ZActivity activity;//activity的引用
	public DrawThread drawThread;//刷帧的线程
	private static Bitmap helpBackBitmap;//帮助界面的背景图
	private static Bitmap[] helpTextBitmaps=new Bitmap[3];//帮助界面中各个文字图片
	private static Bitmap[] helpTitleBitmaps=new Bitmap[3];//帮助界面中各个标题图片
	private static int bitmapindext=0;//图片数组的索引
	private int bitmapx=76,bitmapy=117;//文字图片的绘制坐标
	int downx=0,downy=0;//记录按下时的坐标
	public HelpView(ZActivity activity){
		super(activity);
		getHolder().addCallback(this);
		this.activity = activity;//activity的引用
		this.drawThread = new DrawThread(getHolder(), this);//初始化刷帧线程
	}

	public void onDraw(Canvas canvas)//绘制方法
	{
		helpBackBitmap=PicManager.getPic("help",activity.getResources());
		helpTextBitmaps[0]=PicManager.getPic("system",activity.getResources());
		helpTextBitmaps[1]=PicManager.getPic("dilly",activity.getResources());
		helpTextBitmaps[2]=PicManager.getPic("other",activity.getResources());
		helpTitleBitmaps[0]=PicManager.getPic("system0",activity.getResources());
		helpTitleBitmaps[1]=PicManager.getPic("dilly0",activity.getResources());
		helpTitleBitmaps[2]=PicManager.getPic("other0",activity.getResources());
		canvas.translate(ConstantUtil.LOX, ConstantUtil.LOY);
		canvas.scale(ConstantUtil.RADIO, ConstantUtil.RADIO);
		canvas.clipRect(0,0,1280,720);
		
		canvas.drawBitmap(helpTextBitmaps[bitmapindext], bitmapx, bitmapy, null);
		canvas.drawBitmap(helpBackBitmap, 0, 0, null);
		canvas.drawBitmap(helpTitleBitmaps[bitmapindext], 0, 70, null);
	}
	@Override
	public boolean onTouchEvent(MotionEvent event)
	{
		if(event.getAction()==MotionEvent.ACTION_DOWN)
		{
			int x=ScreenTransUtil.xFromRealToNorm((int)event.getX());
			int y=ScreenTransUtil.yFromRealToNorm((int)event.getY());
			if(x>=ConstantUtil.HelpView_ReturnMainMenu_Left_X&&x<=ConstantUtil.HelpView_ReturnMainMenu_Right_X
					&&y>=ConstantUtil.HelpView_ReturnMainMenu_Up_Y&&y<=ConstantUtil.HelpView_ReturnMainMenu_Down_Y)//返回菜单界面
			{
				bitmapx=76;
				bitmapy=117;
				bitmapindext=0;
				Message msg1 = this.activity.myHandler.obtainMessage(3);
				this.activity.myHandler.sendMessage(msg1);//向主activity发送Handler消息
			}else if(x>=ConstantUtil.HelpView_System_Left_X&&x<=ConstantUtil.HelpView_System_Right_X
					&&y>=ConstantUtil.HelpView_Up_Y&&y<=ConstantUtil.HelpView_Down_Y)//系统
			{
				bitmapx=76;
				bitmapy=117;
				bitmapindext=0;
			}else if(x>ConstantUtil.HelpView_SpecialFigure_Left_X&&x<=ConstantUtil.HelpView_SpecialFigure_Right_X
					&&y>=ConstantUtil.HelpView_Up_Y&&y<=ConstantUtil.HelpView_Down_Y)//特殊人物
			{
				bitmapx=76;
				bitmapy=117;
				bitmapindext=1;
			}else if(x>=ConstantUtil.HelpView_Others_Left_X&&x<=ConstantUtil.HelpView_Others_Right_X
					&&y>=ConstantUtil.HelpView_Up_Y&&y<=ConstantUtil.HelpView_Down_Y)//其他
			{
				bitmapx=76;
				bitmapy=117;
				bitmapindext=2;
			}
			downx=x;downy=y;
		}else if(event.getAction()==MotionEvent.ACTION_MOVE)
		{
			int tempx=ScreenTransUtil.xFromRealToNorm((int)event.getX());
			int tempy=ScreenTransUtil.yFromRealToNorm((int)event.getY());
			if(tempx>=ConstantUtil.HelpView_OnTouchWord_Left_X&&tempx<=ConstantUtil.HelpView_OnTouchWord_Right_X
					&&tempy>=ConstantUtil.HelpView_OnTouchWord_Up_Y&&tempy<=ConstantUtil.HelpView_OnTouchWord_Down_Y)//触控文字
			{
				int prey=tempy-downy;
				if((bitmapy+prey)>(454-helpTextBitmaps[bitmapindext].getHeight())&&(bitmapy+prey)<120)
				{
					bitmapy=bitmapy+prey;
				}
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

		private int sleepSpan =80;//睡眠的毫秒数 
		private SurfaceHolder surfaceHolder;
		private HelpView helpView;
		private boolean isViewOn = false;
		private boolean flag = true;
        public DrawThread(SurfaceHolder surfaceHolder, HelpView helpView) //构造器
        {
        	super.setName("==GameView.DrawThread");
            this.surfaceHolder = surfaceHolder;
            this.helpView=helpView;
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
	                    	helpView.onDraw(c);
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
