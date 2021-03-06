package shipping.common.domain;

import shipping.common.IdGenerator;

public abstract class AbstractEntity {

    protected Long id = IdGenerator.generateId();
    
    public Long getId() {
        return id;
    }
    
    protected void setId (Long id) {
        this.id = id;
    }
    
    @Override
    public boolean equals (Object obj) {
        if (obj == null) { return false; }
        if (obj instanceof AbstractEntity) {
            AbstractEntity compare = (AbstractEntity) obj;
            return getId ().equals (compare.getId ());
        }
        
        return false;
    }
    
    @Override
    public int hashCode () {
        return getId ().intValue ();
    }
    
    @Override
    public abstract AbstractEntity clone ();

}
