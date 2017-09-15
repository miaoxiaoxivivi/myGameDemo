package com.game.zillionaire.util;
import com.game.zillionaire.figure.Figure;
import com.game.zillionaire.map.GameData2;
import com.game.zillionaire.map.Layer;
import com.game.zillionaire.map.MyDrawable;
import com.game.zillionaire.map.MyMeetableDrawable;
import com.game.zillionaire.meetdrawable.GroundDrawable;
import com.game.zillionaire.meetdrawable.NewsDrawable;
import com.game.zillionaire.view.GameView;

public class Room 
{
	public static void addRoom(Figure figure)//加盖一层房屋
	{
		MyMeetableDrawable mmd = figure.father.meetableChecker.check(figure);
		figure.father.isFigure=figure.k;//跳到该人物的位置
		if(mmd != null)
		{
			if(mmd.da!=3&&(mmd.ss!=-1))
			{
				if(mmd.da!=1)
				{
					if(mmd.k<5)
					{
						mmd.k++;
						mmd.bpName=GroundDrawable.bitmaps[mmd.kk][mmd.k];
					}
				}else
				{
					if(mmd.k<4&&mmd.k>=0)
					{
						mmd.k++;
						mmd.dbpName=GroundDrawable.bitmap2[mmd.zb][mmd.k];
					}
				}
			}
		}
	}
	public static void cutRoom(Figure figure)//摧毁一层房屋
	{
		MyMeetableDrawable mmd = figure.father.meetableChecker.check(figure);
		figure.father.isFigure=figure.k;//跳到该人物的位置
		if(mmd != null)
		{
			if(mmd.da!=3&&(mmd.ss!=-1))
			{
				if(mmd.da!=1)
				{
					if(mmd.k>0)
					{
						mmd.k--;
						mmd.bpName=GroundDrawable.bitmaps[mmd.kk][mmd.k];
					}
				}else
				{
					if(mmd.k>0)
					{
						mmd.k--;
						mmd.dbpName=GroundDrawable.bitmap2[mmd.zb][mmd.k];
					}else if(mmd.k==0)
					{
						mmd.k--;
						mmd.dbpName=GroundDrawable.bitmap[mmd.kk];
					}
				}
			}
		}
	}
	public static void changeRoom(Figure figure0,Figure figure)//强占土地
	{
		MyMeetableDrawable mmd = figure0.father.meetableChecker.check(figure0);
		if(mmd != null)
		{
			if(mmd.da!=3&&(mmd.ss!=-1))//土地已被购买
			{
				if(figure0.mhz.zMoney>mmd.value)//总资产够买
				{
					//交换土地
				    figure.mhz.xMoney+=mmd.value;//原拥有者现金增加
				    figure0.mhz.zMoney-=mmd.value;//现拥有者资产减一
				    figure0.count--;//拥有土地减一
					mmd.ss=figure0.ss;
					mmd.kk=figure0.k;
					figure0.count++;//拥有土地加一
				}else
				{
					return;
				}
				if(mmd.da!=1)//小土地
				{
					mmd.bpName=GroundDrawable.bitmaps[mmd.kk][mmd.k];
				}else//大土地
				{
					if(mmd.k>0)
					{
						mmd.bpName=GroundDrawable.bitmap[mmd.kk];//自身图片
						mmd.dbpName=GroundDrawable.bitmap2[mmd.zb][mmd.k];
					}
				}
			}
		}
	}
	
	//新闻摧毁一层楼
	public static void CutRoomNews(GameView gv,int temp,NewsDrawable nd)
	{
		int count=0;
		if(temp==1)//摧毁一栋楼房
		{
			count=1;
		}else
		{
			count=(int)(1+3*Math.random());
		}
		for(int i=nd.tempRow1;i<=nd.tempRow2;i++)
		{
			if(count<1)
			{
				break;
			}
			for(int j=nd.tempCol1;j<=nd.tempCol2;j++)
			{
				if(count<1)
				{
					break;
				}
				Layer l = (Layer)gv.layerList.layers.get(1);//获得上层的图层
				MyDrawable[][] mapMatrix=l.getMapMatrix();
				if(mapMatrix[i][j]!=null&&mapMatrix[i][j].ss!=-1)//土地已被购买
				{
					if(nd.messagenum==2)
					{
						nd.isFire=true;
						nd.tempx=mapMatrix[i][j].bitmapx;
						nd.tempy=mapMatrix[i][j].bitmapy;
					}
					if(mapMatrix[i][j].da==1)//大土地
					{
						mapMatrix[i][j].bpName=GameData2.jfBitmap;
						mapMatrix[i][j].dbpName=GameData2.jfBitmap;
						mapMatrix[i][j].zb=0;
					}else//小土地
					{
						mapMatrix[i][j].bpName=GameData2.jf1Bitmap;
					}
					//人物所拥有的土地减一
					if(mapMatrix[i][j].kk==0)
					{
						gv.figure.count--;
					}else if(mapMatrix[i][j].kk==1)
					{
						gv.figure1.count--;
					}else if(mapMatrix[i][j].kk==1)
					{
						gv.figure2.count--;
					}
					mapMatrix[i][j].kk=-1;
					mapMatrix[i][j].k=-1;
					mapMatrix[i][j].ss=-1;
					mapMatrix[i][j].first=true;
					mapMatrix[i][j].flagg=true;
					gv.room[gv.isFigure-4]=-1;
					count--;
				}
			}
		}
	}
	//新闻加盖一层楼
	public static void addRoomNews(GameView gv,int tempCol1,int tempCol2,int tempRow1,int tempRow2)
	{
		int count=(int)(1+3*Math.random());
		for(int i=tempRow1;i<=tempRow2;i++)
		{
			if(count<1)
			{
				break;
			}
			for(int j=tempCol1;j<=tempCol2;j++)
			{
				if(count<1)
				{
					break;
				}
				Layer l = (Layer)gv.layerList.layers.get(1);//获得上层的图层
				MyDrawable[][] mapMatrix=l.getMapMatrix();
				if(mapMatrix[i][j]!=null&&mapMatrix[i][j].ss!=-1)//土地已被购买
				{
					if(mapMatrix[i][j].da==1)//大土地
					{
						if(mapMatrix[i][j].zb!=0&&mapMatrix[i][j].zb!=1&&mapMatrix[i][j].k<4)
						{
							mapMatrix[i][j].k++;
							mapMatrix[i][j].dbpName=GroundDrawable.bitmap2[mapMatrix[i][j].zb][mapMatrix[i][j].k];
							count--;
						}
					}else//小土地
					{
						if(mapMatrix[i][j].k<5)
						{
							mapMatrix[i][j].k++;
							mapMatrix[i][j].bpName=GroundDrawable.bitmaps[mapMatrix[i][j].kk][mapMatrix[i][j].k];
							count--;
						}
					}
				}
			}
		}
	}
	public static void isGotoWhere(GameView gv)//跳转地图
	{
		switch(gv.isFigure)
		{
			case 0://操作人物
				gv.figure0=gv.figure;
				gv.tempStartRow = gv.figure.startRow;//记录本次绘制时的定位行
				gv.tempStartCol = gv.figure.startCol;//记录本次绘制时的定位列
				gv.tempOffsetX = gv.figure.offsetX;//记录本次绘制时的x偏移
				gv.tempOffsetY = gv.figure.offsetY;//记录本次绘制时的y偏移
			break;
			case 1://系统人物1
				gv.figure0=gv.figure1;
				gv.tempStartRow = gv.figure1.startRow;//记录本次绘制时的定位行
				gv.tempStartCol = gv.figure1.startCol;//记录本次绘制时的定位列
				gv.tempOffsetX = gv.figure1.offsetX;//记录本次绘制时的x偏移
				gv.tempOffsetY = gv.figure1.offsetY;//记录本次绘制时的y偏移
			break;
			case 2://系统人物2
				gv.figure0=gv.figure2;
				gv.tempStartRow = gv.figure2.startRow;//记录本次绘制时的定位行
				gv.tempStartCol = gv.figure2.startCol;//记录本次绘制时的定位列
				gv.tempOffsetX = gv.figure2.offsetX;//记录本次绘制时的x偏移
				gv.tempOffsetY = gv.figure2.offsetY;//记录本次绘制时的y偏移
			break;
			case 3:
			break;
			case 4://茶晶地区-1500-(7-13)(5-8)
				gv.tempStartRow =2;//记录本次绘制时的定位行
				gv.tempStartCol =5;//记录本次绘制时的定位列
				gv.tempOffsetX =0;//记录本次绘制时的x偏移
				gv.tempOffsetY = 0;//记录本次绘制时的y偏移
			break;
			case 5://蓝宝地区-1000-(15-23)(5-10)
				gv.tempStartRow =4;//记录本次绘制时的定位行
				gv.tempStartCol =13;//记录本次绘制时的定位列
				gv.tempOffsetX =0;//记录本次绘制时的x偏移
				gv.tempOffsetY =0;//记录本次绘制时的y偏移
			break;
			case 6://翡翠地区-800-(8-16)(11-16)
				gv.tempStartRow =9;//记录本次绘制时的定位行
				gv.tempStartCol =6;//记录本次绘制时的定位列
				gv.tempOffsetX =0;//记录本次绘制时的x偏移
				gv.tempOffsetY = 0;//记录本次绘制时的y偏移
			break;
			case 7://黑耀-2500-(6-13)(18-24)
				gv.tempStartRow =17;//记录本次绘制时的定位行
				gv.tempStartCol =4;//记录本次绘制时的定位列
				gv.tempOffsetX =0;//记录本次绘制时的x偏移
				gv.tempOffsetY = 0;//记录本次绘制时的y偏移
			break;
			case 8://粉晶-1200-(17-23)(17-23)
				gv.tempStartRow =16;//记录本次绘制时的定位行
				gv.tempStartCol =14;//记录本次绘制时的定位列
				gv.tempOffsetX =0;//记录本次绘制时的x偏移
				gv.tempOffsetY =0;//记录本次绘制时的y偏移
			break;
			case 9://紫晶-600-(24)(12-15)
				gv.tempStartRow =11;//记录本次绘制时的定位行
				gv.tempStartCol =15;//记录本次绘制时的定位列
				gv.tempOffsetX =0;//记录本次绘制时的x偏移
				gv.tempOffsetY = 0;//记录本次绘制时的y偏移
			break;
			case 10://医院
				gv.tempStartRow =8;//记录本次绘制时的定位行
				gv.tempStartCol =0;//记录本次绘制时的定位列
				gv.tempOffsetX =0;//记录本次绘制时的x偏移
				gv.tempOffsetY = 0;//记录本次绘制时的y偏移
				break;
			case 11://监狱
				gv.tempStartRow =8;//记录本次绘制时的定位行
				gv.tempStartCol =15;//记录本次绘制时的定位列
				gv.tempOffsetX =0;//记录本次绘制时的x偏移
				gv.tempOffsetY = 0;//记录本次绘制时的y偏移
				break;
		}
	}
}
