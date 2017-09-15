package com.game.zillionaire.map;

import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import com.game.zillionaire.activity.ZActivity;
import com.game.zillionaire.figure.Figure;
import com.game.zillionaire.thread.FigureGoThread;
import com.game.zillionaire.util.ConstantUtil;
import com.game.zillionaire.view.GameView;

public class SerializableGame {
	
	public SerializableGame(){
	}
	//保存游戏的方法
	public static void saveGameStatus(GameView gameView){
		OutputStream out = null;
		ObjectOutputStream  oout = null;
		String temp="";
		int indext=0;
		try{
			if(ConstantUtil.changeNum==0){ 
				temp="zil00.lll";
				indext=0;
			}else if(ConstantUtil.changeNum==1){
				temp="zil01.lll";
				indext=1;
			}else if(ConstantUtil.changeNum==2){
				temp="zil02.lll";
				indext=2;
			}
			out = gameView.getContext().openFileOutput(temp, indext);
			oout = new ObjectOutputStream(out);
		
			/**
			 * 保存人物对象
			 */
			oout.writeObject(gameView.layerList);
			oout.writeObject(gameView.figure);	
			oout.writeObject(gameView.figure1);
			oout.writeObject(gameView.figure2);
			
			oout.writeInt(gameView.tempStartRow);//屏幕在大地图中的行数
			oout.writeInt(gameView.tempStartCol);//屏幕在大地图中的列数
		}
		catch(Exception e){
			e.printStackTrace();
		}
		finally{
			try{
				oout.close();
				out.close();				
			}
			catch(Exception e){
				e.printStackTrace();
			}
		}
	}
	public static void saveSaveString(GameView gameView){
		OutputStream out = null;
		ObjectOutputStream  oout = null;
		try{
			out = gameView.getContext().openFileOutput("zil04.lll", 3);
			oout = new ObjectOutputStream(out);
			oout.writeInt(ConstantUtil.SheduleChooseViewSaved);//存储数
			oout.writeObject(ConstantUtil.SaveString);
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try{
				oout.close();
				out.close();				
			}
			catch(Exception e){
				e.printStackTrace();
			}
		}
	}
	//加载游戏的方法
	public static void loadingGameStatus(GameView gameView){
		InputStream in = null;
		ObjectInputStream oin = null;
		String temp1="";
		try{
			temp1="zil0"+ConstantUtil.SheduleChooseViewSelected+".lll";
			in = gameView.getContext().openFileInput(temp1);
			oin = new ObjectInputStream(in);
			gameView.figure.ht.flag=false;
			gameView.figure.ht.isGameOn=false;
			gameView.figure.ht.interrupt();
			
			gameView.figure1.ht.flag=false;
			gameView.figure1.ht.isGameOn=false;
			gameView.figure1.ht.interrupt();
			
			gameView.figure2.ht.flag=false;
			gameView.figure2.ht.isGameOn=false;
			gameView.figure2.ht.interrupt();

			gameView.layerList=(LayerList) oin.readObject();
			gameView.figure = (Figure) oin.readObject();//读取英雄对象		
			
			gameView.figure1 = (Figure) oin.readObject();//读取英雄对象	
			gameView.figure2 = (Figure) oin.readObject();//读取英雄对象	
			gameView.tempStartRow = oin.readInt();//屏幕在大地图中的行数
			gameView.tempStartCol = oin.readInt();//屏幕在大地图中的列数

			gameView.meetableChecker = (MeetableLayer)gameView.layerList.layers.get(1);
			//恢复英雄相关信息
			gameView.figure.father = gameView;
			gameView.figure.setBitmap();//获取图片信息
			gameView.figure.hgt = new FigureGoThread(gameView,gameView.figure);
			gameView.figure1.father = gameView;
			gameView.figure1.setBitmap();//获取图片信息
			gameView.figure1.hgt = new FigureGoThread(gameView,gameView.figure1);
			gameView.figure2.father = gameView;
			gameView.figure2.setBitmap();//获取图片信息
			gameView.figure2.hgt = new FigureGoThread(gameView,gameView.figure2);
			
			gameView.activity.myHandler.sendEmptyMessage(10);
		}catch(Exception e){
			e.printStackTrace();
		}
		finally{
			try{
				oin.close();
				in.close();
			}
			catch(Exception e){
				e.printStackTrace();
			}
		}
	}
	public static void loadSaveString(GameView gameView){
		InputStream in = null;
		ObjectInputStream oin = null;
		try{
			in = gameView.getContext().openFileInput("zil04.lll");
			oin = new ObjectInputStream(in);
			
			ConstantUtil.SheduleChooseViewSaved=oin.readInt();//存储数
			ConstantUtil.SaveString=(String[])oin.readObject();
			
		}catch(Exception e){
			e.printStackTrace();
		}
		finally{
			try{
				oin.close();
				in.close();
			}
			catch(Exception e){
				e.printStackTrace();
			}
		}
	}		
	//检查文件是否存在
	public static boolean check(ZActivity h){
		try{
			h.openFileInput("zil00.lll");
			h.openFileInput("zil01.lll");
			h.openFileInput("zil02.lll");
			h.openFileInput("zil04.lll");
		}
		catch(Exception e){
			return false;
		}
		return true;
	}
}
