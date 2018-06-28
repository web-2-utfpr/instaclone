/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

import java.util.HashMap;
import java.util.Map;
import model.bean.Image;
import model.bean.Usuario;

import org.hibernate.SessionFactory;
import org.hibernate.metamodel.Metadata;
import org.hibernate.metamodel.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Environment;
import org.hibernate.engine.transaction.internal.jdbc.JdbcTransactionFactory;
import org.hibernate.cache.redis.SingletonRedisRegionFactory;

public class HibernateUtil {

    private static StandardServiceRegistry registry;
    private static SessionFactory sessionFactory;

    static String host;
    static String username;
    static String password;

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            host = System.getenv("MYSQL_HOST");
            if (host != null) {
                username = System.getenv("MYSQL_USER");
                password = System.getenv("MYSQL_PASSWORD");
            } else {
                host = "jdbc:mysql://192.168.99.100:3306/sql10233623?useSSL=false";
                username = "root";
                password = "root";
            }
            try {
                StandardServiceRegistryBuilder registryBuilder
                        = new StandardServiceRegistryBuilder();

                Map<String, Object> settings = new HashMap<>();
                settings.put(Environment.DRIVER, "com.mysql.cj.jdbc.Driver");
                settings.put(Environment.URL, host);
                settings.put(Environment.USER, username);
                settings.put(Environment.PASS, password);
                settings.put(Environment.HBM2DDL_AUTO, "update");
                settings.put(Environment.SHOW_SQL, true);

                // c3p0 configuration
                settings.put(Environment.C3P0_MIN_SIZE, 5);         //Minimum size of pool
                settings.put(Environment.C3P0_MAX_SIZE, 20);        //Maximum size of pool
                settings.put(Environment.C3P0_ACQUIRE_INCREMENT, 1);//Number of connections acquired at a time when pool is exhausted 
                settings.put(Environment.C3P0_TIMEOUT, 1800);       //Connection idle time
                settings.put(Environment.C3P0_MAX_STATEMENTS, 150); //PreparedStatement cache size

                settings.put(Environment.C3P0_CONFIG_PREFIX + ".initialPoolSize", 5);
                // Secondary Cache
                settings.put(Environment.USE_SECOND_LEVEL_CACHE, true);
                settings.put(Environment.USE_QUERY_CACHE, true);
                settings.put(Environment.CACHE_REGION_FACTORY, SingletonRedisRegionFactory.class.getName());
                settings.put(Environment.CACHE_REGION_PREFIX, "hibernate");

                // optional setting for second level cache statistics
                settings.put(Environment.GENERATE_STATISTICS, "true");
                settings.put(Environment.USE_STRUCTURED_CACHE, "true");

                settings.put(Environment.TRANSACTION_STRATEGY, JdbcTransactionFactory.class.getName());

                // configuration for Redis that used by hibernate
                settings.put(Environment.CACHE_PROVIDER_CONFIG, "hibernate-redis.properties");

                registryBuilder.applySettings(settings);

                registry = registryBuilder.build();
                MetadataSources sources = new MetadataSources(registry);
                sources.addAnnotatedClass(Usuario.class);
                sources.addAnnotatedClass(Image.class);
                Metadata metadata = sources.getMetadataBuilder().build();
                sessionFactory = metadata.getSessionFactoryBuilder().build();
            } catch (Exception e) {
                if (registry != null) {
                    StandardServiceRegistryBuilder.destroy(registry);
                }
                e.printStackTrace();
            }
        }
        return sessionFactory;
    }

    public static void shutdown() {
        if (registry != null) {
            StandardServiceRegistryBuilder.destroy(registry);
        }
    }
}
