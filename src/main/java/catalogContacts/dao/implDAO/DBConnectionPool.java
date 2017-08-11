package catalogContacts.dao.implDAO;

import org.apache.commons.dbcp2.BasicDataSource;

/**
 *
 */
public class DBConnectionPool {
    private static DBConnectionPool instance;
    private BasicDataSource connectionPool;
    private String username = "postgres";
    private String password = "31415926";
    private String dbUrl = "jdbc:postgresql://localhost/catalogcontacs";


    // Singleton
    public static synchronized DBConnectionPool getInstance() {
        if (instance == null) {
            instance = new DBConnectionPool();
        }
        return instance;
    }

    public DBConnectionPool() {

        connectionPool = new BasicDataSource();
        connectionPool.setDriverClassName("org.postgresql.Driver");
        connectionPool.setUrl(dbUrl);
        connectionPool.setUsername(username);
        connectionPool.setPassword(password);
        connectionPool.setInitialSize(3);
    }

    //////
    public BasicDataSource getConnectionPool() {
        return connectionPool;
    }
}
