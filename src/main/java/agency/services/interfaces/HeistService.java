package agency.services.interfaces;

import agency.dto.HeistDTO;
import agency.entity.Heist;
import agency.enumeration.Status;

import java.util.Optional;

public interface HeistService {

     Heist saveHeist(HeistDTO heistDTO);

    Optional<Heist> getHeistById(String name);

    Optional<Heist> getHeistWithNameStatusAndSkillsByName(String name);

    Optional<Status> getHeistStatusByHeistId(String name);
}
