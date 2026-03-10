package org.elis.manoforte.utility;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import javax.sql.DataSource;
import java.util.Properties;

public class DataSourceConfig {
    private static final DataSource dataSource;

    static{
        Properties properties = new Properties();
        // DB connection
        properties.setProperty("dataSourceClassName", "com.mysql.cj.jdbc.MysqlDataSource");
        properties.setProperty("dataSource.user","root");
        properties.setProperty("dataSource.password",System.getenv("db_password"));
        properties.setProperty("dataSource.databaseName","progetto_java_web");
        properties.setProperty("dataSource.serverName","localhost");
        // www.baeldung.com/hikaricp
        properties.setProperty("maximumPoolSize","10");
        properties.setProperty("minimumIdle", "5");
        properties.setProperty("idleTimeout","300000");
        properties.setProperty("connectionTimeout","20000");

        HikariConfig config = new HikariConfig(properties);
        dataSource = new HikariDataSource(config);

    }

    public static DataSource getDataSource(){
        return dataSource;
    }
}
