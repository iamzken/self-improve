package com.gupaoedu.activity.dal.persistence;


import com.gupaoedu.activity.dal.entitys.ActDrawRecord;
import com.gupaoedu.activity.draw.bean.AwardDrawRecordBean;

import java.util.List;

public interface ActDrawRecordMapper {
	/**
	 * 添加抽奖记录
	 * */
	int addActDrawRecord(ActDrawRecord actDrawRecord);


	/**
	 * 查询中奖记录信息
	 * @return 中奖记录列表（包含轮播的10条记录）
	 */
	List<ActDrawRecord> queryDrawRecordList();

}
