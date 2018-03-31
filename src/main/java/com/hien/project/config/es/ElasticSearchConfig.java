package com.hien.project.config.es;


import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.InetAddress;
import java.net.UnknownHostException;


@Configuration
public class ElasticSearchConfig {
    // Host, port, clustername
    @Value("${elasticsearch.host}")
    private String host;

    @Value("${elasticsearch.port}")
    private int port;

    @Value("${elasticsearch.cluster.name}")
    private String clusterName;

    // TODO
//    @Bean
//    public TransportClient esClient() throws UnknownHostException {
//        Settings settings = Settings.builder().put("cluster.name", this.clusterName).put("client.transport.sniff", true).build();
//
//        TransportAddress master = new TransportAddress(InetAddress.getByName(host), port);
//        TransportClient client = new PreBuiltTransportClient(settings).addTransportAddress(master);
//        return client;
//    }
}