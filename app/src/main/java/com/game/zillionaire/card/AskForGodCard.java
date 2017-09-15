package com.game.zillionaire.card;

import static com.game.zillionaire.util.ConstantUtil.GAME_VIEW_SCREEN_COLS;
import static com.game.zillionaire.util.ConstantUtil.GAME_VIEW_SCREEN_ROWS;

import android.graphics.Bitmap;

import com.game.zillionaire.animation.AngelGodAnimation;
import com.game.zillionaire.animation.BigEmGodAnimation;
import com.game.zillionaire.animation.CaiGodAnimation;
import com.game.zillionaire.animation.DogGodAnimation;
import com.game.zillionaire.animation.FuGodAnimation;
import com.game.zillionaire.animation.GroundGodAnimation;
import com.game.zillionaire.animation.QiongGodAnimation;
import com.game.zillionaire.animation.ShuaiGodAnimation;
import com.game.zillionaire.figure.CrashFigure;
import com.game.zillionaire.figure.Figure;
import com.game.zillionaire.map.Layer;
import com.game.zillionaire.map.MyDrawable;
import com.game.zillionaire.view.GameView;

public class AskForGodCard extends UsedCard
{
	public AskForGodCard(GameView gv, Bitmap bitmap) {
		super(gv, bitmap);
	}
	public void setUsed()
	{
		setQS(gv.figure0);
		recoverGame();
	}
	public void setQS(Figure fg)//请神卡
	{
		//绘制碰撞人物
		for(int i=0; i<=GAME_VIEW_SCREEN_ROWS; i++)
		{
			for(int j=0; j<=GAME_VIEW_SCREEN_COLS; j++)
			{
				Layer l = (Layer)gv.layerList.layers.get(0);//获得下层的图层
				MyDrawable[][] mapMatrix=l.getMapMatrix();
				if(mapMatrix[i+gv.tempStartRow][j+gv.tempStartCol].flag)
				{ 
					int id=CrashFigure.idColRow[i+gv.tempStartRow][j+gv.tempStartCol];//根据位置确定神明类型
					for(CrashFigure cf:gv.cfigure)
					{
						if(cf.x==(i+gv.tempStartRow)
							&&cf.y==(j+gv.tempStartCol)//同一个位置
							&&id==cf.id//神明类型一致
							&&mapMatrix[i+gv.tempStartRow][j+gv.tempStartCol].refCol==cf.refCol
							&&mapMatrix[i+gv.tempStartRow][j+gv.tempStartCol].refCol==cf.refRow)
						{
							startAnimotion(id,fg,cf,gv);//启动相应动画
							break;//启动相应动画后，退出循环
						}
					}
					if(gv.figure0.k==0)
					{
						CrashFigure.isMeet1=true;
					}else if(gv.figure0.k==1)
					{
						CrashFigure.isMeet0=true;
					}else if(gv.figure0.k==2)
					{
						CrashFigure.isMeet=true;
					}
				}
			}
		}
	}
	//根据神明类型启动相应的动画
	public boolean startAnimotion(int id,Figure figure,CrashFigure cf,GameView gv)
	{
		switch(id)
		{
		case 0://大财神
		case 3://小财神
			gv.aa=new CaiGodAnimation(gv);
			gv.aa.isAngel=true;//设置标志位为true
			gv.aa.startAnimation();//启动动画						
			gv.aa.setClass(figure.figureFlag);//设置碰撞时的英雄类型：是玩家操控还是系统控制
			figure.id=cf.id;//记录英雄碰撞神明的类型
			gv.aa.bitmapnum=figure.id;//指定播放相应动画
			gv.temp=gv.date+2;//设置神明存活期
			gv.cfigure.remove(cf);
			return true;
		case 1://大福神
		case 5://小福神
			gv.aa=new FuGodAnimation(gv);
			gv.aa.isAngel=true;//设置标志位为true
			gv.aa.startAnimation();//启动动画						
			gv.aa.setClass(figure.figureFlag);//设置碰撞时的英雄类型：是玩家操控还是系统控制
			figure.id=cf.id;//记录碰撞神明的类型
			gv.aa.bitmapnum=figure.id;//指定播放相应动画
			gv.temp=gv.date+2;//设置神明存活期
			gv.cfigure.remove(cf);
			return true;
		case 2://土地公
			gv.aa=new GroundGodAnimation(gv);
			gv.aa.isAngel=true;//设置标志位为true
			gv.aa.startAnimation();//启动动画						
			gv.aa.setClass(figure.figureFlag);//设置碰撞时的英雄类型：是玩家操控还是系统控制
			figure.id=cf.id;//记录碰撞神明的类型
			gv.aa.bitmapnum=figure.id;//指定播放相应动画
			gv.temp=gv.date+2;//设置神明存活期
			gv.cfigure.remove(cf);
			return true;
		case 4://小天使
			gv.aa=new AngelGodAnimation(gv);
			gv.aa.isAngel=true;//设置标志位为true
			gv.aa.startAnimation();//启动动画						
			gv.aa.setClass(figure.figureFlag);//设置碰撞时的英雄类型：是玩家操控还是系统控制
			figure.id=cf.id;//记录碰撞神明的类型
			gv.aa.bitmapnum=figure.id;//指定播放相应动画
			gv.temp=gv.date+2;//设置神明存活期
			gv.cfigure.remove(cf);
			return true;
		case 6://小穷神
		case 7://大穷神
			gv.aa=new QiongGodAnimation(gv);
			gv.aa.isAngel=true;//设置标志位为true
			gv.aa.startAnimation();//启动动画						
			gv.aa.setClass(figure.figureFlag);//设置碰撞时的英雄类型：是玩家操控还是系统控制
			figure.id=cf.id;//记录碰撞神明的类型
			gv.aa.bitmapnum=figure.id;//指定播放相应动画
			gv.temp=gv.date+2;//设置神明存活期
			gv.cfigure.remove(cf);
			return true;
		case 8://小衰神
		case 9://大衰神
			gv.aa=new ShuaiGodAnimation(gv);
			gv.aa.isAngel=true;//设置标志位为true
			gv.aa.startAnimation();//启动动画						
			gv.aa.setClass(figure.figureFlag);//设置碰撞时的英雄类型：是玩家操控还是系统控制
			figure.id=cf.id;//记录碰撞神明的类型
			gv.aa.bitmapnum=figure.id;//指定播放相应动画
			gv.temp=gv.date+2;//设置神明存活期				
			gv.cfigure.remove(cf);
			return true;
		case 10://小恶魔
			gv.aa=new BigEmGodAnimation(gv);
			gv.aa.isAngel=true;//设置标志位为true
			gv.aa.startAnimation();//启动动画						
			gv.aa.setClass(figure.figureFlag);//设置碰撞时的英雄类型：是玩家操控还是系统控制
			figure.id=cf.id;//记录碰撞神明的类型
			gv.aa.bitmapnum=figure.id;//指定播放相应动画
			gv.temp=gv.date+2;//设置神明存活期			
			gv.cfigure.remove(cf);
			return true;
		case 11://恶犬
			gv.aa=new DogGodAnimation(gv);
			gv.aa.isAngel=true;//设置标志位为true
			gv.aa.startAnimation();//启动动画						
			gv.aa.setClass(figure.figureFlag);//设置碰撞时的英雄类型：是玩家操控还是系统控制
			figure.id=cf.id;//记录碰撞神明的类型
			gv.aa.bitmapnum=figure.id;//指定播放相应动画
			gv.temp=gv.date+2;//设置神明存活				
			gv.cfigure.remove(cf);
			return true;
		}
		recoverGame();
		return false;
	}
}
