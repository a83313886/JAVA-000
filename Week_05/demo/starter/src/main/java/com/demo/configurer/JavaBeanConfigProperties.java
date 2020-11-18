package com.demo.configurer;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties("demo.javabean")
public class JavaBeanConfigProperties {
    /**
     * 学生的名称
     */
    private String name = "student101";
}

