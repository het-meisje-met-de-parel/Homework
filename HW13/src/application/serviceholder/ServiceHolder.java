package application.serviceholder;

import cargo.repo.impl.CargoArrayRepoImpl;
import cargo.repo.impl.CargoCollectionRepoImpl;
import cargo.service.CargoService;
import cargo.service.CargoServiceImpl;
import carrier.repo.impl.CarrierArrayRepoImpl;
import carrier.repo.impl.CarrierCollectionRepoImpl;
import carrier.service.CarrierService;
import carrier.service.CarrierServiceImpl;
import transportation.repo.impl.TransportationArrayRepoImpl;
import transportation.repo.impl.TransportationCollectionRepoImpl;
import transportation.service.TransportationService;
import transportation.service.TransportationServiceImpl;

public final class ServiceHolder {

  private static ServiceHolder instance = null;

  private final CarrierService carrierService;
  private final CargoService cargoService;
  private final TransportationService transportationService;

  private ServiceHolder(StorageType storageType) {
    SimpleServiceHolder initedServiceHolder = getInitedServiceHolder(storageType);
    cargoService = initedServiceHolder.cargoService;
    carrierService = initedServiceHolder.carrierService;
    transportationService = initedServiceHolder.transportationService;
  }

  public static void initServiceHolder(StorageType storageType) {
    ServiceHolder.instance = new ServiceHolder(storageType);
  }

  public static ServiceHolder getInstance() {
    return instance;
  }

  private static class SimpleServiceHolder {

    private final CarrierService carrierService;
    private final CargoService cargoService;
    private final TransportationService transportationService;

    public SimpleServiceHolder(
        CarrierService carrierService,
        CargoService cargoService,
        TransportationService transportationService) {
      this.carrierService = carrierService;
      this.cargoService = cargoService;
      this.transportationService = transportationService;
    }
  }

  private SimpleServiceHolder getInitedServiceHolder(StorageType storageType) {
    switch (storageType) {

      case ARRAY: {
        return new SimpleServiceHolder(
            new CarrierServiceImpl(new CarrierArrayRepoImpl()),
            new CargoServiceImpl(new CargoArrayRepoImpl()),
            new TransportationServiceImpl(new TransportationArrayRepoImpl()));
      }

      default: {
        return new SimpleServiceHolder(
            new CarrierServiceImpl(new CarrierCollectionRepoImpl()),
            new CargoServiceImpl(new CargoCollectionRepoImpl()),
            new TransportationServiceImpl(new TransportationCollectionRepoImpl()));
      }
    }
  }

  public CarrierService getCarrierService() {
    return carrierService;
  }

  public CargoService getCargoService() {
    return cargoService;
  }

  public TransportationService getTransportationService() {
    return transportationService;
  }
}
