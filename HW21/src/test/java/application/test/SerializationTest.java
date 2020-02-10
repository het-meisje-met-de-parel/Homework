package application.test;


import static common.solutions.comparator.SimpleComparator.*;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import cargo.domain.ClothersCargo;
import cargo.domain.FoodCargo;

public class SerializationTest {

  private Path tempFile = null;

  @BeforeEach
  public void createTempFile() throws IOException {
    tempFile = Files.createTempFile("temp", "test");
  }

  @AfterEach
  public void deleteTempFile() {
    deleteFile(tempFile);
  }

  @Test
  public void testSerializationFoodCargo() throws Exception {
    FoodCargo cargo = prepareFood();
    String pathToFile = tempFile.toAbsolutePath().toString();

    serializeToFile(cargo, pathToFile);
    FoodCargo deserialized = readSerializedObjectFromFile(pathToFile);


    Assertions.assertTrue(areFoodEntitiesEquals(cargo, deserialized));
  }

  @Test
  public void testSerializationFoodCargos() throws Exception {
    List<FoodCargo> foods = Arrays.asList(prepareFood(), prepareFood());
    String pathToFile = tempFile.toAbsolutePath().toString();
    serializeToFile(foods, pathToFile);
    List<FoodCargo> deserialized = readSerializedObjectFromFile(pathToFile);

    Assertions.assertTrue(areFoodEntitiesEquals(foods, deserialized));
  }

  @Test
  public void testSerializationFoodNullCargo() throws Exception {
    String pathToFile = tempFile.toAbsolutePath().toString();
    serializeToFile(null, pathToFile);
    Object deserialized = readSerializedObjectFromFile(pathToFile);

    Assertions.assertNull(deserialized);
  }

  @Test
  public void testSerializationClothersCargo() throws Exception {
    ClothersCargo clothers = prepareClothers();
    String pathToFile = tempFile.toAbsolutePath().toString();
    serializeToFile(clothers, pathToFile);
    ClothersCargo deserialized = readSerializedObjectFromFile(pathToFile);

    Assertions.assertTrue(areClotherEntitiesEquals(clothers, deserialized));
  }

  @Test
  public void testSerializationClothersCargos() throws Exception {
    List<ClothersCargo> clothers = Arrays.asList(prepareClothers(), prepareClothers());
    String pathToFile = tempFile.toAbsolutePath().toString();

    serializeToFile(clothers, pathToFile);
    List<ClothersCargo> deserialized = readSerializedObjectFromFile(pathToFile);

    Assertions.assertTrue(areClotherEntitiesEquals(clothers, deserialized));
  }

  @Test
  public void testSerializationClothersNullCargos() throws Exception {
    String pathToFile = tempFile.toAbsolutePath().toString();
    serializeToFile(null, pathToFile);
    Object deserialized = readSerializedObjectFromFile(pathToFile);

    Assertions.assertNull(deserialized);
  }

  private <T> void serializeToFile(T entity, String file) throws Exception {
    try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(file))) {
      outputStream.writeObject(entity);
    }
  }

  private <T> T readSerializedObjectFromFile(String file) throws Exception {
    try (ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(file))) {
      return (T) objectInputStream.readObject();
    }
  }

  private FoodCargo prepareFood() {
    FoodCargo food = new FoodCargo();
    food.setId(randLong());
    food.setName(randString());
    food.setWeight(randInt());
    food.setStoreTemperature(randInt());
    food.setExpirationDate(LocalDate.now());

    return food;
  }

  private ClothersCargo prepareClothers() {
    ClothersCargo clothers = new ClothersCargo();
    clothers.setName(randString());
    clothers.setId(randLong());
    clothers.setSize(randString());
    clothers.setWeight(randInt());
    clothers.setMaterial(randString());

    return clothers;
  }

  private String randString() {
    return "dsge4";
  }

  private int randInt() {
    return 89;
  }

  private long randLong() {
    return -43L;
  }

  private boolean areFoodEntitiesEquals(FoodCargo food1, FoodCargo food2) {
    if (food1 == null && food2 == null) {
      return true;
    } else if (food1 != null && food2 == null) {
      return false;
    } else if (food1 == null) {
      return false;
    } else {
      return STRING_COMPARATOR.compare(food1.getName(), food2.getName()) == 0
          && LONG_COMPARATOR.compare(food1.getId(), food2.getId()) == 0
          && food1.getWeight() == food2.getWeight()
          && food1.getStoreTemperature() == food2.getStoreTemperature();
      //continue in this way
    }
  }

  private boolean areFoodEntitiesEquals(List<FoodCargo> foods1, List<FoodCargo> foods2) {
    if (foods1 == null && foods2 == null) {
      return true;
    } else if (foods1 != null && foods2 == null) {
      return false;
    } else if (foods1 == null) {
      return false;
    } else if (foods1.size() != foods2.size()) {
      return false;
    } else {
      for (int i = 0; i < foods1.size(); i++) {
        if (!areFoodEntitiesEquals(foods1.get(i), foods2.get(i))) {
          return false;
        }
      }

      return true;
    }
  }

  private boolean areClotherEntitiesEquals(ClothersCargo clother1, ClothersCargo clother2) {
    if (clother1 == null && clother2 == null) {
      return true;
    } else if (clother1 != null && clother2 == null) {
      return false;
    } else if (clother1 == null) {
      return false;
    } else {
      return STRING_COMPARATOR.compare(clother1.getName(), clother2.getName()) == 0
          && LONG_COMPARATOR.compare(clother1.getId(), clother2.getId()) == 0
          && STRING_COMPARATOR.compare(clother1.getMaterial(), clother2.getMaterial()) == 0;
      //continue in this way
    }
  }

  private boolean areClotherEntitiesEquals(List<ClothersCargo> clothers1,
      List<ClothersCargo> clothers2) {
    if (clothers1 == null && clothers2 == null) {
      return true;
    } else if (clothers1 != null && clothers2 == null) {
      return false;
    } else if (clothers1 == null) {
      return false;
    } else if (clothers1.size() != clothers2.size()) {
      return false;
    } else {
      for (int i = 0; i < clothers1.size(); i++) {
        if (!areClotherEntitiesEquals(clothers1.get(i), clothers2.get(i))) {
          return false;
        }
      }

      return true;
    }
  }

  private void deleteFile(Path path) {
    if (path != null && path.toFile().isFile()) {
      try {
        Files.delete(path);
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }
}
