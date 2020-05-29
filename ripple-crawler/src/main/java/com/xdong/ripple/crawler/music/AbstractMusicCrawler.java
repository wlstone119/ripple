package com.xdong.ripple.crawler.music;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StopWatch;

import com.alibaba.fastjson.JSON;
import com.xdong.ripple.common.BizException;
import com.xdong.ripple.common.utils.HttpUtil;
import com.xdong.ripple.crawler.common.Constant;
import com.xdong.ripple.crawler.common.CrawlerResultDto;
import com.xdong.ripple.crawler.common.CrawlerResultVo;
import com.xdong.ripple.crawler.common.CrawlerTypeEnum;
import com.xdong.ripple.crawler.common.ParamVo;
import com.xdong.ripple.crawler.strategy.CrawlerCompensateInterface;
import com.xdong.ripple.crawler.strategy.CrawlerMusicStrategyInterface;
import com.xdong.ripple.dal.entity.crawler.RpCrawlerTaskDo;
import com.xdong.ripple.dal.entity.crawler.RpCrawlerUrlDo;
import com.xdong.ripple.spi.crawler.IRpCrawlerSongsService;
import com.xdong.ripple.spi.crawler.IRpCrawlerTaskService;
import com.xdong.ripple.spi.crawler.IRpCrawlerUrlService;

/**
 * 类AbstractMusicCrawler.java的实现描述：爬虫抽象实现类
 * 
 * @author wanglei May 19, 2019 4:27:53 PM
 */
public abstract class AbstractMusicCrawler implements CrawlerMusicStrategyInterface, CrawlerCompensateInterface {

	private static Logger logger = Logger.getLogger(AbstractMusicCrawler.class);

	protected String domainUrl;

	@Autowired
	CompensateTaskExecutor taskExecutor;

	@Autowired
	IRpCrawlerSongsService rpSongsServiceImpl;

	@Autowired
	IRpCrawlerTaskService rpCrawlerTaskServiceImpl;

	@Autowired
	protected IRpCrawlerUrlService rpCrawlerUrlServiceImpl;

	protected abstract String getCrawlerUrlByDataUrl(String url, int page);

	protected abstract CrawlerResultDto getResultByCrawlerUrl(String url);

	protected List<RpCrawlerUrlDo> getCrawlerUrlList(ParamVo paramVo) {
		return null;
	}

	@Override
	public CrawlerResultVo execute(ParamVo paramVo) {

		CrawlerResultVo resultVo = new CrawlerResultVo();
		List<ParamVo> paramList = new ArrayList<ParamVo>();

		List<RpCrawlerUrlDo> taskList = getCrawlerUrlList(paramVo);
		if (CollectionUtils.isNotEmpty(taskList)) {
			for (RpCrawlerUrlDo crawlerUrlDo : taskList) {
				ParamVo taskParamVo = new ParamVo();
				BeanUtils.copyProperties(paramVo, taskParamVo);

				taskParamVo.setUrlKey(crawlerUrlDo.getId());
				taskParamVo.setUrl(crawlerUrlDo.getCrawlerUrl());
				taskParamVo.setDomainUrl(crawlerUrlDo.getDomainName());
				taskParamVo.setLimitPage(getLimit(crawlerUrlDo.getCrawlerUrl()));

				paramList.add(taskParamVo);
			}
		} else {
			RpCrawlerUrlDo urlDo = rpCrawlerUrlServiceImpl.getById(paramVo.getUrlKey());

			paramVo.setUrl(urlDo.getCrawlerUrl());
			paramVo.setDomainUrl(urlDo.getDomainName());
			paramVo.setLimitPage(getLimit(urlDo.getCrawlerUrl()));

			paramList.add(paramVo);
		}

		if (CollectionUtils.isNotEmpty(paramList)) {
			for (ParamVo taskParam : paramList) {
				logger.info("开始处理爬虫任务请求，taskParam:" + JSON.toJSONString(taskParam));
				startTask(taskParam);
			}
		}

		return resultVo;
	}

	private CrawlerResultVo startTask(ParamVo paramVo) {

		StopWatch stopWatch = new StopWatch();
		stopWatch.start();

		RpCrawlerTaskDo taskDo = new RpCrawlerTaskDo();
		taskDo.setUrlId(paramVo.getUrlKey());
		taskDo.setStartTime(new Date());
		taskDo.setcTime(new Date());
		taskDo.setcUser("system");
		taskDo.setmTime(new Date());
		taskDo.setmUser("system");
		rpCrawlerTaskServiceImpl.save(taskDo);

		CrawlerResultVo resultVo = mulitThreadExecute(paramVo);

		taskDo.setConsumerTime((int) stopWatch.getTotalTimeSeconds());
		taskDo.setInsertCount(resultVo.getInsertCount());
		taskDo.setRepeatCount(resultVo.getRepeatCount());
		taskDo.setStratThreadCount(resultVo.getStratThreadCount());
		taskDo.setEndTime(new Date());

		stopWatch.stop();
		rpCrawlerTaskServiceImpl.updateById(taskDo);

		return resultVo;
	}

	private Integer getLimit(String crawlerUrl) {
		URL url;
		try {
			url = new URL(crawlerUrl);
			Map<String, String> paramObj = HttpUtil.splitQueryString(url.getQuery());
			if (paramObj.get("limitFlag") != null) {
				return Integer.parseInt(paramObj.get("limitFlag"));
			}
		} catch (MalformedURLException e) {
			throw BizException.create(String.format("param error: %s", crawlerUrl));
		}
		return null;
	}

	public boolean specialCrawler() {
		return true;
	}

	public CrawlerResultVo mulitThreadExecute(ParamVo paramVo) {

		CrawlerResultVo resultVo = new CrawlerResultVo();

		// 启动线程数量
		int stratThreadCount = 0;

		// 任务参数
		resultVo.setParamVo(paramVo);

		List<CrawlerResultDto> resultList = new ArrayList<CrawlerResultDto>();

		int begin = paramVo.getBegin() <= 0 ? 0 : paramVo.getBegin();
		int end = paramVo.getLimitPage() <= 0 ? 1 : paramVo.getLimitPage();

		ExecutorService service = null;

		try {
			service = Executors.newCachedThreadPool();

			// 一页url启动一个线程去爬取数据
			List<FutureTask<CrawlerResultDto>> list = new ArrayList<FutureTask<CrawlerResultDto>>();
			for (int i = begin; i < end; i++) {
				stratThreadCount++;
				FutureTask<CrawlerResultDto> task = (FutureTask<CrawlerResultDto>) service
						.submit(new CrawlerCallable(paramVo, i));
				list.add(task);
			}

			// 任务启动线程数
			resultVo.setStratThreadCount(stratThreadCount);

			for (FutureTask<CrawlerResultDto> task : list) {
				try {
					resultList.add(task.get());
				} catch (Exception e) {
					logger.error("线程任务执行异常,任务信息:" + JSON.toJSONString(task), e);
				}
			}
		} finally {

			logger.info("线程调度完毕，resultList:" + JSON.toJSONString(resultList));

			if (service != null) {
				service.shutdown();
				logger.info("任务执行完毕，关闭线程池!");
			}
		}

		if (CollectionUtils.isNotEmpty(resultList)) {
			List<String> resultUrlList = new ArrayList<String>();

			for (CrawlerResultDto resultDto : resultList) {
				resultVo.setInsertCount(resultDto.getInsertCount() + resultVo.getInsertCount());
				resultVo.setRepeatCount(resultDto.getRepatCount() + resultVo.getRepeatCount());
				resultUrlList.add(resultDto.getUrl());
			}
		}

		return resultVo;
	}

	class CrawlerCallable implements Callable<CrawlerResultDto> {

		private String url;

		public CrawlerCallable(ParamVo paramVo, int page) {
			url = getCrawlerUrlByDataUrl(paramVo.getUrl(), page);
		}

		@Override
		public CrawlerResultDto call() {
			return getResultByCrawlerUrl(url);
		}
	}
}
