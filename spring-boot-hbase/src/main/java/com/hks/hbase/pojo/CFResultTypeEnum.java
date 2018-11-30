package com.hks.hbase.pojo;

/**
 * 推荐算法结果类型枚举
 */
public enum CFResultTypeEnum {
    /**
     * 资讯聚合推荐（包括新闻、图集、视频、小视频）
     */
    //INFO("cf_result_info"),
    /**
     * 文章类推荐
     */
    //ARTICLE("cf_result_article"),
    /**
     * 图集类推荐
     */
    //ALBUM("cf_result_album"),
    /**
     * 视频类推荐（易车视频、社区视频）
     */
    //VIDEO("cf_result_video"),

    /**
     * 自媒体新闻
     */
    NEWS_MEDIA("cf_result_news_media"),
    /**
     * 易车新闻
     */
    NEWS_YICHE("cf_result_news_yiche"),
    /**
     * 自媒体图集
     */
    ALBUM_MEDIA("cf_result_album_media"),
    /**
     * 自媒体视频（社区视频）
     */
    VIDEO_MEDIA("cf_result_video_media"),
    /**
     * 易车视频
     */
    VIDEO_YICHE("cf_result_video_yiche"),
    /**
     * 小视频推荐
     */
    SMALLVIDEO("cf_result_small_video"),

    /**
     * 动态
     */
    FEED("cf_result_feed"),

    /**
     * 直播
     */
    LIVE("cf_result_live"),

    /**
     * 口碑
     */
    KOUBEI("cf_result_koubei"),

    /**
     * 帖子
     */
    POSTS("cf_result_posts"),

    /**
     * 车型图集
     */
    ALBUM_CAR("cf_result_album_car"),

    /**
     * 用户推荐
     */
    USER("cf_result_user"),
    /**
     * 车型推荐
     */
    CARTYPE("cf_result_car_type");


    /**
     * HBase表名
     */
    private String name;

    CFResultTypeEnum(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }
}
