package web.rest;

import agency.ProjectApplication;
import agency.dto.HeistDTO;
import agency.entity.Heist;
import agency.enumeration.Status;
import agency.repository.HeistRepository;
import agency.services.converters.HeistConverter;
import agency.services.interfaces.HeistService;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;

import java.time.LocalDateTime;


@SpringBootTest(classes = {ProjectApplication.class, TestConfiguration.class})
public class HeistServiceUnitTest {

    @Autowired
    private HeistRepository heistRepository;
    @Autowired
    private HeistService heistService;
    @Autowired
    private HeistConverter heistConverter;


    private static final String NAME = "Fábrica Nacional de Moneda y Timbre";
    private static final String LOCATION = "Madrid";
    private static final Status STATUS = Status.PLANNING;
    private static final LocalDateTime START_DATE_TIME = LocalDateTime.of(2020, 5, 15, 12, 10, 1);
    private static final LocalDateTime END_DATE_TIME = LocalDateTime.of(2020, 5, 16, 11, 15, 35);
    private static final Status STATUS_AVAILABLE = Status.AVAILABLE;
    private static final Status STATUS_PLANNING = Status.PLANNING;


    public HeistDTO newHeistDTO() {

        Heist heist = heistRepository.findById(NAME).get();

        HeistDTO heistCustom = heistConverter.toDto(heist);

        return heistCustom;

    }

    @Test
    void saveHeistTest() {

        Heist heist = heistRepository.findById(NAME).get();

        Heist heistServiceCheck = heistService.saveHeist(newHeistDTO());

        heistRepository.saveAndFlush(heistServiceCheck);

        Assert.assertNotNull(heistServiceCheck);
        Assert.assertEquals(heist.getName(), heistServiceCheck.getName());
        Assert.assertEquals(heist.getStartTime(), heistServiceCheck.getStartTime());
        Assert.assertEquals(heist.getLocation(), heistServiceCheck.getLocation());
        Assert.assertEquals(heist.getStatus(), heistServiceCheck.getStatus());
        Assert.assertEquals(heist.getStatus(), heistServiceCheck.getStatus());

    }

    @Test
    void getHeistByHeistIdTest() {

        Heist heistByName = heistRepository.findById(NAME).get();

        Heist heistById = heistService.getHeistById(NAME).get();

        Assert.assertNotNull(heistById);
        Assert.assertEquals(heistByName.getName(), heistById.getName());
        Assert.assertEquals(heistByName.getEndTime(), heistById.getEndTime());
        Assert.assertEquals(heistByName.getLocation(), heistById.getLocation());
        Assert.assertEquals(heistByName.getStatus(), heistById.getStatus());

    }

    @Test
    void getHeistWithNameStatusAndSkillsByNameTest() {

        Heist heistWithNameStatusAndSkillsByName = heistService.getHeistWithNameStatusAndSkillsByName(NAME).get();

        Assert.assertNotNull(heistWithNameStatusAndSkillsByName);
        Assert.assertEquals(heistWithNameStatusAndSkillsByName.getName(), NAME);
        Assert.assertEquals(heistWithNameStatusAndSkillsByName.getStatus(), STATUS_PLANNING);

    }

    @Test
    void getHeistStatusByHeistId() {

        Status heistStatus = heistService.getHeistStatusByHeistId(NAME).get();

        Assert.assertNotNull(heistStatus);
        Assert.assertEquals(heistStatus, STATUS_PLANNING);

    }

}
