package shipping.cargo.repo;

import shipping.cargo.domain.Cargo;
import shipping.storage.Storage;

public class CargoRepo implements ICargoRepo {

    private final Storage storage;

    public CargoRepo(Storage storage) {
	this.storage = storage;
    }

    @Override
    public void add(Cargo cargo) {
	storage.addCargo(cargo);
    }

    @Override
    public Cargo delete(Cargo cargo) {
	return storage.deleteCargo(cargo);
    }

    @Override
    public Cargo get(Long id) {
	return storage.getCargoById(id);
    }

    @Override
    public Cargo get(String name) {
	return storage.getCargoByName(name);
    }

    @Override
    public Cargo[] getAll() {
	return storage.getAllCargo();
    }

}
