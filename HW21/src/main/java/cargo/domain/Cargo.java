package cargo.domain;

import common.business.domain.BaseEntity;

public abstract class Cargo extends BaseEntity {

  protected String name;
  protected int weight;
  protected CargoType cargoType;

  public Cargo() {
    cargoType = this.getCargoType();
  }

  public abstract CargoType getCargoType();

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public int getWeight() {
    return weight;
  }

  public void setWeight(int weight) {
    this.weight = weight;
  }

  @Override
  public String toString() {
    return "Cargo{" +
        "id=" + id +
        ", name='" + name + '\'' +
        ", weight=" + weight +
        ", cargoType=" + cargoType +
        '}';
  }
}
