package org.stfc.datasource;

import org.stfc.model.Proposal;

import java.util.ArrayList;
import java.util.List;

public class HardcodedDataSource implements DataSource {

    @Override
    public List<Proposal> getLatestProposals() {
        List<Proposal> proposals = new ArrayList<>();
        for(int i = 0; i < 10; i++) {
            Proposal p = new Proposal();
            p.setRb(Integer.toString(i + 1));
            p.setTitle("Proposal" + (i + 1));
            proposals.add(p);
        }

        return proposals;
    }
}
