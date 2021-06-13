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
 * 爬虫任务表
 * </p>
 *
 * @author wanglei
 * @since 2019-05-19
 */
@TableName("rp_crawler_task")
public class RpCrawlerTaskDo extends Model<RpCrawlerTaskDo> {

	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	@TableId(value = "id", type = IdType.AUTO)
	private Long id;
	/**
	 * 爬取网站的url主键
	 */
	@TableField("url_id")
	private Long urlId;
	/**
	 * 爬取数据量
	 */
	@TableField("insert_count")
	private Integer insertCount;
	/**
	 * 重复数据量
	 */
	@TableField("repeat_count")
	private Integer repeatCount;
	/**
	 * 启动多线程数
	 */
	@TableField("strat_thread_count")
	private Integer stratThreadCount;
	/**
	 * 任务开始时间
	 */
	@TableField("start_time")
	private Date startTime;
	/**
	 * 任务结束时间
	 */
	@TableField("end_time")
	private Date endTime;
	/**
	 * 任务结束耗时（单位：秒）
	 */
	@TableField("consumer_time")
	private Integer consumerTime;
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

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getUrlId() {
		return urlId;
	}

	public void setUrlId(Long urlId) {
		this.urlId = urlId;
	}

	public Integer getInsertCount() {
		return insertCount;
	}

	public void setInsertCount(Integer insertCount) {
		this.insertCount = insertCount;
	}

	public Integer getRepeatCount() {
		return repeatCount;
	}

	public void setRepeatCount(Integer repeatCount) {
		this.repeatCount = repeatCount;
	}

	public Integer getStratThreadCount() {
		return stratThreadCount;
	}

	public void setStratThreadCount(Integer stratThreadCount) {
		this.stratThreadCount = stratThreadCount;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public Integer getConsumerTime() {
		return consumerTime;
	}

	public void setConsumerTime(Integer consumerTime) {
		this.consumerTime = consumerTime;
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

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

	@Override
	public String toString() {
		return "RpCrawlerTaskDo{" + "id=" + id + ", urlId=" + urlId + ", insertCount=" + insertCount + ", repeatCount="
				+ repeatCount + ", stratThreadCount=" + stratThreadCount + ", startTime=" + startTime + ", endTime="
				+ endTime + ", consumerTime=" + consumerTime + ", cTime=" + cTime + ", cUser=" + cUser + ", mTime="
				+ mTime + ", mUser=" + mUser + "}";
	}
}
