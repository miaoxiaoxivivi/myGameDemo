package com.game.zillionaire.card;

import static com.game.zillionaire.util.ConstantUtil.GAME_VIEW_SCREEN_COLS;
import static com.game.zillionaire.util.ConstantUtil.GAME_VIEW_SCREEN_ROWS;
import static com.game.zillionaire.util.ConstantUtil.MAP_COLS;
import static com.game.zillionaire.util.ConstantUtil.MAP_ROWS;
import android.graphics.Bitmap;
import android.view.MotionEvent;
import android.view.View;

import com.game.zillionaire.dialog.DialogEnum;
import com.game.zillionaire.map.Layer;
import com.game.zillionaire.map.MyDrawable;
import com.game.zillionaire.meetdrawable.GroundDrawable;
import com.game.zillionaire.util.ConstantUtil;
import com.game.zillionaire.util.PicManager;
import com.game.zillionaire.util.ScreenTransUtil;
import com.game.zillionaire.view.GameView;

public class DemonCard extends UsedCard
{
	int count=0;//计算可摧毁的房屋栋数
	public DemonCard(GameView gv, Bitmap bitmap) {
		super(gv, bitmap);
		count=0;
	}
	@Override
	public boolean onTouch(View view, MotionEvent event)
	{
		int x=ScreenTransUtil.xFromRealToNorm((int)event.getX());//得到点击的X坐标
		int y=ScreenTransUtil.yFromRealToNorm((int)event.getY());//得到点击的Y坐标
		if(drawdialog)
		{
			q:for(int i=0; i<=GAME_VIEW_SCREEN_ROWS; i++)
			{   
				for(int j=0; j<=GAME_VIEW_SCREEN_COLS; j++)
				{
					Layer l = (Layer)gv.layerList.layers.get(1);
					MyDrawable[][] mapMatrix=l.getMapMatrix();
					if(mapMatrix[i+gv.tempStartRow][j+gv.tempStartCol]!= null)
					{
						MyDrawable mmd=mapMatrix[i+gv.tempStartRow][j+gv.tempStartCol];
						Bitmap selfBmp=PicManager.getPic(mmd.bpName, gv.activity.getResources());
						if((mmd.da==1||mmd.da==2)&&mmd.ss!=-1)
						{
							if(x>=mmd.bitmapx&&x<=(mmd.bitmapx+64)&&y>=mmd.bitmapy&&y<=(mmd.bitmapy+selfBmp.getHeight()))
							{
								if(mmd.da==1)//大土地
								{
									pp=1;
									this.x=mmd.bitmapx+selfBmp.getWidth()-128;
									this.y=mmd.bitmapy+selfBmp.getHeight()-96;
								}else if(mmd.da==2)//小土地
								{
									pp=0;
									this.x=mmd.bitmapx+selfBmp.getWidth()-64;
									this.y=mmd.bitmapy+selfBmp.getHeight()-48;
								}
								md=mmd;
								break q;
							}
						}
					}
				}
			}
			if(x>=ConstantUtil.AngelCard_NoUseCard_RecoverGame_Left_X&&x<=ConstantUtil.AngelCard_NoUseCard_RecoverGame_Right_X
					&&y>=ConstantUtil.AngelCard_RecoverGame_Up_Y&&y<=ConstantUtil.AngelCard_RecoverGame_Down_Y)//不使用卡片
			{
				recoverGame();
			}else if(x>=ConstantUtil.AngelCard_UseCard_RecoverGame_Left_X&&x<=ConstantUtil.AngelCard_UseCard_RecoverGame_Right_X
					&&y>=ConstantUtil.AngelCard_RecoverGame_Up_Y&&y<=ConstantUtil.AngelCard_RecoverGame_Down_Y)//使用卡片
			{
				setEM();
				recoverGame();
			}
		}
		return true;
	}
	public void setUsed()//系统人物调用
	{
		setEM0();
	}
	public void setEM()//恶魔卡
	{
		for(int i=md.col; i>=0; i--)//向左搜索
		{ 
			Layer l = (Layer)gv.layerList.layers.get(1);
			MyDrawable[][] mapMatrix=l.getMapMatrix();
			if((i-gv.tempStartCol)>=0&&(i-gv.tempStartCol)<=GAME_VIEW_SCREEN_ROWS)
			{
				MyDrawable mm=mapMatrix[md.row][i];
				if(!(cutRoom(mm)))//判断是否加盖房屋
				{
					break;
				}
			}
		}
		for(int i=md.col+1; i<=MAP_COLS; i++)//向右搜索
		{ 
			Layer l = (Layer)gv.layerList.layers.get(1);
			MyDrawable[][] mapMatrix=l.getMapMatrix();
			if((i-gv.tempStartCol)>=0&&(i-gv.tempStartCol)<=GAME_VIEW_SCREEN_COLS)
			{
				MyDrawable mm=mapMatrix[md.row][i];
				if(!(cutRoom(mm)))//判断是否加盖房屋
				{
					break;
				}
			}
		}
		if(count>1)
		{
			return;
		}
		for(int i=md.row+1; i<=MAP_ROWS; i++)//向下搜索
		{  
			Layer l = (Layer)gv.layerList.layers.get(1);
			MyDrawable[][] mapMatrix=l.getMapMatrix();
			if((i-gv.tempStartRow)>=0&&(i-gv.tempStartRow)<=GAME_VIEW_SCREEN_ROWS)
			{
				MyDrawable mm=mapMatrix[i][md.col];
				if(!(cutRoom(mm)))//判断是否加盖房屋
				{
					break;
				}
			}
		}
		for(int i=md.row-1; i>=0; i--)//向上搜索
		{  
			Layer l = (Layer)gv.layerList.layers.get(1);
			MyDrawable[][] mapMatrix=l.getMapMatrix();
			if((i-gv.tempStartRow)>=0&&(i-gv.tempStartRow)<=GAME_VIEW_SCREEN_ROWS)
			{
				MyDrawable mm=mapMatrix[i][md.col];
				if(!(cutRoom(mm)))//判断是否加盖房屋
				{
					break;
				}
			}
		}
		gv.showDialog.initMessage(gv.figure0.figureName,gv.figure0.k,true,true,DialogEnum.MESSAGE_ONE,20,-1);
		gv.isDialog=true;//绘制对话框
	}
	public void setEM0()
	{
		for(MyDrawable mm:mapList)
		{
			cutRoom(mm);
		}
		mapList.clear();
		gv.showDialog.initMessage(gv.figure0.figureName,gv.figure0.k,true,true,DialogEnum.MESSAGE_ONE,20,-1);
		gv.isDialog=true;//绘制对话框
		recoverGame();
	}
	public boolean cutRoom(MyDrawable mm)//是否摧毁房屋
	{
		if(mm!= null&&mm.ss!=-1)
		{
			if(mm.da==2)//小土地
			{
				if(mm.k>0)
				{
					mm.k=0;
					mm.bpName=GroundDrawable.bitmaps[mm.kk][mm.k];
					count++;
					return true;
				}
			}else if(mm.da==1)//大土地
			{
				if(mm.k>=0)
				{
					mm.k=-1;
					mm.dbpName=mm.bpName;
					mm.flagg=false;
		    		mm.first=false;
					count++;
					return true;
				}
			}
		}
		return false;
	}
}
