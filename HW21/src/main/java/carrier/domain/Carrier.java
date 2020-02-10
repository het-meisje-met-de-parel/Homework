package carrier.domain;


import common.business.domain.BaseEntity;

public class Carrier extends BaseEntity {

  private String name;
  private String address;
  private CarrierType carrierType;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public CarrierType getCarrierType() {
    return carrierType;
  }

  public void setCarrierType(CarrierType carrierType) {
    this.carrierType = carrierType;
  }

  @Override
  public String toString() {
    return "Carrier{" +
        "id='" + id + '\'' +
        "name='" + name + '\'' +
        ", address='" + address + '\'' +
        ", carrierType=" + carrierType +
        '}';
  }
}
