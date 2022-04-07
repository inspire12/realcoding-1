package ac.cnu.realcoding.configurations;

import lombok.extern.slf4j.Slf4j;
import org.h2.tools.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;

import java.sql.SQLException;

@Slf4j
@Configuration
public class H2ConsoleConfiguration {
    private Server webServer;

    @Value("${h2-console.port}")
    Integer h2ConsolePort;

    @EventListener(ContextRefreshedEvent.class)
    public void start() throws SQLException {
        log.info("starting h2 console at port {}", h2ConsolePort);
        this.webServer = Server.createWebServer("-webPort", h2ConsolePort.toString(), "-webAllowOthers");
        this.webServer.start();
    }


    @EventListener(ContextClosedEvent.class)
    public void stop() {
        log.info("stopping h2 console at port {}", h2ConsolePort);
        this.webServer.stop();
    }
}
