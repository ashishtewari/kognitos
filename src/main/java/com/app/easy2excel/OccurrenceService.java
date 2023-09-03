package com.app.easy2excel;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.app.easy2excel.domains.Input;
import com.app.easy2excel.domains.Output;
import com.app.easy2excel.entity.Occurrence;
import java.util.Map;

public class OccurrenceService {
    private DynamoDBMapper dynamoDBMapper;
    private static  String jsonBody = null;

    public APIGatewayProxyResponseEvent saveOccurrence(APIGatewayProxyRequestEvent apiGatewayRequest, Context context){
        initDynamoDB();
        Input input = Utility.convertStringToInput(apiGatewayRequest.getBody(),context);

        String word = input.getWord().toLowerCase();
        Occurrence occurrence = dynamoDBMapper.load(Occurrence.class,word);
        occurrence =  incrementAndSaveOccurrence(occurrence, word, context);

        Output output = new Output();
        output.setOccurrences(occurrence.getCount());

        jsonBody = Utility.convertOutputToString(output, context);

        context.getLogger().log("fetch occurrence By word:::" + jsonBody);
        return createAPIResponse(jsonBody,200,Utility.createHeaders());
    }

    public APIGatewayProxyResponseEvent getCountByWord(APIGatewayProxyRequestEvent apiGatewayProxyRequestEvent,
                                                       Context context){
        initDynamoDB();
        String word = apiGatewayProxyRequestEvent.getPathParameters().get("word").toLowerCase();
        Occurrence occurrence = dynamoDBMapper.load(Occurrence.class,word);
        occurrence =  incrementAndSaveOccurrence(occurrence, word, context);
        jsonBody = Utility.convertObjToString(occurrence, context);
        context.getLogger().log("fetch occurrence By word:::" + jsonBody);
        return createAPIResponse(jsonBody,200,Utility.createHeaders());
    }


    public Occurrence incrementAndSaveOccurrence(Occurrence occurrence, String word, Context context){
        if(occurrence==null){
            occurrence = new Occurrence();
            occurrence.setWord(word);
            occurrence.setCount(0);
        }
        occurrence.setCount(occurrence.getCount()+1);
        dynamoDBMapper.save(occurrence);
        jsonBody = Utility.convertObjToString(occurrence,context) ;
        context.getLogger().log("data saved successfully to dynamodb:::" + jsonBody);
        return occurrence;
    }

    public void initDynamoDB(){
        AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard().build();
        dynamoDBMapper = new DynamoDBMapper(client);
    }
    private APIGatewayProxyResponseEvent createAPIResponse(String body, int statusCode, Map<String,String> headers ){
        APIGatewayProxyResponseEvent responseEvent = new APIGatewayProxyResponseEvent();
        responseEvent.setBody(body);
        responseEvent.setHeaders(headers);
        responseEvent.setStatusCode(statusCode);
        return responseEvent;
    }
}
