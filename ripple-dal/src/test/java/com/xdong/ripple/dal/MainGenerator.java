package com.xdong.ripple.dal;

/**
 * 类Generator.java的实现描述：mp代码生成器
 * 
 * @author wanglei 2018年4月19日 下午3:25:27
 */
public class MainGenerator {

    /**
     * <p>
     * MySQL 生成演示
     * </p>
     */
    public static void main(String[] args) {
        // 作者
        String author = "wanglei";
        // 文件目录
        final String dir = "idol";
        // 本地项目路径
        String project_url = "/usr/local/workspace/xdong/ripple";
        // 前缀
        String tablePrefix = "";
        // 表名
        //String[] table_names = new String[] { "rp_crawler_url", "rp_crawler_songs", "rp_crawler_songs_sheet" };
        String[] table_names = new String[] { "xd_idol", "xd_idol_home", "xd_idol_home_view","xd_idol_home_view_detail" };
        
        boolean isNeedController = false;

        for (String table_name : table_names) {
            Generator.doGenerator(author, dir, project_url, tablePrefix, table_name, isNeedController);
        }

    }

}
