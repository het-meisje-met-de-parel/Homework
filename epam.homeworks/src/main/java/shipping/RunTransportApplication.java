package shipping;

import shipping.cargo.domain.Cargo;
import shipping.cargo.domain.FoodCargo;
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
import shipping.transportation.domain.Transportation;
import shipping.transportation.repo.ITransportationRepo;
import shipping.transportation.repo.TransportationRepo;
import shipping.transportation.service.ITransportationsService;
import shipping.transportation.service.TransportationsService;

public class RunTransportApplication {
    
    public static ITransportationsService transportationsService;
    public static ICarriersService carriersService;
    public static ICargosService cargosService;
    
    public static void main (String [] args) {
        initialize ();
        
        Cargo cargo = new FoodCargo ("Apple", 150);
        cargosService.add (cargo);
        
        Carrier carrier = new Carrier ("Sea");
        carrier.setCarrierType (CarrierType.SHIP);
        carrier.setAddress ("Italy, Rome");
        carriersService.add (carrier);
        
        Transportation tr = transportationsService.createAndAdd (cargo, carrier);
        tr.setDescription ("Transportation for test");
        
        for (Transportation transportation : transportationsService.getAll ()) {
            System.out.println (transportation);
            System.out.println (transportation.getCargo ());
            System.out.println (transportation.getCarrier ());
        }
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
