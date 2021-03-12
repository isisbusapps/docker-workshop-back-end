package org.stfc.datasource;

import org.stfc.model.Proposal;

import java.util.List;

public interface DataSource {
    List<Proposal> getLatestProposals();
}
