package com.ys.mgr.config;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;
import org.yaml.snakeyaml.Yaml;

import java.io.FileInputStream;
import java.util.List;
import java.util.Map;

/**
 *
 * Date: 2017/12/28 16:54
 */
@Slf4j
@Component
@Data
public class MyDynamicPropertyUtil {
    @Value("${custom.props.dynamicPath}")
    private String dynamicPath;

    public Map<String, Object> getProperties() {
        try {
            Yaml yaml = new Yaml();
            Map<String, Object> map = yaml.loadAs(new FileInputStream(ResourceUtils.getFile(dynamicPath)), Map.class);
            log.debug("dynamic properties : {}", map);
            return map;
        } catch (Exception e) {
            log.error("load dynamic properties error", e);
            return null;
        }
    }

    public List<String> getSearchKey() {
        try {
            return (List<String>) getProperties().get("searchKey");
        } catch (Exception e) {
            log.error("load searchKey error", e);
            return null; // 默认值
        }
    }

    public String getSolrServer() {
        try {
            return (String) getProperties().get("solrName");
        } catch (Exception e) {
            log.error("load solrName error", e);
            return null; // 默认值
        }
    }

    public Long getStatisticsAdvertisementScoreDay(){
        try {
            Integer scoreDay = (Integer) getProperties().get("statisticsAdvertisementScoreDay");
            return Long.valueOf(scoreDay);
        } catch (Exception e) {
            log.error("load statisticsAdvertisementScoreDay error", e);
            return null; // 默认值
        }
    }

    public String getRestapiAmap() {
        try {
            return (String) getProperties().get("restapiAmap");
        } catch (Exception e) {
            log.error("load restapiAmap error", e);
            return null; // 默认值
        }
    }

    public String getBaiduMapAPI() {
        try {
            return (String) getProperties().get("baiduMapAPI");
        } catch (Exception e) {
            log.error("load restapiAmap error", e);
            return null; // 默认值
        }
    }

}
