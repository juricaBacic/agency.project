package web.rest;

import agency.ProjectApplication;
import agency.entity.Heist;
import agency.enumeration.Status;
import agency.repository.HeistRepository;
import agency.services.interfaces.HeistService;
import agency.services.interfaces.HeistStartManually;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.http.HttpStatus;

import java.util.Optional;

@SpringBootTest(classes = {ProjectApplication.class, TestConfiguration.class})
public class HeistStartManuallyUnitTest {

    private static final String HEIST_NAME = "Fábrica Nacional de Moneda y Timbre";

    @Autowired
    HeistService heistService;

    @Autowired
    HeistStartManually heistStartManually;

    @Autowired
    HeistRepository heistRepository;


    @Test
    void heistStartManuallyTest(){

        Heist findHeistById = heistRepository.findById(HEIST_NAME).get();

        Assert.assertTrue(findHeistById.getStatus().equals(Status.READY));

        HttpStatus statusChange = heistStartManually.startHeistManually(HEIST_NAME);

        Assert.assertTrue(statusChange.is4xxClientError());
    }

}
