package cargo.repo.impl;


import cargo.domain.Cargo;
import cargo.search.CargoSearchCondition;
import common.solutions.utils.CollectionUtils;
import storage.IdGenerator;
import storage.Storage;

import java.util.*;

public class CargoCollectionRepoImpl extends CommonCargoRepo {

  private static Storage storage = Storage.getInstance ();
    
  @Override
  public Cargo getByIdFetchingTransportations(long id) {
    return findById(id);
  }

  @Override
  public Cargo[] findByName(String name) {
    List<Cargo> result = new ArrayList<>();

    for (Cargo carrier : storage.cargoCollection) {
      if (Objects.equals(carrier.getName(), name)) {
        result.add(carrier);
      }
    }

    return result.toArray(new Cargo[0]);
  }

  @Override
  public List<Cargo> search(CargoSearchCondition searchCondition) {
    List<Cargo> cargos = getAll();

    if (CollectionUtils.isNotEmpty(cargos)) {
      if (searchCondition.needSorting()) {
        Comparator<Cargo> cargoComparator = createCargoComparator(searchCondition);
        cargos.sort(searchCondition.isAscOrdering() ? cargoComparator : cargoComparator.reversed());
      }
    }

    return cargos;
  }

  @Override
  public Cargo findById(Long id) {
    for (Cargo carrier : storage.cargoCollection) {
      if (id != null && id.equals(carrier.getId())) {
        return carrier;
      }
    }

    return null;
  }

  @Override
  public void save(Cargo cargo) {
    cargo.setId(IdGenerator.generateId());
    storage.cargoCollection.add(cargo);
  }

  @Override
  public boolean update(Cargo entity) {
    return true;
  }

  @Override
  public boolean deleteById(Long id) {
    Iterator<Cargo> iter = storage.cargoCollection.iterator();

    boolean removed = false;
    while (iter.hasNext()) {
      if (id != null && id.equals(iter.next().getId())) {
        iter.remove();
        removed = true;
        break;
      }
    }

    return removed;
  }

  @Override
  public List<Cargo> getAll() {
    return storage.cargoCollection;
  }

  @Override
  public int countAll() {
    return storage.cargoCollection.size();
  }


}
