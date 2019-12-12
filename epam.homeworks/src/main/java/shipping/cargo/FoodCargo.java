package shipping.cargo;

public class FoodCargo extends Cargo {

    public FoodCargo(String name, int weight) {
        super(CargoType.FOOD, name, weight);
    }

}
