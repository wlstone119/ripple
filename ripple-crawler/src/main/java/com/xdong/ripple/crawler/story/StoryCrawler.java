package com.xdong.ripple.crawler.story;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.xdong.ripple.crawler.common.CrawlerResultVo;
import com.xdong.ripple.crawler.common.ParamVo;
import com.xdong.ripple.crawler.strategy.CrawlerStrategyInterface;

/**
 * 类StoryCrawler.java的实现描述：TODO 类实现描述
 * 
 * @author wanglei Mar 24, 2019 9:31:28 PM
 */
@Component
public class StoryCrawler implements CrawlerStrategyInterface {

    private static Logger logger    = Logger.getLogger(StoryCrawler.class);

    private static String domainUrl = "";

    private String        storyPath;

    @Override
    public CrawlerResultVo execute(ParamVo paramVo) {
        try {
            domainUrl = paramVo.getDomainUrl();
            return crawls(paramVo.getUrl(), paramVo.getBegin(), paramVo.getEnd());
        } catch (Exception e) {
            logger.error(String.format("运行爬虫时出现异常： 参数【%s】", JSON.toJSONString(paramVo)), e);
        }
        return null;
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    public CrawlerResultVo crawls(final String url, int begin, int end) throws Exception {

        CrawlerResultVo resultVo = new CrawlerResultVo();

        logger.info("抓取任务开始...");
        long t0 = System.currentTimeMillis();

        List<String> resultList = new ArrayList<String>();
        if (begin == 0) {
            crawlFirstStory(url);
            resultList.add(url);
        }

        ExecutorService exec = Executors.newCachedThreadPool();
        List<Callable<List>> threadList = new ArrayList<Callable<List>>();
        for (int i = begin; i < end; i++) {
            final int start = begin;
            final int complete = (begin + 5) > end ? end : begin + 5;
            Callable<List> task = new Callable<List>() {

                @Override
                public List call() {
                    List<String> varList = new ArrayList<String>();
                    for (int page = start; page < complete; page++) {
                        try {
                            logger.info(String.format("线程：【" + Thread.currentThread().getName() + "】开始第-> page: 【%s】 页",
                                                      page));
                            varList.add(crawlStory(url + "index_", page));
                            logger.info(String.format("线程：【" + Thread.currentThread().getName()
                                                      + "】已完成第-> page: 【%s】 页", page));
                        } catch (Exception e) {
                            logger.error(String.format("线程：【" + Thread.currentThread().getName()
                                                       + "】爬取第-> page: 【%s】 页时出现异常！", page),
                                         e);
                        }
                    }

                    return varList;

                }
            };
            threadList.add(task);
            begin += 5;
        }

        List<Future<List>> taskList = exec.invokeAll(threadList);
        for (Future<List> task : taskList) {
            List<String> result = task.get();
            resultList.addAll(result);
        }

        logger.info("抓取任务结束... 耗时：" + (System.currentTimeMillis() - t0) / 1000 + "秒");

        resultVo.setResultList(resultList);

        return resultVo;
    }

    public void crawlFirstStory(String url) throws Exception {
        Document dom = Jsoup.connect(url).get();
        Element impNewsDiv = dom.getElementsByClass("news_list").get(0);
        new File(storyPath).mkdirs();
        loopSearch(impNewsDiv);
    }

    public String crawlStory(String url, int page) throws Exception {
        url = url + page + ".html";
        Document dom = Jsoup.connect(url).get();
        Element impNewsDiv = dom.getElementsByClass("news_list").get(0);
        new File(storyPath).mkdirs();
        loopSearch(impNewsDiv);
        logger.info(String.format("线程：【" + Thread.currentThread().getName() + "】已完成任务-> url: %s", url));
        return url;
    }

    private void loopSearch(Element root) throws IOException {
        if (root.hasAttr("href") && root.hasAttr("title")) {
            final String storyUrl = root.attr("href").trim();
            if (!storyUrl.endsWith(".html")) {
                return;
            }
            final String storyTitle = root.attr("title").trim();
            Document newsDom = Jsoup.connect(domainUrl + storyUrl).get();
            Element ele = newsDom.getElementsByClass("news").get(0);
            ele.children().remove();
            write(storyTitle, ele.html().replaceAll("<br />", ""));
        } else {
            if (!root.children().isEmpty()) {
                for (Element element : root.children()) {
                    loopSearch(element);
                }
            }
        }
    }

    private void write(String newsTital, String html) throws UnsupportedEncodingException, IOException {

        File file = new File(storyPath + "/" + newsTital + ".txt");
        OutputStream os = new FileOutputStream(file);
        os.write(html.getBytes("gbk"));
        os.flush();
        os.close();
    }

}
