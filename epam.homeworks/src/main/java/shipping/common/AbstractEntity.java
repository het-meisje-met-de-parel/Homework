package shipping.common;

public abstract class AbstractEntity {

    protected final Long id = IdGenerator.generateId();

    public Long getId() {
        return id;
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

}
