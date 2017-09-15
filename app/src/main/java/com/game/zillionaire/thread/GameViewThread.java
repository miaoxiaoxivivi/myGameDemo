package com.game.zillionaire.thread;

import com.game.zillionaire.view.GameView;
/*
 * 该类主要负责定时读取GameView的状态，如果为待命则切换人物
 * 使其产生动画
 */
public class GameViewThread extends Thread {
	
	GameView gv;//游戏视图类的引用
	int sleepSpan = 180;//休眠时间
	int waitSpan = 1500;//空转时的等待时间
	public boolean flag;//线程是否执行标志位
	boolean isChanging;//是否需要换骰子动画
	
	public GameViewThread(){}
	//构造器
	public GameViewThread(GameView gv){
		super.setName("==GameViewThread");
		this.gv = gv;
		flag = true;
	}
	//线程执行方法
	public void run()
	{
		while(flag)
		{//线程正在执行
			while(isChanging)
			{
				if(gv.isDialog||gv.isFigureMove||gv.isWW)//出现对换框时，则不变换人物
				{
					gv.status=1;
				}
				//需要换人物
				if(gv.status==0)
				{
					try{//先睡一下
						Thread.sleep(sleepSpan);
					}
					catch(Exception e){
						e.printStackTrace();
					}
					if(gv.isFigure==0)
					{
						gv.isFigure=1;
						gv.isMenu=false;//不绘制菜单
						try{
							Thread.sleep(500);
						}
						catch(Exception e){//先睡一下
							e.printStackTrace();
						}
						gv.gvu.isGoDice(gv.figure1);//系统人物1掷骰子
					}else if(gv.isFigure==1)
					{
						gv.isFigure=2;
						gv.isMenu=false;//不绘制菜单
						try{
							Thread.sleep(500);
						}
						catch(Exception e){//先睡一下
							e.printStackTrace();
						}
						gv.gvu.isGoDice(gv.figure2);//系统人物2掷骰子
					}
					else if(gv.isFigure==2)
					{
						gv.isFigure=0;
						if(gv.isGoTo&&gv.figure.isDreaw&&(!gv.isWW))
						{
							gv.isDraw=false;//绘制GO图标
							gv.isYuanBao=true;//显示元宝图标
							gv.isMenu=true;//绘制菜单
						}else
						{
							try{
								Thread.sleep(500);
							}
							catch(Exception e){//先睡一下
								e.printStackTrace();
							}
							gv.gvu.isGoDice(gv.figure);//操作人物掷骰子
						}
						gv.count++;//当人物全部走过之后，计数器加1
					}
				}
				gv.setStatus(1);
				this.setChanging(false);
			}
			try{//不需要换骰子时线程的空转等待时间
				Thread.sleep(waitSpan);
			}
			catch(Exception e){
				e.printStackTrace();
			}
		}
	}
	//方法：设置是否需要换骰子
	public void setChanging(boolean isChanging)
	{
		this.isChanging = isChanging;
	}
}