package com.example.test;

import com.mongodb.Block;
import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.connection.ConnectionPoolSettings;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory;

import java.util.concurrent.TimeUnit;

@SpringBootApplication
@Configuration
public class TryConnectMongodbApplication {

    @Value("${spring.data.mongodb.uri}")
    String uri;
    @Value("${spring.data.mongodb.database}")
    String database;

    public static void main(String[] args) {
        SpringApplication.run(TryConnectMongodbApplication.class, args);
    }

    /*
        自行实例化 MongoClient 实现客户端连接配置，或者在 application.yml 中指定连接参数。
        自行实例化可以指定更多的配置参数。
        2022-4-26: 本地监控 db.serverStatus().connections 查到的 current 数，不止这里产生的连接数，接近 2 倍。
     */
    @Bean
    public MongoClient mongoClient() {
//        return MongoClients.create(uri);
        MongoClientSettings settings = MongoClientSettings.builder()
                .applyConnectionString(new ConnectionString(uri))
                .applyToConnectionPoolSettings(builder -> builder
                        .maxSize(10)
                        .minSize(2)
                        .maxConnectionIdleTime(90, TimeUnit.SECONDS)
                        .maintenanceFrequency(2, TimeUnit.MINUTES))
                .build();
        return MongoClients.create(settings);
    }

    @Bean
    public MongoDatabaseFactory mongoDatabaseFactory() {
        return new SimpleMongoClientDatabaseFactory(mongoClient(), database);
    }
}
