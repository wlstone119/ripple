package com.xdong.ripple.common.crawler;

import com.xdong.ripple.common.enums.CrawlerMusicTaskTypeEnum;

public class SupplementErrorTask {

    // 任务来源：baidu wangyi
    private String                   compensateClass;

    // 0 : 歌单 1 : 歌曲
    private CrawlerMusicTaskTypeEnum taskType;

    // 任务url
    private String                   taskUrl;

    // 重试次数
    private int                      retryCount = 0;

    public SupplementErrorTask(String compensateClass, CrawlerMusicTaskTypeEnum taskType, String taskUrl){
        this.compensateClass = compensateClass;
        this.taskType = taskType;
        this.taskUrl = taskUrl;
    }

    public String getCompensateClass() {
        return compensateClass;
    }

    public void setCompensateClass(String compensateClass) {
        this.compensateClass = compensateClass;
    }

    public CrawlerMusicTaskTypeEnum getTaskType() {
        return taskType;
    }

    public void setTaskType(CrawlerMusicTaskTypeEnum taskType) {
        this.taskType = taskType;
    }

    public String getTaskUrl() {
        return taskUrl;
    }

    public void setTaskUrl(String taskUrl) {
        this.taskUrl = taskUrl;
    }

    public int getRetryCount() {
        return retryCount;
    }

    public void setRetryCount(int retryCount) {
        this.retryCount = retryCount;
    }

}
