package cargo.service;

import cargo.domain.Cargo;
import cargo.exception.unckecked.CargoDeleteConstraintViolationException;
import cargo.repo.CargoRepo;
import cargo.search.CargoSearchCondition;
import transportation.domain.Transportation;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class CargoServiceImpl implements CargoService {

  private CargoRepo cargoRepo;

  public CargoServiceImpl(CargoRepo cargoRepo) {
    this.cargoRepo = cargoRepo;
  }

  @Override
  public void save(Cargo cargo) {
    cargoRepo.save(cargo);
  }

  @Override
  public Cargo findById(Long id) {
    return Optional.ofNullable(id).map(cargoRepo::findById).orElse(null);
  }

  @Override
  public Cargo getByIdFetchingTransportations(Long id) {
    return Optional.ofNullable(id).map(cargoRepo::getByIdFetchingTransportations).orElse(null);
  }

  @Override
  public List<Cargo> getAll() {
    return cargoRepo.getAll();
  }

  @Override
  public int countAll() {
    return this.cargoRepo.countAll();
  }

  @Override
  public List<Cargo> findByName(String name) {
    return Optional.ofNullable(name).map(cargoRepo::findByName).map(Arrays::asList)
         . map(list -> list.size() > 0 ? list : null).orElse(Collections.emptyList());
  }

  @Override
  public boolean deleteById(Long id) {
    return Optional.ofNullable(id).map(this::getByIdFetchingTransportations).map(cargo -> {
      List<Transportation> transportations = cargo.getTransportations();
      boolean hasTransportations = transportations != null && transportations.size() > 0;
      if (hasTransportations) {
        throw new CargoDeleteConstraintViolationException(id);
      }
      return cargoRepo.deleteById(id);
    }).orElse(false);
  }

  @Override
  public boolean update(Cargo cargo) {
    return Optional.ofNullable(cargo).map(cargoRepo::update).orElse(false);
  }

  @Override
  public List<Cargo> search(CargoSearchCondition cargoSearchCondition) {
    return cargoRepo.search(cargoSearchCondition);
  }
}
