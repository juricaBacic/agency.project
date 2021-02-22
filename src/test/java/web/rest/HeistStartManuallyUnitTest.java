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
    void heistStartManuallyTest() {

        Optional<Heist> findHeistById = heistRepository.findById(HEIST_NAME);

        if (!findHeistById.isEmpty()) {

            heistStartManually.startHeistManually(HEIST_NAME).toString();

            Status statusByHeistId = heistRepository.getStatusByHeistId(HEIST_NAME).get();

            Assert.assertEquals(statusByHeistId, Status.READY);
        }

    }
}
