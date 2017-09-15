package com.game.zillionaire.soundmanager;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import com.game.zillionaire.activity.ZActivity;
import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;

public class SoundManager {
	ZActivity activity;//定义activity
	public MediaPlayer mp;//定义MediaPlayer播放器
	HashMap<String,PuSound> soundList=new HashMap<String,PuSound>();//定义存放声音的HashMap
	boolean isCirculate=true;//是否循环查找
	boolean circulate=true;//是否循环查找
	AssetManager assetManager;//获得assets目录
	AssetFileDescriptor temp;
	SoundPool soundPool;//音效
	int count=0;//循环次数计数器
	long min=0;//比较得出最小的时间
	public float volumn=0;//音量
	int loopFinal=0;
	public AudioManager am;//声音管理类
	
	
	public SoundManager(ZActivity activity)//构造器
	{
		this.activity=activity;
		assetManager= activity.getAssets();//获得assets的目录
		am =(AudioManager)activity.getSystemService(Context.AUDIO_SERVICE);//声音大小
		mp=new MediaPlayer();//播放音乐的对象赋值
	}
	public void StartBackGroundSound()//开启背景音乐
	{
		try
		{
			temp=assetManager.openFd("sound/"+activity.soundName);//加载音乐
			mp.setDataSource//设置mp
			(
				temp.getFileDescriptor(), 
				temp.getStartOffset(), 
				temp.getLength()
			);
			mp.setLooping(true);//循环播放
			mp.prepare();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		mp.start();//开启
	}
	public void EndBackGroundSound()//关闭背景音乐
	{
		if(mp.isPlaying())//如果背景音乐正在播放
		{
			mp.stop();//停止播放音乐
		}
	}
	
	public String getMediaPlayer(String soundName)//获得背景音乐
	{
		String spTemp=null;
		isCirculate=true;//每次调用这个方法，循环查找即为true
		while(isCirculate)
		{
			if(soundList.get(soundName)!=null)//如果HashMap中有此音效
			{
				spTemp=soundList.get(soundName).soundID;//直接获得其ID
				soundList.get(soundName).date=System.nanoTime();//获得一个比较大小的字符串
				if(mp!=null)//当有音乐播放时
				{
					activity.sm.mp.release();//将播放器资源释放掉
				}
				mp=new MediaPlayer();//给播放音乐的对象赋值
				String path="sound/"+soundName;//获得音乐路径
				try {
					temp=assetManager.openFd(path);//打开文件
					mp.setDataSource//为Mediaplayer设置资源
					(
						temp.getFileDescriptor(), 
						temp.getStartOffset(), 
						temp.getLength()
					);
					mp.setLooping(true);//循环播放
					mp.prepare();//准备
				} catch (IOException e) {
					e.printStackTrace();
				}
				isCirculate=false;//跳出循环
			}else//如果HashMap中没有此音效
			{
				LoadingBackMusic(soundName);//加载背景音乐
			}
		}
		return spTemp;
	}

	@SuppressWarnings("rawtypes")
	public void LoadingBackMusic(String soundName)//加载背景音乐
	{
		PuSound soundTemp = new PuSound();
		if(soundList.size()<5)//如果HashMap长度小于5，直接加载音效
		{
			String path="sound/"+soundName;//获得音乐路径
			try
			{
				if(mp!=null)//当有音乐播放时
				{
					activity.sm.mp.release();//将播放器资源释放掉
				}
				mp=new MediaPlayer();
				temp=assetManager.openFd(path);	//加载背景音乐
				mp.setDataSource//为Mediaplayer设置资源
				(
					temp.getFileDescriptor(), 
					temp.getStartOffset(), 
					temp.getLength()
				);
				mp.setLooping(true);//循环播放
				mp.prepare();//准备
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
			soundTemp.soundID=soundName;//获得声音ID
			soundTemp.date=System.nanoTime();//获得一个比较大小的字符串
			soundList.put(soundName,soundTemp);//将音乐信息放入列表里
		}else//如果HashMap长度大于5，删除部分音效，加载需要的音效
		{
			Iterator<Entry<String, PuSound>> iter = soundList.entrySet().iterator();
			while (iter.hasNext())
			{
				Map.Entry entry = (Map.Entry) iter.next();
				if(count==0)//第一次循环
				{
					min=((PuSound)entry.getValue()).date;//记录当前日期值
					count++;//计数器加1
				}else
				{
					if(min>((PuSound)entry.getValue()).date)//如果当前日期大于获得的日期
					{
						min=((PuSound)entry.getValue()).date;//赋值给最小日期
					}
				}
			}
			soundList.remove(min);//删除音效
			soundTemp.soundID=soundName;//获得声音ID
			soundTemp.date=System.nanoTime();//获得当前时间
			soundList.put(soundName,soundTemp);//将此声音加到HashMap中
		}
	}
	
	public void playBackMusic(String soundName)//播放背景音乐 
	{
		circulate=true;
		while(circulate)//是否循环
		{
			if(getMediaPlayer(soundName)!=null)//如果获得了此音效
			{
				mp.start();//开启
				circulate=false;//不再循环
			}
		}
	}
}
