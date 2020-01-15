package common.business.domain;

import java.io.Serializable;

public abstract class BaseEntity implements Serializable {
    
    private static final long serialVersionUID = 2549916619472678457L;
    
    protected Long id;
    
    public Long getId () {
        return id;
    }
    
    public void setId (Long id) {
        this.id = id;
    }
    
    @Override
    public boolean equals (Object obj) {
        if (obj == null) { return false; }
        
        if (!(obj instanceof BaseEntity)) {
            return false;
        }
        
        BaseEntity entity = (BaseEntity) obj;
        if (id == null && entity.id != null) {
            return false;
        }
        
        return (id == null && entity.id == null) || id.equals (entity.id);
    }
    
}
