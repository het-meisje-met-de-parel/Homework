package shipping;

import shipping.cargo.Cargo;
import shipping.cargo.CargoType;
import shipping.cargo.FoodCargo;
import shipping.cargo.TypedCargo;
import shipping.carrier.Carrier;
import shipping.carrier.CarrierType;
import shipping.storage.Storage;
import shipping.transportation.Transportation;

import java.util.Date;

public class DemoTransportCompany {

    public static void main(String[] args){

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
        carrier1.setTransportations(new Transportation[] {transportation1});

        Transportation transportation2 = new Transportation();
        transportation2.setCargo(cargo2);
        transportation2.setCarrier(carrier2);
        transportation2.setDescription("As fast as it is possible.");
        transportation2.setBillTo("2000");
        transportation2.setDate(new Date());
        cargo2.setTransportations(new Transportation[] {transportation2});
        carrier2.setTransportations(new Transportation[] {transportation2});

        Transportation transportation3 = new Transportation();
        transportation3.setCargo(cargo3);
        transportation3.setCarrier(carrier3);
        transportation3.setDescription("As fast as it is possible.");
        transportation3.setBillTo("3000");
        transportation3.setDate(new Date());
        cargo3.setTransportations(new Transportation[] {transportation3});

        Transportation transportation4 = new Transportation();
        transportation4.setCargo(cargo1);
        transportation4.setCarrier(carrier3);
        transportation4.setDescription("As fast as it is possible.");
        transportation4.setBillTo("3500");
        transportation4.setDate(new Date());
        cargo1.setTransportations(new Transportation[] {transportation1, transportation4});
        carrier3.setTransportations(new Transportation[] {transportation3, transportation4});

        Storage storage = new Storage();
        storage.addCargo(cargo1);
        storage.addCargo(cargo2);
        storage.addCargo(cargo3);
        storage.addCarrier(carrier1);
        storage.addCarrier(carrier2);
        storage.addCarrier(carrier3);
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
        System.out.println("Find 'Air' cargo in storage by id: \n");
        System.out.println(storage.getCarrierById(carrier1.getId()));
        System.out.println();
        System.out.println("Find 'Road' cargo in storage by id: \n");
        System.out.println(storage.getCarrierById(carrier3.getId()));
        System.out.println();
        System.out.println("Find 'See' cargo in storage by name: \n");
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
    }
}
