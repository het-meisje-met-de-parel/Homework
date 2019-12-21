package shipping;

import shipping.cargo.domain.*;
import shipping.cargo.repo.CargoRepo;
import shipping.cargo.repo.ICargoRepo;
import shipping.cargo.service.CargosService;
import shipping.cargo.service.ICargosService;
import shipping.carrier.domain.Carrier;
import shipping.carrier.domain.CarrierType;
import shipping.carrier.repo.CarrierRepo;
import shipping.carrier.repo.ICarrierRepo;
import shipping.carrier.service.CarriersService;
import shipping.carrier.service.ICarriersService;
import shipping.exceptions.NotDeletedException;
import shipping.exceptions.NotExistingEntityExeption;
import shipping.transportation.domain.Transportation;
import shipping.transportation.repo.ITransportationRepo;
import shipping.transportation.repo.TransportationRepo;
import shipping.transportation.service.ITransportationsService;
import shipping.transportation.service.TransportationsService;

import java.io.IOException;

public class RunTransportApplication {
    
    public static ITransportationsService transportationsService;
    public static ICarriersService carriersService;
    public static ICargosService cargosService;
    
    public static void main (String [] args) {
        initialize ();
        
        Cargo cargo = new FoodCargo ("Apple", 175);
        cargosService.add (cargo);
        Cargo testCargo = cargo;
        
        cargo = new FoodCargo ("Bannana", 250);
        cargosService.add (cargo);
        
        cargo = new FoodCargo ("Apple", 150);
        cargosService.add (cargo);
        
        cargo = new TypedCargo (CargoType.OTHER, "Barrel", 3500);
        cargosService.add (cargo);
        
        cargo = new ClothCargo ("Coat", 1150);
        cargosService.add (cargo);
        
        Carrier carrier = new Carrier ("Sea");
        carrier.setCarrierType (CarrierType.SHIP);
        carrier.setAddress ("Italy, Rome");
        carriersService.add (carrier);
        
        try {
            Transportation tr = transportationsService.createAndAdd (testCargo, carrier);
            tr.setDescription ("Transportation for test");
        }catch (NotExistingEntityExeption neee){
            System.err.println(neee.getMessage());
        }

        System.out.println ("All transportations:");
        System.out.println ();
        for (Transportation transportation : transportationsService.getAll ()) {
            System.out.println (transportation);
            System.out.println (transportation.getCargo ());
            System.out.println (transportation.getCarrier ());
        }
        
        System.out.println ();
        System.out.println ("Sorted by name and weight cargos:");
        System.err.println ();
        for (Cargo tmpCargo : cargosService.getSortedByNameAndWeight ()) {
            System.out.println (tmpCargo);
        }

        Cargo nonExistingCargo = new FoodCargo ("Fruit", 5);
        try {
            cargosService.delete (nonExistingCargo);
        } catch (NotDeletedException nde) {
            System.err.println (nde);
        }
        System.out.println("Код после исключения");
    }
    
    public static void initialize () {
        ITransportationRepo transportationRepo = new TransportationRepo ();
        ICarrierRepo carrierRepo = new CarrierRepo ();
        ICargoRepo cargoRepo = new CargoRepo ();
        
        carriersService = new CarriersService (carrierRepo);
        cargosService = new CargosService (cargoRepo);
        
        transportationsService = new TransportationsService (transportationRepo);
        transportationsService.setCarriersService (carriersService);
        transportationsService.setCargosService (cargosService);
    }
    
}
