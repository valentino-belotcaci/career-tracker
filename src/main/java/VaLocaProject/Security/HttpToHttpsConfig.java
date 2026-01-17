package VaLocaProject.Security;

import org.apache.catalina.connector.Connector;
import org.springframework.boot.tomcat.servlet.TomcatServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HttpToHttpsConfig {

    @Bean
    TomcatServletWebServerFactory servletContainer() {
        TomcatServletWebServerFactory tomcat =
                new TomcatServletWebServerFactory();

        tomcat.addAdditionalConnectors(redirectConnector());
        return tomcat;
    }

    private Connector redirectConnector() {
        Connector connector =
                new Connector(TomcatServletWebServerFactory.DEFAULT_PROTOCOL);

        // Listen on plain HTTP
        connector.setScheme("http");
        connector.setPort(8080);
        connector.setSecure(false);

        // Redirect to the HTTPS port (commonly 8443). Ensure SSL is enabled (server.port=8443 + server.ssl.* in application.properties)
        connector.setRedirectPort(8443);
        return connector;
    }
}
