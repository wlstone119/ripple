package com.xdong.ripple.mvc;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.xdong.ripple.common.vo.XdIdolHomeViewDetailVo;
import com.xdong.ripple.dal.entity.idol.XdIdolHomeViewDetailDo;
import com.xdong.ripple.dal.entity.system.RpSysDictDo;
import com.xdong.ripple.spi.idol.IXdIdolHomeViewDetailService;
import com.xdong.ripple.spi.system.IRpSysDictService;

@Controller
@RequestMapping("/idol")
public class IdolController extends BaseController {

    private static final String          prefix           = "idol";

    private static final String          needVersion2Type = "home_view_need_version2";

    private static final String          needType         = "siteUrl";

    @Autowired
    private IXdIdolHomeViewDetailService xdIdolHomeViewDetailServiceImpl;

    @Autowired
    private IRpSysDictService            rpSysDictServiceImpl;

    @RequestMapping(value = "/home", method = { RequestMethod.GET, RequestMethod.POST })
    @ResponseBody
    public ModelAndView pageInit(String enctryIdolStr) {
        ModelAndView mav = new ModelAndView();

        if (StringUtils.isNotBlank(enctryIdolStr)) {
            Map<String, List<XdIdolHomeViewDetailDo>> modelMap = new HashMap<String, List<XdIdolHomeViewDetailDo>>();
            Map<String, List<XdIdolHomeViewDetailVo>> version2Map = new HashMap<String, List<XdIdolHomeViewDetailVo>>();

            Long idolId = Long.parseLong(enctryIdolStr);

            List<XdIdolHomeViewDetailDo> homeViewList = xdIdolHomeViewDetailServiceImpl.getHomeViewDetailByIdolId(idolId);
            if (CollectionUtils.isNotEmpty(homeViewList)) {
                for (XdIdolHomeViewDetailDo viewDetailDo : homeViewList) {
                    if (modelMap.containsKey(viewDetailDo.getIdolHomeType())) {
                        modelMap.get(viewDetailDo.getIdolHomeType()).add(viewDetailDo);
                    } else {
                        List<XdIdolHomeViewDetailDo> detailList = new ArrayList<XdIdolHomeViewDetailDo>();
                        detailList.add(viewDetailDo);
                        modelMap.put(viewDetailDo.getIdolHomeType(), detailList);
                    }
                }
            }

            for (Map.Entry<String, List<XdIdolHomeViewDetailDo>> entryKeyMap : modelMap.entrySet()) {
                if (CollectionUtils.isNotEmpty(entryKeyMap.getValue())) {
                    sortList(entryKeyMap.getValue());
                }
            }

            // 需要按照类型进行排列的试图模块
            for (Map.Entry<String, List<XdIdolHomeViewDetailDo>> entryKeyMap : modelMap.entrySet()) {
                if (needType.equals(entryKeyMap.getKey())) {
                    List<XdIdolHomeViewDetailDo> resultList = entryKeyMap.getValue();
                    Map<String, List<XdIdolHomeViewDetailDo>> detailMap = new HashMap<>();
                    for (XdIdolHomeViewDetailDo detailDo : resultList) {
                        if (detailMap.containsKey(detailDo.getSiteType())) {
                            detailMap.get(detailDo.getSiteType()).add(detailDo);
                        } else {
                            List<XdIdolHomeViewDetailDo> list = new ArrayList<>();
                            list.add(detailDo);
                            detailMap.put(detailDo.getSiteType(), list);
                        }
                    }

                    version2Map.put(entryKeyMap.getKey(), convertToView2(detailMap));
                }
            }

            // 需要每行3个排列的试图处理
            Set<String> dicts = getNeedVersion2Type();
            if (CollectionUtils.isNotEmpty(dicts)) {
                for (Map.Entry<String, List<XdIdolHomeViewDetailDo>> entryKeyMap : modelMap.entrySet()) {
                    if (dicts.contains(entryKeyMap.getKey())) {
                        List<XdIdolHomeViewDetailDo> siteList = entryKeyMap.getValue();
                        if (CollectionUtils.isNotEmpty(siteList)) {
                            Map<String, List<XdIdolHomeViewDetailDo>> verson2Map = handlerVersion2View(siteList);
                            version2Map.put("version2" + entryKeyMap.getKey(), convertToView(verson2Map));
                        }
                    }
                }
            }

            mav.setViewName(getUrl(prefix, "idol_home"));
            mav.addAllObjects(modelMap);
            mav.addAllObjects(version2Map);
        }

        return mav;
    }

    /**
     * 将需要处理的试图每行3个排列
     * 
     * @param viewList
     * @return
     */
    private Map<String, List<XdIdolHomeViewDetailDo>> handlerVersion2View(List<XdIdolHomeViewDetailDo> viewList) {
        Map<String, List<XdIdolHomeViewDetailDo>> verson2Map = new HashMap<String, List<XdIdolHomeViewDetailDo>>();

        // 第几排
        int row = 1;
        for (int i = 1; i < viewList.size() + 1; i++) {
            if (verson2Map.containsKey(String.valueOf(row))) {
                verson2Map.get(String.valueOf(row)).add(viewList.get(i - 1));
            } else {
                List<XdIdolHomeViewDetailDo> list = new ArrayList<XdIdolHomeViewDetailDo>();
                list.add(viewList.get(i - 1));
                verson2Map.put(String.valueOf(row), list);
            }

            // 上一排最后一条记录开始组装下一排
            if (i == row * 3) {
                row++;
            }
        }
        return verson2Map;
    }

    /**
     * 获取需要version2版本试图类型
     * 
     * @return
     */
    private Set<String> getNeedVersion2Type() {
        Set<String> types = new HashSet<String>();
        types.add("siteUrl");
        types.add("articles");
        List<RpSysDictDo> dictList = rpSysDictServiceImpl.listByType(needVersion2Type);
        if (CollectionUtils.isNotEmpty(dictList)) {
            for (RpSysDictDo dictDo : dictList) {
                types.add(dictDo.getValue());
            }
        }
        return types;
    }

    private List<XdIdolHomeViewDetailVo> convertToView(Map<String, List<XdIdolHomeViewDetailDo>> viewMap) {
        if (viewMap != null) {
            List<XdIdolHomeViewDetailVo> resutList = new ArrayList<XdIdolHomeViewDetailVo>();
            for (Map.Entry<String, List<XdIdolHomeViewDetailDo>> entry : viewMap.entrySet()) {
                XdIdolHomeViewDetailVo viewVo = new XdIdolHomeViewDetailVo();
                viewVo.setRowIndex(entry.getKey());
                viewVo.setViewList(entry.getValue());

                resutList.add(viewVo);
            }

            return resutList;
        }
        return null;
    }

    private List<XdIdolHomeViewDetailVo> convertToView2(Map<String, List<XdIdolHomeViewDetailDo>> viewMap) {
        if (viewMap != null) {
            List<XdIdolHomeViewDetailVo> resutList = new ArrayList<XdIdolHomeViewDetailVo>();
            for (Map.Entry<String, List<XdIdolHomeViewDetailDo>> entry : viewMap.entrySet()) {
                XdIdolHomeViewDetailVo viewVo = new XdIdolHomeViewDetailVo();
                viewVo.setSiteType(entry.getKey());
                viewVo.setViewList(sortList(entry.getValue()));

                resutList.add(viewVo);
            }

            return resutList;
        }
        return null;
    }

    private List<XdIdolHomeViewDetailDo> sortList(List<XdIdolHomeViewDetailDo> list) {
        Collections.sort(list, new Comparator<XdIdolHomeViewDetailDo>() {

            @Override
            public int compare(XdIdolHomeViewDetailDo o1, XdIdolHomeViewDetailDo o2) {
                // 按排序号排序
                return o1.getSort() < (o2.getSort()) ? -1 : 1;
            }
        });

        return list;
    }
}
