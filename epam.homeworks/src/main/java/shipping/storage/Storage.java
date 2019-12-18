package shipping.storage;

import shipping.cargo.domain.Cargo;
import shipping.carrier.domain.Carrier;
import shipping.transportation.domain.Transportation;

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
    
        public Cargo deleteCargo(Cargo cargo) {
        int index = -1;
        for (int i = 0; i < cargoIndex; i++) {
            if (massCargo[i].getId() == cargo.getId()) {
            index = i;
            break;
            }
        }

        if (index == -1) {
            return null;
        }
        Cargo value = massCargo[index];

        if (index != cargoIndex - 1) {
            System.arraycopy(massCargo, index + 1, massCargo, index, cargoIndex - index);
        }
        cargoIndex--;
        return value;
    }

    public void addCarrier(Carrier carrier){
        if (massCarrier.length == carrierIndex){
            massCarrier = Arrays.copyOf(massCarrier, carrierIndex *2);
        }
        massCarrier[carrierIndex] = carrier;
        carrierIndex++;
    }
    
    public Carrier deleteCarrier(Carrier carrier) {
	int index = -1;
	for (int i = 0; i < carrierIndex; i++) {
	    if (massCarrier[i].getId() == carrier.getId()) {
		index = i;
		break;
	    }
	}

	if (index == -1) {
	    return null;
	}
	Carrier value = massCarrier[index];

	if (index != cargoIndex - 1) {
	    System.arraycopy(massCarrier, index + 1, massCarrier, index, carrierIndex - index);
	}
	carrierIndex--;
	return value;
    }

    public void printAllCargo(){
        for (int a = 0; (a < cargoIndex); a++){
            System.out.println(massCargo [a]);
        }
    }

    public void printAllCarrier(){
        for (int b = 0; (b < carrierIndex); b++){
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

}
