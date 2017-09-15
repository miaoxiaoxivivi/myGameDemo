package com.game.zillionaire.figure;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.ArrayList;
import java.util.HashMap;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import com.game.zillionaire.map.MyMeetableDrawable;
import com.game.zillionaire.meetdrawable.MessageEnum;
import com.game.zillionaire.thread.FigureGoThread;
import com.game.zillionaire.util.MoneyHZ;
import com.game.zillionaire.util.PicManager;
import com.game.zillionaire.view.GameView;
import static com.game.zillionaire.util.ConstantUtil.FIGURE_ANIMATION_FRAMES;
import static com.game.zillionaire.util.ConstantUtil.FIGURE_ANIMATION_SEGMENTS;
import static com.game.zillionaire.util.ConstantUtil.FIGURE_ANIMATION_SLEEP_SPAN;
import static com.game.zillionaire.util.ConstantUtil.FIGURE_HEIGHT;
import static com.game.zillionaire.util.ConstantUtil.FIGURE_NO_ANIMATION_SLEEP_SPAN;
import static com.game.zillionaire.util.ConstantUtil.FIGURE_WIDTH;
import static com.game.zillionaire.util.ConstantUtil.TILE_SIZE_X;
import static com.game.zillionaire.util.ConstantUtil.TILE_SIZE_Y;

public class Figure implements Externalizable
{
	public GameView father;//Activity引用
	public int direction = -1;//英雄的移动方向，4：下，5：左，6:右，7：上。同时也代表当前英雄的动画段号，从零开始
	int currentFrame = 0;//当前英雄的动画段的当前动画帧，从零开始
	public int col;//英雄的定位点在大地图中的列，定位点为下面的格子的中心
	public int row;//英雄的定位点在大地图中的行，定位点为下面的格子的中心
	public int x;//英雄『中心点』的x坐标，用于绘制 
	public int y;//英雄『中心点』的y坐标，用于绘制
	int width;//英雄的宽度=======在initAnimationSegment方法中初始化
	int height;//英雄的高度=======在initAnimationSegment方法中初始化
	public int[] CardNum={15,9,-1,-1,-1,-1,-1,-1,-1,-1,
			-1,-1,-1,-1,-1,-1,-1,-1,-1,-1};//人物已购买的卡片
	static Bitmap [][] figureAnimationSegments;//存放人物所有动画段的图片
	static Bitmap [][] figureAnimationSegments1;//存放人物所有动画段的图片
	static Bitmap [][] figureAnimationSegments2;//存放人物所有动画段的图片
	static Bitmap [][] figureAnimationSegments3;//存放人物所有动画段的图片
	public int startRow;//屏幕在大地图中的行数
	public int startCol;//屏幕在大地图中的列数
	public int offsetX ;//屏幕定位点在大地图上的x方向偏移，用来实现无级滚屏
	public int offsetY ;//屏幕定位点在大地图上的y方向偏移，用来实现无级滚屏
	//人物坐标
	public int topLeftCornerX;
	public int topLeftCornerY;
	public int k;//房子标志
	public int ss;//土地主人标志
	public int count=0;//记录各个人物的土地数
	
	public int Bitmapindext=0;//人物图片的索引
	public int day=0;//人物消失的天数
	public MoneyHZ mhz;
	public int zdDay=0;//头顶炸弹的步数
	public boolean isWG=false;//判断是否使用了乌龟卡
	public boolean isZD=false;//判断头上是否戴有炸弹
	public boolean isDreaw=true;//人物是否存在
	public MessageEnum figureFlag;//人物标志，用于辨别人物是玩家操控还是系统操控*********
	public boolean isHero=true;//是否绘制英雄的标志位，默认是true表示绘制
	public int id;//表示英雄碰撞到的神明的类型	
	public ArrayList<Integer> mp=new ArrayList<Integer>();//存放买的彩票
	public int isWhere=-1;//判断人物是在哪里
	public boolean isStop=false;//判断人物是否停止前进
	
	public MyMeetableDrawable previousDrawable;//记录上一个碰到的可遇Drawable对象引用
	public int[][] room={{-1,-1},{-1,-1},{-1,-1},{-1,-1},{-1,-1},{-1,-1}};//六个地区
	
	public HashMap<Integer,Integer> mps=new HashMap<Integer,Integer> ();//持股数
	public HashMap<Integer,String> mpsName=new HashMap<Integer,String> ();//股票名称
	public HashMap <Integer,Double> mpsCost=new HashMap<Integer,Double>();//成本
	
	public ArrayList<Bitmap []> animationSegment = new ArrayList<Bitmap []>();//存放英雄所有的动画段，每个动画段为一个一维数组
	public HeroThread ht;//负责英雄动画换帧的线程
	public FigureGoThread hgt;//负责英雄走路的线程
	
	public String[] figureNames= {"孙小美","小公主","假小子"};//所有人物姓名
	public String figureName;//人物姓名
	public String headBitmapName;
	public String[] headBitmapNames={"tou0","tou2","tou1"};//头像
	public String tImageName;
	public String[] tImageNames={"sun1","sun3","sun2"};//游戏主界面显示正在游戏人物信息
	public String touName;
	public String[] touNames={"tou01","tou11","tou21"};//人物头像
	
	public Figure(){
	};
	//构造器：
	public Figure(GameView father,int col,int row,int startRow,int startCol,int offsetX,int offsetY,int k,MessageEnum figureFlag,int direction,int Bitmapindext)
	{
		this.col = col;
		this.row = row;
		this.x = col*TILE_SIZE_X+TILE_SIZE_X/2+1;
		this.y = row*TILE_SIZE_Y+TILE_SIZE_Y/2+1;
		this.startRow=startRow;
		this.startCol=startCol;
		this.offsetX=offsetX;
		this.offsetY=offsetY;
		this.k=k;
		this.ss=k;
		this.Bitmapindext=Bitmapindext;
		this.father = father;
		this.figureFlag=figureFlag;
		this.direction=direction;
		ht = new HeroThread();
		hgt = new FigureGoThread(father,this);
		setBitmap();
		mhz=new MoneyHZ();//初始化人物的资金
		for(int i=0;i<7;i++)
		{//每支股票的持股数默认为0
			mps.put(i, 0);
			mpsName.put(i, "0");
			mpsCost.put(i, 0.0);
		}	
	}
	//初始化人物对象图片
	public void setBitmap()
	{
		int[][] width={{0,0},{0,0},{0,0},{0,0},{290,100},{290,100},{290,100},{290,100}};
		int[] height={0,95,190,286,0,95,190,286};
		Bitmap tempBmp = null;
		while(tempBmp==null)
		{
			tempBmp = PicManager.getPic("rw2",father.activity.getResources()); //加载包含人物动画的大图
		}
		figureAnimationSegments = new Bitmap[FIGURE_ANIMATION_SEGMENTS][FIGURE_ANIMATION_FRAMES];
		for(int i=0;i<FIGURE_ANIMATION_SEGMENTS;i++)//对大图进行切割，转换成Bitmap的二维数组
		{
			for(int j=0;j<FIGURE_ANIMATION_FRAMES;j++)
			{
				figureAnimationSegments[i][j] = Bitmap.createBitmap(tempBmp, width[i][j],height[i],FIGURE_WIDTH, FIGURE_HEIGHT);
			}
		}
		tempBmp = null;//释放掉大图
		while(tempBmp==null)
		{
		    tempBmp =  PicManager.getPic("rw1",father.activity.getResources()); //加载包含人物1动画的大图
		}
		figureAnimationSegments1 = new Bitmap[FIGURE_ANIMATION_SEGMENTS][FIGURE_ANIMATION_FRAMES];
		for(int i=0;i<FIGURE_ANIMATION_SEGMENTS;i++)//对大图进行切割，转换成Bitmap的二维数组
		{
			for(int j=0;j<FIGURE_ANIMATION_FRAMES;j++)
			{
				figureAnimationSegments1[i][j] = Bitmap.createBitmap(tempBmp, width[i][j],height[i],FIGURE_WIDTH, FIGURE_HEIGHT);
			}
		}
		tempBmp = null;//释放掉大图
		while(tempBmp==null)
		{
			tempBmp = PicManager.getPic("rw",father.activity.getResources()); //加载包含人物1动画的大图
		}
		figureAnimationSegments2 = new Bitmap[FIGURE_ANIMATION_SEGMENTS][FIGURE_ANIMATION_FRAMES];
		for(int i=0;i<FIGURE_ANIMATION_SEGMENTS;i++)//对大图进行切割，转换成Bitmap的二维数组
		{
			for(int j=0;j<FIGURE_ANIMATION_FRAMES;j++)
			{
				figureAnimationSegments2[i][j] = Bitmap.createBitmap(tempBmp, width[i][j],height[i],FIGURE_WIDTH, FIGURE_HEIGHT);
			}
		}
		tempBmp = null;//释放掉大图
		while(tempBmp==null)
		{
			tempBmp = PicManager.getPic("ww",father.activity.getResources()); //加载包含机器娃娃动画的大图
		}
		figureAnimationSegments3 = new Bitmap[FIGURE_ANIMATION_SEGMENTS][FIGURE_ANIMATION_FRAMES];
		int[] width0={0,120,240,360,60,180,300,420};
		for(int i=0;i<FIGURE_ANIMATION_SEGMENTS;i++)//对大图进行切割，转换成Bitmap的二维数组
		{
			for(int j=0;j<FIGURE_ANIMATION_FRAMES;j++)
			{
				figureAnimationSegments3[i][j] = Bitmap.createBitmap(tempBmp, width0[i],0,60, 70);
			}
		}
		tempBmp = null;//释放掉大图
		
		if(this.Bitmapindext==0)
		{
			this.initAnimationSegment(figureAnimationSegments);//为英雄初始化动画段列表
			figureName=figureNames[0];//孙小美
			
			headBitmapName=headBitmapNames[0];//孙小美头像
			tImageName=tImageNames[0];
			touName=touNames[0];
			
		}else if(this.Bitmapindext==1)
		{
			this.initAnimationSegment(figureAnimationSegments1);//为英雄初始化动画段列表
			figureName=figureNames[1];//小公主
			
			headBitmapName=headBitmapNames[1];//小公主头像
			tImageName=tImageNames[1];
			touName=touNames[1];
			
		}else if(this.Bitmapindext==2)
		{
			this.initAnimationSegment(figureAnimationSegments2);//为英雄初始化动画段列表
			figureName=figureNames[2];//假小子
			
			headBitmapName=headBitmapNames[2];//假小子头像
			tImageName=tImageNames[2];
			touName=touNames[2];
			
		}else if(this.Bitmapindext==3)
		{
			this.initAnimationSegment(figureAnimationSegments3);//为英雄初始化动画段列表
		}
		this.setAnimationDirection(direction);//初始化英雄朝向右，静态右
        this.startAnimation();//启动英雄动画
	}
	//方法：初始化动画段列表
	public void initAnimationSegment(Bitmap [][] segments)
	{
		for(Bitmap [] segment:segments)
		{
			addAnimationSegment(segment);
		}
		this.height = segments[0][0].getHeight();//初始化英雄图片高度
		this.width = segments[0][0].getWidth();//初始化英雄图片宽度
	}
	//方法：向动画段列表中添加动画段,该方法会在初始化动画段列表中被调用
	public void addAnimationSegment(Bitmap [] segment)
	{
		this.animationSegment.add(segment);
	}
	//方法：设置方向，同时也是动画段索引
	public void setAnimationDirection(int direction)
	{
		this.direction = direction;
	}
	//方法：开始换帧动画
	public void startAnimation()
	{
		ht.isGameOn = true;//设置换帧标志位为真
		if(!ht.isAlive())//如果换帧线程没有启动则启动它
		{
			ht.start();
		}
	}
	//方法：在屏幕上绘制自己,根据传入的屏幕定位row和col计算出相对坐标画出
	public void drawSelf(Canvas canvas,int startRow,int startCol,int offsetX,int offsetY)
	{
		if(direction>animationSegment.size())//没有此方向的动作则不绘制
		{
			return;
		}
		Bitmap bmp = animationSegment.get(direction)[currentFrame];
		topLeftCornerX = x-TILE_SIZE_X/2-1 - startCol*TILE_SIZE_X;//先计算出左上角坐标，再转换成屏幕的相对坐标
		topLeftCornerY = y+TILE_SIZE_Y/2-height - startRow*TILE_SIZE_Y;//先计算出左上角坐标，再转换成屏幕的相对坐标
		topLeftCornerX=topLeftCornerX-offsetX-20;
		topLeftCornerY=topLeftCornerY-offsetY-22;
		canvas.drawBitmap(bmp, topLeftCornerX, topLeftCornerY, null);
	}
	//方法：换帧
	public void nextFrame()
	{
		int frameLength = this.animationSegment.get(direction).length;
		this.currentFrame = (this.currentFrame+1)%frameLength;
	}
	//方法：激活英雄的走路线程，传入格子数使其开动
	public void startToGo(int steps)
	{
		hgt.setMoving(true);
		hgt.setSteps(steps);
		if(!hgt.isAlive())//没启动就启动
		{
			hgt.start();
		}
	}
	//内部线程类：负责定时更改英雄的动画帧，但是不负责改变动画段
	public class HeroThread extends Thread{
		public boolean flag;//线程的run方法是否执行的标志位
		public boolean isGameOn;//是否进行换帧的标志位
		public HeroThread()
		{
			super.setName("==Hero.HeroThread");
			flag = true;
		}
		public void run()
		{
			while(flag)
			{
				while(isGameOn)
				{
					try
					{
						nextFrame();//进行换帧操作
					}catch(Exception e)
					{
					}
					try
					{
						Thread.sleep(FIGURE_ANIMATION_SLEEP_SPAN);
					}catch(Exception e)
					{
						e.printStackTrace();
					}
				}
				//空转的时候也要睡眠
				try
				{
					Thread.sleep(FIGURE_NO_ANIMATION_SLEEP_SPAN);
				}catch(Exception e)
				{
					e.printStackTrace();
				}
			}
		}
	}
	
	public void writeExternal(ObjectOutput out) throws IOException {
		out.writeInt(col);
		out.writeInt(row);
		out.writeInt(x);
		out.writeInt(y);
		out.writeInt(startRow);
		out.writeInt(startCol);
		out.writeInt(offsetX);
		out.writeInt(offsetY);
		out.writeInt(topLeftCornerX);
		out.writeInt(topLeftCornerY);
		out.writeInt(k);
		out.writeInt(ss);
		out.writeInt(count);
		out.writeObject(CardNum);
		out.writeInt(currentFrame);
		out.writeInt(direction);
		out.writeInt(Bitmapindext);
		out.writeInt(day);
		out.writeObject(mhz);
		out.writeBoolean(isWG);
		out.writeBoolean(isZD);
		out.writeBoolean(isDreaw);
		out.writeObject(figureFlag);
		out.writeBoolean(isHero);
		out.writeInt(id);
		out.writeInt(isWhere);
		out.writeBoolean(isStop);
		out.writeUTF(figureName);
		out.writeUTF(headBitmapName);
		out.writeUTF(tImageName);
		out.writeUTF(touName);
	}
	public void readExternal(ObjectInput in) throws IOException,ClassNotFoundException 
	{
		this.col=in.readInt();
		this.row=in.readInt();
		this.x=in.readInt();
		this.y=in.readInt();
		this.startRow = in.readInt();
		this.startCol = in.readInt();
		this.offsetX = in.readInt();
		this.offsetY = in.readInt();
		this.topLeftCornerX = in.readInt();
		this.topLeftCornerY = in.readInt();
		this.k = in.readInt();
		this.ss=in.readInt();
		this.count = in.readInt();
		CardNum=(int[])in.readObject();
		this.currentFrame=in.readInt();
		this.direction=in.readInt();
		this.Bitmapindext=in.readInt();
		this.day=in.readInt();
		this.mhz=(MoneyHZ) in.readObject();
		this.isWG=in.readBoolean();
		this.isZD=in.readBoolean();
		this.isDreaw=in.readBoolean();
		this.figureFlag=(MessageEnum) in.readObject();
		this.isHero=in.readBoolean();
		this.id=in.readInt();
		this.isWhere=in.readInt();
		this.isStop=in.readBoolean();
		this.figureName=in.readUTF();
		this.headBitmapName=in.readUTF();
		this.tImageName=in.readUTF();
		this.touName=in.readUTF();
		//恢复相关信息和数据
		ht = new HeroThread();
	}
	public String get_touName()
	{
		return touName;
	}
	public void set_touName(String touName)
	{
		this.touName=touName;
	}
	public String get_tImageName()
	{
		return tImageName;
	}
	public void set_tImageName(String tImageName)
	{
		this.tImageName=tImageName;
	}
	public String get_headBitmapName()
	{
		return headBitmapName;
	}
	public void set_headBitmapName(String headBitmapName)
	{
		this.headBitmapName=headBitmapName;
	}
	public String get_figureName()
	{
		return figureName;
	}
	public void set_figureName(String figureName)
	{
		this.figureName=figureName;
	}
	public MessageEnum get_figureFlag()
	{
		return figureFlag;
	}
	public void set_figureFlag(MessageEnum figureFlag)
	{
		this.figureFlag=figureFlag;
	}
	public MoneyHZ get_mhz()
	{
		return mhz;
	}
	public void set_mhz(MoneyHZ mhz)
	{
		this.mhz=mhz;
	}
	public int gettopLeftCornerX(){
		return topLeftCornerX;
	}
	public void settopLeftCornerX(int topLeftCornerX){
		this.topLeftCornerX=topLeftCornerX;
	}
	public int gettopLeftCornerY(){
		return topLeftCornerY;
	}
	public void settopLeftCornerY(int topLeftCornerY){
		this.topLeftCornerY=topLeftCornerY;
	}
	public int getK(){
		return k;
	}
	public void setK(int k){
		this.k=k;
	}
	public int getSs(){
		return ss;
	}
	public void setSs(int ss){
		this.ss=ss;
	}
	public int getCount(){
		return count;
	}
	public void setCount(int count){
		this.count=count;
	}
	public int[] getCardNum(){
		return CardNum;
	}
	public void setcardNum(int[] CardNum){
		this.CardNum=CardNum;
	}
	public int getBitmapindext(){
		return Bitmapindext;
	}
	public void setBitmapindext(int Bitmapindext){
		this.Bitmapindext=Bitmapindext;
	}	
	public int getDay(){
		return day;
	}
	public void setDay(int day){
		this.day=day;
	}

	public boolean getIsWg(){
		return isWG;
	}
	public void setIsWg(boolean isWG){
		this.isWG=isWG;
	}
	public boolean getIsZD(){
		return isZD;
	}
	public void setIsZD(boolean isZD){
		this.isZD=isZD;
	}
	public boolean getIsDreaw(){
		return isDreaw;
	}
	public void setIsDreaw(boolean isDreaw){
		this.isDreaw=isDreaw;
	}
	public boolean getIsHero(){
		return isHero;
	}
	public void setIsHero(boolean isHero){
		this.isHero=isHero;
	}
	public int getId(){
		return id;
	}
	public void setId(int id){
		this.id=id;
	}
	public int getIsWhere(){
		return isWhere;
	}
	public void setIsWhere(int isWhere){
		this.isWhere=isWhere;
	}
	public boolean getIsStop(){
		return isStop;
	}
	public void setIsStop(boolean isStop){
		this.isStop=isStop;
	}
}
