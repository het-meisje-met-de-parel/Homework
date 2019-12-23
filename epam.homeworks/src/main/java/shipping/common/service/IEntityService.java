package shipping.common.service;

import shipping.common.domain.AbstractEntity;
import shipping.common.repo.IEntityRepo;
import shipping.exceptions.IdentifierMissedException;
import shipping.exceptions.NotExistingEntityExeption;

public interface IEntityService <T extends AbstractEntity> extends IEntityRepo <T> {
    
    @Override
    void add (T entity) throws IdentifierMissedException;
    
    T update (T entity) throws NotExistingEntityExeption;
    
}
