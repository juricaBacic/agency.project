package web.rest;

import agency.ProjectApplication;
import agency.entity.Skill;
import agency.repository.SkillRepository;
import agency.services.interfaces.SkillService;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;


@SpringBootTest(classes = {ProjectApplication.class, TestConfiguration.class})
public class SkillServiceUnitTest {

    private static final String SKILL_NAME = "combat";

    @Autowired
    SkillService skillService;
    @Autowired
    SkillRepository skillRepository;


    public Skill skill() {

        Skill skill = new Skill();
        skill.setName(SKILL_NAME);

        return skill;
    }

    @Test
    void getSkillByIdTest() {

        Skill skillById = skillService.findSkillById(SKILL_NAME).get();

        Assert.assertNotNull(skillById);
        Assert.assertEquals(skillById.getName(), SKILL_NAME);

    }

    @Test
    void saveSkillTest() {

        Skill save = skillRepository.save(skill());

        Assert.assertNotNull(save);
        Assert.assertEquals(save.getName(), SKILL_NAME);

    }

}
