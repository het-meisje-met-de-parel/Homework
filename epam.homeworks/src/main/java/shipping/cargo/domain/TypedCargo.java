package shipping.cargo.domain;

public class TypedCargo extends Cargo {

    public TypedCargo(CargoType cargoType, String name, int weight) {
        super(cargoType, name, weight);
    }

    @Override
    public TypedCargo clone () {
        TypedCargo cargo = new TypedCargo (getCargoType (), getName(), getWeight());
        cargo.setId (getId());
        return cargo;
    }

}
