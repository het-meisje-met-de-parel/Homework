package shipping.cargo.domain;

public class ClothCargo extends Cargo {

    public ClothCargo(String name, int weight) {
        super(CargoType.CLOTH, name, weight);
    }

}
