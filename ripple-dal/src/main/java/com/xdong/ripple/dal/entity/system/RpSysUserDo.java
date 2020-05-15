package com.xdong.ripple.dal.entity.system;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;

/**
 * <p>
 * 
 * </p>
 *
 * @author wanglei
 * @since 2019-03-23
 */
@TableName("rp_sys_user")
public class RpSysUserDo extends Model<RpSysUserDo> {

	private static final long serialVersionUID = 1L;

	@TableId(value = "user_id", type = IdType.AUTO)
	private Long userId;
	/**
	 * 用户名
	 */
	private String username;
	private String name;
	/**
	 * 密码
	 */
	private String password;
	@TableField("dept_id")
	private Long deptId;
	/**
	 * 邮箱
	 */
	private String email;
	/**
	 * 手机号
	 */
	private String mobile;
	/**
	 * 状态 0:禁用，1:正常
	 */
	private Integer status;
	/**
	 * 创建用户id
	 */
	@TableField("user_id_create")
	private Long userIdCreate;
	/**
	 * 创建时间
	 */
	@TableField("gmt_create")
	private Date gmtCreate;
	/**
	 * 修改时间
	 */
	@TableField("gmt_modified")
	private Date gmtModified;
	/**
	 * 性别
	 */
	private Long sex;
	/**
	 * 出身日期
	 */
	private Date birth;
	@TableField("pic_id")
	private Long picId;
	/**
	 * 现居住地
	 */
	@TableField("live_address")
	private String liveAddress;
	/**
	 * 爱好
	 */
	private String hobby;
	/**
	 * 省份
	 */
	private String province;
	/**
	 * 所在城市
	 */
	private String city;
	/**
	 * 所在地区
	 */
	private String district;

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Long getDeptId() {
		return deptId;
	}

	public void setDeptId(Long deptId) {
		this.deptId = deptId;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Long getUserIdCreate() {
		return userIdCreate;
	}

	public void setUserIdCreate(Long userIdCreate) {
		this.userIdCreate = userIdCreate;
	}

	public Date getGmtCreate() {
		return gmtCreate;
	}

	public void setGmtCreate(Date gmtCreate) {
		this.gmtCreate = gmtCreate;
	}

	public Date getGmtModified() {
		return gmtModified;
	}

	public void setGmtModified(Date gmtModified) {
		this.gmtModified = gmtModified;
	}

	public Long getSex() {
		return sex;
	}

	public void setSex(Long sex) {
		this.sex = sex;
	}

	public Date getBirth() {
		return birth;
	}

	public void setBirth(Date birth) {
		this.birth = birth;
	}

	public Long getPicId() {
		return picId;
	}

	public void setPicId(Long picId) {
		this.picId = picId;
	}

	public String getLiveAddress() {
		return liveAddress;
	}

	public void setLiveAddress(String liveAddress) {
		this.liveAddress = liveAddress;
	}

	public String getHobby() {
		return hobby;
	}

	public void setHobby(String hobby) {
		this.hobby = hobby;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	@Override
	protected Serializable pkVal() {
		return this.userId;
	}

	@Override
	public String toString() {
		return "RpSysUserDo{" + "userId=" + userId + ", username=" + username + ", name=" + name + ", password="
				+ password + ", deptId=" + deptId + ", email=" + email + ", mobile=" + mobile + ", status=" + status
				+ ", userIdCreate=" + userIdCreate + ", gmtCreate=" + gmtCreate + ", gmtModified=" + gmtModified
				+ ", sex=" + sex + ", birth=" + birth + ", picId=" + picId + ", liveAddress=" + liveAddress + ", hobby="
				+ hobby + ", province=" + province + ", city=" + city + ", district=" + district + "}";
	}
}
