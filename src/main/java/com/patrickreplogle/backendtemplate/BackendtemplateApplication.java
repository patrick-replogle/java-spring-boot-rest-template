package com.patrickreplogle.backendtemplate;

import com.patrickreplogle.backendtemplate.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class BackendtemplateApplication {

    @Autowired
    private static Environment env;

    private static boolean stop = false;

    private static void checkEnvironmentVariable(String envvar) {
        if (System.getenv(envvar) == null) {
            stop = true;
        }
    }

    public static void main(String[] args) {
        // Check to see if the environment variables exists. If they do not, stop execution of application.
        checkEnvironmentVariable(Constants.OAUTHCLIENTID);
        checkEnvironmentVariable(Constants.OAUTHCLIENTSECRET);

        if (!stop) {
            SpringApplication.run(BackendtemplateApplication.class, args);
        }
    }
}
