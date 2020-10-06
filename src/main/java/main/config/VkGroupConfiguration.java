package main.config;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * Конфигурация файла vk_group.properties
 */
@Data
@Configuration
@RequiredArgsConstructor
@PropertySource("/vk_group.properties")
public class VkGroupConfiguration {

    @Value("${accessToken}")
    private String accessToken;
    @Value("${groupId}")
    private int groupId;
}

