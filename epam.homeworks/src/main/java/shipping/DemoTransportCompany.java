package shipping;

import shipping.cargo.Cargo;
import shipping.cargo.CargoType;
import shipping.carrier.Carrier;
import shipping.carrier.CarrierType;
import shipping.storage.Storage;
import shipping.transportation.Transportation;

import java.util.Date;

public class DemoTransportCompany {

    public static void main(String[] args){

        Cargo cargo1 = new Cargo();
        cargo1.setId(1L);
        cargo1.setName("Rose");
        cargo1.setWeight(100);
        cargo1.setCargoType(CargoType.FLOWER);


        Cargo cargo2 = new Cargo();
        cargo2.setId(2L);
        cargo2.setName("Tulip");
        cargo2.setWeight(200);
        cargo2.setCargoType(CargoType.FLOWER);

        Cargo cargo3 = new Cargo();
        cargo3.setId(3L);
        cargo3.setName("Pizza");
        cargo3.setWeight(300);
        cargo3.setCargoType(CargoType.FOOD);

        Carrier carrier1 = new Carrier();
        carrier1.setId(1L);
        carrier1.setName("Air");
        carrier1.setAddress("France");
        carrier1.setCarrierType(CarrierType.PLANE);

        Carrier carrier2 = new Carrier();
        carrier2.setId(2L);
        carrier2.setName("See");
        carrier2.setAddress("Holland");
        carrier2.setCarrierType(CarrierType.SHIP);

        Carrier carrier3 = new Carrier();
        carrier3.setId(3L);
        carrier3.setName("Road");
        carrier3.setAddress("Italy");
        carrier3.setCarrierType(CarrierType.TRAIN);

        Transportation transportation1 = new Transportation();
        transportation1.setId(1L);
        transportation1.setCargo(cargo1);
        transportation1.setCarrier(carrier1);
        transportation1.setDescription("As fast as it is possible.");
        transportation1.setBillTo("1000");
        transportation1.setDate(new Date());
        cargo1.setTransportations(new Transportation[] {transportation1});
        carrier1.setTransportations(new Transportation[] {transportation1});

        Transportation transportation2 = new Transportation();
        transportation2.setId(2L);
        transportation2.setCargo(cargo2);
        transportation2.setCarrier(carrier2);
        transportation2.setDescription("As fast as it is possible.");
        transportation2.setBillTo("2000");
        transportation2.setDate(new Date());
        cargo2.setTransportations(new Transportation[] {transportation2});
        carrier2.setTransportations(new Transportation[] {transportation2});

        Transportation transportation3 = new Transportation();
        transportation2.setId(3L);
        transportation2.setCargo(cargo3);
        transportation2.setCarrier(carrier3);
        transportation2.setDescription("As fast as it is possible.");
        transportation2.setBillTo("3000");
        transportation2.setDate(new Date());
        cargo3.setTransportations(new Transportation[] {transportation3});
        carrier3.setTransportations(new Transportation[] {transportation3});

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

    }
}
