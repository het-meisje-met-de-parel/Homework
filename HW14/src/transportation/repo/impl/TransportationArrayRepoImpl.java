package transportation.repo.impl;


import common.solutions.utils.ArrayUtils;
import storage.IdGenerator;
import storage.Storage;
import transportation.domain.Transportation;
import transportation.repo.TransportationRepo;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static common.business.repo.CommonRepoHelper.findEntityIndexInArrayStorageById;

public class TransportationArrayRepoImpl implements TransportationRepo {

  private static final Transportation[] EMPTY_TRANSPORTATION_ARRAY = new Transportation[0];
  private static Storage storage = Storage.getInstance ();

  @Override
  public void save(Transportation transportation) {
    if (storage.transportationIndex == storage.transportationArray.length) {
      Transportation[] newTransportations =
          new Transportation[storage.transportationArray.length * 2];
      ArrayUtils.copyArray(storage.transportationArray, newTransportations);
      storage.transportationArray = newTransportations;
    }

    transportation.setId(IdGenerator.generateId());
    storage.transportationArray[storage.transportationIndex] = transportation;
    storage.transportationIndex++;
  }

  @Override
  public Transportation findById(Long id) {
    for (Transportation transportation : storage.transportationArray) {
      if (transportation != null && transportation.getId().equals(id)) {
        return transportation;
      }
    }

    return null;
  }

  @Override
  public List<Transportation> getAll() {
    Transportation[] transportations = excludeNullableElementsFromArray(storage.transportationArray);
    return transportations.length == 0 ? Collections.emptyList()
        : Arrays.asList(storage.transportationArray);
  }

  @Override
  public int countAll() {
    return getAll().size();
  }

  private Transportation[] excludeNullableElementsFromArray(Transportation[] transportations) {
    int sizeOfArrWithNotNullElems = 0;

    for (Transportation transportation : transportations) {
      if (transportation != null) {
        sizeOfArrWithNotNullElems++;
      }
    }

    if (sizeOfArrWithNotNullElems == 0) {
      return EMPTY_TRANSPORTATION_ARRAY;
    } else {
      Transportation[] result = new Transportation[sizeOfArrWithNotNullElems];
      int index = 0;
      for (Transportation transportation : transportations) {
        if (transportation != null) {
          result[index++] = transportation;
        }
      }

      return result;
    }
  }


  @Override
  public boolean update(Transportation transportation) {
    return true;
  }

  @Override
  public boolean deleteById(Long id) {
    Integer indexToDelete = findEntityIndexInArrayStorageById(storage.transportationArray, id);

    if (indexToDelete == null) {
      return false;
    } else {
      ArrayUtils.removeElement(storage.transportationArray, indexToDelete);
      return true;
    }
  }
}
