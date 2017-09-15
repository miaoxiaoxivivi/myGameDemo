package com.game.zillionaire.map;

import java.io.Serializable;

import com.game.zillionaire.activity.ZActivity;
import com.game.zillionaire.figure.Figure;
import android.content.res.Resources;

public class MeetableLayer extends Layer implements Serializable{//实现Serializable接口
	private static final long serialVersionUID = -8198661934376346999L;//持久化版本序列号
	public MyMeetableDrawable[][] mapMatrixMeetable;//实际地图
	private MyMeetableDrawable[][] mapMatrixForMeetable;//可遇矩阵
	
	public MeetableLayer(){}//空构造器
	
	public MeetableLayer(ZActivity at,Resources resources) //构造器
	{
		super(at, resources);
		GameData2.initMapData(at);//初始化上层地图数据
		this.mapMatrixMeetable = GameData2.mapData;//获取上层地图信息
		initMapMatrixForMeetable();//计算可遇矩阵
	}

	public MyDrawable[][] getMapMatrix()//mapMatrixMeetable的get方法
	{
		return mapMatrixMeetable;//返回上层地图信息的二维数组
	}
	
	public void initMapMatrixForMeetable()//计算可遇矩阵mapMatrixForMeetable
	{
		mapMatrixForMeetable = new MyMeetableDrawable[31][31];//初始化可遇矩阵
		for(int i=0; i<mapMatrixMeetable.length; i++)
		{
			for(int j=0; j<mapMatrixMeetable[i].length; j++)
			{
				if(mapMatrixMeetable[i][j] != null)//实际地图上对应的位置不为空时
				{
					int x = mapMatrixMeetable[i][j].col - mapMatrixMeetable[i][j].refCol;
					int y = mapMatrixMeetable[i][j].row + mapMatrixMeetable[i][j].refRow;
					int[][] meetableMatrix = mapMatrixMeetable[i][j].meetableMatrix;
					for(int k=0; k<meetableMatrix.length; k++)
					{
						mapMatrixForMeetable[y-meetableMatrix[k][1]][x+meetableMatrix[k][0]] = mapMatrixMeetable[i][j];//为可遇矩阵赋值
					}					
				}
			}
		}
	}
	
	public MyMeetableDrawable check(Figure figure){//检测是否遇上
		int col = figure.col;//获取人物的列数
		int row = figure.row;//获取人物的行数
		if(mapMatrixForMeetable[row][col] != null&&mapMatrixForMeetable[row][col].da==3)//检测所站位置的可遇物
		{
			return mapMatrixForMeetable[row][col];
		}
		switch(figure.direction%4)//还是先按方向查看
		{
			case 0://向下
			case 3://向上
				if(mapMatrixForMeetable[row][col-1]!= null&&figure.father.notInMatrix[row][col-1]!=0)//左边检测到了可遇物
				{
					if(mapMatrixForMeetable[row][col-1].da==2&&mapMatrixForMeetable[row][col-1].k<5)//左边检测到的为小土地
					{
						return mapMatrixForMeetable[row][col-1];
					}else if(mapMatrixForMeetable[row][col-1].da==1)//左边检测到的为大土地
					{
						if(mapMatrixForMeetable[row][col-1].zb==0||mapMatrixForMeetable[row][col-1].zb==1)//检测物为公园或者加气站
						{
							if(mapMatrixForMeetable[row][col-1].k<0)//该土地等级小于0
							{
								return mapMatrixForMeetable[row][col-1];
							}
						}else//检测物为其他房屋
						{
							if(mapMatrixForMeetable[row][col-1].k<4)//该土地等级小于4
							{
								return mapMatrixForMeetable[row][col-1];
							}
						}
					}
				}else if(mapMatrixForMeetable[row][col+1] != null&&figure.father.notInMatrix[row][col+1]!=0)//右边检测到了可遇物
				{
					if(mapMatrixForMeetable[row][col+1].da==2&&mapMatrixForMeetable[row][col+1].k<5)//右边检测到的为小土地
					{
						return mapMatrixForMeetable[row][col+1];
					}else if(mapMatrixForMeetable[row][col+1].da==1)//右边检测到的为大土地
					{
						if(mapMatrixForMeetable[row][col+1].zb==0||mapMatrixForMeetable[row][col+1].zb==1)//检测物为公园或者加气站
						{
							if(mapMatrixForMeetable[row][col+1].k<0)//该土地等级小于0
							{
								return mapMatrixForMeetable[row][col+1];
							}
						}else
						{
							if(mapMatrixForMeetable[row][col+1].k<4)//该土地等级小于4
							{
								return mapMatrixForMeetable[row][col+1];
							}
						}
					}
				}
			break;//中断
			case 1://向左
			case 2://向右
				if(mapMatrixForMeetable[row-1][col] != null&&figure.father.notInMatrix[row-1][col]!=0)//上边检测到了可遇物
				{
					if(mapMatrixForMeetable[row-1][col].da==2&&mapMatrixForMeetable[row-1][col].k<5)
					{
						return mapMatrixForMeetable[row-1][col];
					}else if(mapMatrixForMeetable[row-1][col].da==1)
					{
						if(mapMatrixForMeetable[row-1][col].zb==0||mapMatrixForMeetable[row-1][col].zb==1)//公园和加气站
						{
							if(mapMatrixForMeetable[row-1][col].k<0)
							{
								return mapMatrixForMeetable[row-1][col];
							}
						}else
						{
							if(mapMatrixForMeetable[row-1][col].k<4)
							{
								return mapMatrixForMeetable[row-1][col];
							}
						}
					}
				}else if(mapMatrixForMeetable[row+1][col] != null&&figure.father.notInMatrix[row+1][col]!=0)//下边检测到了可遇物
				{
					if(mapMatrixForMeetable[row+1][col].da==2&&mapMatrixForMeetable[row+1][col].k<5)
					{
						return mapMatrixForMeetable[row+1][col];
					}else if(mapMatrixForMeetable[row+1][col].da==1)
					{
						if(mapMatrixForMeetable[row+1][col].zb==0||mapMatrixForMeetable[row+1][col].zb==1)//公园和加气站
						{
							if(mapMatrixForMeetable[row+1][col].k<0)
							{
								return mapMatrixForMeetable[row+1][col];
							}
						}else
						{
							if(mapMatrixForMeetable[row+1][col].k<4)
							{
								return mapMatrixForMeetable[row+1][col];
							}
						}
					}
				}
			break;
		}
		return null;//如果没有检测到则返回null值
	}
}