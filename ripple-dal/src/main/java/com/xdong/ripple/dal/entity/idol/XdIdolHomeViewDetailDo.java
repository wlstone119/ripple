package com.xdong.ripple.dal.entity.idol;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

/**
 * <p>
 * idol首页试图详情h维护表
 * </p>
 *
 * @author wanglei
 * @since 2019-03-20
 */
@TableName("xd_idol_home_view_detail")
public class XdIdolHomeViewDetailDo extends Model<XdIdolHomeViewDetailDo> {

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
	 * 首页试图配置主键id
	 */
	@TableField("pre_view_id")
	private Long preViewId;
	/**
	 * 首页试图类型：banner，网页，新闻，视频
	 */
	@TableField("idol_home_type")
	private String idolHomeType;
	/**
	 * 试图标题
	 */
	private String title;
	/**
	 * 试图详情描述
	 */
	private String description;
	/**
	 * 试图
	 */
	private String content;
	/**
	 * 排序号
	 */
	private Integer sort;
	/**
	 * 外链url
	 */
	@TableField("out_href")
	private String outHref;
	/**
	 * 门面图片url
	 */
	@TableField("pic_url")
	private String picUrl;
	/**
	 * siteUrlg类型描述
	 */
	@TableField("site_type")
	private String siteType;

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

	public Long getPreViewId() {
		return preViewId;
	}

	public void setPreViewId(Long preViewId) {
		this.preViewId = preViewId;
	}

	public String getIdolHomeType() {
		return idolHomeType;
	}

	public void setIdolHomeType(String idolHomeType) {
		this.idolHomeType = idolHomeType;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public String getOutHref() {
		return outHref;
	}

	public void setOutHref(String outHref) {
		this.outHref = outHref;
	}

	public String getPicUrl() {
		return picUrl;
	}

	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}

	public String getSiteType() {
		return siteType;
	}

	public void setSiteType(String siteType) {
		this.siteType = siteType;
	}

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

	@Override
	public String toString() {
		return "XdIdolHomeViewDetailDo{" + "id=" + id + ", cTime=" + cTime + ", cUser=" + cUser + ", mTime=" + mTime
				+ ", mUser=" + mUser + ", preViewId=" + preViewId + ", idolHomeType=" + idolHomeType + ", title="
				+ title + ", description=" + description + ", content=" + content + ", sort=" + sort + ", outHref="
				+ outHref + ", picUrl=" + picUrl + ", siteType=" + siteType + "}";
	}
}
