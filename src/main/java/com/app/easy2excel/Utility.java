package com.app.easy2excel;

import com.amazonaws.services.lambda.runtime.Context;
import com.app.easy2excel.domains.Input;
import com.app.easy2excel.domains.Output;
import com.app.easy2excel.entity.Occurrence;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.HashMap;
import java.util.Map;

public class Utility {
    public static Map<String,String> createHeaders(){
        Map<String,String> headers = new HashMap<>();
        headers.put("Content-Type","application/json");
        headers.put("X-amazon-author","ashish");
        headers.put("X-amazon-apiVersion","v1");
        return  headers ;
    }


    public static String convertObjToString(Occurrence occurrence, Context context){
        String jsonBody = null;
        try {
            jsonBody =   new ObjectMapper().writeValueAsString(occurrence);
        } catch (JsonProcessingException e) {
            context.getLogger().log( "Error while converting obj to string:::" + e.getMessage());
        }
        return jsonBody;
    }


    public static Input convertStringToInput(String jsonBody, Context context){
        Input input = null;
        try {
            input =   new ObjectMapper().readValue(jsonBody,Input.class);
        } catch (JsonProcessingException e) {
            context.getLogger().log( "Error while converting string to obj:::" + e.getMessage());
        }
        return input;
    }
    public static Output convertStringToOutput(String jsonBody, Context context){
        Output output = null;
        try {
            output =   new ObjectMapper().readValue(jsonBody,Output.class);
        } catch (JsonProcessingException e) {
            context.getLogger().log( "Error while converting string to obj:::" + e.getMessage());
        }
        return output;
    }


    public static String convertOutputToString(Output output, Context context){
        String jsonBody = null;
        try {
            jsonBody =   new ObjectMapper().writeValueAsString(output);
        } catch (JsonProcessingException e) {
            context.getLogger().log( "Error while converting obj to string:::" + e.getMessage());
        }
        return jsonBody;
    }

    public static String convertInputToString(Input input, Context context){
        String jsonBody = null;
        try {
            jsonBody =   new ObjectMapper().writeValueAsString(input);
        } catch (JsonProcessingException e) {
            context.getLogger().log( "Error while converting obj to string:::" + e.getMessage());
        }
        return jsonBody;
    }
}
