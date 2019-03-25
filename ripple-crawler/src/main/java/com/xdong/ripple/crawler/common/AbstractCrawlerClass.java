package com.xdong.ripple.crawler.common;

import java.io.File;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import edu.uci.ics.crawler4j.crawler.CrawlConfig;
import edu.uci.ics.crawler4j.crawler.CrawlController;
import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.crawler.WebCrawler;
import edu.uci.ics.crawler4j.fetcher.PageFetcher;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtConfig;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtServer;
import edu.uci.ics.crawler4j.url.WebURL;

public abstract class AbstractCrawlerClass extends WebCrawler {

	protected static Logger log = LoggerFactory.getLogger(AbstractCrawlerClass.class);

	// 电影资源过滤
	public static final Pattern filmPattern = PatternEnum.FILM.getRegValue();

	// 图片资源过滤
	public static final Pattern imgPattern = PatternEnum.IMG.getRegValue();

	// 种子链接：即需要抓去的网站链接
	public static String[] crawlDomains;

	public static File storageFolder;

	// 默认启动线程数量
	private static int defaultThreadNumber = 5;

	// 默认保存文件根目录
	private static String defaultRootFolder = "crawler";

	// 默认文件存储目录
	private static String defaultStorageFolder = "fileFolder";

	// 默认爬虫机器人名称
	private static String defaultUserAgentname = "baiduspider";

	// 默认爬虫爬取的最大深度
	private static int defaultMaxDepth = 1;

	/**
	 * 需要保存文件的爬虫构造器
	 * 
	 * @param domain
	 *            域名
	 * @param storageFolderName
	 *            文件保存目录
	 */
	public static void configure(String[] domain, String storageFolderName) {
		crawlDomains = domain;

		storageFolder = new File(storageFolderName);
		if (!storageFolder.exists()) {
			storageFolder.mkdirs();
		}
	}

	/**
	 * 不需要保存文件的爬虫构造器
	 * 
	 * @param domain
	 */
	public static void configure(String[] domain) {
		crawlDomains = domain;
	}

	public abstract boolean shouldVisit(Page referringPage, WebURL url);

	public abstract void visit(Page page);

	protected static CrawlController createCrawlController(String[] crawlDomains, String userAgentName,
			String rootFolder, int maxDepthOfCrawling) throws Exception {
		Assert.notNull(crawlDomains);

		CrawlConfig config = new CrawlConfig();
		config.setCrawlStorageFolder(rootFolder);
		config.setIncludeBinaryContentInCrawling(true);
		config.setMaxDepthOfCrawling(maxDepthOfCrawling);

		// 页面爬取工具类
		PageFetcher pageFetcher = new PageFetcher(config);

		// 网站爬虫定义配置
		RobotstxtConfig robotstxtConfig = new RobotstxtConfig();
		robotstxtConfig.setUserAgentName(userAgentName);

		RobotstxtServer robotstxtServer = new RobotstxtServer(robotstxtConfig, pageFetcher);
		CrawlController controller = new CrawlController(config, pageFetcher, robotstxtServer);
		for (String domain : crawlDomains) {
			controller.addSeed(domain);
		}

		return controller;
	}

	public static <T extends WebCrawler> void start(Class<T> webCrawler, String[] crawlDomains, Integer threadNumber,
			Integer maxDepthOfCrawling, String rootFolder, String storageFolder, String userAgentName)
			throws Exception {

		rootFolder = StringUtils.isBlank(rootFolder) ? defaultRootFolder : rootFolder;
		storageFolder = StringUtils.isBlank(storageFolder) ? defaultStorageFolder : rootFolder;
		userAgentName = StringUtils.isBlank(userAgentName) ? defaultUserAgentname : userAgentName;
		threadNumber = threadNumber == null ? defaultThreadNumber : threadNumber;
		maxDepthOfCrawling = maxDepthOfCrawling == null ? defaultMaxDepth : maxDepthOfCrawling;

		CrawlController controller = createCrawlController(crawlDomains, userAgentName, rootFolder, maxDepthOfCrawling);
		configure(crawlDomains, storageFolder);
		controller.start(webCrawler, threadNumber);
	}

}
