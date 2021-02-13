package agency.services.implementations;

import agency.entity.Heist;
import agency.repository.HeistRepository;
import agency.services.interfaces.HeistService;
import org.springframework.stereotype.Service;

@Service
public class HeistServiceImpl  implements HeistService {

    private HeistRepository heistRepository;

    public HeistServiceImpl(HeistRepository heistRepository) {
        this.heistRepository = heistRepository;
    }

    @Override
    public Heist saveHeist(Heist heist) {

        return heistRepository.save(heist);

    }
}
