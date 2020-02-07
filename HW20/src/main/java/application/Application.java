package application;

import static cargo.domain.CargoField.*;
import static common.solutions.search.OrderType.*;
import static java.util.Collections.*;
import static storage.initor.StorageInitorFactory.*;

import java.time.LocalDate;
import java.util.*;

import application.serviceholder.ServiceHolder;
import application.serviceholder.StorageType;
import cargo.domain.Cargo;
import cargo.domain.CargoField;
import cargo.domain.ClothersCargo;
import cargo.domain.FoodCargo;
import cargo.search.CargoSearchCondition;
import cargo.service.CargoService;
import carrier.service.CarrierService;
import common.business.exception.checked.InitStorageException;
import common.business.exception.checked.ReportException;
import common.solutions.search.OrderType;
import common.solutions.utils.CollectionUtils;
import reporting.ReportDefaultService;
import reporting.ReportService;
import storage.initor.InitStorageType;
import storage.initor.StorageInitor;
import transportation.service.TransportationService;

public class Application {

  private static final String SEPARATOR = "--------------";
  private static CargoService cargoService;
  private static CarrierService carrierService;
  private static TransportationService transportationService;

  public static void main(String[] args) {
    try {
      ServiceHolder.initServiceHolder(StorageType.COLLECTION);
      carrierService = ServiceHolder.getInstance().getCarrierService();
      transportationService = ServiceHolder.getInstance().getTransportationService();
      cargoService = ServiceHolder.getInstance().getCargoService();

      StorageInitor storageInitor = getStorageInitor(InitStorageType.XML_SAX_FILE);
      storageInitor.initStorage();

      testTransactionalSave ();
      printStorageData();
      demoSearchOperations();
      demoSortOperations();
      demoExceptions();

      demoReportService();

      System.out.println();
      System.out.println("Тестирование большого стрима");
      System.out.println(cargoService.getUniqueExpirationDatesOfFoodCargosAfterDate(LocalDate.now()));
    } catch (InitStorageException e) {
      e.printStackTrace();
    }catch (Exception e){
      e.printStackTrace();
    }
  }

  private static void testTransactionalSave () {
      var cargo1 = new FoodCargo ();
      cargo1.setName ("MELON");
      cargo1.setWeight (1623);
      cargo1.setExpirationDate (LocalDate.now ().plusWeeks (11));
      cargo1.setStoreTemperature (2);
      
      var cargo2 = new ClothersCargo ();
      cargo2.setName ("SLIPPERS");
      cargo2.setWeight (743);
      cargo2.setSize ("38");
      cargo2.setMaterial ("WOOL");
      
      cargoService.saveSeveral (List.of (cargo1, cargo2));
  }

  private static void demoSearchOperations() {
    System.out.println("SEARCH CARGO BY ID = 1");
    System.out.println(cargoService.findById(1L));
    printSeparator();

    System.out.println("SEARCH CARRIER BY ID = 8");
    System.out.println(carrierService.findById(8L));
    printSeparator();

    System.out.println("SEARCH CARGOES BY NAME = 'Clothers_Name_1'");
    CollectionUtils.printCollection(cargoService.findByName("Clothers_Name_1"));
    printSeparator();

    System.out.println("SEARCH CARRIERS BY NAME = 'Carrier_Name'");
    CollectionUtils.printCollection(carrierService.findByName("Carrier_Name"));
  }

  private static void printStorageData() {
    System.out.println("ALL CARGOS");
    cargoService.printAll();
    printSeparator();

    System.out.println("ALL CARRIERS");
    carrierService.printAll();
    printSeparator();

    System.out.println("ALL TRANSPOORTATIONS");
    transportationService.printAll();
    printSeparator();
  }

  private static void printSeparator() {
    System.out.println(SEPARATOR);
  }

  private static void demoSortOperations() {
    demoCargoSorting(singletonList(NAME), ASC);
    demoCargoSorting(singletonList(NAME), DESC);

    demoCargoSorting(singletonList(WEIGHT), ASC);
    demoCargoSorting(singletonList(WEIGHT), DESC);

    demoCargoSorting(Arrays.asList(NAME, WEIGHT), ASC);
    demoCargoSorting(Arrays.asList(NAME, WEIGHT), DESC);
  }

  private static String getOrderingConditionsAsString(CargoSearchCondition condition) {
    StringBuilder result = new StringBuilder();
    result.append(" ORDER BY ");

    Iterator<CargoField> iter = condition.getSortFields().iterator();
    while (iter.hasNext()) {
      CargoField fld = iter.next();
      result.append(fld);

      if (iter.hasNext()) {
        result.append(",");
      }
    }

    result.append(" ").append(condition.getOrderType());

    return result.toString();
  }

  private static void demoCargoSorting(Collection<CargoField> sortFields, OrderType orderType) {
    CargoSearchCondition cargoSearchCondition = new CargoSearchCondition();
    cargoSearchCondition.setOrderType(orderType);
    cargoSearchCondition.setSortFields(new LinkedHashSet<>(sortFields));
    System.out.println(
        "---------Sorting '" + getOrderingConditionsAsString(cargoSearchCondition) + "'------");
    CollectionUtils.printCollection (cargoService.search(cargoSearchCondition));
    System.out.println();
  }

  private static void demoExceptions() {
    System.out.println("------Demo  exceptions------------");
    Long firstCargo = cargoService.getAll().get(0).getId();
    Optional<Cargo> cargoOptional = cargoService.getByIdFetchingTransportations(firstCargo);

    if (cargoOptional.isPresent()) {
      Cargo cargo = cargoOptional.get();

      System.out.println("Try to delete cargo");
      System.out.println("Cargo details:");
      System.out.println("id: " + cargo.getId());
      System.out.println("name: " + cargo.getName());
      System.out.println("total transportations: " + (cargo.getTransportations() != null ? cargo
          .getTransportations().size() : 0));
      System.out.println();
      try {
        cargoService.deleteById(cargo.getId());
      } catch (Exception e) {
        System.out.println("OOPS, something went wrong!");
        System.out.println(e.getMessage());
      }
    }
  }

  private static void demoReportService() throws ReportException {
    System.out.println("----------Demo report service ---------------");
    ReportService reportService = new ReportDefaultService(
        cargoService, carrierService, transportationService
    );
    reportService.exportData();
  }
}
