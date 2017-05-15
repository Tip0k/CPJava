package view;

import controller.RoutesController;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.ListSelectionModel;
import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;
import model.Route;
import model.Tools;

public class RoutesView extends javax.swing.JInternalFrame {

    private static RoutesView routesView;
    private final RoutesController routesController;

    private RoutesView() {
        routesController = new RoutesController();
        initComponents();
        //setResizable(true);//
        setIconifiable(true);
        setVisible(true);
        setClosable(true);
        frameIcon = new ImageIcon(this.getClass().getClassLoader().getResource("resources/images/iconBlack.png"));
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        updateTableRoutes();
    }

    public static RoutesView getRoutesView() {
        if (routesView != null) {
            routesView.dispose();
        }
        routesView = new RoutesView();
        return routesView;
    }

    public void updateTableRoutes() {
        try {
            jTable1.setModel(new RoutesTableModel());
            jTable1.getColumnModel().getColumn(0).setMinWidth(50);
            jTable1.getColumnModel().getColumn(1).setMaxWidth(50);
            jTable1.getColumnModel().getColumn(2).setMinWidth(150);
            jTable1.getColumnModel().getColumn(2).setMaxWidth(300);
            jTable1.getColumnModel().getColumn(3).setMinWidth(150);
            jTable1.getColumnModel().getColumn(3).setMaxWidth(300);
            jTable1.getColumnModel().getColumn(4).setMaxWidth(150);
            jTable1.updateUI();
        } catch (Exception ex) {
            MainView.showErrorPane("Сталась помилка при оновлені таблиці.", ex);
        }
    }

    public class RoutesTableModel extends AbstractTableModel {

        private ArrayList<Route> routes;

        public RoutesTableModel() {
            super();
            routes = routesController.getRoutes();
        }

        @Override
        public int getRowCount() {
            return routes.size();
        }

        @Override
        public int getColumnCount() {
            return 5;
        }

        @Override
        public String getColumnName(int columnIndex) {
            switch (columnIndex) {
                case 0:
                    return "№";
                case 1:
                    return "ID";
                case 2:
                    return "Пункт відправлення";
                case 3:
                    return "Пункт прибуття";
                case 4:
                    return "Відстань (км)";
                default:
                    return "Error";
            }
        }

        @Override
        public Class<?> getColumnClass(int columnIndex) {
            return String.class;
        }

        @Override
        public boolean isCellEditable(int rowIndex, int columnIndex) {
            return false;
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            switch (columnIndex) {
                case 0:
                    return rowIndex + 1;
                case 1:
                    return routes.get(rowIndex).getId();
                case 2:
                    return routes.get(rowIndex).getStartPoint();
                case 3:
                    return routes.get(rowIndex).getEndPoint();
                case 4:
                    return Tools.convertAndPowFromX(routes.get(rowIndex).getDistanceM(), 3);
                default:
                    return "Error";
            }
        }

        @Override
        public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
            //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public void addTableModelListener(TableModelListener l) {
            listenerList.add(TableModelListener.class, l);
        }

        @Override
        public void removeTableModelListener(TableModelListener l) {
            listenerList.remove(TableModelListener.class, l);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();

        setTitle("Маршрути");
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

        jButton1.setText("Додати маршрут");
        jButton1.setPreferredSize(new java.awt.Dimension(139, 32));
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setLabel("Видалити маршрут");
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
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButton2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 698, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 434, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        AddRouteView addRoute = AddRouteView.getAddRouteView(routesController);
        addRoute.setLocation(this.getLocation().x + this.getWidth() / 2 - addRoute.getWidth() / 2,
                this.getLocation().y + 20);
        this.getParent().add(addRoute);
        addRoute.run();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        if (jTable1.isColumnSelected(0) || jTable1.isColumnSelected(1)
                || jTable1.isColumnSelected(2) || jTable1.isColumnSelected(3)) {
            int ID = Integer.parseInt(jTable1.getValueAt(jTable1.getSelectedRow(), 0).toString());
            routesController.deleteRoute(ID);
            routesController.updateTableRoutes(jTable1);
        } else {
            MainView.showErrorPane("Виберіть маршрут, який потрібно видалити.", new Exception());
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        updateTableRoutes();
    }//GEN-LAST:event_jButton3ActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JScrollPane jScrollPane1;
    private volatile javax.swing.JTable jTable1;
    // End of variables declaration//GEN-END:variables
}
