/**
 *
 */
package com.liaoyb.security.config;

import com.liaoyb.security.properties.MySocialProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author zhailiang
 */
@Configuration
@EnableConfigurationProperties(MySocialProperties.class)
public class SecurityCoreConfig {


}
