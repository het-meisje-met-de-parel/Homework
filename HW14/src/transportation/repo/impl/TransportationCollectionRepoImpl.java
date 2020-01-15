package transportation.repo.impl;


import storage.IdGenerator;
import storage.Storage;
import transportation.domain.Transportation;
import transportation.repo.TransportationRepo;

import java.util.Iterator;
import java.util.List;

public class TransportationCollectionRepoImpl implements TransportationRepo {

  private static Storage storage = Storage.getInstance ();
    
  @Override
  public void save(Transportation transportation) {
    transportation.setId(IdGenerator.generateId());
    storage.transportationCollection.add(transportation);
  }

  @Override
  public Transportation findById(Long id) {
    for (Transportation transportation : storage.transportationCollection) {
      if (transportation.getId().equals(id)) {
        return transportation;
      }
    }

    return null;
  }

  @Override
  public List<Transportation> getAll() {
    return storage.transportationCollection;
  }

  @Override
  public boolean update(Transportation transportation) {
    return true;
  }

  @Override
  public boolean deleteById(Long id) {
    boolean deleted = false;

    Iterator<Transportation> iter = storage.transportationCollection.iterator();
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
    return storage.transportationCollection.size();
  }
}
