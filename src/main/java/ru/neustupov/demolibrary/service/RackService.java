package ru.neustupov.demolibrary.service;

import java.util.Optional;
import org.springframework.stereotype.Service;
import ru.neustupov.demolibrary.model.Rack;
import ru.neustupov.demolibrary.repository.RackRepository;

@Service
public class RackService {

  private RackRepository rackRepository;

  public RackService(RackRepository rackRepository) {
    this.rackRepository = rackRepository;
  }

  public Optional<Rack> getRackById(Long id) {
    return rackRepository.findById(id);
  }

  public Rack save(Rack rack) {
    return rackRepository.save(rack);
  }
}
