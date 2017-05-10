package view;

import controller.CourierController;
import javax.swing.ImageIcon;
import javax.swing.ListSelectionModel;

public class CouriersView extends javax.swing.JInternalFrame {

    private static CouriersView courierView;
    private CourierController courierController;

    private CouriersView() {
        courierController = new CourierController();
        initComponents();
        //setResizable(true);//
        setIconifiable(true);
        setVisible(true);
        setClosable(true);
        frameIcon = new ImageIcon(this.getClass().getClassLoader().getResource("resources/images/iconBlack.png"));
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        courierController.updateTableCouriers(jTable1);
    }

    public static CouriersView getCouriersView() {
        if (courierView != null) {
            courierView.dispose();
        }
        courierView = new CouriersView();
        return courierView;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();

        setTitle("Кур'єри");
        setFrameIcon(null);
        setName(""); // NOI18N
        try {
            setSelected(true);
        } catch (java.beans.PropertyVetoException e1) {
            e1.printStackTrace();
        }

        jTable1.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Пункт відправлення", "Пункт прибуття", "Відстань"
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        jButton1.setText("Додати кур'єра");
        jButton1.setPreferredSize(new java.awt.Dimension(139, 32));
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("Видалити кур'єра");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setText("Оновити");
        jButton3.setPreferredSize(new java.awt.Dimension(100, 32));
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButton2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 163, Short.MAX_VALUE)
                .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 445, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 293, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        AddCourierView addCourierView = AddCourierView.getAddCourierView(courierController);
        addCourierView.setLocation(this.getLocation().x + this.getWidth() / 2 - addCourierView.getWidth() / 2,
                this.getLocation().y + 30);
        this.getParent().add(addCourierView);
        addCourierView.run();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        if (jTable1.isColumnSelected(0) || jTable1.isColumnSelected(1) || 
                jTable1.isColumnSelected(2) || jTable1.isColumnSelected(3) || 
                jTable1.isColumnSelected(4) || jTable1.isColumnSelected(5))  {
            int ID = Integer.parseInt(jTable1.getValueAt(jTable1.getSelectedRow(), 0).toString());
            courierController.deleteCourier(ID);
            courierController.updateTableCouriers(jTable1);
        } else {
            MainView.showErrorPane("Виберіть кур'єра, якого потрібно видалити.", new Exception());
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        courierController.updateTableCouriers(jTable1);
    }//GEN-LAST:event_jButton3ActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JScrollPane jScrollPane1;
    private volatile javax.swing.JTable jTable1;
    // End of variables declaration//GEN-END:variables
}
