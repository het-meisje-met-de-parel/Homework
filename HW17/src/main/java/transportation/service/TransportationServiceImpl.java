package transportation.service;

import transportation.domain.Transportation;
import transportation.repo.TransportationRepo;

import java.util.List;
import java.util.Optional;

public class TransportationServiceImpl implements TransportationService {

  private TransportationRepo transportationRepo;

  public TransportationServiceImpl(
      TransportationRepo transportationRepo) {
    this.transportationRepo = transportationRepo;
  }

  @Override
  public boolean deleteById(Long id) {
    return transportationRepo.deleteById(id);
  }

  @Override
  public void save(Transportation transportation) {
    transportationRepo.save(transportation);
  }

  @Override
  public Transportation findById(Long id) {
    return transportationRepo.findById(id);
  }

  @Override
  public List<Transportation> getAll() {
    return transportationRepo.getAll();
  }

  @Override
  public boolean update(Transportation transportation) {
    return Optional.ofNullable (transportation).map(transportationRepo::update).orElse(false);
  }
}
