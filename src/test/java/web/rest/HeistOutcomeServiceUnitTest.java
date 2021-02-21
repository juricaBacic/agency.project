package web.rest;


import agency.ProjectApplication;
import agency.entity.Heist;
import agency.enumeration.OutcomeStatus;
import agency.enumeration.Status;
import agency.services.interfaces.HeistOutcomeService;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;



@SpringBootTest(classes = {ProjectApplication.class, TestConfiguration.class})
public class HeistOutcomeServiceUnitTest {

    @Autowired
    HeistOutcomeService heistOutcomeService;

    private static final String DEFAULT_NAME = "Fábrica Nacional de Moneda y Timbre";
    private static final OutcomeStatus OUTCOME_STATUS_FAILED = OutcomeStatus.FAILED;


    @Test
    void outcomeOfTheHeistTest(){

        OutcomeStatus outcomeStatus = heistOutcomeService.outcomeOfTheHeist(DEFAULT_NAME);

        Assert.assertNotNull(outcomeStatus);
        Assert.assertEquals(outcomeStatus, OUTCOME_STATUS_FAILED);


    }

}
