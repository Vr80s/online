package com.xczhihui.medical.service;

import java.io.IOException;

import org.apache.solr.client.solrj.SolrServerException;

public interface DoctorSolrService {

    void initDoctorsSolrData() throws IOException, SolrServerException;
}
