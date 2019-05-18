package com.xdong.ripple.common.enums;

public enum CrawlerMusicTaskTypeEnum {

     SONGSHEET(0, "歌单"), 
     SONGS(1, "歌曲");

    int    taskType;
    String desc;

    CrawlerMusicTaskTypeEnum(int taskType, String desc){
        this.taskType = taskType;
        this.desc = desc;
    }

    public int getTaskType() {
        return taskType;
    }
    
    public void setTaskType(int taskType) {
        this.taskType = taskType;
    }
    
    public String getDesc() {
        return desc;
    }
    
    public void setDesc(String desc) {
        this.desc = desc;
    }

}
