package carrier.repo.impl;


import carrier.domain.Carrier;
import carrier.repo.CarrierRepo;
import storage.IdGenerator;
import storage.Storage;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

public class CarrierCollectionRepoImpl implements CarrierRepo {

  private static Storage storage = Storage.getInstance ();
    
  @Override
  public void save(Carrier carrier) {
    carrier.setId(IdGenerator.generateId());
    storage.carrierCollection.add(carrier);
  }

  @Override
  public Carrier findById(Long id) {
    for (Carrier carrier : storage.carrierCollection) {
      if (carrier.getId().equals(id)) {
        return carrier;
      }
    }

    return null;
  }

  @Override
  public Carrier getByIdFetchingTransportations(long id) {
    return findById(id);
  }

  @Override
  public Carrier[] findByName(String name) {
    List<Carrier> result = new ArrayList<>();

    for (Carrier carrier : storage.carrierCollection) {
      if (Objects.equals(carrier.getName(), name)) {
        result.add(carrier);
      }
    }

    return result.toArray(new Carrier[0]);
  }

  @Override
  public boolean deleteById(Long id) {
    Iterator<Carrier> iter = storage.carrierCollection.iterator();

    boolean removed = false;
    while (iter.hasNext()) {
      if (iter.next().getId().equals(id)) {
        iter.remove();
        removed = true;
        break;
      }
    }

    return removed;
  }

  @Override
  public List<Carrier> getAll() {
    return storage.carrierCollection;
  }

  @Override
  public int countAll() {
    return storage.carrierCollection.size();
  }

  @Override
  public boolean update(Carrier carrier) {
    return true;
  }

}
