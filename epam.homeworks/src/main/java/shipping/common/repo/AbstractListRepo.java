package shipping.common.repo;

import java.util.ArrayList;
import java.util.List;

import shipping.common.domain.AbstractEntity;
import shipping.exceptions.NotDeletedException;
import shipping.exceptions.NotExistingEntityExeption;

public abstract class AbstractListRepo <T extends AbstractEntity> implements IEntityRepo <T> {
    
    protected final List <T> storage = new ArrayList <> ();
    
    @Override
    public void add (T entity) {
        storage.add (entity);
    }
    
    @Override
    public T delete (T entity) throws NotDeletedException {
        if (storage.remove (entity)) {
            return entity;
        }
        
        throw new NotDeletedException ("Given entity doesn't exist");
    }
    
    @Override
    public T get (Long id) throws NotExistingEntityExeption {
        for (T entity : storage) {
            if (entity.getId ().equals (id)) {
                @SuppressWarnings ("unchecked")
                T tmp = (T) entity.clone ();
                return tmp;
            }
        }
        
        throw new NotExistingEntityExeption("Given entity doesn't exist");
    }
    
    @Override
    public List <T> getAll () {
        List <T> result = new ArrayList <> ();
        for (T entity : storage) {
            @SuppressWarnings ("unchecked")
            T tmp = (T) entity.clone ();
            result.add(tmp);
        }
        return result;
    }
    
}
