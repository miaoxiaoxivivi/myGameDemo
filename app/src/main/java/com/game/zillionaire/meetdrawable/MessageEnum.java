package com.game.zillionaire.meetdrawable;

import java.io.Serializable;

public enum MessageEnum implements Serializable
{
	FREE_UP_LEVEL,//部分地区建筑免费加盖一层
	STOP_ONE,//每人停留一次
	FIRE_HOME,//xx一处民宅瓦斯爆炸房屋失火
	Artificial_Control,//由玩家操控的人物,
	System_Control_1,//系统控制的人物1
	System_Control_2,//系统控制的人物2
}