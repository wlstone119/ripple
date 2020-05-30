package com.xdong.ripple.crawler.news;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;

import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.xdong.ripple.crawler.common.CrawlerResultVo;
import com.xdong.ripple.crawler.common.ParamVo;
import com.xdong.ripple.crawler.strategy.CrawlerStrategyInterface;

/**
 * 类SinaNewsCrawler.java的实现描述：TODO 类实现描述
 * 
 * @author wanglei Mar 24, 2019 9:27:23 PM
 */
public class SinaNewsCrawler implements CrawlerStrategyInterface {

    private static Logger logger = Logger.getLogger(SinaNewsCrawler.class);

    @Autowired
    private String        newsPath;

    @Override
    public CrawlerResultVo execute(ParamVo paramVo) {
        try {
           // crawl(paramVo.getUrl());
        } catch (Exception e) {
            logger.error(String.format("运行爬虫时出现异常： 参数【%s】", JSON.toJSONString(paramVo)), e);
        }
        return null;
    }

    public void crawl(String url) throws Exception {
        logger.info("抓取任务开始...");
        long t0 = System.currentTimeMillis();
        Document dom = Jsoup.connect(url).get();
        Element impNewsDiv = dom.getElementById("fin_tabs0_c0");

        new File(newsPath).mkdirs();
        loopSearch(impNewsDiv);
        logger.info("抓取任务结束... 耗时：" + (System.currentTimeMillis() - t0) / 1000 + "秒");
    }

    private void loopSearch(Element root) throws IOException {
        if (root.hasAttr("href") && !root.hasAttr("rel")) {// <a>标签
            final String newsUrl = root.attr("href").trim();// 新闻链接
            if (!newsUrl.endsWith(".shtml")) {
                return;// 目录路径
            }
            final String newsTital = root.html().trim();// 新闻标题
            Document newsDom = Jsoup.connect(newsUrl).get();
            write(newsTital, newsDom.html());
        } else {
            if (!root.children().isEmpty()) {
                for (Element element : root.children()) {
                    loopSearch(element);
                }
            }
        }
    }

    private void write(String newsTital, String html) throws UnsupportedEncodingException, IOException {

        File file = new File(newsPath + "/" + newsTital + ".html");
        OutputStream os = new FileOutputStream(file);
        os.write(html.getBytes("utf-8"));
        os.flush();
        os.close();
    }

}
