package com.xdong.ripple.crawler.common;

import java.util.List;

/**
 * 类CrawlerResultVo.java的实现描述：TODO 类实现描述 
 * @author wanglei May 19, 2019 1:03:53 PM
 */
public class CrawlerResultVo {

    // 本次爬虫爬取数据使用参数
    private ParamVo      paramVo;

    // 本次爬虫爬取数据涉及链接
    private List<String> resultList;

    // 爬虫爬取新数据总数
    private int          insertCount;

    // 爬虫爬取数据重复数据总数
    private int          repeatCount;

    // 爬虫启动线程数
    private int          stratThreadCount;

    public ParamVo getParamVo() {
        return paramVo;
    }

    public void setParamVo(ParamVo paramVo) {
        this.paramVo = paramVo;
    }

    public List<String> getResultList() {
        return resultList;
    }

    public void setResultList(List<String> resultList) {
        this.resultList = resultList;
    }

    public int getInsertCount() {
        return insertCount;
    }

    public void setInsertCount(int insertCount) {
        this.insertCount = insertCount;
    }

    public int getRepeatCount() {
        return repeatCount;
    }

    public void setRepeatCount(int repeatCount) {
        this.repeatCount = repeatCount;
    }

    public int getStratThreadCount() {
        return stratThreadCount;
    }

    public void setStratThreadCount(int stratThreadCount) {
        this.stratThreadCount = stratThreadCount;
    }

}
