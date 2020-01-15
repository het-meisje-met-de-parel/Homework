package storage;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import cargo.domain.Cargo;
import carrier.domain.Carrier;
import transportation.domain.Transportation;

public class Storage implements Serializable {

  private static final long serialVersionUID = -6360320407911974455L;

  private static final int ARRAY_CAPACITY = 10;
  
  private static final Storage INSTANCE = new Storage ();
  
  public static Storage getInstance () {
      return INSTANCE;
  }
  
  private Storage () {}

  public Cargo[] cargoArray = new Cargo[ARRAY_CAPACITY];
  public int cargoIndex = 0;
  public List<Cargo> cargoCollection = new ArrayList<>();

  public Carrier[] carrierArray = new Carrier[ARRAY_CAPACITY];
  public int carrierIndex = 0;
  public List<Carrier> carrierCollection = new ArrayList<>();

  public Transportation[] transportationArray = new Transportation[ARRAY_CAPACITY];
  public int transportationIndex = 0;
  public List<Transportation> transportationCollection = new ArrayList<>();
  
}
