package com.xdong.ripple.dal.entity.wx;

import com.baomidou.mybatisplus.enums.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 微信小程序banner表
 * </p>
 *
 * @author wanglei
 * @since 2021-06-14
 */
@TableName("wx_banner")
public class WxBannerDo extends Model<WxBannerDo> {

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
     * banner名
     */
    @TableField("banner_name")
    private String bannerName;
    /**
     * banner描述
     */
    @TableField("banner_description")
    private String bannerDescription;
    /**
     * banner图片
     */
    @TableField("banner_image")
    private String bannerImage;
    /**
     * banner链接
     */
    @TableField("banner_link")
    private String bannerLink;


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

    public String getBannerName() {
        return bannerName;
    }

    public void setBannerName(String bannerName) {
        this.bannerName = bannerName;
    }

    public String getBannerDescription() {
        return bannerDescription;
    }

    public void setBannerDescription(String bannerDescription) {
        this.bannerDescription = bannerDescription;
    }

    public String getBannerImage() {
        return bannerImage;
    }

    public void setBannerImage(String bannerImage) {
        this.bannerImage = bannerImage;
    }

    public String getBannerLink() {
        return bannerLink;
    }

    public void setBannerLink(String bannerLink) {
        this.bannerLink = bannerLink;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "WxBannerDo{" +
        ", id=" + id +
        ", cTime=" + cTime +
        ", cUser=" + cUser +
        ", mTime=" + mTime +
        ", mUser=" + mUser +
        ", bannerName=" + bannerName +
        ", bannerDescription=" + bannerDescription +
        ", bannerImage=" + bannerImage +
        ", bannerLink=" + bannerLink +
        "}";
    }
}
