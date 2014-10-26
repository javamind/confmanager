package com.ninjamind.confman;

import com.ninjamind.confman.config.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;

/**
 * Lanceur de l'application web
 *
 * @author Guillaume EHRET
 */

@ComponentScan
@EnableAutoConfiguration
public class ConfmanApplication extends SpringBootServletInitializer {

    private final Logger log = LoggerFactory.getLogger(ConfmanApplication.class);

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application
                .profiles(addDefaultProfile())
                .showBanner(false)
                .sources(SpringApplicationBuilder.class);
    }

    public static void main(String[] args) throws Exception {
        SpringApplication.run(ConfmanApplication.class, args);
    }

    /**
     * Set a default profile if it has not been set.
     * <p/>
     * <p>
     * Please use -Dspring.profiles.active=dev
     * </p>
     */
    private String addDefaultProfile() {
        String profile = System.getProperty("spring.profiles.active");
        if (profile != null) {
            log.info("Running with Spring profile(s) : {}", profile);
            return profile;
        }

        log.warn("No Spring profile configured, running with default configuration");
        return Constants.SPRING_PROFILE_PRODUCTION;
    }
}
