package com.example.test;

import com.mongodb.client.MongoClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class TestController {

    @Resource
    private MongoTemplate mongoTemplate;
    @Autowired
    private MongoClient mongoClient;

    @RequestMapping("/test")
    public long getCollectionCount() {
//        return mongoTemplate.count(Query.query(Criteria.where("isDeleted").is(true)), "order");
        return mongoTemplate.count(new Query(), "startup_log");
    }
}
