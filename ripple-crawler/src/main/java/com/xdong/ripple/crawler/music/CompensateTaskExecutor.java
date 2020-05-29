package com.xdong.ripple.crawler.music;

import java.util.concurrent.LinkedBlockingQueue;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.xdong.ripple.common.crawler.SupplementErrorTask;
import com.xdong.ripple.common.utils.SpringUtil;
import com.xdong.ripple.crawler.strategy.CrawlerCompensateInterface;

/**
 * 类CompensateTaskExecutor.java的实现描述：音乐爬虫补单任务执行器
 * 
 * @author wanglei May 18, 2019 8:08:50 PM
 */
@Service
public class CompensateTaskExecutor implements InitializingBean {

	private static Logger logger = Logger.getLogger(CompensateTaskExecutor.class);

	public volatile LinkedBlockingQueue<SupplementErrorTask> errorUrlQueue = new LinkedBlockingQueue<SupplementErrorTask>();

	private static final int maxCount = 3;

	@Override
	public void afterPropertiesSet() throws Exception {

		while (true) {

			logger.info("定时任务补单线程被唤醒，开始执行补单任务，【队列长度=" + errorUrlQueue.size() + "】");

			if (errorUrlQueue != null && errorUrlQueue.size() > 0) {

				SupplementErrorTask task = errorUrlQueue.poll();
				logger.info("存在【" + errorUrlQueue.size() + "】条歌曲收录补单任务,开始执行爬取异常的,task:" + JSON.toJSONString(task));

				compensate(task);

			} else {
				Thread.sleep(3000L);
			}

			logger.info("定时任务补单线程执行补单任务完成，剩余任务【队列长度=" + errorUrlQueue.size() + "】条");
		}
	}

	private void compensate(SupplementErrorTask task) {
		if (task.getRetryCount() > maxCount) {
			logger.error("线程：【" + Thread.currentThread().getName() + "】-> 执行补单任务，重试一直未成功且超过最大次数+" + maxCount
					+ ",taskUrl:" + task.getTaskUrl());
			return;
		}

		CrawlerCompensateInterface compensate = (CrawlerCompensateInterface) SpringUtil
				.getBeansByName(task.getCompensateClass());

		switch (task.getTaskType()) {
		case SONGSHEET:
			compensate.songSheetUrlCompensate(task);
			break;
		case SONGS:
			compensate.songUrlCompensate(task);
			break;
		}

	}
}
