package agency.services.interfaces;

import agency.dto.HeistDTO;
import agency.entity.Heist;
import agency.entity.HeistMember;
import agency.enumeration.Status;

import java.util.Optional;

public interface HeistService {

 Heist saveHeist(HeistDTO heistDTO);

 Optional<Heist> getHeistById(String name);

 Optional<Heist> getHeistWithNameStatusANdSkillsByName(String name);

 Optional<Status> getHeistStatusByHeistId(String name);
}
