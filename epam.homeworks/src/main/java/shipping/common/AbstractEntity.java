package shipping.common;

public abstract class AbstractEntity {

    protected final Long id = IdGenerator.generateId();

    public Long getId() {
        return id;
    }

}
