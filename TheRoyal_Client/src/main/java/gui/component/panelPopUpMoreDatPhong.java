package gui.component;


import javax.swing.*;

import gui.event.EventPopUpMoreDatPhong;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


public class panelPopUpMoreDatPhong extends JPanel {

    private JPopupMenu popupMenu;
    private JMenuItem menuItem;
    private JMenuItem menuItem1;
    private JMenuItem menuItem2;

    public panelPopUpMoreDatPhong() {
        initComponents();
        initPopupMenu();
        addMouseListenerToClosePopup();
    }

    // Initialize the popup menu
    private void initPopupMenu() {
        popupMenu = new JPopupMenu();
        // Create and add a menu item
        // list item
        menuItem = new JMenuItem("Đặt Phòng Trước");


        menuItem1 = new JMenuItem("Đặt nhiều phòng");

        menuItem2 = new JMenuItem("Thanh toán nhiều phòng");


        popupMenu.add(menuItem);
        popupMenu.add(menuItem1);
//        popupMenu.add(menuItem2);
    }

    // event click
    public void addEvent(EventPopUpMoreDatPhong event) {
        menuItem.addActionListener(e -> {
            event.datPhongTruoc();
        });
        menuItem1.addActionListener(e -> {
            event.thueNhieuPhong();
        });
        menuItem2.addActionListener(e -> {
            event.thanhToanNhieuPhong();
        });
    }

    public void showPopupMenu(Component component, int x, int y) {
        if (popupMenu != null) {
            popupMenu.show(component, x, y);
        } else {
            System.out.println("Error: PopupMenu not initialized");
        }
    }

    private void addMouseListenerToClosePopup() {
        MouseAdapter mouseAdapter = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (popupMenu != null && popupMenu.isVisible() && !popupMenu.getBounds().contains(e.getPoint())) {
                    popupMenu.setVisible(false);
                }
            }
        };

        addMouseListener(mouseAdapter);
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {

        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGap(0, 300, Short.MAX_VALUE)
        );
    }                
}
