package common.business.service;

import java.util.List;
import java.util.Optional;

public interface CommonService<TYPE, ID> {
  TYPE findById(ID id);

  void save(TYPE entity);

  boolean update(TYPE entity);

  boolean deleteById(ID id);

  List<TYPE> getAll();

  default int countAll() {
    return Optional.ofNullable(getAll()).orElse(List.of()).size();
  }

  default void printAll() {
    Optional.ofNullable(getAll()).orElse(List.of()).forEach(System.out::println);
  }
}
