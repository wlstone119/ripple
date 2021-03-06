package com.xdong.ripple.crawler.common;

public class Constant {

	/******************** 爬虫-URL类型 ************************/

	public static final String CRAWLER_TYPE_MUSIC = "music";

	public static final String CRAWLER_TYPE_SEARCH = "search";

	/******************** 爬虫-百度音乐常量 ************************/

	public static final String CRAWLER_RESOURCE_BAIDU = "baidu";

	public static final String CRAWLER_PREFIXURL_BAIDU = "http://music.baidu.com/";

	public static final String CRAWLER_RESOURCE_BAIDU_URL = "http://music.baidu.com/songlist/tag/安静?orderType=1&offset=$offset&third_type=";

	/******************** 爬虫-新浪财经新闻常量 ************************/

	public static final String CRAWLER_RESOURCE_SINA = "sina";

	public static final String CRAWLER_PREFIXURL_SINA = "http://finance.sina.com.cn/";

	public static final String CRAWLER_RESOURCE_SINA_URL = "http://finance.sina.com.cn/";

	/******************** 爬虫-网易云音乐常量 ************************/

	public static final String CRAWLER_RESOURCE_WANGYI = "wangyi";

	public static final String CRAWLER_PREFIXURL_WANGYI = "https://music.163.com/";

	public static final String CRAWLER_RESOURCE_WANGYI_URL = "http://music.163.com/discover/playlist/?cat=民谣&order=hot&limit=35";

	public static final String CRAWLER_CRAWLER_CLASS_WANGYI = "wyCloudMusicCrawler";
	
	public static final String CRAWLER_CRAWLER_URL_WANGYI = "https://music.163.com/discover/playlist/?order=hot";

}
