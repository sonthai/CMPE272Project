package camel.rest.utils;

import camel.rest.domain.ResponseMessage;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class Utils {
    public static JsonNode convertJsonNode(String s) {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = null;
        try {
            jsonNode = objectMapper.readTree(s);
        } catch (Exception e) {}

        return jsonNode;
    }

    public static ResponseMessage constructMsg(int errorCode, String message, List<Map<String, Object>> data) {
        ResponseMessage res = new ResponseMessage();
        res.setErrorCode(errorCode);
        res.setMessage(message);
        res.setDataResponse(data);

        return  res;

    }

    public static List<String> flattenMap(Map<String, Object> map) {
        List<String> list = new ArrayList<>();
        StringBuilder keys = new StringBuilder();
        StringBuilder values = new StringBuilder();

        keys.append("(");
        values.append("(");
        for (Map.Entry<String, Object> pair: map.entrySet()) {
            if (keys.length() > 1) {
                keys.append(",");
                values.append(",");
            }
            keys.append(pair.getKey());
            if (pair.getValue() instanceof String) {
                values.append("'").append(pair.getValue()).append("'");
            } else if (pair.getValue() instanceof Integer || pair.getValue() instanceof Double) {
                values.append(pair.getValue());
            }
        }

        keys.append(")");
        values.append(")");
        list.add(keys.toString());
        list.add(values.toString());

        return list;
    }

    public static String flattenKeyValuePair(Map<String, Object> map, String delim) {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, Object> pair: map.entrySet()) {
            if (sb.length() > 0) {
                sb.append(" ").append(delim).append(" ");
            }

            sb.append(pair.getKey()).append("=");

            if (pair.getValue() instanceof String) {
                sb.append("'").append(pair.getValue()).append("'");
            } else if (pair.getValue() instanceof Integer || pair.getValue() instanceof Double) {
                sb.append(pair.getValue());
            }

        }

        return sb.toString();
    }
    public String doGet(String urlString) {
        try {
            //OAuthConsumer consumer = new CommonsHttpOAuthConsumer(TwitterCredentials.consumerKey, TwitterCredentials.consumerSecret);
            //consumer.setTokenWithSecret(TwitterCredentials.accessToken, TwitterCredentials.accessTokenSecret);
            CloseableHttpClient client = HttpClientBuilder.create().build();
            HttpGet request = new HttpGet(urlString);
            //consumer.sign(request);

            HttpResponse response = client.execute(request);
            BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
            StringBuilder sb = new StringBuilder();
            String line;
            while((line = rd.readLine())!= null) {
                sb.append(line);
            }
            rd.close();
            return sb.toString();
        } catch (Exception e) {
            return e.getMessage();
        }
    }


}
