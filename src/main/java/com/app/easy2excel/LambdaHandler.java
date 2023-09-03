package com.app.easy2excel;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;

public class LambdaHandler implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {


    @Override
    public APIGatewayProxyResponseEvent  handleRequest(APIGatewayProxyRequestEvent apiGatewayRequest, Context context) {
        OccurrenceService occurrenceService = new OccurrenceService();
        switch (apiGatewayRequest.getHttpMethod()) {

            case "POST":
                return occurrenceService.saveOccurrence(apiGatewayRequest, context);

            case "GET":
                if (apiGatewayRequest.getPathParameters() != null) {
                    String word = apiGatewayRequest.getPathParameters().get("word");
                    if(word!=null){
                        return occurrenceService.getCountByWord(apiGatewayRequest, context);
                    }

                }
            default:
                throw new Error("Unsupported Methods:::" + apiGatewayRequest.getHttpMethod());

        }
    }
 }
