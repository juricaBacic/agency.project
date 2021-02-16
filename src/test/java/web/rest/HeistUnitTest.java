package web.rest;

import agency.ProjectApplication;
import agency.entity.Heist;
import agency.enumeration.Status;
import agency.repository.HeistRepository;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import java.time.LocalDateTime;
import java.util.Optional;

@SpringBootTest(classes = {ProjectApplication.class, TestConfiguration.class})
public class HeistUnitTest {


    @Autowired
    private HeistRepository heistRepository;

    private LocalDateTime START_DATE_TIME = LocalDateTime.of(2020, 5, 15, 12, 10, 1);
    private LocalDateTime END_DATE_TIME = LocalDateTime.of(2020, 5, 16, 11, 15, 35);



    @Test
    void checkIsHeistSavedTest() {

        Heist heistFind = heistRepository.findById("Bank of Madrid").get();

        System.out.println(heistFind);

        Heist heistCustom = new Heist();

        heistCustom.setName("Bank of Madrid");
        heistCustom.setStatus(Status.AVAILABLE);
        heistCustom.setLocation("Madrid");
        heistCustom.setStartTime(START_DATE_TIME);
        heistCustom.setEndTime(END_DATE_TIME);

        Heist hst = heistRepository.saveAndFlush(heistCustom);

        Assert.assertNotNull(heistFind);
        Assert.assertEquals(hst.getName(), heistCustom.getName());
        Assert.assertEquals(hst.getStartTime(), heistCustom.getStartTime());
        Assert.assertEquals(hst.getLocation(), heistCustom.getLocation());
        Assert.assertEquals(hst.getStatus(), heistCustom.getStatus());

    }
}
