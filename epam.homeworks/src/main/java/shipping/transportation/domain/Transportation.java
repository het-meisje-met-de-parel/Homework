package shipping.transportation.domain;

import shipping.cargo.domain.Cargo;
import shipping.cargo.domain.ClothCargo;
import shipping.carrier.domain.Carrier;
import shipping.common.AbstractEntity;

import java.util.Date;

public class Transportation extends AbstractEntity {

    private Cargo cargo;
    private Carrier carrier;
    private String description;
    private String billTo;
    
    private Date date = new Date ();
    
    public Transportation (Cargo cargo, Carrier carrier) {
        this.carrier = carrier; 
        this.cargo = cargo;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBillTo() {
        return billTo;
    }

    public void setBillTo(String billTo) {
        this.billTo = billTo;
    }

    public Cargo getCargo() {
        return cargo;
    }

    public Carrier getCarrier() {
        return carrier;
    }

    @Override
    public String toString () {
        return String.format("Transportation #%d, cargo #%d, carrier #%d, billing %s, date %s",
                id, cargo.getId(), carrier.getId(), billTo, date.toString());
    }
    
    @Override
    public Transportation clone () {
        Transportation transportation = new Transportation (getCargo(), getCarrier());
        transportation.setDescription (getDescription());
        transportation.setBillTo (getBillTo());
        transportation.setDate (getDate());
        transportation.setId (getId());
        return transportation;
    }

}
