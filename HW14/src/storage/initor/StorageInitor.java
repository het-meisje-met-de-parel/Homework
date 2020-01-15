package storage.initor;

import common.business.exception.checked.InitStorageException;

public interface StorageInitor {
  void initStorage() throws InitStorageException;
}
