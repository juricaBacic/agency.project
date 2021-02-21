package web.rest;


import agency.ProjectApplication;
import agency.dto.HeistDTO;
import agency.dto.HeistSkillDTO;
import agency.entity.Heist;
import agency.entity.HeistSkill;
import agency.entity.Skill;
import agency.repository.HeistRepository;
import agency.repository.HeistSkillRepository;
import agency.services.converters.HeistConverter;
import agency.services.converters.HeistSkillConverter;
import agency.services.interfaces.HeistService;
import agency.services.interfaces.HeistSkillService;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@SpringBootTest(classes = {ProjectApplication.class, TestConfiguration.class})
public class HeistSkillServiceUnitTest {


    @Autowired
    HeistSkillService heistSkillService;
    @Autowired
    HeistSkillRepository heistSkillRepository;
    @Autowired
    HeistRepository heistRepository;
    @Autowired
    HeistConverter heistConverter;
    @Autowired
    HeistService heistService;
    @Autowired
    HeistSkillConverter heistSkillConverter;


    private static final String HEIST_NAME = "Fábrica Nacional de Moneda y Timbre";

    public HeistDTO heistSkillUpdateDto(Heist heist) {

        HeistDTO heistDTO = new HeistDTO();

        final Set<HeistSkill> heistSkills = heistSkillRepository.findHeistSkillByHeist(heist);
        final Set<HeistSkillDTO> heistSkillDTOS = new HashSet<>();

        for (HeistSkill heistSkill : heistSkills) {
            heistSkillDTOS.add(heistSkillConverter.toDto(heistSkill));
            heistDTO.setSkills(heistSkillDTOS);
        }
        return heistDTO;
    }

    @Test
    void saveHeistSkillTest() {

        Set<HeistSkill> heistSkillById = heistSkillRepository.findHeistSkillByHeist_Name(HEIST_NAME);

        for (HeistSkill heistSkill : heistSkillById) {

            HeistSkill saveHeistSkill = heistSkillRepository.save(heistSkill);

            Assert.assertNotNull(saveHeistSkill);
            Assert.assertTrue(saveHeistSkill.getMember() > 0);
            Assert.assertTrue(saveHeistSkill.getLevel().contains("*"));
        }
    }


    @Test
    void updateHeistSkill() {

        Heist heistById = heistRepository.findById(HEIST_NAME).get();

        heistSkillService.updateHeistSkill(heistSkillUpdateDto(heistById), HEIST_NAME);

        Assert.assertNotNull(heistById);
        Assert.assertTrue(heistSkillUpdateDto(heistById).getSkills().size() > 0);


    }

    @Test
    void heistSkillByHeistId() {

        Set<HeistSkill> heistSkillsById = heistSkillService.heistSkillByHeistId(HEIST_NAME);

        Assert.assertNotNull(heistSkillsById);

        for (HeistSkill heistSkillList : heistSkillsById) {

            Assert.assertNotNull(heistSkillList);
            Assert.assertTrue(!heistSkillList.getSkill().getName().isEmpty());
            Assert.assertTrue(!heistSkillList.getLevel().isBlank());
            Assert.assertTrue(heistSkillList.getMember() > 0);

        }
    }

}
