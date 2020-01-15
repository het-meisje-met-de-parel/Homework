package carrier.repo.impl;


import carrier.domain.Carrier;
import carrier.repo.CarrierRepo;
import common.solutions.utils.ArrayUtils;
import storage.IdGenerator;
import storage.Storage;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import static common.business.repo.CommonRepoHelper.findEntityIndexInArrayStorageById;

public class CarrierArrayRepoImpl implements CarrierRepo {

  private static final Carrier[] EMPTY_CARRIER_ARRAY = new Carrier[0];
  private static Storage storage = Storage.getInstance ();

  @Override
  public void save(Carrier carrier) {
    if (storage.carrierIndex == storage.carrierArray.length) {
      Carrier[] newCarriers = new Carrier[storage.carrierArray.length * 2];
      ArrayUtils.copyArray(storage.carrierArray, newCarriers);
      storage.carrierArray = newCarriers;
    }

    carrier.setId(IdGenerator.generateId());
    storage.carrierArray[storage.carrierIndex] = carrier;
    storage.carrierIndex++;
  }

  @Override
  public boolean update(Carrier carrier) {
    return true;
  }

  @Override
  public Carrier getByIdFetchingTransportations(long id) {
    return findById(id);
  }

  @Override
  public Carrier[] findByName(String name) {
    Carrier[] searchResultWithNullableElems = getByNameIncludingNullElements(name);
    if (searchResultWithNullableElems == null || searchResultWithNullableElems.length == 0) {
      return EMPTY_CARRIER_ARRAY;
    } else {
      return excludeNullableElementsFromArray(searchResultWithNullableElems);
    }
  }

  private Carrier[] getByNameIncludingNullElements(String name) {
    Carrier[] result = new Carrier[storage.carrierArray.length];

    int curIndex = 0;
    for (Carrier carrier : storage.carrierArray) {
      if (carrier != null && Objects.equals(carrier.getName(), name)) {
        result[curIndex++] = carrier;
      }
    }

    return result;
  }


  private Carrier[] excludeNullableElementsFromArray(Carrier[] carriers) {
    int sizeOfArrWithNotNullElems = 0;

    for (Carrier carrier : carriers) {
      if (carrier != null) {
        sizeOfArrWithNotNullElems++;
      }
    }

    if (sizeOfArrWithNotNullElems == 0) {
      return EMPTY_CARRIER_ARRAY;
    } else {
      Carrier[] result = new Carrier[sizeOfArrWithNotNullElems];
      int index = 0;
      for (Carrier carrier : carriers) {
        if (carrier != null) {
          result[index++] = carrier;
        }
      }

      return result;
    }
  }

  @Override
  public List<Carrier> getAll() {
    Carrier[] carriers = excludeNullableElementsFromArray(storage.carrierArray);
    return carriers.length == 0 ? Collections.emptyList() : Arrays.asList(storage.carrierArray);
  }

  @Override
  public int countAll() {
    return this.getAll().size();
  }

  @Override
  public Carrier findById(Long id) {
    for (Carrier carrier : storage.carrierArray) {
      if (carrier != null && carrier.getId().equals(id)) {
        return carrier;
      }
    }

    return null;
  }

  @Override
  public boolean deleteById(Long id) {
    Integer indexToDelete = findEntityIndexInArrayStorageById(storage.carrierArray, id);

    if (indexToDelete == null) {
      return false;
    } else {
      ArrayUtils.removeElement(storage.carrierArray, indexToDelete);
      return true;
    }
  }
}
