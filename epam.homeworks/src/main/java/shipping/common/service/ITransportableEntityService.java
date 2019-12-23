package shipping.common.service;

import java.util.List;

import shipping.common.domain.AbstractTransportableEntity;

public interface ITransportableEntityService <T extends AbstractTransportableEntity> extends IEntityService <T> {
    
    List <T> getByName (String name);
    
}
