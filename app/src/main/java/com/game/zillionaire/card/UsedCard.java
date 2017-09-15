package com.game.zillionaire.card;

import static com.game.zillionaire.util.ConstantUtil.GAME_VIEW_SCREEN_COLS;
import static com.game.zillionaire.util.ConstantUtil.GAME_VIEW_SCREEN_ROWS;
import static com.game.zillionaire.util.ConstantUtil.TILE_SIZE_X;
import static com.game.zillionaire.util.ConstantUtil.TILE_SIZE_Y;

import java.util.ArrayList;

import com.game.zillionaire.map.GameData2;
import com.game.zillionaire.map.MyDrawable;
import com.game.zillionaire.util.PicManager;
import com.game.zillionaire.view.GameView;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.View;

public abstract class  UsedCard implements View.OnTouchListener
{
	public boolean isDrawCard=false;//判断是否绘制卡片
	public int x;//物体绘制坐标x
	public int y;//物体绘制坐标y
	GameView gv;//GameView的对象
	MyDrawable md=null;//MyDrawable对象
	public int count=0;//头像数目
	public int[][] xx=new int[3][2];//人物的标志索引
	public boolean isChange=true;//判断是绘制障碍物、炸弹对话框还是绘制其他对话框
	public int iscount=0;//绘制次数
	Bitmap bitmap0;//使用的卡片图
	public int pp=0;//选中框的索引
	public boolean drawdialog=false;//是否绘制选择是否确定使用该卡片标志位
	public static int currentFigure=-1;//当前的人物索引
	public static ArrayList<MyDrawable> mapList= new ArrayList<MyDrawable>();
	
	public UsedCard(GameView gv,Bitmap bitmap)
	{
		this.gv=gv;
		this.isChange=true;//对话框类型赋值
		this.isDrawCard=true;//是否绘制卡片赋值
		this.bitmap0=bitmap;//使用的卡片图片赋值
		this.x=this.gv.figure0.topLeftCornerX+20;//物体绘制坐标x赋值
		this.y=this.gv.figure0.topLeftCornerY+70;//物体绘制坐标y赋值
	}
	public void onDraw(Canvas canvas)//绘制方法
	{
		if(gv.isFigure==0)//操作人物使用卡片
		{
			if(isChange)//触屏选择地方类型
			{
				if(iscount<=8)//绘制卡片触屏提示对话框
				{
					Bitmap bmpSelf=PicManager.getPic(GameData2.bmpDialogBack, gv.activity.getResources());//获取对话框图片
					canvas.drawBitmap(bmpSelf, 280, 180, null);//绘制对话框图片
					Paint paint = new Paint();//创建画笔
					paint.setARGB(255, 42, 48, 103);//设置字体颜色
					paint.setAntiAlias(true);//抗锯齿
					paint.setFakeBoldText(true);//字体加粗
					paint.setTextSize(26);//设置文字大小
					canvas.drawText("请选择视野内任一栋建筑物。", 320, 260, paint);//对话框中的信息
				}else
				{
					drawdialog=true;//绘制选择是否确定使用该卡片
					canvas.drawBitmap(UseCardView.isbitmap, 650, 400,null);//绘制确定是否使用卡片选择框
					canvas.drawBitmap(UseCardView.bbitmap[pp], x, y,null);//绘制与卡片相对应的物体
				}
				iscount++;//绘制次数加一
			}else//直接使用
			{
				if(iscount<=10)//绘制使用卡片对话框
				{
					Bitmap bmpSelf=PicManager.getPic(GameData2.bmpDialogBacks[1], gv.activity.getResources());//获取对话框图片
					canvas.drawBitmap(bmpSelf, 280, 180, null);//绘制对话框图片
					Bitmap headBitmap=PicManager.getPic(gv.figure0.headBitmapName, gv.activity.getResources());
					canvas.drawBitmap(headBitmap,300, 180,null);//绘制头像
					canvas.drawBitmap(bitmap0, 570, 188,null);//绘制要使用的卡片
				}else
				{
					if(count==0)//屏幕范围内没有人物时
					{
						setUsed();
					}else//绘制人物选择框
					{
						this.drawdialog=true;//绘制选择是否确定使用该卡片标志位
						canvas.drawBitmap(UseCardView.dialogBitmap, 280, 180, null);//绘制
						if(count==3)//绘制3个人物头像
						{
							canvas.drawBitmap(UseCardView.bitmap[xx[0][0]], 330, 260, null);
							canvas.drawBitmap(UseCardView.bitmap[xx[1][0]], 450, 260, null);
							canvas.drawBitmap(UseCardView.bitmap[xx[2][0]], 570, 260, null);
						}else if(count==2)//绘制2个人物头像
						{
							canvas.drawBitmap(UseCardView.bitmap[xx[0][0]], 380, 260, null);
							canvas.drawBitmap(UseCardView.bitmap[xx[1][0]], 530, 260, null);
						}else if(count==1)//绘制1个人物头像
						{
							canvas.drawBitmap(UseCardView.bitmap[xx[0][0]], 450, 260, null);
						}
					}
				}
			}
			iscount++;//绘制次数加一
		}
	}
	@Override
	public boolean onTouch(View arg0, MotionEvent arg1) {
		return false;
	}
	public void isSS()//搜集屏幕中的人物个数
	{
		//上层
		q:for(int i=0; i<GAME_VIEW_SCREEN_ROWS; i++)
		{
			for(int j=0; j<GAME_VIEW_SCREEN_COLS; j++)
			{
				//计算人物个数
				if(gv.figure.isDreaw&&gv.figure.y/TILE_SIZE_Y-gv.tempStartRow == i && gv.figure.x/TILE_SIZE_X-gv.tempStartCol == j)//英雄在这里
				{
					xx[count][0]=gv.figure.Bitmapindext;//头像图片索引
					xx[count][1]=gv.figure.k;//人物对应的编号
					count++;
				}
				if(gv.figure1.isDreaw&&gv.figure1.y/TILE_SIZE_Y-gv.tempStartRow == i && gv.figure1.x/TILE_SIZE_X-gv.tempStartCol == j)//英雄在这里
				{
					xx[count][0]=gv.figure1.Bitmapindext;//头像图片索引
					xx[count][1]=gv.figure1.k;//人物对应的编号
					count++;
				}
				if(gv.figure2.isDreaw&&gv.figure2.y/TILE_SIZE_Y-gv.tempStartRow == i && gv.figure2.x/TILE_SIZE_X-gv.tempStartCol == j)//英雄在这里
				{
					xx[count][0]=gv.figure2.Bitmapindext;//头像图片索引
					xx[count][1]=gv.figure2.k;//人物对应的编号
					count++;
				}
				if(count>=3)
				{
					break q;
				}
			}
		}
	}
	public void setBitmap(Bitmap bm,int indext)
	{
	}
	
	//方法：返还监听器，回复游戏状态
	public void recoverGame()
	{
		this.isDrawCard=false;//不绘制卡片
		this.isChange=true;//对话框类型还原
		this.count=0;//头像数目还原
		this.pp=0;//选中框的索引还原
		this.iscount=0;//绘制次数还原
		drawdialog=false;//是否绘制选择是否确定使用该卡片标志位还原
		this.gv.setOnTouchListener(this.gv);//返还监听器
		this.gv.setStatus(0);//重新设置GameView为待命状态
		if(gv.isFigure==0)//是操作人物时
		{
			gv.isDraw=false;//绘制GO图标
			gv.isMenu=true;//绘制游戏界面中的小菜单
		}
	}
	public void setUsed() {
	}
}
