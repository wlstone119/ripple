package com.xdong.ripple.config;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.shardingsphere.api.config.sharding.KeyGeneratorConfiguration;
import org.apache.shardingsphere.api.config.sharding.ShardingRuleConfiguration;
import org.apache.shardingsphere.api.config.sharding.TableRuleConfiguration;
import org.apache.shardingsphere.api.config.sharding.strategy.InlineShardingStrategyConfiguration;
import org.apache.shardingsphere.api.config.sharding.strategy.StandardShardingStrategyConfiguration;
import org.apache.shardingsphere.shardingjdbc.api.ShardingDataSourceFactory;
import org.slf4j.Logger;
import org.springframework.boot.bind.RelaxedPropertyResolver;
import org.springframework.context.ApplicationContextException;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.util.StringUtils;

import com.alibaba.druid.pool.DruidDataSource;
import com.baomidou.mybatisplus.plugins.PaginationInterceptor;
import com.xdong.ripple.commonservice.util.SecurityUtil;

@Configuration
public class DatabaseConfiguration implements EnvironmentAware {

    private static final Logger     LOGGER = org.slf4j.LoggerFactory.getLogger(DatabaseConfiguration.class);

    private Environment             environment;
    private RelaxedPropertyResolver datasourcePropertyResolver;

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
        this.datasourcePropertyResolver = new RelaxedPropertyResolver(environment, "spring.datasource.");
    }

    // @Bean(initMethod = "init", destroyMethod = "close")
    public DataSource getShardingDataSource() throws SQLException {

        ShardingRuleConfiguration shardingRuleConfig = new ShardingRuleConfiguration();

        shardingRuleConfig.getTableRuleConfigs().add(getOrderTableRuleConfiguration());
        shardingRuleConfig.getTableRuleConfigs().add(getOrderItemTableRuleConfiguration());
        shardingRuleConfig.getBindingTableGroups().add("t_order, t_order_item");
        shardingRuleConfig.getBroadcastTables().add("t_config");
        // shardingRuleConfig.setDefaultDatabaseShardingStrategyConfig(
        // new InlineShardingStrategyConfiguration("user_id", "ds${user_id % 2}"));
        // shardingRuleConfig.setDefaultTableShardingStrategyConfig(
        // new StandardShardingStrategyConfiguration("order_id", new ModuloShardingTableAlgorithm()));

        Properties props = new Properties();
        return ShardingDataSourceFactory.createDataSource(createDataSourceMap(), shardingRuleConfig, props);
    }

    private static KeyGeneratorConfiguration getKeyGeneratorConfiguration() {
        KeyGeneratorConfiguration result = new KeyGeneratorConfiguration("", "order_id");
        return result;
    }

    TableRuleConfiguration getOrderTableRuleConfiguration() {
        TableRuleConfiguration result = new TableRuleConfiguration("t_order", "ds0.t_order${0..1}");
        result.setKeyGeneratorConfig(getKeyGeneratorConfiguration());
        return result;
    }

    TableRuleConfiguration getOrderItemTableRuleConfiguration() {
        TableRuleConfiguration result = new TableRuleConfiguration("t_order_item", "ds0.t_order_item${0..1}");
        return result;
    }

    Map<String, DataSource> createDataSourceMap() throws SQLException {
        Map<String, DataSource> result = new HashMap<>();
        result.put("ds0", createDataSource());
        return result;
    }

    @Bean(initMethod = "init", destroyMethod = "close")
    public DataSource createDataSource() throws SQLException {
        if (StringUtils.isEmpty(datasourcePropertyResolver.getProperty("url"))) {

            LOGGER.debug("Your database connection pool configuration is incorrect!"
                         + " Please check your Spring profile, current profiles are:"
                         + Arrays.toString(environment.getActiveProfiles()));

            throw new ApplicationContextException("Database connection pool is not configured correctly");
        }
        DruidDataSource druidDataSource = new DruidDataSource();
        druidDataSource.setUrl(datasourcePropertyResolver.getProperty("url"));
        druidDataSource.setUsername(datasourcePropertyResolver.getProperty("username"));
        try {
            druidDataSource.setPassword(SecurityUtil.decryption(datasourcePropertyResolver.getProperty("password")));
        } catch (Exception e) {
            e.printStackTrace();
        }
        druidDataSource.setInitialSize(Integer.parseInt(datasourcePropertyResolver.getProperty("initialSize")));
        druidDataSource.setMinIdle(Integer.parseInt(datasourcePropertyResolver.getProperty("minIdle")));
        druidDataSource.setMaxActive(Integer.parseInt(datasourcePropertyResolver.getProperty("maxActive")));
        druidDataSource.setMaxWait(Integer.parseInt(datasourcePropertyResolver.getProperty("maxWait")));
        druidDataSource.setTimeBetweenEvictionRunsMillis(Integer.parseInt(datasourcePropertyResolver.getProperty("timeBetweenEvictionRunsMillis")));
        druidDataSource.setMinEvictableIdleTimeMillis(Integer.parseInt(datasourcePropertyResolver.getProperty("minEvictableIdleTimeMillis")));
        druidDataSource.setValidationQuery(datasourcePropertyResolver.getProperty("validationQuery"));
        druidDataSource.setTestWhileIdle(Boolean.parseBoolean(datasourcePropertyResolver.getProperty("testWhileIdle")));
        druidDataSource.setTestOnBorrow(Boolean.parseBoolean(datasourcePropertyResolver.getProperty("testOnBorrow")));
        druidDataSource.setTestOnReturn(Boolean.parseBoolean(datasourcePropertyResolver.getProperty("testOnReturn")));
        druidDataSource.setPoolPreparedStatements(Boolean.parseBoolean(datasourcePropertyResolver.getProperty("poolPreparedStatements")));
        druidDataSource.setMaxOpenPreparedStatements(Integer.parseInt(datasourcePropertyResolver.getProperty("maxOpenPreparedStatements")));
        return druidDataSource;
    }

    @Bean
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
    }
}
