package fr.asso.placeholder.streamsample;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties("sink.es")
public class ElasticsearchSinkProperties {
    private String index;
    private String type = "_doc";
}
