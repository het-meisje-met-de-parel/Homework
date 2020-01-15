package cargo.repo.impl;


import cargo.domain.Cargo;
import cargo.search.CargoSearchCondition;
import common.solutions.utils.ArrayUtils;
import common.solutions.utils.CollectionUtils;
import storage.IdGenerator;
import storage.Storage;

import java.util.*;

import static common.business.repo.CommonRepoHelper.findEntityIndexInArrayStorageById;

public class CargoArrayRepoImpl extends CommonCargoRepo {

  private static final Cargo[] EMPTY_CARGO_ARRAY = new Cargo[0];
  private static Storage storage = Storage.getInstance ();

  @Override
  public Cargo getByIdFetchingTransportations(long id) {
    return findById(id);
  }

  @Override
  public Cargo[] findByName(String name) {
    Cargo[] searchResultWithNullableElems = getByNameIncludingNullElements(name);
    if (searchResultWithNullableElems == null
        || searchResultWithNullableElems.length == 0) {
      return EMPTY_CARGO_ARRAY;
    } else {
      return excludeNullableElementsFromArray(searchResultWithNullableElems);
    }
  }

  private Cargo[] getByNameIncludingNullElements(String name) {
    Cargo[] result = new Cargo[storage.cargoArray.length];

    int curIndex = 0;
    for (Cargo carrier : storage.cargoArray) {
      if (carrier != null && Objects.equals(carrier.getName(), name)) {
        result[curIndex++] = carrier;
      }
    }

    return result;
  }

  /**
   * [A,B,C, null, null] [A,B,C, null, null, null, D] [A,B,C]
   *
   * new String[3]
   */
  private Cargo[] excludeNullableElementsFromArray(Cargo[] cargos) {
    int sizeOfArrWithNotNullElems = 0;

    for (Cargo cargo : cargos) {
      if (cargo != null) {
        sizeOfArrWithNotNullElems++;
      }
    }

    if (sizeOfArrWithNotNullElems == 0) {
      return EMPTY_CARGO_ARRAY;
    } else {
      Cargo[] result = new Cargo[sizeOfArrWithNotNullElems];
      int index = 0;
      for (Cargo cargo : cargos) {
        if (cargo != null) {
          result[index++] = cargo;
        }
      }

      return result;
    }
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
    for (Cargo cargo : storage.cargoArray) {
      if (cargo != null && id != null && id.equals(cargo.getId())) {
        return cargo;
      }
    }

    return null;
  }

  @Override
  public void save(Cargo cargo) {
    if (storage.cargoIndex == storage.cargoArray.length) {
      Cargo[] newCargos = new Cargo[storage.cargoArray.length * 2];
      ArrayUtils.copyArray(storage.cargoArray, newCargos);
      storage.cargoArray = newCargos;
    }

    cargo.setId(IdGenerator.generateId());
    storage.cargoArray[storage.cargoIndex] = cargo;
    storage.cargoIndex++;
  }

  @Override
  public boolean update(Cargo entity) {
    return true;
  }

  @Override
  public boolean deleteById(Long id) {
    Integer indexToDelete = findEntityIndexInArrayStorageById(storage.cargoArray, id);

    if (indexToDelete == null) {
      return false;
    } else {
      ArrayUtils.removeElement(storage.cargoArray, indexToDelete);
      return true;
    }
  }

  @Override
  public List<Cargo> getAll() {
    Cargo[] cargos = excludeNullableElementsFromArray(storage.cargoArray);
    return cargos.length == 0 ? Collections.emptyList() : Arrays.asList(storage.cargoArray);
  }

  @Override
  public int countAll() {
    return getAll().size();
  }

}
