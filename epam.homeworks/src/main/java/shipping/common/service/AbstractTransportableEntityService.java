package shipping.common.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import shipping.common.domain.AbstractTransportableEntity;
import shipping.common.repo.IEntityRepo;
import shipping.exceptions.IdentifierMissedException;

public abstract class AbstractTransportableEntityService <T extends AbstractTransportableEntity>
        extends AbstractEntityService <T> implements ITransportableEntityService <T> {

    public AbstractTransportableEntityService (IEntityRepo <T> repo) {
        super (repo);
    }
    
    @Override
    public void add (T entity) throws IdentifierMissedException {
        Objects.requireNonNull (entity);
        if (entity.getName () == null) {
            throw new IdentifierMissedException ("Entity name is null");
        }
        
        super.add (entity);
    }
    
    @Override
    public List <T> getByName (String name) {
        List <T> result = new ArrayList <> ();
        if (name == null) { return result; }
        
        for (T entity : getAll ()) {
            if (name.equals (entity.getName ())) {
                result.add (entity);
            }
        }
        
        return result;
    }
    
}
