package com.cbx.party.config

import javax.sql.DataSource
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.jdbc.DataSourceBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary

@Configuration
class PartyDataSourceConfiguration {
    @Bean
    @Primary
    @ConfigurationProperties(prefix = "party.datasource")
    fun partyDataSource():DataSource{
        return DataSourceBuilder.create().build()
    }

    @Bean(name=["camundaBpmDataSource"])
    @ConfigurationProperties(prefix = "camunda.datasource")
    fun camundaDataSource():DataSource{
        return DataSourceBuilder.create().build()
    }
}
