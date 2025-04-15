package gui.event;

import java.rmi.RemoteException;

public interface TableActionEvent {
    void tangSoLuong(int row) throws RemoteException;

    void giamSoLuong(int row) throws RemoteException;

}