package shipping.common.service;

import java.util.List;
import java.util.Objects;

import shipping.common.domain.AbstractEntity;
import shipping.common.repo.IEntityRepo;
import shipping.exceptions.IdentifierMissedException;
import shipping.exceptions.NotExistingEntityExeption;

public abstract class AbstractEntityService <T extends AbstractEntity> implements IEntityService <T> {
    
    protected final IEntityRepo <T> repo;
    
    public AbstractEntityService (IEntityRepo <T> repo) {
        this.repo = repo;
    }
    
    @Override
    public void add (T entity) throws IdentifierMissedException {
        Objects.requireNonNull (entity);
        
        if (entity.getId () == null) {
            throw new IdentifierMissedException ("Entity identifier is null");
        }
        
        repo.add (entity);
    }
    
    @Override
    public T update (T entity) throws NotExistingEntityExeption {
        if (delete (entity) != null) {
            add (entity);
        }
        
        return get (entity.getId ());
    }

    @Override
    public T delete (T entity) throws NotExistingEntityExeption {
        return repo.delete (entity);
    }

    @Override
    public T get (Long id) throws NotExistingEntityExeption {
        return repo.get (id);
    }

    @Override
    public List <T> getAll () {
        return repo.getAll ();
    }
    
}
