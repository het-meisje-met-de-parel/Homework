package carrier.service;

import carrier.domain.Carrier;
import carrier.exception.unchecked.CarrierDeleteConstraintViolationException;
import carrier.repo.CarrierRepo;
import transportation.domain.Transportation;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

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
  public Optional<Carrier> findById(Long id) {
    if (id != null) {
      return carrierRepo.findById(id);
    }

    return Optional.empty();
  }

  @Override
  public Optional<Carrier> getByIdFetchingTransportations(Long id) {
    if (id != null) {
      return carrierRepo.getByIdFetchingTransportations(id);
    }

    return Optional.empty();
  }

  @Override
  public List<Carrier> findByName(String name) {
    Carrier[] found = carrierRepo.findByName(name);

    return (found == null || found.length == 0) ? Collections.emptyList() : Arrays.asList(found);
  }

  @Override
  public List<Carrier> getAll() {
    return carrierRepo.getAll();
  }

  @Override
  public int countAll() {
    return this.carrierRepo.countAll();
  }

  @Override
  public boolean deleteById(Long id) {
    Optional<Carrier> carrier = this.getByIdFetchingTransportations(id);

    if (carrier.isPresent()) {
      List<Transportation> transportations = carrier.get().getTransportations();
      boolean hasTransportations = transportations != null && transportations.size() > 0;
      if (hasTransportations) {
        throw new CarrierDeleteConstraintViolationException(id);
      }

      return carrierRepo.deleteById(id);
    } else {
      return false;
    }
  }

  @Override
  public void printAll() {
    List<Carrier> carriers = carrierRepo.getAll();
    for (Carrier carrier : carriers) {
      System.out.println(carrier);
    }
  }

  @Override
  public boolean update(Carrier carrier) {
    if (carrier != null) {
      carrierRepo.update(carrier);
    }

    return false;
  }
}
