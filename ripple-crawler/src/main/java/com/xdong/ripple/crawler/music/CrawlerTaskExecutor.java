package com.xdong.ripple.crawler.music;

import java.util.concurrent.LinkedBlockingQueue;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xdong.ripple.common.utils.SpringUtil;
import com.xdong.ripple.crawler.strategy.CrawlerMusicStrategyInterface;
import com.xdong.ripple.dal.entity.crawler.RpCrawlerUrlDo;
import com.xdong.ripple.spi.crawler.IRpCrawlerUrlService;

/**
 * 爬虫任务执行器
 * 
 */
@Service
public class CrawlerTaskExecutor implements InitializingBean {

	private static Logger logger = Logger.getLogger(CrawlerTaskExecutor.class);

	@Autowired
	private IRpCrawlerUrlService rpCrawlerUrlServiceImpl;

	// 任务队列
	public volatile LinkedBlockingQueue<Long> taskIdQueue = new LinkedBlockingQueue<Long>();

	public Object lockMonitor = new Object();

	@Override
	public void afterPropertiesSet() throws Exception {

		logger.info("************开始初始化爬虫任务执行器************");

		new Thread(new Runnable() {
			@Override
			public void run() {
				while (true) {
					if (taskIdQueue != null && taskIdQueue.size() > 0) {
						Long taskId = taskIdQueue.poll();
						logger.info("存在【" + taskIdQueue.size() + "】条爬虫任务,开始执行任务taskId:" + taskId);
						execute(taskId);

						logger.info("等待爬虫任务结束,开始休眠30s......");
						sleep(30);
						logger.info("等待爬虫任务结束,休眠30s结束!!!!!!");
					} else {
						sleep(3);
					}
				}
			}
		}, "ExecuteTaskExecutor").start();

		logger.info("************初始化爬虫任务执行器完成************");
	}

	private void sleep(int secTime) {
		try {
			Thread.sleep(secTime * 1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private void execute(Long taskId) {
		RpCrawlerUrlDo taskDo = rpCrawlerUrlServiceImpl.selectById(taskId);

		if (taskDo != null) {

			CrawlerMusicStrategyInterface strategy = (CrawlerMusicStrategyInterface) SpringUtil
					.getBeansByName(taskDo.getCrawlerClass());

			strategy.startTask(taskId);
		}
	}
}
