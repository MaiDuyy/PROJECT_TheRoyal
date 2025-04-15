package dao;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface GenericDAO<T, ID> extends Remote {
    boolean save(T t);

    boolean update(T t);

    boolean delete(ID id) throws RemoteException;

    T findById(ID id);

    List<T> getAll();
}
