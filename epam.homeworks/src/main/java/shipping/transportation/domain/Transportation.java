package shipping.transportation.domain;

import shipping.cargo.domain.Cargo;
import shipping.carrier.domain.Carrier;
import shipping.common.AbstractEntity;

import java.util.Date;

public class Transportation extends AbstractEntity {

    private Cargo cargo;
    private Carrier carrier;
    private String description;
    private String billTo;
    private Date date;

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

    public void setCargo(Cargo cargo) {
        this.cargo = cargo;
    }

    public Carrier getCarrier() {
        return carrier;
    }

    public void setCarrier(Carrier carrier) {
        this.carrier = carrier;
    }

    @Override
    public String toString () {
        return String.format("Transportation #%d, cargo #%d, carrier #%d, billing %s, date %s",
                id, cargo.getId(), carrier.getId(), billTo, date.toString());
    }

}
