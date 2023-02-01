package by.nalivaiko.config;

import by.nalivaiko.entity.*;
import lombok.RequiredArgsConstructor;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.AvailableSettings;
import org.hibernate.service.ServiceRegistry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;


@Configuration
@RequiredArgsConstructor
public class SessionConfig {
    private final HibernateConfig config;

    @Bean
    public SessionFactory getSessionFactory() {
        try {
            org.hibernate.cfg.Configuration configuration = new org.hibernate.cfg.Configuration();
            Properties settings = getProperties();
            configuration.setProperties(settings);
            configuration.addAnnotatedClass(Order.class);
            ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                    .applySettings(configuration.getProperties()).build();
            return configuration.buildSessionFactory(serviceRegistry);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    public Properties getProperties() {
        Properties settings = new Properties();
        settings.put(AvailableSettings.DRIVER, config.getDriver());
        settings.put(AvailableSettings.URL, config.getUrl());
        settings.put(AvailableSettings.USER, config.getUser());
        settings.put(AvailableSettings.PASS, config.getPassword());
        settings.put(AvailableSettings.DIALECT, config.getDialect());
        settings.put(AvailableSettings.SHOW_SQL, "true");
        settings.put(AvailableSettings.FORMAT_SQL, "true");
        settings.put(AvailableSettings.CURRENT_SESSION_CONTEXT_CLASS, "thread");
        settings.put(AvailableSettings.HBM2DDL_AUTO, "update");
        return settings;
    }
}
