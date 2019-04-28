package com.sapozhnikov.organizationmanagement.config;

import com.opentable.db.postgres.embedded.EmbeddedPostgres;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.io.IOException;

@Configuration
public class DatasourceConfig {

    @Bean
    public DataSource createEmbeddedPostgres() throws IOException {
        return EmbeddedPostgres.start().getPostgresDatabase();
    }
}