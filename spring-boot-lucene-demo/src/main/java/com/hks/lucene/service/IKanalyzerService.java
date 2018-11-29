package com.hks.lucene.service;

import java.util.Map;

public interface IKanalyzerService {

    /**
     * 传入content获取分词
     * @param content 文本内容
     * @return  返回 map 词，出现次数
     */
    Map<String,Integer> getParticipleByStr(String content);

    /**
     *
     * @param content
     * @param useSmart 自动智能匹配
     * @return
     */
    Map<String,Integer> getParticipleByStr(String content, boolean useSmart);

}