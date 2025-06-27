package com.heks.springbootollama.service;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Description;
import org.springframework.core.NestedExceptionUtils;

import java.util.List;
import java.util.function.Function;

@Configuration
public class SubwayTools {
    private static final Logger logger = LoggerFactory.getLogger(SubwayTools.class);

    public record SubwayStationDetailsRequest(String stationName) {
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public record SubwayStationDetails(String stationName,
                                       Integer totalSubwayLine,
                                       List<String> subwayLineNameList) {
    }

    @Bean
    @Description("获取所在位置的地铁线路")
    public Function<SubwayStationDetailsRequest, SubwayStationDetails> getSubwayStationDetails() {
        logger.info("register getSubwayStationDetails");
        return request -> {
            try {
                logger.info("req :{}",request.toString());
                return findSubwayStationDetails(request.stationName());
            } catch (Exception e) {
                logger.warn("Booking details: {}", NestedExceptionUtils.getMostSpecificCause(e).getMessage());
                return new SubwayStationDetails(request.stationName(), 0, null);
            }
        };
    }

    /**
     * 获取某个地点的地铁线路
     *
     * @param stationName stationName
     */
    private SubwayStationDetails findSubwayStationDetails(String stationName) {
        //mock数据
        if (stationName.contains("天府三街")) {
            return new SubwayStationDetails(stationName, 1, List.of("地铁1号线"));
        } else if (stationName.contains("三岔")) {
            return new SubwayStationDetails(stationName, 2, List.of("地铁18号线", "地铁19号线"));
        } else if (stationName.contains("西博城")) {
            return new SubwayStationDetails(stationName, 3, List.of("地铁1号线", "地铁6号线", "地铁18号线"));
        } else if (stationName.contains("海洋公园")) {
            return new SubwayStationDetails(stationName, 3, List.of("地铁1号线", "地铁18号线", "地铁16号线"));
        } else {
            return null;
        }
    }
}
