package com.xdong.ripple.common.vo;

import java.util.List;

import com.xdong.ripple.dal.entity.idol.XdIdolHomeViewDetailDo;

/**
 * 类XdIdolHomeViewDetailVo.java的实现描述：verson2 siteUrl类型排版试图对象
 * 
 * @author wanglei 2018年9月26日 下午2:38:50
 */
public class XdIdolHomeViewDetailVo {

    private String                       rowIndex;

    private String                       siteType;

    private List<XdIdolHomeViewDetailDo> viewList;

    public String getRowIndex() {
        return rowIndex;
    }

    public void setRowIndex(String rowIndex) {
        this.rowIndex = rowIndex;
    }

    public String getSiteType() {
        return siteType;
    }

    public void setSiteType(String siteType) {
        this.siteType = siteType;
    }

    public List<XdIdolHomeViewDetailDo> getViewList() {
        return viewList;
    }

    public void setViewList(List<XdIdolHomeViewDetailDo> viewList) {
        this.viewList = viewList;
    }
}
