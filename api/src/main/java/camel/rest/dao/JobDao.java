package camel.rest.dao;

import camel.rest.domain.ResponseMessage;

import java.util.LinkedHashMap;


public interface JobDao {
    public ResponseMessage findJobs(LinkedHashMap<String, Object> jobData);
    public ResponseMessage applyHistory(LinkedHashMap<String, Object> userData);
    public ResponseMessage applyJob(LinkedHashMap<String, Object> jobData);
    public ResponseMessage createJob(LinkedHashMap<String, Object> jobData);
    public ResponseMessage getCompanyList();

}
