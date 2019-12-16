package shipping.carrier.repo;

import shipping.carrier.domain.Carrier;
import shipping.storage.Storage;

public class CarrierRepo implements ICarrierRepo {

    private final Storage storage;

    public CarrierRepo(Storage storage) {
	this.storage = storage;
    }

    @Override
    public void add(Carrier carrier) {
	storage.addCarrier(carrier);
    }

    @Override
    public Carrier delete(Carrier carrier) {
	return storage.deleteCarrier(carrier);
    }

    @Override
    public Carrier get(Long id) {
	return storage.getCarrierById(id);
    }

    @Override
    public Carrier get(String name) {
	return storage.getCarrierByName(name);
    }

    @Override
    public Carrier[] getAll() {
	return storage.getAllCarriers();
    }

}
