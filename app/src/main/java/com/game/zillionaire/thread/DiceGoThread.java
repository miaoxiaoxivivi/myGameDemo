package com.game.zillionaire.thread;

import com.game.zillionaire.figure.Dice;

public class DiceGoThread extends Thread 
{
	Dice di;
	public boolean flag;//线程是否执行标志位
	public boolean isMoving=false;//骰子是否在走标志位
	int sleepSpan = 40;//骰子走路时没一小步的休眠时间
	int waitSpan = 1000;//骰子不走时线程空转的等待时间
	public DiceGoThread(Dice di)
	{
		this.di=di;
		this.flag=true;
	}
	//线程执行方法
	public void run()
	{
		while(flag)
		{
			while(isMoving)
			{
				if(!(di.father.isGoTo))
				{
					di.k=5;
				}
				if(di.nextFrame())
				{
					if(di.k==5)
					{
						sleepSpan=500;
					}
				}else
				{
					di.father.indext++;
					di.father.figure0.startToGo(di.father.indext);
					di.flag=false;
					sleepSpan=40;
					di.k=0;
					this.setMoving(false);
					break;
				}
				try{//先睡一下
					Thread.sleep(sleepSpan);
				}
				catch(Exception e){
					e.printStackTrace();
				}
			}
			try{//线程空转等待
				Thread.sleep(waitSpan);
			}
			catch(Exception e){
				e.printStackTrace();
			}
		}
	}
	//方法：设置是否走路标志位
	public void setMoving(boolean isMoving)
	{
		this.isMoving = isMoving;
	}
}
