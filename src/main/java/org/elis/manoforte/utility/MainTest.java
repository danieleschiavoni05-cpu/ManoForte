package org.elis.manoforte.utility;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.util.HashMap;
import java.util.Map;

public class MainTest {

    public static void main(String[] args) {
        Map<String,String> properties = new HashMap<>();
        properties.put("jakarta.persistence.jdbc.driver", "com.mysql.cj.jdbc.Driver");
        properties.put("jakarta.persistence.jdbc.url", "jdbc:mysql://localhost:3306/ManoForte_jpa");
        properties.put("jakarta.persistence.jdbc.user", "root");
        properties.put("jakarta.persistence.jdbc.password", "root");
        properties.put("jakarta.persistence.schema-generation.database.action", "drop-and-create");
        properties.put("hibernate.show_sql", "true");
        properties.put("hibernate.format_sql", "true");
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("default",properties);

    }
}
