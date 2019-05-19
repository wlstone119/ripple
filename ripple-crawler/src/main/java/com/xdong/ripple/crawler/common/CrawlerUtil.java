package com.xdong.ripple.crawler.common;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import com.xdong.ripple.common.utils.RandomUtil;

/**
 * 类CrawlerUtil.java的实现描述：TODO 类实现描述
 * 
 * @author wanglei Mar 24, 2019 9:15:27 PM
 */
public class CrawlerUtil {

    private static Logger logger = Logger.getLogger(CrawlerUtil.class);

    public static Document connectUrl(String url) {
        Long sleepTime = new RandomUtil().randomTime(url);
        logger.info("爬虫线程等待，休眠时间：" + sleepTime + "(ms), 等待执行的url：【" + url + "】");
        try {
            Thread.sleep(sleepTime);
            return Jsoup.connect(url).userAgent("Mozilla/5.0 (Windows; U; Windows NT 5.1; en-US; rv:0.9.4)").get();
        } catch (Exception e) {
            logger.info("请求url：【" + url + "】获取网页信息时出现异常", e);
        }
        return null;
    }

    public static void main(String[] args) throws IOException {
        String url = "https://music.163.com/discover/playlist?cat=安静&order=hot&limit=35&offset=1295";
        Document dom = connectUrl(url);
        Element songSheets = dom.getElementById("m-pl-container");
        System.out.println(songSheets);

    }

}
