package camel.rest.dao;

import camel.rest.database.CompanyObject;
import camel.rest.database.JobHistory;
import camel.rest.database.JobObject;
import camel.rest.database.QueryObject;
import camel.rest.domain.ResponseMessage;
import camel.rest.utils.Constants;
import camel.rest.utils.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;


public class JobDaoImpl implements JobDao {
    Logger LOG = LoggerFactory.getLogger(JobDaoImpl.class);

    @Override
    public ResponseMessage findJobs(LinkedHashMap<String, Object> jobQuery) {
        // Get Job from Dice API
        // query work: skill, city, areacode, state
        String query = flatQueryMap(jobQuery);

        List<Map<String, Object>> response = loadJobsFromDice(query);

        // Get Job from database
        List<Map<String, Object>> jobFromDB =  loadJobFromDB(jobQuery);

        if (jobFromDB.size() > 0) {
            response.addAll(jobFromDB);
        }
        return Utils.constructMsg(0, Constants.SUCCESS_JOB_LOAD_FROM_DB, response);
    }

    @Override
    public ResponseMessage applyHistory(LinkedHashMap<String, Object> userData) {
        ResponseMessage response = null;
        QueryObject jobQuery = new JobHistory();
        jobQuery.setOperation("SELECT");
        jobQuery.setTable("job_applied");
        jobQuery.setQueryFields(new String[] {"*"});
        StringBuilder whereClause = new StringBuilder();
        String msgRtn = "";
        if (userData.get("companyID") != null) {
            whereClause.append("companyID='").append(userData.get("companyID")).append("'");
            msgRtn = Constants.SUCCESS_JOB_TRACKING_FROM_RECRUITER;
        } else if (userData.get("userName") != null) {
            whereClause.append("userName='").append(userData.get("userName")).append("'");
            msgRtn = Constants.SUCCESS_JOB_TRACKING_FROM_APPLICANT;
        }

        jobQuery.setWhereClause(whereClause.toString());
        jobQuery.executeQuery();
        response = Utils.constructMsg(0, msgRtn, jobQuery.getRecords());

        return  response;
    }

    @Override
    public ResponseMessage applyJob(LinkedHashMap<String, Object> jobData) {
        ResponseMessage response = null;
        QueryObject queryObject = new JobObject();
        queryObject.setOperation("INSERT");
        queryObject.setTable("job_applied");
        jobData.put("date", null);
        List<String> insertValues = Utils.flattenMap(jobData);
        queryObject.setValues(insertValues);
        queryObject.executeQuery();
        response = Utils.constructMsg(0, Constants.SUCCESS_JOB_APPLY, null);

        return  response;
    }

    @Override
    public ResponseMessage createJob(LinkedHashMap<String, Object> jobData) {
        ResponseMessage response = null;
        QueryObject queryObject = new JobObject();
        queryObject.setOperation("INSERT");
        queryObject.setTable("job");
        jobData.put("date", null);
        List<String> insertValues = Utils.flattenMap(jobData);
        queryObject.setValues(insertValues);
        queryObject.executeQuery();
        response = Utils.constructMsg(0, Constants.SUCCESS_JOB_CREATE, null);

        return  response;
    }

    @Override
    public ResponseMessage getCompanyList() {
        QueryObject jobQuery = new CompanyObject();
        jobQuery.setOperation("SELECT");
        jobQuery.setTable("job_applied");
        jobQuery.setQueryFields(new String[] {"distinct(companyID)"});
        jobQuery.executeQuery();

        List<String> companyList = new ArrayList<>();

        for (Map<String, Object> map: jobQuery.getRecords()) {
            companyList.add((String) map.get("company"));
        }

        ResponseMessage response = new ResponseMessage();
        response.setErrorCode(0);
        response.setDataArrayList(companyList);
        response.setMessage("Success Load company list");
        return response;
    }

    private List<Map<String, Object>> loadJobsFromDice(String query) {
        String url = Constants.diceURL + "?" + query + "sort=2&sd=a";
        String response = Utils.doGet(url);

        return Utils.parseJobJsonString(response);
    }

    private String flatQueryMap(LinkedHashMap<String, Object> jobQuery) {
        StringBuilder res = new StringBuilder();

        for(Map.Entry<String, Object> entry: jobQuery.entrySet()) {
            if (res.length() > 0) {
                res.append("&");
            }
            res.append(entry.getKey()).append("=").append(entry.getValue());
        }

        return res.toString();
    }

    private List<Map<String, Object>> loadJobFromDB(LinkedHashMap<String, Object> jobQuery) {
        QueryObject queryObject = new JobObject();
        queryObject.setOperation("SELECT");
        queryObject.setQueryFields(new String[] {"*"});
        queryObject.setTable("job");


        String skills = (String) jobQuery.remove("skills");
        String [] skillsArr = null;
        if (skills != null) {
            skillsArr = skills.replaceAll("\\s","").toLowerCase().split(",");

        }

        if (jobQuery.size() > 0) {
            String whereClause = Utils.flattenKeyValuePair(jobQuery, "AND");
            queryObject.setWhereClause(whereClause);
        }

        queryObject.executeQuery();

        List<Map<String, Object>> rows = queryObject.getRecords();

        if (skillsArr != null && skillsArr.length > 0) {
            List<Map<String, Object>> filter = new ArrayList<>();
            for(Map<String, Object> map : rows) {
                String skillStr = (String) map.get("skills");
                Set<String> skillLst = new HashSet<>(Arrays.asList(skillStr.replaceAll("\\s","").split(",")));
                for (String s: skillsArr) {
                    if (skillLst.contains(s)) {
                        filter.add(map);
                        break;
                    }
                }
            }
            rows = filter;
        }

        return rows;
    }


}
