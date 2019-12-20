package shipping.cargo.domain;

public class ClothCargo extends Cargo {

    public ClothCargo(String name, int weight) {
        super(CargoType.CLOTH, name, weight);
    }

    @Override
    public ClothCargo clone () {
        ClothCargo cargo = new ClothCargo (getName(), getWeight());
        cargo.setId (getId());
        return cargo;
    }

}
