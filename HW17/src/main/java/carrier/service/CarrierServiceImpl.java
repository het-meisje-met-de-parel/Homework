package carrier.service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import carrier.domain.Carrier;
import carrier.exception.unchecked.CarrierDeleteConstraintViolationException;
import carrier.repo.CarrierRepo;
import transportation.domain.Transportation;

public class CarrierServiceImpl implements CarrierService {

  private CarrierRepo carrierRepo;

  public CarrierServiceImpl(
      CarrierRepo carrierRepo) {
    this.carrierRepo = carrierRepo;
  }

  @Override
  public void save(Carrier carrier) {
    carrierRepo.save(carrier);
  }

  @Override
  public Carrier findById(Long id) {
    return Optional.ofNullable (id).map(carrierRepo::findById).orElse(null);
  }

  @Override
  public Carrier getByIdFetchingTransportations(Long id) {
      return Optional.ofNullable (id).map(carrierRepo::getByIdFetchingTransportations).orElse(null);
  }

  @Override
  public List<Carrier> findByName(String name) {
    Carrier[] found = carrierRepo.findByName(name);

    return (found == null || found.length == 0) ? List.of () : Arrays.asList(found);
  }

  @Override
  public List<Carrier> getAll() {
    return carrierRepo.getAll();
  }

  @Override
  public boolean deleteById(Long id) {
    return Optional.ofNullable (getByIdFetchingTransportations(id)).map ((carrier) -> {
      List<Transportation> transportations = carrier.getTransportations();
      boolean hasTransportations = transportations != null && transportations.size() > 0;
      if (hasTransportations) {
        throw new CarrierDeleteConstraintViolationException(id);
      }

      return carrierRepo.deleteById(id);
    }).orElse(false);
  }

  @Override
  public boolean update(Carrier carrier) {
    return Optional.ofNullable (carrier).map(carrierRepo::update).orElse (false);
  }
}
