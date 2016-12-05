package camel.rest.dao;

import camel.rest.database.JobObject;
import camel.rest.database.QueryObject;
import camel.rest.domain.ResponseMessage;
import camel.rest.utils.Constants;
import camel.rest.utils.Utils;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


public class JobDaoImpl implements JobDao {
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
        return Utils.constructMsg(0, "Job List", response);
    }

    @Override
    public ResponseMessage applyHistory(LinkedHashMap<String, Object> userData) {
        return null;
    }

    @Override
    public ResponseMessage applyJob(LinkedHashMap<String, Object> jobData) {
        return null;
    }

    @Override
    public ResponseMessage createJob(LinkedHashMap<String, Object> jobData) {
        ResponseMessage response = null;
        QueryObject queryObject = new JobObject();
        queryObject.setOperation("INSERT");
        queryObject.setTable("job");
        List<String> insertValues = Utils.flattenMap(jobData);
        queryObject.setValues(insertValues);
        queryObject.executeQuery();
        response = Utils.constructMsg(0, Constants.SUCCESS_JOB_CREATE, null);

        return  response;
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
        String whereClause = Utils.flattenKeyValuePair(jobQuery, "AND");
        queryObject.setWhereClause(whereClause);

        queryObject.executeQuery();

        List<Map<String, Object>> rows = queryObject.getRecords();

        return rows;
    }


}
