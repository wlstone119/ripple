package com.xdong.ripple.dal.entity.crawler;

import com.baomidou.mybatisplus.enums.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 歌单表
 * </p>
 *
 * @author wanglei
 * @since 2019-03-20
 */
@TableName("rp_crawler_songs_sheet")
public class RpCrawlerSongsSheetDo extends Model<RpCrawlerSongsSheetDo> {

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
     * 歌单名称
     */
    private String name;
    /**
     * 作者
     */
    private String author;
    /**
     * 封面
     */
    @TableField("cover_img")
    private String coverImg;
    /**
     * 介绍
     */
    private String introduction;
    /**
     * 播放次数
     */
    @TableField("play_count")
    private Integer playCount;
    /**
     * 收藏次数
     */
    @TableField("collect_count")
    private Integer collectCount;
    /**
     * 分享次数
     */
    @TableField("share_count")
    private Integer shareCount;
    /**
     * 点赞次数
     */
    @TableField("comment_count")
    private Integer commentCount;
    /**
     * 排序
     */
    private Integer sort;
    /**
     * 资源路径
     */
    private String resourcepath;


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

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getCoverImg() {
        return coverImg;
    }

    public void setCoverImg(String coverImg) {
        this.coverImg = coverImg;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public Integer getPlayCount() {
        return playCount;
    }

    public void setPlayCount(Integer playCount) {
        this.playCount = playCount;
    }

    public Integer getCollectCount() {
        return collectCount;
    }

    public void setCollectCount(Integer collectCount) {
        this.collectCount = collectCount;
    }

    public Integer getShareCount() {
        return shareCount;
    }

    public void setShareCount(Integer shareCount) {
        this.shareCount = shareCount;
    }

    public Integer getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(Integer commentCount) {
        this.commentCount = commentCount;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public String getResourcepath() {
        return resourcepath;
    }

    public void setResourcepath(String resourcepath) {
        this.resourcepath = resourcepath;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "RpCrawlerSongsSheetDo{" +
        "id=" + id +
        ", cTime=" + cTime +
        ", cUser=" + cUser +
        ", mTime=" + mTime +
        ", mUser=" + mUser +
        ", name=" + name +
        ", author=" + author +
        ", coverImg=" + coverImg +
        ", introduction=" + introduction +
        ", playCount=" + playCount +
        ", collectCount=" + collectCount +
        ", shareCount=" + shareCount +
        ", commentCount=" + commentCount +
        ", sort=" + sort +
        ", resourcepath=" + resourcepath +
        "}";
    }
}
