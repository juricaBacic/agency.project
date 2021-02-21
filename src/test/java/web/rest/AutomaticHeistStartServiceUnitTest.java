package web.rest;

import org.junit.Assert;
import agency.ProjectApplication;
import agency.enumeration.Status;
import agency.repository.HeistRepository;
import agency.services.interfaces.AutomaticHeistStartService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;


@SpringBootTest(classes = {ProjectApplication.class, TestConfiguration.class})
public class AutomaticHeistStartServiceUnitTest {



    private static final  String NAME = "Fábrica Nacional de Moneda y Timbre";
    private static final Status STATUS_RDY = Status.READY;


    @Autowired
    private HeistRepository heistRepository;
    @Autowired
    private AutomaticHeistStartService automaticHeistStartService;



    @Test
    void startHeistAutomaticallyAndChangeStatusTest(){

        Status statusByHeistId = heistRepository.getStatusByHeistId(NAME).get();

        automaticHeistStartService.startHeistAutomaticallyAndChangeStatus();

        Assert.assertEquals(statusByHeistId, STATUS_RDY);


    }
}
