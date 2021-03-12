package org.stfc.datasource;

import oracle.jdbc.driver.OracleConnection;
import oracle.jdbc.pool.OracleDataSource;
import org.stfc.model.Proposal;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DatabaseDataSource implements DataSource {
    private OracleConnection connection;

    public DatabaseDataSource() {
        try {
            OracleDataSource dataSource = new OracleDataSource();
            dataSource.setURL("jdbc:oracle:thin:@//localhost:1521/xepdb");
            dataSource.setUser("iops_v4");
            dataSource.setPassword("pa55w0rdTolocalDB");

            connection = (OracleConnection) dataSource.getConnection();
        } catch (SQLException ex) {
            System.out.println("Failed to connect to the DB: " + ex);
        }
    }

    @Override
    public List<Proposal> getLatestProposals() {
        List<Proposal> proposals = new ArrayList<>();

        try {
            Statement stm = connection.createStatement();
            ResultSet results = stm.executeQuery("" +
                    "SELECT * " +
                    "FROM proposal " +
                    "ORDER BY submitted_date DESC " +
                    "FETCH FIRST 10 ROWS ONLY"
            );

            while (results.next()) {
                Proposal p = new Proposal();
                p.setRb(results.getString("reference_number"));
                p.setTitle(results.getString("name"));
                proposals.add(p);
            }
        } catch (SQLException ex) {
            System.out.println("Failed to fetch proposals from the DB: " + ex);
        }

        return proposals;
    }
}
