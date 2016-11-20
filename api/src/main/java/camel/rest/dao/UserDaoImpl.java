package camel.rest.dao;

import camel.rest.database.DatabaseConnection;
import camel.rest.model.User;
import org.springframework.beans.factory.annotation.Autowired;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDaoImpl implements UserDao {
    //@Autowired
    //private DataSource myDataSource;

    @Override
    public User findUser(String userName) {
        /*User user = new User();
        user.setUserName("sdthai");
        user.setEmail("son.ccsf@gmail.com");
        user.setPassword("1234545");*/
        //DatabaseConnection DBconn = new DatabaseConnection();
        DataSource dataSource = DatabaseConnection.getDataSource();

        String sql = "SELECT * FROM testUser";
        Connection conn = null;
        User user = null;

        try {
            conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                user = new User();
                user.setUserName(rs.getString("userName"));
                user.setEmail(rs.getString("Email"));
            }

            rs.close();
            ps.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {}
            }
        }

        return user;
    }

    @Override
    public void register(User user) {

    }

    //public void setDataSource(DataSource dataSource) {
        //this.dataSource = dataSource;
    //}
}
