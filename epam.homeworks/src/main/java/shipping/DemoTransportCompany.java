package shipping;

import shipping.cargo.domain.Cargo;
import shipping.cargo.domain.CargoType;
import shipping.cargo.domain.FoodCargo;
import shipping.cargo.domain.TypedCargo;
import shipping.cargo.repo.CargoRepo;
import shipping.cargo.repo.ICargoRepo;
import shipping.carrier.domain.Carrier;
import shipping.carrier.domain.CarrierType;
import shipping.carrier.repo.CarrierRepo;
import shipping.carrier.repo.ICarrierRepo;
import shipping.storage.Storage;
import shipping.transportation.domain.Transportation;

import java.util.Date;

public class DemoTransportCompany {

    public static void main(String[] args) {
		Cargo cargo1 = new TypedCargo(CargoType.FLOWER, "Rose", 100);
		Cargo cargo2 = new TypedCargo(CargoType.FLOWER, "Tulip", 200);
		Cargo cargo3 = new FoodCargo("Pizza", 300);

		Carrier carrier1 = new Carrier("Air");
		carrier1.setAddress("France");
		carrier1.setCarrierType(CarrierType.PLANE);

		Carrier carrier2 = new Carrier("See");
		carrier2.setAddress("Holland");
		carrier2.setCarrierType(CarrierType.SHIP);

		Carrier carrier3 = new Carrier("Road");
		carrier3.setAddress("Italy");
		carrier3.setCarrierType(CarrierType.TRAIN);

		Transportation transportation1 = new Transportation();
		transportation1.setCargo(cargo1);
		transportation1.setCarrier(carrier1);
		transportation1.setDescription("As fast as it is possible.");
		transportation1.setBillTo("1000");
		transportation1.setDate(new Date());
		carrier1.setTransportations(new Transportation[] { transportation1 });

		Transportation transportation2 = new Transportation();
		transportation2.setCargo(cargo2);
		transportation2.setCarrier(carrier2);
		transportation2.setDescription("As fast as it is possible.");
		transportation2.setBillTo("2000");
		transportation2.setDate(new Date());
		cargo2.setTransportations(new Transportation[] { transportation2 });
		carrier2.setTransportations(new Transportation[] { transportation2 });

		Transportation transportation3 = new Transportation();
		transportation3.setCargo(cargo3);
		transportation3.setCarrier(carrier3);
		transportation3.setDescription("As fast as it is possible.");
		transportation3.setBillTo("3000");
		transportation3.setDate(new Date());
		cargo3.setTransportations(new Transportation[] { transportation3 });

		Transportation transportation4 = new Transportation();
		transportation4.setCargo(cargo1);
		transportation4.setCarrier(carrier3);
		transportation4.setDescription("As fast as it is possible.");
		transportation4.setBillTo("3500");
		transportation4.setDate(new Date());
		cargo1.setTransportations(new Transportation[] { transportation1, transportation4 });
		carrier3.setTransportations(new Transportation[] { transportation3, transportation4 });

		Storage storage = new Storage();

		ICarrierRepo carrierRepo = new CarrierRepo(storage);
		ICargoRepo cargoRepo = new CargoRepo(storage);

		cargoRepo.add(cargo1);
		cargoRepo.add(cargo2);
		cargoRepo.add(cargo3);
		carrierRepo.add(carrier1);
		carrierRepo.add(carrier2);
		carrierRepo.add(carrier3);

		System.out.println("All cargo: \n");
		storage.printAllCargo();
		System.out.println();
		System.out.println("All carriers: \n");
		storage.printAllCarrier();
		System.out.println();
		System.out.println("Find 'Tulip' cargo in storage by id: \n");
		System.out.println(storage.getCargoById(cargo2.getId()));
		System.out.println();
		System.out.println("Find 'Pizza' cargo in storage by name: \n");
		System.out.println(storage.getCargoByName(cargo3.getName()));
		System.out.println();
		System.out.println("Find 'Air' carrier in storage by id: \n");
		System.out.println(storage.getCarrierById(carrier1.getId()));
		System.out.println();
		System.out.println("Find 'Road' carrier in storage by id: \n");
		System.out.println(storage.getCarrierById(carrier3.getId()));
		System.out.println();
		System.out.println("Find 'See' carrier in storage by name: \n");
		System.out.println(storage.getCarrierByName(carrier2.getName()));
		System.out.println();
		System.out.println("Find Transportation in storage by id: \n");
		System.out.println(storage.getTransportationById(transportation4.getId()));
		System.out.println();
		System.out.println("Number of cargo in storage: \n");
		System.out.println(storage.getAllCargo().length);
		System.out.println();
		System.out.println("Number of carriers in storage: \n");
		System.out.println(storage.getAllCarriers().length);
		System.out.println();

		System.out.println("Delete 'Tulip' cargo from storage:");
		storage.printAllCargo();
		System.out.println();
		cargoRepo.delete(cargo2);
		storage.printAllCargo();
		System.out.println();
		cargoRepo.add(cargo2);
		storage.printAllCargo();
    }
}
