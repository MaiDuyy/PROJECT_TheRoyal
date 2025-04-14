package gui.format_ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import gui.button.ActionButton;
import gui.event.TableActionEvent;
import javax.swing.ImageIcon;

/**
 * @author RAVEN
 */
public class PanelAction extends javax.swing.JPanel {

    /**
     * Creates new form PanelAction
     */
    public PanelAction() {
        initComponents();

    }

    public void initEvent(TableActionEvent event, int row) {
        cmdAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                event.tangSoLuong(row);
            }
        });
        cmdMinus.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                event.giamSoLuong(row);
            }
        });

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
    private void initComponents() {

        cmdMinus = new ActionButton();
        cmdAdd = new ActionButton();

        cmdMinus.setIcon(new ImageIcon(PanelAction.class.getResource("/ICON/icon/minus (4).png"))); // NOI18N
        cmdMinus.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        cmdMinus.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                cmdMinusActionPerformed(evt);
            }
        });

        cmdAdd.setIcon(new ImageIcon(PanelAction.class.getResource("/ICON/icon/plus.png"))); // NOI18N
        cmdAdd.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(cmdMinus, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(12, 12, 12)
                                .addComponent(cmdAdd, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addContainerGap())
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(cmdMinus, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(cmdAdd, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
    }// </editor-fold>                        

    private void cmdMinusActionPerformed(ActionEvent evt) {
        // TODO add your handling code here:
    }                                        


    // Variables declaration - do not modify                     
    private ActionButton cmdAdd;
    private ActionButton cmdMinus;
    // End of variables declaration                   
}
