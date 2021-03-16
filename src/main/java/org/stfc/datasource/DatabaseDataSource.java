package org.stfc.datasource;

import oracle.jdbc.driver.OracleConnection;
import oracle.jdbc.pool.OracleDataSource;
import org.stfc.model.Proposal;
import uk.stfc.bisapps.config.BISAppProperties;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DatabaseDataSource implements DataSource {
    private OracleConnection connection;

    public DatabaseDataSource() {
        BISAppProperties properties = new BISAppProperties("C:\\FBS\\BISAppSettings\\BISAppSettings.config");

        try {
            OracleDataSource dataSource = new OracleDataSource();
            dataSource.setURL(properties.getProperty("DOCKERWORKSHOP.DBURL"));
            dataSource.setUser(properties.getProperty("DOCKERWORKSHOP.DBUser"));
            dataSource.setPassword(properties.getProperty("DOCKERWORKSHOP.DBPassword"));

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
                    "SELECT * FROM " +
                    "(SELECT * FROM proposal ORDER BY submitted_date DESC) " +
                    "WHERE ROWNUM <= 10"
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
