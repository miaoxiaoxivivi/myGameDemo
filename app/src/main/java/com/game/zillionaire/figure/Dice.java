package com.game.zillionaire.figure;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import com.game.zillionaire.thread.DiceGoThread;
import com.game.zillionaire.util.PicManager;
import com.game.zillionaire.view.GameView;

import static com.game.zillionaire.util.ConstantUtil.Dice_ANIMATION_FRAMES;
import static com.game.zillionaire.util.ConstantUtil.Dice_ANIMATION_SEGMENTS;
import static com.game.zillionaire.util.ConstantUtil.Dice_HEIGHT;
import static com.game.zillionaire.util.ConstantUtil.Dice_WIDTH;

public class Dice 
{
	public GameView father;//Activity引用
	int currentFrame = 0;//当前英雄的动画段的当前动画帧，从零开始
	public int x=0;//骰子的x坐标，用于绘制 
	public int y=0;//骰子的y坐标，用于绘制
	Bitmap bitmap;//当前帧图片
	Bitmap[] bitmaps=new Bitmap[Dice_ANIMATION_FRAMES];//所有骰子的帧数组
	public static Bitmap [][] bmpDice;//存放骰子动画的图片数组  
	public int k=1;
	public boolean flag=false;//是否绘制骰子
	public DiceGoThread dgt;//负责英雄走路的线程
	
	public Dice(GameView gameView)
	{
		father=gameView;
		dgt=new DiceGoThread(this);
		dgt.start();
	}
	public void setBitmap(int x, int y)
	{
		Bitmap tempBmp=null;
		while(tempBmp==null)
		{
			tempBmp=PicManager.getPic("dice",father.activity.getResources());//加载包含骰子动画的大图
		}
		bmpDice = new Bitmap[Dice_ANIMATION_SEGMENTS][Dice_ANIMATION_FRAMES];//声明数组
		for(int i=0;i<6;i++)//对大图进行切割，转换成Bitmap的二维数组
		{
			for(int j=0;j<5;j++)
			{
				bmpDice[i][j] = Bitmap.createBitmap(tempBmp, j*Dice_WIDTH, i*Dice_HEIGHT,Dice_WIDTH, Dice_HEIGHT);
			}
		}
		this.x = x;
		this.y = y;
		dgt.setMoving(true);
		if(father.indext!=-1)
		{
			this.bitmaps= bmpDice[father.indext];//获得换帧动画
			bitmap = bitmaps[0];//第一张图
			flag=true;
		}
	}
	public void draw(Canvas canvas)//绘制方法
	{
		canvas.drawBitmap(bitmap, x, y, null);
	}
	public boolean nextFrame()//换帧，成功返回true。否则返回false
	{
		if(k < bitmaps.length)
		{
			bitmap = bitmaps[k];
			this.x-=40;
			if(k<3)
			{
				this.y-=30;
			}else
			{
				this.y+=30;
			}
			k++;
			return true;
		}
		return false;
	}
}
