package com.xdong.ripple.dal.entity.idol;

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
 * idol表
 * </p>
 *
 * @author wanglei
 * @since 2019-03-20
 */
@TableName("xd_idol")
public class XdIdolDo extends Model<XdIdolDo> {

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
     * idol姓名
     */
    @TableField("idol_name")
    private String idolName;
    /**
     * 性别
     */
    private Integer sex;
    /**
     * 出生日期
     */
    private String birthday;
    /**
     * 经纪公司
     */
    private String company;


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

    public String getIdolName() {
        return idolName;
    }

    public void setIdolName(String idolName) {
        this.idolName = idolName;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "XdIdolDo{" +
        "id=" + id +
        ", cTime=" + cTime +
        ", cUser=" + cUser +
        ", mTime=" + mTime +
        ", mUser=" + mUser +
        ", idolName=" + idolName +
        ", sex=" + sex +
        ", birthday=" + birthday +
        ", company=" + company +
        "}";
    }
}
