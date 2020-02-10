package transportation.repo.impl;


import static storage.Storage.transportationCollection;

import storage.IdGenerator;
import transportation.domain.Transportation;
import transportation.repo.TransportationRepo;

import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import cargo.domain.Cargo;
import carrier.domain.Carrier;

public class TransportationCollectionRepoImpl implements TransportationRepo {

  @Override
  public void save(Transportation transportation) {
    transportation.setId(IdGenerator.generateId());
    transportationCollection.add(transportation);
  }

  @Override
  public Optional<Transportation> findById(Long id) {
    for (Transportation transportation : transportationCollection) {
      if (transportation.getId().equals(id)) {
        return Optional.of(transportation);
      }
    }

    return Optional.empty();
  }

  @Override
  public List<Transportation> getAll() {
    return transportationCollection;
  }

  @Override
  public boolean update(Transportation transportation) {
    return true;
  }

  @Override
  public boolean deleteById(Long id) {
    boolean deleted = false;

    Iterator<Transportation> iter = transportationCollection.iterator();
    while (iter.hasNext()) {
      if (iter.next().getId().equals(id)) {
        iter.remove();
        deleted = true;
        break;
      }
    }
    return deleted;
  }

  @Override
  public int countAll() {
    return transportationCollection.size();
  }
  
  @Override
  public List <Transportation> findByCargo (Cargo cargo) {
      return getAll ().stream ().filter (tr -> Objects.equals (cargo.getId (), tr.getCargo ().getId ()))
              . collect (Collectors.toList ());
  }
  
  @Override
  public List <Transportation> findByCarrier (Carrier carrier) {
    return getAll ().stream ().filter (tr -> Objects.equals (carrier.getId (), tr.getCargo ().getId ()))
         . collect (Collectors.toList ());
  }
}
