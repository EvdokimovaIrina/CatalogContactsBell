package catalogContacts.dao.impl;

import catalogContacts.dao.exception.DaoException;
import org.apache.commons.dbcp2.BasicDataSource;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 *
 */
public class DBConnectionPool {
    private static DBConnectionPool instance;
    private BasicDataSource connectionPool;

    // Singleton
    public static synchronized DBConnectionPool getInstance() throws DaoException {

        if (instance == null) {
            synchronized (DBConnectionPool.class) {
                if (instance == null) {
                    instance = new DBConnectionPool();
                }
            }
        }
        return instance;
    }

    public DBConnectionPool() throws DaoException {
        Properties properties = new Properties();
        try {

            InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("config.properties");
            properties.load(inputStream);
            inputStream.close();

        } catch (IOException | NullPointerException e) {
            throw  new DaoException("Ошибка при считывании файла параметров подключения к БД ",e);
        }
        connectionPool = new BasicDataSource();
        connectionPool.setDriverClassName(properties.getProperty("db.driverClassName"));
        connectionPool.setUrl(properties.getProperty("db.dbUrl"));
        connectionPool.setUsername(properties.getProperty("db.username"));
        connectionPool.setPassword(properties.getProperty("db.password"));
        connectionPool.setInitialSize(3);
    }

    //////
    public BasicDataSource getConnectionPool() {
        return connectionPool;
    }
}
