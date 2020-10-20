package cn.cxnxs.letuschat.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

/**
 * <p>websocket配置类</p>
 *
 * @author mengjinyuan
 * @date 2020-10-14 23:24
 **/
@Slf4j
@Configuration
public class WebSocketConfig  {

    @Bean
    public ServerEndpointExporter serverEndpointExporter() {
        log.info("------注册websocket------");
        return new ServerEndpointExporter();
    }
}
