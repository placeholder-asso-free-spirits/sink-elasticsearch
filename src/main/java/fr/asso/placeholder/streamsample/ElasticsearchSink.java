package fr.asso.placeholder.streamsample;

import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Processor;
import org.springframework.stereotype.Service;
import org.springframework.cloud.stream.messaging.Sink;

import java.io.IOException;

@SpringBootApplication
@EnableBinding(Processor.class)
public class ElasticsearchSink {

    private static Logger LOGGER = LoggerFactory.getLogger(ElasticsearchSink.class.getName());

    private RestHighLevelClient restHighLevelClient;

    private ElasticsearchSinkProperties elasticsearchSinkProperties;

    public static void main(String[] args) {
        SpringApplication.run(ElasticsearchSink.class, args);
    }

    @Autowired
    public ElasticsearchSink(RestHighLevelClient restHighLevelClient, ElasticsearchSinkProperties elasticsearchSinkProperties) {
        this.restHighLevelClient = restHighLevelClient;
        this.elasticsearchSinkProperties = elasticsearchSinkProperties;
    }

    @StreamListener(Sink.INPUT)
    public void process(String message) throws IOException {
        LOGGER.info("Handling data: {}", message);
        IndexRequest indexRequest = new IndexRequest()
                .index(elasticsearchSinkProperties.getIndex())
                .type(elasticsearchSinkProperties.getType())
                .source(message, XContentType.JSON);
        restHighLevelClient.index(indexRequest, RequestOptions.DEFAULT);
    }
}
