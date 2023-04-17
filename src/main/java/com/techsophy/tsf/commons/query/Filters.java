package com.techsophy.tsf.commons.query;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import org.springframework.data.mongodb.core.query.Criteria;

import java.util.Map;

@Data
public class Filters
{


    private Map<String, FilterOperation> operations;

    public static void main(String[] args) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Filters f= mapper.readValue( "{ \"operations\" : { \"formData.userId\" : {\"equals\" : \"abcd\"} }}"
//                "{\"operations\":" +
//                "{ \"formData.userId\": " +    "{ \"equals\" : \"<request.userId>\"} " +
////                "\"formData.createdById\" :{ equals: <request.userId>}," +
////                "\"formData.emailId\":{equals:<request.emailId>}," +
////                "\"formData.mobileNumber\":{equals:<request.mobileNumber>\"}," +
////                "\"formData.context\":{\"equals\":\"<request.context>\"}"
//                +"}"
                , Filters.class);


        System.out.println("filters: "+f);

    }

}
