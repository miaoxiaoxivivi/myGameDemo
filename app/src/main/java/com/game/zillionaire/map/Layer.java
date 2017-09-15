package com.game.zillionaire.map;

import java.io.Serializable;

import com.game.zillionaire.activity.ZActivity;

import android.content.res.Resources;
/**
 *  
 * 该类为地图的下层
 *
 */
public class Layer implements Serializable{//实现Serializable接口
	private static final long serialVersionUID = 8356764959284943179L;//持久化版本序列号
	private MyDrawable[][] mapMatrix;//表示地图的二维数组
	
	public Layer(){}//空构造器
	public Layer(ZActivity at,Resources resources)//构造器
	{
		GameData.initMapData(at);//初始化底层地图数据
		this.mapMatrix = GameData.mapData;//获取底层地图信息
	}
	public MyDrawable[][] getMapMatrix()//mapMatrix的get方法
	{
		return mapMatrix;//返回底层地图信息的二维数组
	}
	public int[][] getNotIn()//得到不可通过矩阵
	{
		int[][] result = new int[31][31];//创建一个int型的二维数组
		for(int i=0; i<mapMatrix.length; i++)
		{
			for(int j=0; j<mapMatrix[i].length; j++)
			{
				int x = mapMatrix[i][j].col - mapMatrix[i][j].refCol;
				int y = mapMatrix[i][j].row + mapMatrix[i][j].refRow;
				int[][] notIn = mapMatrix[i][j].noThrough;
				for(int k=0; k<notIn.length; k++)
				{
					result[y-notIn[k][1]][x+notIn[k][0]] = 1;//不可通过点置1
				}
			}
		}
		return result;//返回不可通过矩阵
	}
}
