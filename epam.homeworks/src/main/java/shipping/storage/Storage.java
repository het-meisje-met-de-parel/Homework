package shipping.storage;

import shipping.cargo.Cargo;
import shipping.carrier.Carrier;

import java.util.Arrays;

public class Storage {

    Cargo [] massCargo = new Cargo[1];
    Carrier [] massCarrier = new Carrier[1];
    int i = 0;
    int j = 0;


    public void addCargo(Cargo cargo){
        if (massCargo.length == i){
            massCargo = Arrays.copyOf(massCargo, i*2);
        }
        massCargo[i] = cargo;
        i++;
    }

    public void addCarrier(Carrier carrier){
        if (massCarrier.length == j){
            massCarrier = Arrays.copyOf(massCarrier, j*2);
        }
        massCarrier[j] = carrier;
        j++;
    }

    public void printAllCargo(){
        for (int a = 0; (a < i); a++){
            System.out.println("Id = " + massCargo[a].getId() +
                    "\nname = " + massCargo[a].getName() +
                    "\nweight = " + massCargo[a].getWeight() +
                    "\ncargoType = " + massCargo[a].getCargoType() +
                    "\nTransportation = " + massCargo[a].getTransportations().length);
        }
    }

    public void printAllCarrier(){
        for (int b = 0; (b < j); b++){
            System.out.println("Id = " + massCarrier[b].getId() +
                    "\nname = " + massCarrier[b].getName() +
                    "\naddress = " + massCarrier[b].getAddress() +
                    "\ncarrierType = " + massCarrier[b].getCarrierType() +
                    "\nTransportation = " + massCarrier[b].getTransportations().length);
        }
    }

}
