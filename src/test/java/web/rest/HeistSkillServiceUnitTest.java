package web.rest;


import agency.ProjectApplication;
import agency.dto.HeistDTO;
import agency.entity.HeistSkill;
import agency.repository.HeistSkillRepository;
import agency.services.interfaces.HeistSkillService;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@SpringBootTest(classes = {ProjectApplication.class, TestConfiguration.class})
public class HeistSkillServiceUnitTest {


    @Autowired
    HeistSkillService heistSkillService;
    @Autowired
    HeistSkillRepository heistSkillRepository;


    private static final String HEIST_NAME = "Fábrica Nacional de Moneda y Timbre";


    @Test
    void saveHeistSkillTest(){


        Set<HeistSkill> heistSkillById = heistSkillRepository.findHeistSkillByHeist_Name(HEIST_NAME);

        for (HeistSkill heistSkill:heistSkillById) {

            HeistSkill saveHeistSkill = heistSkillRepository.save(heistSkill);

            Assert.assertNotNull(saveHeistSkill);
            Assert.assertTrue(saveHeistSkill.getMember() > 0);
            Assert.assertTrue(saveHeistSkill.getLevel().contains("*"));

        }

    }

    void updateHeistSkill(){

    }

    void heistSkillByHeistId(){


    }

}
