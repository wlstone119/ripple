package com.xdong.ripple.dal.entity.idol;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;

/**
 * <p>
 * idol首页试图维护表
 * </p>
 *
 * @author wanglei
 * @since 2019-03-20
 */
@TableName("xd_idol_home_view")
public class XdIdolHomeViewDo extends Model<XdIdolHomeViewDo> {

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
	 * 配置描述
	 */
	@TableField("pre_name")
	private String preName;

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

	public String getPreName() {
		return preName;
	}

	public void setPreName(String preName) {
		this.preName = preName;
	}

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

	@Override
	public String toString() {
		return "XdIdolHomeViewDo{" + "id=" + id + ", cTime=" + cTime + ", cUser=" + cUser + ", mTime=" + mTime
				+ ", mUser=" + mUser + ", preName=" + preName + "}";
	}
}
