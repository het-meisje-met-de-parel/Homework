package cargo.repo.impl;


import static common.business.repo.CommonRepoHelper.findEntityIndexInArrayStorageById;
import static storage.Storage.cargoArray;
import static storage.Storage.cargoIndex;

import cargo.domain.Cargo;
import cargo.search.CargoSearchCondition;
import carrier.domain.Carrier;
import common.solutions.utils.ArrayUtils;
import common.solutions.utils.CollectionUtils;
import storage.IdGenerator;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class CargoArrayRepoImpl extends CommonCargoRepo {

  private static final Cargo[] EMPTY_CARGO_ARRAY = new Cargo[0];

  @Override
  public Optional<Cargo> getByIdFetchingTransportations(long id) {
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
    Cargo[] result = new Cargo[cargoArray.length];

    int curIndex = 0;
    for (Cargo carrier : cargoArray) {
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
  public Optional<Cargo> findById(Long id) {
    for (Cargo cargo : cargoArray) {
      if (cargo != null && id != null && id.equals(cargo.getId())) {
        return Optional.of(cargo);
      }
    }

    return Optional.empty();
  }

  @Override
  public void save(Cargo cargo) {
    if (cargoIndex == cargoArray.length) {
      Cargo[] newCargos = new Cargo[cargoArray.length * 2];
      ArrayUtils.copyArray(cargoArray, newCargos);
      cargoArray = newCargos;
    }

    cargo.setId(IdGenerator.generateId());
    cargoArray[cargoIndex] = cargo;
    cargoIndex++;
  }

  @Override
  public boolean update(Cargo entity) {
    return true;
  }

  @Override
  public boolean deleteById(Long id) {
    Integer indexToDelete = findEntityIndexInArrayStorageById(cargoArray, id).orElse(null);

    if (indexToDelete == null) {
      return false;
    } else {
      ArrayUtils.removeElement(cargoArray, indexToDelete);
      return true;
    }
  }

  @Override
  public List<Cargo> getAll() {
    Cargo[] cargos = excludeNullableElementsFromArray(cargoArray);
    return cargos.length == 0 ? Collections.emptyList() : Arrays.asList(cargoArray);
  }

  @Override
  public int countAll() {
    return getAll().size();
  }

  @Override
  public void saveCargoAndCarrier (Cargo cargo, Carrier carrier) {
      throw new UnsupportedOperationException ();
  }

}
