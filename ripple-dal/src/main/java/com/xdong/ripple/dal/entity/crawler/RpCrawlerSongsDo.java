package com.xdong.ripple.dal.entity.crawler;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 音乐表
 * </p>
 *
 * @author wanglei
 * @since 2019-03-20
 */
@TableName("rp_crawler_songs")
public class RpCrawlerSongsDo extends Model<RpCrawlerSongsDo> {

	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	@TableId(value = "id", type = IdType.AUTO)
	private Long id;
	/**
	 * 创建时间
	 */
	@TableField("c_time")
	private Date cTime;
	/**
	 * 创建用户
	 */
	@TableField("c_user")
	private String cUser;
	/**
	 * 更新时间
	 */
	@TableField("m_time")
	private Date mTime;
	/**
	 * 更新用户
	 */
	@TableField("m_user")
	private String mUser;
	/**
	 * 音乐名称
	 */
	private String name;
	/**
	 * 歌手
	 */
	@TableField("song_author")
	private String songAuthor;
	/**
	 * 专辑
	 */
	@TableField("song_album")
	private String songAlbum;
	/**
	 * 音乐类型
	 */
	private String type;
	/**
	 * 状态
	 */
	private String status;
	/**
	 * 封面
	 */
	@TableField("song_album_pic")
	private String songAlbumPic;
	/**
	 * 时长
	 */
	@TableField("song_duration")
	private String songDuration;
	/**
	 * 音乐链接
	 */
	@TableField("song_url")
	private String songUrl;
	/**
	 * 资源路径
	 */
	private String resourcepath;
	/**
	 * 歌单
	 */
	@TableField("song_sheet")
	private String songSheet;
	/**
	 * 排序
	 */
	private Integer sort;
	/**
	 * 来源
	 */
	private String resource;

	/**
	 * 外网歌曲id
	 */
	private Long songId;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getcTime() {
		return cTime;
	}

	public void setcTime(Date cTime) {
		this.cTime = cTime;
	}

	public String getcUser() {
		return cUser;
	}

	public void setcUser(String cUser) {
		this.cUser = cUser;
	}

	public Date getmTime() {
		return mTime;
	}

	public void setmTime(Date mTime) {
		this.mTime = mTime;
	}

	public String getmUser() {
		return mUser;
	}

	public void setmUser(String mUser) {
		this.mUser = mUser;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSongAuthor() {
		return songAuthor;
	}

	public void setSongAuthor(String songAuthor) {
		this.songAuthor = songAuthor;
	}

	public String getSongAlbum() {
		return songAlbum;
	}

	public void setSongAlbum(String songAlbum) {
		this.songAlbum = songAlbum;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getSongAlbumPic() {
		return songAlbumPic;
	}

	public void setSongAlbumPic(String songAlbumPic) {
		this.songAlbumPic = songAlbumPic;
	}

	public String getSongDuration() {
		return songDuration;
	}

	public void setSongDuration(String songDuration) {
		this.songDuration = songDuration;
	}

	public String getSongUrl() {
		return songUrl;
	}

	public void setSongUrl(String songUrl) {
		this.songUrl = songUrl;
	}

	public String getResourcepath() {
		return resourcepath;
	}

	public void setResourcepath(String resourcepath) {
		this.resourcepath = resourcepath;
	}

	public String getSongSheet() {
		return songSheet;
	}

	public void setSongSheet(String songSheet) {
		this.songSheet = songSheet;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public String getResource() {
		return resource;
	}

	public void setResource(String resource) {
		this.resource = resource;
	}

	public Long getSongId() {
		return songId;
	}

	public void setSongId(Long songId) {
		this.songId = songId;
	}

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

	@Override
	public String toString() {
		return "RpCrawlerSongsDo{" + "id=" + id + ", cTime=" + cTime + ", cUser=" + cUser + ", mTime=" + mTime
				+ ", mUser=" + mUser + ", name=" + name + ", songAuthor=" + songAuthor + ", songAlbum=" + songAlbum
				+ ", type=" + type + ", status=" + status + ", songAlbumPic=" + songAlbumPic + ", songDuration="
				+ songDuration + ", songUrl=" + songUrl + ", resourcepath=" + resourcepath + ", songSheet=" + songSheet
				+ ", sort=" + sort + ", resource=" + resource + ", songId=" + songId + "}";
	}
}
