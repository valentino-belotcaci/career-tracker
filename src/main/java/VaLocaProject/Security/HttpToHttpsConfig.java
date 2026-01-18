package VaLocaProject.Security;

import org.apache.catalina.connector.Connector;
import org.springframework.boot.tomcat.servlet.TomcatServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HttpToHttpsConfig {

    @Bean
    // A servlet is a java class that handles HTTP requests and repsonses (like in the JWTFilter class) 
    TomcatServletWebServerFactory servletContainer() {
        TomcatServletWebServerFactory tomcat =
                new TomcatServletWebServerFactory();

        tomcat.addAdditionalConnectors(redirectConnector());
        return tomcat;
    }

    // This connector takes HTTP requests from a HTTP connection,
    // marks it as secure and redirect to HTTPS, we could directly 
    // just host the HTTPS server but other servers and search engine expect this behavior
    // and if some client need to use only HTTP connection it still works.
    private Connector redirectConnector() {
        Connector connector =
                new Connector(TomcatServletWebServerFactory.DEFAULT_PROTOCOL);

        // These lines explain to the Tomcat server to listen 
        // to port 8080 with scheme http for requests 
        // and redirect them to port 8443 with https
        connector.setScheme("http");
        connector.setPort(8080);
        connector.setSecure(false);
        connector.setRedirectPort(8443);

        return connector;
    }
}
