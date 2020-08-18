package DAO;

import java.util.List;

public interface DAOInterface<T> {
    public void save(T t);

    public void update(T t);

    public void delete(T t);
}
