package camel.rest.database;

import org.apache.commons.dbcp2.BasicDataSource;

import javax.sql.DataSource;

/**
 * Created by sdthai on 11/19/2016.
 */
public class DatabaseConnection {
    private static DataSource dataSource = null;

    public static DataSource getDataSource() {
        if (dataSource == null) {
            dataSource = setupDataSource("jdbc:mysql://localhost:3306/testdb");
        }

        return dataSource;
    }

    private static DataSource setupDataSource(String connectURI) {
        BasicDataSource ds = new BasicDataSource();
        ds.setDriverClassName("com.mysql.jdbc.Driver");
        ds.setUsername("root");
        ds.setPassword("1234");
        ds.setUrl(connectURI);
        return ds;
    }
}
