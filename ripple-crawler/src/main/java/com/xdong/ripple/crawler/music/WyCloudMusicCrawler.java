package com.xdong.ripple.crawler.music;

import java.io.IOException;
import java.util.Date;

import org.apache.log4j.Logger;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.xdong.ripple.common.crawler.SupplementErrorTask;
import com.xdong.ripple.common.enums.CrawlerMusicTaskTypeEnum;
import com.xdong.ripple.crawler.common.Constant;
import com.xdong.ripple.crawler.common.CrawlerResultDto;
import com.xdong.ripple.crawler.common.CrawlerUtil;
import com.xdong.ripple.dal.entity.crawler.RpCrawlerSongsDo;

/**
 * 类WyCloudMusicCrawler.java的实现描述：TODO 类实现描述
 * 
 * @author wanglei Mar 24, 2019 9:21:38 PM
 */
@Service
public class WyCloudMusicCrawler extends AbstractMusicCrawler {

	private static Logger logger = Logger.getLogger(WyCloudMusicCrawler.class);

	@Override
	public CrawlerResultDto getResultByCrawlerUrl(String url) {
		CrawlerResultDto resultDto = new CrawlerResultDto();
		getWyMusicByCat(url, resultDto);
		return resultDto;
	}

	@Override
	public String getCrawlerUrlByDataUrl(String url, int page) {
		String offset = String.valueOf(page * 35);
		return (url.contains("offset")) ? url.replaceAll("offset", offset) : url + "&offset=" + offset;
	}

	@Override
	public boolean songSheetUrlCompensate(SupplementErrorTask task) {
		getWyMusicByCat(task.getTaskUrl(), null);
		return true;
	}

	@Override
	public boolean songUrlCompensate(SupplementErrorTask task) {
		return false;
	}

	/**
	 * 爬取网易云音乐歌曲：类目
	 * url：http://music.163.com/discover/playlist/?cat=民谣&order=hot&limit=35
	 * 
	 * @param url
	 */
	public String getWyMusicByCat(String url, CrawlerResultDto resultDto) {
		Document dom = CrawlerUtil.connectUrl(url);
		if (dom == null) {
			logger.error("线程：【" + Thread.currentThread().getName() + "】-> 未获取到网页信息，终止爬虫，url:" + url);

			taskExecutor.errorUrlQueue
					.add(new SupplementErrorTask(this.getClass().getName(), CrawlerMusicTaskTypeEnum.SONGSHEET, url));

			logger.info("待补单队列信息：" + JSON.toJSONString(taskExecutor.errorUrlQueue));

			return url + "【NULL PAGE】";
		}

		Element songSheets = dom.getElementById("m-pl-container");
		if (songSheets != null) {
			for (Element songSheet : songSheets.children()) {
				loopSongSheet(songSheet, resultDto);
			}
		}

		return url;
	}

	/**
	 * 一个歌单元素循环，获取歌单中所有歌曲写入数据库
	 * 
	 * @param root
	 * @throws IOException
	 */
	private void loopSongSheet(Element songSheet, CrawlerResultDto resultDto) {
		if (songSheet.hasAttr("href") && songSheet.hasAttr("title")
				&& ("tit f-thide s-fc0".equals(songSheet.attr("class")))) {// <a>标签
			String songUrl = songSheet.attr("href").trim();// 歌单url

			// 歌单链接
			String songSheetUrl = appendUrl(songUrl);

			// 获取歌单下的所有歌曲
			Document songs = CrawlerUtil.connectUrl(songSheetUrl);
			Element songsDiv = songs.getElementById("song-list-pre-cache");
			Elements songSheetDiv = songs.getElementsByClass("cntc");
			Elements songsList = songsDiv.getElementsByTag("ul");
			if (songsList != null) {
				writeToDB(songsList, songSheetDiv.get(0), resultDto);
			}
		} else {
			if (!songSheet.children().isEmpty()) {
				for (Element element : songSheet.children()) {
					loopSongSheet(element, resultDto);
				}
			}
		}
	}

	/**
	 * 将得到的歌集下的音乐保存至数据库
	 * 
	 * @param musicTr
	 * @throws IOException
	 */
	private void writeToDB(Elements songsList, Element songSheetDiv, CrawlerResultDto resultDto) {

		RpCrawlerSongsDo songDo = new RpCrawlerSongsDo();
		songDo.setcTime(new Date());
		songDo.setmTime(new Date());
		songDo.setmUser("system");
		songDo.setcUser("system");
		songDo.setStatus("valid");
		songDo.setResource(Constant.CRAWLER_RESOURCE_WANGYI);

		// 歌单名称
		Elements songSheetH2 = songSheetDiv.getElementsByTag("h2");
		if (!songSheetH2.isEmpty()) {
			songDo.setSongSheet(songSheetH2.html().replaceAll("&nbsp;", " "));
		}

		// 歌曲类型
		StringBuffer sb = new StringBuffer();
		Elements songType = songSheetDiv.getElementsByClass("u-tag");
		for (Element song : songType) {
			sb.append(" " + song.html());
		}
		songDo.setType(sb.toString());

		String songUrl = "";
		String href = null;
		Elements songs = songsList.get(0).getElementsByTag("li");
		for (Element song : songs) {
			href = song.getElementsByTag("a").attr("href");
			songUrl = getSongUrlById(href);
			Long songId = Long.parseLong(href.substring(href.indexOf("=") + 1, href.length()));

			if (rpSongsServiceImpl.checkSongIdExists(songId, Constant.CRAWLER_RESOURCE_WANGYI)) {
				// logger.info("撞库成功，已存在：" + Constant.CRAWLER_RESOURCE_WANGYI + "-" + songId);
				if (resultDto != null) {
					resultDto.setRepatCount(resultDto.getRepatCount() + 1);
				}
				continue;
			}

			// 播放器
			songDo.setResourcepath(appendIframe(songUrl));

			// 歌名
			songDo.setName(song.getElementsByTag("a").html());
			// 歌曲url
			songDo.setSongUrl(songUrl);

			songDo.setSongId(songId);

			// 歌曲url获取信息
			Document songDetail = CrawlerUtil.connectUrl(songDo.getSongUrl());
			Elements songDetailDiv = songDetail.getElementsByClass("cnt");
			Elements songDetailArr = songDetailDiv.get(0).getElementsByTag("p");
			for (int i = 0; i < songDetailArr.size(); i++) {
				Element ele = songDetailArr.get(i);

				if (containSpan(ele)) {
					songDo.setSongAuthor(ele.getElementsByTag("span").attr("title"));
				} else {
					songDo.setSongAlbum(ele.getElementsByTag("a").html());
				}
			}

			// 专辑图片
			Elements eles = songDetail.getElementsByClass("f-cb");
			Element albumPic = eles.get(0);
			String albumPicUrl = albumPic.getElementsByTag("img").attr("src");
			songDo.setSongAlbumPic(
					albumPicUrl.indexOf("?") > 0 ? albumPicUrl.substring(0, albumPicUrl.indexOf("?")) : albumPicUrl);

			rpSongsServiceImpl.save(songDo);

			logger.info("已收录歌曲：" + songDo.getName() + " 歌曲来源及主键：" + Constant.CRAWLER_RESOURCE_WANGYI + "-" + songId);

			if (resultDto != null) {
				resultDto.setInsertCount(resultDto.getInsertCount() + 1);
			}
		}

	}

	private boolean containSpan(Element ele) {
		for (Element unit : ele.children()) {
			if (!unit.getElementsByTag("span").isEmpty()) {
				return true;
			}
		}
		return false;
	}

	private String appendUrl(String url) {
		return domainUrl + url;
	}

	private String getSongUrlById(String songIdStr) {
		return domainUrl + songIdStr;
	}

	private String appendIframe(String id) {
		return "<iframe frameborder=\"no\" border=\"0\" marginwidth=\"0\" "
				+ "marginheight=\"0\" width=330 height=86 src=\"//music.163.com/outchain/player?type=2&id=" + id
				+ "&auto=1&height=66\"></iframe>";
	}
}
