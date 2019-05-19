package com.xdong.ripple.crawler.common;

public class CrawlerResultDto {

    private String url;

    private int    insertCount;

    private int    repatCount;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getInsertCount() {
        return insertCount;
    }

    public void setInsertCount(int insertCount) {
        this.insertCount = insertCount;
    }

    public int getRepatCount() {
        return repatCount;
    }

    public void setRepatCount(int repatCount) {
        this.repatCount = repatCount;
    }

}
