package com.game.zillionaire.animation;

import java.io.Serializable;

import com.game.zillionaire.figure.CrashFigure;
import com.game.zillionaire.meetdrawable.MessageEnum;
import com.game.zillionaire.util.ConstantUtil;
import com.game.zillionaire.util.PicManager;
import com.game.zillionaire.view.GameView;

import android.graphics.Bitmap;
import android.graphics.Canvas;

public class CaiGodAnimation extends MeetableAnimation implements Serializable{
	//����
	private static final long serialVersionUID = 6769421262326922838L;
	public static String godPicName[][]={{"luckyb1","luckyb2","luckyb3","luckyb4"},
			{"luckyd1","luckyd2","luckyd3","luckyd4","luckyd5"}};//0�����  1С����
	GameView father;
	public MessageEnum me;//��ʾӢ������
	public int k=0;//֡������
	int tempLength=0;//����

	CaiGodAnimotionThread cgat;
	public static Bitmap[] bitmapsBig=new Bitmap[godPicName[0].length];
	public static Bitmap[] bitmapsSmall=new Bitmap[godPicName[1].length];
	public CaiGodAnimation(){}
	public CaiGodAnimation(GameView gv)
	{
		super(gv);
		this.father=gv;
		if(cgat==null)
		{
			cgat=new CaiGodAnimotionThread();
		}
	}
	@Override
	public void drawGod(Canvas canvas) {
		for(int i=0;i<bitmapsBig.length;i++)
		{//�����ͼƬ
			bitmapsBig[i]=PicManager.getPic(godPicName[0][i],father.activity.getResources());
		}
		for(int i=0;i<bitmapsSmall.length;i++)
		{//С����ͼƬ
			bitmapsSmall[i]=PicManager.getPic(godPicName[1][i],father.activity.getResources());
		}
		if(isAngel)
		{
			if(bitmapnum==0)
			{//�����
				canvas.drawBitmap(bitmapsBig[k],ConstantUtil.SCREEN_HEIGHT/2-150, ConstantUtil.SCREEN_WIDTH/2-100, null);
			}
			else if(bitmapnum==3)
			{//С����
				canvas.drawBitmap(bitmapsSmall[k],ConstantUtil.SCREEN_HEIGHT/2-150, ConstantUtil.SCREEN_WIDTH/2-100, null);
			}
		}
	}
	@Override
	public  void setClass(MessageEnum me)
	{
		this.me=me;
	}
	@Override
	public void nextFrame() {
		if(bitmapnum==0)
		{
			tempLength=godPicName[0].length;
		}
		else if(bitmapnum==3)
		{
			tempLength=godPicName[1].length;
		}
		k++;
		if(k==(tempLength-1))
		{
			try{
				Thread.sleep(1800);
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		if(k>=tempLength)
		{
			cgat.isGameOn=false;//ֹͣ��֡
			cgat.flag=false;//ִֹͣ��run����
			isAngel=false;//ֹͣ����֡
			switch(me)
			{//����Ӣ�����ͣ���Ҳٿػ���ϵͳ����
				case Artificial_Control:
					CrashFigure.isMeet1=true;
					break;
				case System_Control_1:
					CrashFigure.isMeet0=true;
					break;
				case System_Control_2:
					CrashFigure.isMeet=true;
					break;
			}
			recoverGame();
			cgat=null;//�߳������ÿ�
		}
	}
	@Override
	public void startAnimation() {
		cgat.isGameOn=true;//��־λ��Ϊtrue
		if(!cgat.isAlive())//����߳�û�п��� �����߳�
		{
			cgat.start();
		}
	}
	public void recoverGame()//�ָ���Ϸ״̬
	{
		k=0;
		isAngel=false;
		bitmapnum=-1;
		halfOrNot=-1;//�ָ�
		gv.isFigureMove=false;//�������������ﶯ��
		if(gv.currentDrawable==null)
		{
			gv.setOnTouchListener(gv);//����������
			gv.setStatus(0);//��������GameViewΪ����״̬
			gv.gvt.setChanging(true);//�����任������߳�
		}
	}
	class CaiGodAnimotionThread extends Thread
	{
		boolean flag;//�̵߳�run�����Ƿ�ִ�еı�־λ
		boolean isGameOn;//�Ƿ���л�֡�ı�־λ
		public CaiGodAnimotionThread()
		{
			flag = true;
		}
		public void run(){
			while(flag){
				while(isGameOn){
					try{
						nextFrame();
					}
					catch(Exception e){
						e.printStackTrace();
					}
					try{
						Thread.sleep(400);//��ͼƬ���0.4��
					}
					catch(Exception e){
						e.printStackTrace();
					}
				}
			}
		}
	}
}
