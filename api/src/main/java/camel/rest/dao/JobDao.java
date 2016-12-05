package camel.rest.dao;

import camel.rest.domain.ResponseMessage;

import java.util.LinkedHashMap;


public interface JobDao {
    public ResponseMessage findJobs(LinkedHashMap<String, Object> jobData);
}
