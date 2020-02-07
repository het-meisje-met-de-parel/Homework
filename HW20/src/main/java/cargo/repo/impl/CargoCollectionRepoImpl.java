package cargo.repo.impl;


import static storage.Storage.*;

import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import cargo.domain.Cargo;
import cargo.search.CargoSearchCondition;
import common.solutions.utils.CollectionUtils;
import storage.IdGenerator;

public class CargoCollectionRepoImpl extends CommonCargoRepo {

  @Override
  public Optional<Cargo> getByIdFetchingTransportations(long id) {
    return findById(id);
  }

  @Override
  public Cargo[] findByName(String name) {
    return getAll().stream().filter((a) -> a.getName().equals(name)).toArray(Cargo [] :: new);
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
  public Optional<Cargo> findById(Long id) {
    for (Cargo carrier : cargoCollection) {
      if (id != null && id.equals(carrier.getId())) {
        return Optional.of(carrier);
      }
    }

    return Optional.empty();
  }

  @Override
  public void save(Cargo cargo) {
    cargo.setId(IdGenerator.generateId());
    cargoCollection.add(cargo);
  }

  @Override
  public boolean update(Cargo entity) {
    return true;
  }

  @Override
  public boolean deleteById(Long id) {
    Iterator<Cargo> iter = cargoCollection.iterator();

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
    return cargoCollection;
  }

  @Override
  public int countAll() {
    return cargoCollection.size();
  }

}
