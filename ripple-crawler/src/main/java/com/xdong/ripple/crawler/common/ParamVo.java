package com.xdong.ripple.crawler.common;

/**
 * @description 爬虫参数传递时使用
 * @author stone
 * @date 2017年4月11日
 */
public class ParamVo {

	private Long urlKey;

	private int begin;

	private int end;

	private String searchKey;

	private String modelName;

	private Integer limitPage;

	public String getSearchKey() {
		return searchKey;
	}

	public void setSearchKey(String searchKey) {
		this.searchKey = searchKey;
	}

	public Long getUrlKey() {
		return urlKey;
	}

	public void setUrlKey(Long urlKey) {
		this.urlKey = urlKey;
	}

	public int getBegin() {
		return begin;
	}

	public void setBegin(int begin) {
		this.begin = begin;
	}

	public int getEnd() {
		return end;
	}

	public void setEnd(int end) {
		this.end = end;
	}

	public String getModelName() {
		return modelName;
	}

	public void setModelName(String modelName) {
		this.modelName = modelName;
	}

	public Integer getLimitPage() {
		return limitPage;
	}

	public void setLimitPage(Integer limit) {
		this.limitPage = limit;
	}
}
