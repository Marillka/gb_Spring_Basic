package com.rayumov.configs;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;


//@PropertySource() - означает что мы хотим добавить какие то property файлы.
@Configuration
@PropertySource("secrets.properties")
public class AppConfig {
}
