package info.developerblog.spring.cloud.marathon.discovery.ribbon;

import com.netflix.client.config.IClientConfig;
import com.netflix.loadbalancer.IPing;
import com.netflix.loadbalancer.Server;
import com.netflix.loadbalancer.ServerList;
import com.netflix.loadbalancer.ServerListFilter;
import info.developerblog.spring.cloud.marathon.discovery.MarathonDiscoveryProperties;
import mesosphere.marathon.client.Marathon;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by aleksandr on 07.07.16.
 */
@Configuration
public class MarathonRibbonClientConfiguration {
    @Autowired
    private Marathon client;

    public MarathonRibbonClientConfiguration() {
    }

    @Bean
    @ConditionalOnMissingBean
    public ServerList<?> ribbonServerList(IClientConfig config, MarathonDiscoveryProperties properties) {
        MarathonServerList serverList = new MarathonServerList(client, properties);
        serverList.initWithNiwsConfig(config);
        return serverList;
    }

    @Bean
    public ServerListFilter<Server> ribbonServerListFilter() {
        return new HealthServiceServerListFilter();
    }

    @Bean
    @ConditionalOnMissingBean
    public IPing ribbonPing() {
        return new MarathonPing();
    }
}
