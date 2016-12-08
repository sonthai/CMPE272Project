package camel.rest.services;


import camel.rest.dao.JobDaoImpl;
import camel.rest.domain.ResponseMessage;
import org.apache.camel.Exchange;

import java.util.LinkedHashMap;

public class JobServices {
    JobDaoImpl jobDao;

    public void findJobs(Exchange exchange) {
        jobDao = new JobDaoImpl();
        LinkedHashMap<String, Object> jobData = (LinkedHashMap) exchange.getIn().getBody();

        ResponseMessage msg = jobDao.findJobs(jobData);
        exchange.getIn().setBody(msg);
        exchange.getIn().setHeader(Exchange.CONTENT_TYPE, "application/json");
        exchange.getIn().setHeader(Exchange.HTTP_RESPONSE_CODE, "200");
    }

    public void applyJob(Exchange exchange) {
        jobDao = new JobDaoImpl();
        LinkedHashMap<String, Object> jobData = (LinkedHashMap) exchange.getIn().getBody();

        ResponseMessage msg = jobDao.applyJob(jobData);
        exchange.getIn().setBody(msg);
        exchange.getIn().setHeader(Exchange.CONTENT_TYPE, "application/json");
        exchange.getIn().setHeader(Exchange.HTTP_RESPONSE_CODE, "200");
    }

    public void jobTracking(Exchange exchange) {
        jobDao = new JobDaoImpl();
        LinkedHashMap<String, Object> jobData = (LinkedHashMap) exchange.getIn().getBody();

        ResponseMessage msg = jobDao.applyHistory(jobData);
        exchange.getIn().setBody(msg);
        exchange.getIn().setHeader(Exchange.CONTENT_TYPE, "application/json");
        exchange.getIn().setHeader(Exchange.HTTP_RESPONSE_CODE, "200");
    }

    public void createJob(Exchange exchange) {
        jobDao = new JobDaoImpl();
        LinkedHashMap<String, Object> jobData = (LinkedHashMap) exchange.getIn().getBody();

        ResponseMessage msg = jobDao.createJob(jobData);
        exchange.getIn().setBody(msg);
        exchange.getIn().setHeader(Exchange.CONTENT_TYPE, "application/json");
        exchange.getIn().setHeader(Exchange.HTTP_RESPONSE_CODE, "200");
    }

    public void getCompanyList(Exchange exchange) {
        jobDao = new JobDaoImpl();

        ResponseMessage msg = jobDao.getCompanyList();
        exchange.getIn().setBody(msg);
        exchange.getIn().setHeader(Exchange.CONTENT_TYPE, "application/json");
        exchange.getIn().setHeader(Exchange.HTTP_RESPONSE_CODE, "200");
    }
}
