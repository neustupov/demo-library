package ru.neustupov.demolibrary.service;

import org.springframework.stereotype.Service;
import ru.neustupov.demolibrary.model.Rack;
import ru.neustupov.demolibrary.repository.RackRepository;

@Service
public class RackService {

  private RackRepository rackRepository;

  public Rack getRackById(Long id){
    return rackRepository.getById(id);
  }
}
