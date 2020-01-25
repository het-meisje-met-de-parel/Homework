package common.solutions.utils;

import java.util.Collection;

public final class CollectionUtils {

  private CollectionUtils() {

  }

  public static void printCollection(Collection<?> collection) {
    collection.forEach(System.out::println);
  }

  public static boolean isNotEmpty(Collection<?> collection) {
    return collection != null && !collection.isEmpty();
  }

}
