package catalogContacts.dao.impl;

import catalogContacts.dao.exception.DaoException;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;


/**
 *
 */
public class DBConnectionPool {
    private volatile static DBConnectionPool instance;
    private DataSource connectionPool;

    // Singleton
    public static DBConnectionPool getInstance() throws DaoException {

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
        InitialContext initContext = null;
        try {
            initContext = new InitialContext();
            connectionPool = (DataSource) initContext.lookup("java:comp/env/jdbc/catalogcontacs");
        } catch (NamingException e) {
            throw new DaoException("Ошибка подключения к БД. "+e);
        }
    }

    //////
    public DataSource getConnectionPool() {
        return connectionPool;
    }
}
