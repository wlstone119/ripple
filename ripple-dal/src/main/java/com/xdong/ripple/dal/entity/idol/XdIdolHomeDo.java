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
 * idol首页试图展示表
 * </p>
 *
 * @author wanglei
 * @since 2019-03-20
 */
@TableName("xd_idol_home")
public class XdIdolHomeDo extends Model<XdIdolHomeDo> {

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
	 * idol主键
	 */
	@TableField("idol_id")
	private Long idolId;
	/**
	 * idol配置首页主键id
	 */
	@TableField("home_id")
	private Long homeId;

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

	public Long getIdolId() {
		return idolId;
	}

	public void setIdolId(Long idolId) {
		this.idolId = idolId;
	}

	public Long getHomeId() {
		return homeId;
	}

	public void setHomeId(Long homeId) {
		this.homeId = homeId;
	}

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

	@Override
	public String toString() {
		return "XdIdolHomeDo{" + "id=" + id + ", cTime=" + cTime + ", cUser=" + cUser + ", mTime=" + mTime + ", mUser="
				+ mUser + ", idolId=" + idolId + ", homeId=" + homeId + "}";
	}
}
