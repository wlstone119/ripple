package com.xdong.ripple.crawler.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum CrawlerTypeEnum {

	INIT("init", "初始化"),
	SHOW("show", "显示页面"),
	MUSIC("music", "音乐"), 
	NEWS("news", "新闻"),

	;
	private String code;
	private String desc;

	public static String getDescByCode(String code) {
		if (code == null) {
			return null;
		}

		for (CrawlerTypeEnum crawlerTypeEnum : CrawlerTypeEnum.values()) {
			if (crawlerTypeEnum.getCode().equals(code)) {
				return crawlerTypeEnum.getDesc();
			}
		}

		return null;
	}

	public static CrawlerTypeEnum getEnumByCode(String code) {
		if (code == null) {
			return null;
		}

		for (CrawlerTypeEnum crawlerTypeEnum : CrawlerTypeEnum.values()) {
			if (crawlerTypeEnum.getCode().equals(code)) {
				return crawlerTypeEnum;
			}
		}

		return null;
	}

}
