package com.game.zillionaire.view;

import com.game.zillionaire.activity.ZActivity;
import com.game.zillionaire.util.ConstantUtil;
import com.game.zillionaire.util.PicManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.os.Message;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * 
 * 该类为进度条界面
 * 当程序长时间加载时给用户提示
 *
 */
public class LoadingView extends SurfaceView implements SurfaceHolder.Callback
{
	ZActivity activity;//activity的引用
	public DrawThread drawThread;//刷帧的线程
	public float angle=0;//初始旋转角度
	public int count=0;//绘制次数
	
	static Bitmap loadingBitmap;//加载背景图片
	static Bitmap crilBitmap;//加载圈图片
	static Bitmap crilBitmap0;//加载圈图片
	public LoadingView(ZActivity activity) {//构造器 
		super(activity);
		this.activity = activity;//activity的引用
        getHolder().addCallback(this);
        this.drawThread = new DrawThread(getHolder(), this);//初始化刷帧线程
	}
	public void onDraw(Canvas canvas)//自己写的绘制方法
	{
		if(canvas==null)
		{
			return;
		}
		loadingBitmap=PicManager.getPic("loading",activity.getResources());//获取加载背景图片
		crilBitmap=PicManager.getPic("loading0",activity.getResources());//获取加载图片
		crilBitmap0=PicManager.getPic("loading1",activity.getResources());//获取加载图片
		if(count>200)
		{
			Message msg1 = this.activity.myHandler.obtainMessage(7);
			this.activity.myHandler.sendMessage(msg1);//向主activity发送Handler消息
		}
		canvas.save();
		canvas.translate(ConstantUtil.LOX, ConstantUtil.LOY);
		canvas.scale(ConstantUtil.RADIO, ConstantUtil.RADIO);
		canvas.clipRect(0,0,1280,720);
		canvas.drawColor(Color.BLACK);
		
		canvas.drawBitmap(loadingBitmap, 0, 0,null);//绘制加载图片
   
		Matrix m1=new Matrix();
		m1.setRotate(angle,83,83);
		Matrix m3=new Matrix();
		m3.setTranslate(300,120);
		Matrix mzz=new Matrix();
		mzz.setConcat(m3, m1);
		canvas.drawBitmap(crilBitmap, mzz, null);
		canvas.drawBitmap(crilBitmap0, mzz, null);
		canvas.restore();
		count++;
	}
	
	public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
	}
	
	public void surfaceCreated(SurfaceHolder holder) {//创建时被调用
        this.drawThread.setFlag(true);
        this.drawThread.start();//启动刷帧线程
	}
	
	public void surfaceDestroyed(SurfaceHolder holder) {//释放时被调用
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
		private LoadingView loadingView;
		private boolean flag = false;
        public DrawThread(SurfaceHolder surfaceHolder, LoadingView loadingView) {//构造器
        	super.setName("==LoadingView.DrawThrea");
            this.surfaceHolder = surfaceHolder;
            this.loadingView = loadingView;
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
                    	loadingView.onDraw(c);
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