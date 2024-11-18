package com.mycompany.myapp.domain;

import java.util.UUID;

public class AIETestSamples {

    public static AIE getAIESample1() {
        return new AIE()
            .id("id1")
            .name("name1")
            .type("type1")
            .description("description1")
            .createdBy("createdBy1")
            .icon("icon1")
            .version("version1")
            .category("category1")
            .aieMetadata("aieMetadata1")
            .userID("userID1")
            .organizationName("organizationName1")
            .tenantID("tenantID1");
    }

    public static AIE getAIESample2() {
        return new AIE()
            .id("id2")
            .name("name2")
            .type("type2")
            .description("description2")
            .createdBy("createdBy2")
            .icon("icon2")
            .version("version2")
            .category("category2")
            .aieMetadata("aieMetadata2")
            .userID("userID2")
            .organizationName("organizationName2")
            .tenantID("tenantID2");
    }

    public static AIE getAIERandomSampleGenerator() {
        return new AIE()
            .id(UUID.randomUUID().toString())
            .name(UUID.randomUUID().toString())
            .type(UUID.randomUUID().toString())
            .description(UUID.randomUUID().toString())
            .createdBy(UUID.randomUUID().toString())
            .icon(UUID.randomUUID().toString())
            .version(UUID.randomUUID().toString())
            .category(UUID.randomUUID().toString())
            .aieMetadata(UUID.randomUUID().toString())
            .userID(UUID.randomUUID().toString())
            .organizationName(UUID.randomUUID().toString())
            .tenantID(UUID.randomUUID().toString());
    }
}
