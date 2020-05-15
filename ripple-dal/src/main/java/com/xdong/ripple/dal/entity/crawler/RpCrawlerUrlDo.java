package com.xdong.ripple.dal.entity.crawler;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 爬虫url列表
 * </p>
 *
 * @author wanglei
 * @since 2019-03-20
 */
@TableName("rp_crawler_url")
public class RpCrawlerUrlDo extends Model<RpCrawlerUrlDo> {

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
	 * 网站名称
	 */
	private String name;
	/**
	 * 网站模块
	 */
	@TableField("module_name")
	private String moduleName;
	/**
	 * 网站域名
	 */
	@TableField("domain_name")
	private String domainName;
	/**
	 * 爬取url
	 */
	@TableField("crawler_url")
	private String crawlerUrl;
	/**
	 * 网站类型
	 */
	private String type;
	/**
	 * 爬虫策略类
	 */
	@TableField("crawler_class")
	private String crawlerClass;
	/**
	 * 是否有效
	 */
	@TableField("is_valid")
	private String isValid;

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

	public String getModuleName() {
		return moduleName;
	}

	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}

	public String getDomainName() {
		return domainName;
	}

	public void setDomainName(String domainName) {
		this.domainName = domainName;
	}

	public String getCrawlerUrl() {
		return crawlerUrl;
	}

	public void setCrawlerUrl(String crawlerUrl) {
		this.crawlerUrl = crawlerUrl;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getCrawlerClass() {
		return crawlerClass;
	}

	public void setCrawlerClass(String crawlerClass) {
		this.crawlerClass = crawlerClass;
	}

	public String getIsValid() {
		return isValid;
	}

	public void setIsValid(String isValid) {
		this.isValid = isValid;
	}

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

	@Override
	public String toString() {
		return "RpCrawlerUrlDo{" + "id=" + id + ", cTime=" + cTime + ", cUser=" + cUser + ", mTime=" + mTime
				+ ", mUser=" + mUser + ", name=" + name + ", moduleName=" + moduleName + ", domainName=" + domainName
				+ ", crawlerUrl=" + crawlerUrl + ", type=" + type + ", crawlerClass=" + crawlerClass + ", isValid="
				+ isValid + "}";
	}
}
