package com.jumbo.store.geo.config;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.MongoTemplate;

import com.mongodb.client.MongoClients;
import de.flapdoodle.embed.mongo.MongodExecutable;
import de.flapdoodle.embed.mongo.MongodStarter;
import de.flapdoodle.embed.mongo.config.MongodConfig;
import de.flapdoodle.embed.mongo.config.Net;
import de.flapdoodle.embed.mongo.distribution.Version;
import de.flapdoodle.embed.process.runtime.Network;

@TestConfiguration
public class EmbeddedMongoConfig {

    @Bean
    public MongodExecutable embeddedMongoServer() throws Exception {
        MongodStarter starter = MongodStarter.getDefaultInstance();
        int port = 27017;
        MongodConfig mongodConfig = MongodConfig.builder()
                .version(Version.Main.PRODUCTION)
                .net(new Net(port, Network.localhostIsIPv6()))
                .build();
        return starter.prepare(mongodConfig);
    }

    @Bean
    public MongoTemplate mongoTemplate(MongodExecutable mongodExecutable) throws Exception {
        mongodExecutable.start();
        return new MongoTemplate(MongoClients.create("mongodb://localhost:27017"), "testdb");
    }
}