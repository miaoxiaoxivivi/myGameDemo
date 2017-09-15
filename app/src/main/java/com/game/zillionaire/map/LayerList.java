package com.game.zillionaire.map;

import java.io.Serializable;
import java.util.ArrayList;
import com.game.zillionaire.activity.ZActivity;
import android.content.res.Resources;

public class LayerList implements Serializable{
	private static final long serialVersionUID = -6325921004729216060L;//持久化版本序列号
	public ArrayList<Layer> layers = new ArrayList<Layer>();//存储Layer的容器
	
	public LayerList(){}//空构造器
	public LayerList(ZActivity at,Resources resources)//构造器
	{
		this.init(at,resources);//调用初始化资源方法
	}
	public void init(ZActivity at,Resources resources)//初始化资源
	{
		Layer l = new Layer(at,resources);//创建Layer对象
		layers.add(l);//添加底层地图
		MeetableLayer ml = new MeetableLayer(at,resources);//创建MeetableLayer对象
		layers.add(ml);//添加上层地图
	}
	public int[][] getTotalNotIn()//得到总不可通过矩阵
	{
		int[][] result = new int[31][31];
		for(Layer layer : layers)//对所有层进行循环
		{
			int[][] tempNotIn = layer.getNotIn();//获得各个层的不可通过矩阵
			for(int i=0; i<tempNotIn.length; i++)//对不可通过矩阵进行循环
			{
				for(int j=0; j<tempNotIn[i].length; j++)
				{
					result[i][j] = result[i][j] | tempNotIn[i][j];//或运算，得到总不可通过点
				}
			}
		}		
		return result;//返回总不可通过矩阵
	}
}
