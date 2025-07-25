package service;

import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public  interface GenericService<T, ID> extends Remote {
    boolean save(T t) throws RemoteException;

    boolean update(T t) throws RemoteException;

    boolean delete(ID id) throws RemoteException;

    T findById(ID id) throws RemoteException;

    List<T> getAll() throws RemoteException;
}
