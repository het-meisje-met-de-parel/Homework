package shipping.cargo.domain;

public class FoodCargo extends Cargo {

    public FoodCargo(String name, int weight) {
        super(CargoType.FOOD, name, weight);
    }

    @Override
    public FoodCargo clone () {
        FoodCargo cargo = new FoodCargo (getName(), getWeight());
        cargo.setId (getId());
        return cargo;
    }

}
