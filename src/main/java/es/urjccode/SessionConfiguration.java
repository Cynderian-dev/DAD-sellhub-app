package es.urjccode;
import org.springframework.context.annotation.Bean;
import org.springframework.session.hazelcast.config.annotation.web.http.EnableHazelcastHttpSession;
import com.hazelcast.config.Config;


@EnableHazelcastHttpSession
public class SessionConfiguration {
	@Bean
	public Config config() {
		var config = new Config();
		config.getNetworkConfig().getJoin().getMulticastConfig().setEnabled(true);
		return config;
	}

}
