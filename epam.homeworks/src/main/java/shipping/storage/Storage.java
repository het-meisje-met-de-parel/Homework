package shipping.storage;

import shipping.cargo.Cargo;
import shipping.carrier.Carrier;
import shipping.transportation.Transportation;

import java.util.Arrays;

public class Storage {

    private Cargo [] massCargo = new Cargo[1];
    private int cargoIndex = 0;

    private Carrier [] massCarrier = new Carrier[1];
    private int carrierIndex = 0;


    public void addCargo(Cargo cargo){
        if (massCargo.length == cargoIndex){
            massCargo = Arrays.copyOf(massCargo, cargoIndex *2);
        }
        massCargo[cargoIndex] = cargo;
        cargoIndex++;
    }

    public void addCarrier(Carrier carrier){
        if (massCarrier.length == carrierIndex){
            massCarrier = Arrays.copyOf(massCarrier, carrierIndex *2);
        }
        massCarrier[carrierIndex] = carrier;
        carrierIndex++;
    }

    public void printAllCargo(){
        for (int a = 0; (a < cargoIndex); a++){
            /*
            System.out.println("Id = " + massCargo[a].getId() +
                    "\nname = " + massCargo[a].getName() +
                    "\nweight = " + massCargo[a].getWeight() +
                    "\ncargoType = " + massCargo[a].getCargoType() +
                    "\nTransportation = " + massCargo[a].getTransportations().length);
             */
            System.out.println(massCargo [a]);
        }
    }

    public void printAllCarrier(){
        for (int b = 0; (b < carrierIndex); b++){
            /*
            System.out.println("Id = " + massCarrier[b].getId() +
                    "\nname = " + massCarrier[b].getName() +
                    "\naddress = " + massCarrier[b].getAddress() +
                    "\ncarrierType = " + massCarrier[b].getCarrierType() +
                    "\nTransportation = " + massCarrier[b].getTransportations().length);
             */
            System.out.println(massCarrier [b]);
        }
    }

    public Cargo getCargoById (Long id) {
        for (int i = 0; i < cargoIndex; i++) {
            if (massCargo [i].getId() == id) {
                return massCargo [i];
            }
        }
        return null;
    }

    public Cargo getCargoByName (String name) {
        for (int i = 0; i < cargoIndex; i++) {
            if (massCargo [i].getName().equals(name)) {
                return massCargo [i];
            }
        }
        return null;
    }

    public Cargo [] getAllCargo () {
        return Arrays.copyOf(massCargo, cargoIndex);
    }

    public Carrier getCarrierById (Long id) {
        for (int i = 0; i < carrierIndex; i++) {
            if (massCarrier [i].getId() == id) {
                return massCarrier [i];
            }
        }
        return null;
    }

    public Carrier getCarrierByName (String name) {
        for (int i = 0; i < carrierIndex; i++) {
            if (massCarrier [i].getName().equals(name)) {
                return massCarrier [i];
            }
        }
        return null;
    }

    public Carrier [] getAllCarriers () {
        return Arrays.copyOf(massCarrier, carrierIndex);
    }

    public Transportation getTransportationById (Long id) {
        for (int i = 0; i < cargoIndex; i++) {
            Transportation [] trans = massCargo [i].getTransportations();
            for (int j = 0; j < trans.length; j++) {
                if (trans [j].getId() == id) {
                    return trans [j];
                }
            }
        }
        for (int i = 0; i < carrierIndex; i++) {
            Transportation [] trans = massCarrier [i].getTransportations();
            for (int j = 0; j < trans.length; j++) {
                if (trans [j].getId() == id) {
                    return trans [j];
                }
            }
        }
        return null;
    }

}
