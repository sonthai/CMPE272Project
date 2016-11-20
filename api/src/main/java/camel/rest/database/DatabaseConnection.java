package camel.rest.database;

import org.apache.commons.dbcp2.BasicDataSource;
//import org.springframework.jdbc.core.JdbcTemplate;


import javax.sql.DataSource;

//@Configuration
//@PropertySource("classpath:database.properties")
public class DatabaseConnection {
    //@Value("${driverClassName:com.mysql.cj.jdbc.Driver}")
    private static String driver = "com.mysql.cj.jdbc.Driver";

    //@Value("${url:jdbc:mysql://localhost:3306/cmpe272}")
    private static String url ="jdbc:mysql://localhost:3306/cmpe272";

    //@Value("${username:root}")
    private static String username = "root";

    //@Value("${password:1234}")
    private static String password = "1234";

    //@Value("${initialPoolSize:25")
    private static int initialPoolSize = 25;

    //@Value("${activeConn: 10}")
    private static int activeConn = 10;

    private static DataSource dataSource = null;

    public DataSource getDataSource() {
        if (dataSource == null) {
            dataSource = createDataSource();
        }

        return dataSource;
    }

    public DataSource  createDataSource() {;
        BasicDataSource ds = new BasicDataSource();
        ds.setValidationQuery("SELECT 1");
        ds.setDriverClassName(driver);
        ds.setUsername(username);
        ds.setPassword(password);
        ds.setUrl(url);
        ds.setInitialSize(initialPoolSize);
        ds.setMaxTotal(activeConn);

        return ds;
    }

    /*public JdbcTemplate getJdbcTemplate() {
        return new JdbcTemplate(getDataSource());
    }*/
}
