package org.stfc;

import org.stfc.datasource.DataSource;
import org.stfc.datasource.DatabaseDataSource;
import org.stfc.datasource.HardcodedDataSource;
import org.stfc.model.Proposal;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.Map;

@Path("latest-proposals")
public class Controller {
    private final DataSource dataSource;

    public Controller() {
        Map<String,String> envVars = System.getenv();
        if (envVars.containsKey("DATASOURCE") && envVars.get("DATASOURCE").equals("DB")) {
            dataSource = new DatabaseDataSource();
        } else {
            dataSource = new HardcodedDataSource();
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Proposal> getLatestProposals() {
        return dataSource.getLatestProposals();
    }
}
