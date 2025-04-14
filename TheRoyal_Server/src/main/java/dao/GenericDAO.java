package dao;

import java.rmi.Remote;
import java.util.List;

public interface GenericDAO<T, ID> extends Remote {
    boolean save(T t);

    boolean update(T t);

    boolean delete(ID id);

    T findById(ID id);

    List<T> getAll();
}
