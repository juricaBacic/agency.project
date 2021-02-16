package agency.services.interfaces;

import agency.dto.HeistDTO;
import agency.entity.Heist;
import agency.entity.HeistMember;

public interface HeistService {

 Heist saveHeist(HeistDTO heistDTO);
}
