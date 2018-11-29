package com.hks.lucene;

import com.hks.lucene.service.IKanalyzerService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class IKAnalyzerTest {

    @Autowired
    private IKanalyzerService iKanalyzerService;

    @Test
    public void contextLoads() {
        String str = "18T开瑞K50S海马旅行轿宝骏SUV巴博斯CLS级希旺微面WEINISICLSJIAMGXINGSHUN科沃兹三厢嘉年华君威GSAUTHENTICSEVEREST吉利商旅车LOVA吉奥GS50福田SUV战鹰东风小康V07S进口博悦力帆MINIEQUINOX宝来晨风TOLEDOSL级ARRIZO5ARRIZO7IDS概念车北汽威旺307北汽威旺306进口ARRIZO3GENESIS小康风光IX20帝威IX25英菲尼迪G25广丰AX7东风风神海马ZM2海马ZM3V1电动车尊驰旗胜CUV长安睿骋REATON神骐F30皮卡卡曼英菲尼迪G37东风御风检测系列X3进口SAIC30汽车SAM传祺GA3T600红旗奔腾全球鹰GX7全球鹰GX6雪佛兰乐驰斯柯达法比亚吉威劲玛IX45XIANDAIVELOSTER全球鹰GX2全球鹰GX5上汽大通宝马2系运动旅行车名爵TF";

        //String str = "";
        Map<String,Integer> dictMap= iKanalyzerService.getParticipleByStr(str);

        for(Map.Entry<String,Integer> item:  dictMap.entrySet()){
            System.out.println(item.getKey()+":"+item.getValue());
        }
        System.out.println("===========================================");
        dictMap= iKanalyzerService.getParticipleByStr(str,false);
        for(Map.Entry<String,Integer> item:  dictMap.entrySet()){
            System.out.println(item.getKey()+":"+item.getValue());
        }
    }

}
