package shipping.common.repo;

import java.util.List;

import shipping.common.domain.AbstractEntity;
import shipping.exceptions.NotExistingEntityExeption;

public interface IEntityRepo <T extends AbstractEntity> {
    
    void add (T entity);
    
    T delete (T entity) throws NotExistingEntityExeption;
    
    T get (Long id) throws NotExistingEntityExeption;
    
    List <T> getAll ();
    
}
