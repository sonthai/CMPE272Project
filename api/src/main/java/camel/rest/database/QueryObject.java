package camel.rest.database;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import javax.sql.DataSource;


public class QueryObject {
    private String table;
    private String operation;
    private String[] queryFields;
    private String whereClause;
    private List<String> values;
    List<Map<String, Object>> result;

    public String getTable() {
        return table;
    }

    public void setTable(String table) {
        this.table = table;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public String getWhereClause() {
        return whereClause;
    }

    public String[] getQueryFields() {
        return queryFields;
    }

    public void setQueryFields(String[] queryFields) {
        this.queryFields = queryFields;
    }

    public void setWhereClause(String whereClause) {
        this.whereClause = whereClause;
    }


    public void setValues(List<String> values) {
        this.values = values;
    }

    public List<Map<String, Object>> getRecords() {
        return result;
    }


    public String getQuery() {
        StringBuilder sb = new StringBuilder();
        sb.append(operation).append(" ");
        if (operation.equalsIgnoreCase("select")) {
            if (queryFields[0].equalsIgnoreCase("*")) {
                sb.append(" * ");
            } else {
                sb.append(StringUtils.join(queryFields, ",")).append(" ");
            }
            sb.append(" FROM ").append(table).append(" ");
            if (whereClause != null && whereClause.length() > 0) {
                sb.append(" WHERE ").append(whereClause);
            }

        } else if (operation.equalsIgnoreCase("insert")) {
            sb.append("INTO ").append(table).append(" ");
            sb.append(values.get(0) + " VALUES " + values.get(1));
        } else if (operation.equalsIgnoreCase("update")) {
            sb.append(table);
            sb.append(" SET ");
            sb.append(values.get(0));
            if (whereClause != null && whereClause.length() > 0) {
                sb.append(" WHERE ").append(whereClause);
            }
        } else if (operation.equalsIgnoreCase("delete")) {

        }


        return sb.toString();
    }

    public void executeQuery() {
        Connection conn = null;
        result = new ArrayList<>();
        try {
            String query = getQuery();
            DatabaseConnection dbConn = new DatabaseConnection();
            DataSource dataSource = dbConn.getDataSource();
            conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(query);

            if (operation.equalsIgnoreCase("select")) {
                ResultSet rs = ps.executeQuery();
                result = this.parse(rs);

                rs.close();
            } else {
                ps.executeUpdate();
            }

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
    }

    public List<Map<String, Object>> parse(ResultSet rs) {
        return null;
    };
}