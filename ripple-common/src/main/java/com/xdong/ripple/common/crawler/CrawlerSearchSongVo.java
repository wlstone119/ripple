package com.xdong.ripple.common.crawler;

import java.util.List;

import com.xdong.ripple.dal.entity.crawler.RpCrawlerSongsDo;

/**
 * 类CrawlerSearchSongVo.java的实现描述：爬虫搜索界面返回model
 * 
 * @author wanglei 2019年4月26日 下午2:38:50
 */
public class CrawlerSearchSongVo {

	private String rowIndex;

	private int cssLength;

	private String type;

	private List<RpCrawlerSongsDo> viewList;

	public String getRowIndex() {
		return rowIndex;
	}

	public void setRowIndex(String rowIndex) {
		this.rowIndex = rowIndex;
	}

	public int getCssLength() {
		return cssLength;
	}

	public void setCssLength(int cssLength) {
		this.cssLength = cssLength;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public List<RpCrawlerSongsDo> getViewList() {
		return viewList;
	}

	public void setViewList(List<RpCrawlerSongsDo> viewList) {
		this.viewList = viewList;
	}

}
