package com.app.easy2excel;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.app.easy2excel.domains.Input;
import com.app.easy2excel.domains.Output;
import com.app.easy2excel.entity.Occurrence;
import java.util.HashMap;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class OccurrencesTest {

    @Mock
    private Context context;

    @Spy
    @InjectMocks
    OccurrenceService occurrenceService;

    @Mock
    private LambdaLogger logger;

    @Mock
    private DynamoDBMapper dynamoDBMapper;


    @BeforeEach
    void setUp() {
        // Set up your mocks
        doNothing().when(occurrenceService).initDynamoDB();
        doNothing().when(dynamoDBMapper).save(any());
        when(context.getLogger()).thenReturn(logger);
    }

    @Test
    void testSaveWordOccurrence(){

        Occurrence occurrence = initializeMockOccurrence("ashish", 1);
        // mock the dependencies
        Mockito.when(dynamoDBMapper.load(Occurrence.class,"ashish"))
                .thenReturn(occurrence);

        // create request body
        APIGatewayProxyRequestEvent request = initializeMockOccurrenceRequest("ashish");


        APIGatewayProxyResponseEvent response =
                occurrenceService.saveOccurrence(request, context);

        Output output = Utility.convertStringToOutput(response.getBody(), context);


        //  Assert
        assertEquals(200, response.getStatusCode());
        assertEquals(2, output.getOccurrences());

    }

    private Occurrence initializeMockOccurrence(String word, int count){
        Occurrence occurrence = new Occurrence();
        occurrence.setWord("ashish");
        occurrence.setCount(1);
        return occurrence;
    }

    private APIGatewayProxyRequestEvent initializeMockOccurrenceRequest(String word){
        APIGatewayProxyRequestEvent requestEvent = new APIGatewayProxyRequestEvent();
        Input inputPassed = new Input();
        inputPassed.setWord(word);
        requestEvent.setBody(Utility.convertInputToString(inputPassed, context));

        Map<String, String>input = new HashMap<>();
        input.put("word", word);
        requestEvent.setPathParameters(input);
        return requestEvent;
    }

}
