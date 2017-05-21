package view;

import controller.OrderController;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import javax.swing.DefaultCellEditor;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;
import model.Courier;
import model.Order;
import model.OrderStatus;
import model.Route;
import model.Tools;

public class OrderView extends javax.swing.JInternalFrame {

    private static OrderView orderView;
    private OrderController orderController;
    private volatile JTable orderTable;

    private OrderView() {
        orderController = new OrderController();
        initComponents();
        //setResizable(true);//
        setIconifiable(true);
        setVisible(true);
        setClosable(true);
        frameIcon = new ImageIcon(this.getClass().getClassLoader().getResource("resources/images/iconBlack.png"));
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        orderTable = new JTable(new OrderTableModel()) {
            @Override
            public String getToolTipText(MouseEvent e) {
                String tip = null;
                try {
                    Point p = e.getPoint();
                    int rowIndex = rowAtPoint(p);
                    int colIndex = columnAtPoint(p);
                    int realColumnIndex = convertColumnIndexToModel(colIndex);
                    Order order = ((OrderTableModel) getModel()).getOrderByRow(rowIndex);

                    if (realColumnIndex == 2) {
                        Route tmp = order.getRoute();
                        tip = tmp.toString();
                    } else if (realColumnIndex == 3) {
                        tip = order.getClientName() + ", " + order.getClientPhone();
                    } else if (realColumnIndex == 4) {
                        Route tmp = order.getRoute();
                        tip = tmp.getStartPoint() + ", " + order.getStartPointAdress();
                    } else if (realColumnIndex == 5) {
                        Route tmp = order.getRoute();
                        tip = tmp.getEndPoint() + ", " + order.getEndPointAdress();
                    } else if (realColumnIndex == 6) {
                        Courier tmp = order.getCourier();
                        tip = tmp.getName() + ", " + tmp.getPhone() + ", " + tmp.getTransport().getName();
                    } else if (realColumnIndex == 7) {
                        tip = order.getTariff().toString();
                    } else if (realColumnIndex == 9) {
                        tip = order.getOrderDate();
                    } else if (realColumnIndex == 10) {
                        tip = order.getDoneDate() + ", затримка " + Tools.convertFromMinutes(order.getDelayMin());
                    } else {
                        tip = super.getToolTipText(e);
                    }
                } catch (Exception ex) {
                    tip = "";
                }
                return tip;
            }
        };
        orderTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    int orderId = Integer.parseInt(orderTable.getValueAt(getSelectedRowInOrderTable(), 1).toString());
                    java.awt.EventQueue.invokeLater(new Runnable() {
                        public void run() {
                            try {
                                Order o = orderController.getOrder(orderId);
                                if (o.getStatus().equals(OrderStatus.IS_PERFORMED) || o.getStatus().equals(OrderStatus.DONE)) {
                                    return;
                                } else {
                                    AddOrderView addOrderView = AddOrderView.getChangeOrderView(orderController, o);
                                    addOrderView.setLocation(orderView.getLocation().x + orderView.getWidth() / 2 - addOrderView.getWidth() / 2,
                                            orderView.getLocation().y + 30);
                                    orderView.getParent().add(addOrderView);
                                    addOrderView.toFront();
                                    addOrderView.setSelected(true);
                                }
                            } catch (Exception ex) {
                                MainView.showErrorPane("Помилка при читанні запису з БД.", ex);
                            }
                        }
                    });
                }
            }
        });
        updateTableOrders();
    }

    public static OrderView getOrderView() {
        if (orderView != null) {
            orderView.dispose();
        }
        orderView = new OrderView();
        return orderView;
    }

    public void updateTableOrders() {
        try {
            orderTable.setModel(new OrderTableModel());
            orderTable.getColumnModel().getColumn(0).setMaxWidth(50);
            orderTable.getColumnModel().getColumn(0).setMinWidth(50);
            orderTable.getColumnModel().getColumn(1).setMaxWidth(50);
            orderTable.getColumnModel().getColumn(1).setMinWidth(50);
            orderTable.getColumnModel().getColumn(2).setMaxWidth(70);
            orderTable.getColumnModel().getColumn(2).setMinWidth(70);
            orderTable.getColumnModel().getColumn(3).setMinWidth(70);
            orderTable.getColumnModel().getColumn(4).setMinWidth(70);
            orderTable.getColumnModel().getColumn(5).setMinWidth(70);
            orderTable.getColumnModel().getColumn(6).setMinWidth(70);
            orderTable.getColumnModel().getColumn(7).setMinWidth(50);
            orderTable.getColumnModel().getColumn(7).setMaxWidth(50);
            orderTable.getColumnModel().getColumn(8).setMinWidth(70);
            orderTable.getColumnModel().getColumn(9).setMinWidth(70);
            orderTable.getColumnModel().getColumn(10).setMinWidth(70);
            orderTable.getColumnModel().getColumn(11).setMinWidth(70);
            jScrollPane2.setViewportView(orderTable);
            setColumnEditor();
            orderTable.updateUI();
        } catch (Exception ex) {
            MainView.showErrorPane("Сталась помилка при завантаженні записів з БД.", ex);
        }
    }

    public void setColumnEditor() {
        //column 10
        JComboBox<String> status;
        status = new JComboBox<>();
        for (String s : OrderStatus.getAllStatuses()) {
            status.addItem(s);
        }

        TableColumn tc = orderTable.getColumnModel().getColumn(11);
        tc.setCellEditor(new DefaultCellEditor(status));
        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
        renderer.setToolTipText("Натисніть щоб змінити статус");
        tc.setCellRenderer(renderer);
    }

    public class OrderTableModel extends AbstractTableModel {

        private ArrayList<Order> orders;

        public OrderTableModel() {
            super();
            orders = orderController.getOrders();
        }

        public Order getOrderByRow(int rowIndex) {
            return orders.get(rowIndex);
        }

        @Override
        public int getRowCount() {
            return orders.size();
        }

        @Override
        public int getColumnCount() {
            return 12;
        }

        @Override
        public String getColumnName(int columnIndex) {
            switch (columnIndex) {
                case 0:
                    return "№";
                case 1:
                    return "ID";
                case 2:
                    return "ID марш.";
                case 3:
                    return "Клієнт";
                case 4:
                    return "Адреса відп.";
                case 5:
                    return "Адреса приб.";
                case 6:
                    return "Кур'єр";
                case 7:
                    return "Тариф";
                case 8:
                    return "Вартість (грн)";
                case 9:
                    return "Дата замов.";
                case 10:
                    return "Дата викон.";
                case 11:
                    return "Статус";
                default:
                    return "";
            }
        }

        @Override
        public Class<?> getColumnClass(int columnIndex) {
            if (columnIndex == 11) {
                return JComboBox.class;
            } else {
                return String.class;
            }
        }

        @Override
        public boolean isCellEditable(int rowIndex, int columnIndex) {
            if (columnIndex == 11) {
                return true;
            } else {
                return false;
            }
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            switch (columnIndex) {
                case 0:
                    return rowIndex + 1;
                case 1:
                    return orders.get(rowIndex).getId();
                case 2:
                    return orders.get(rowIndex).getRoute().getId();
                case 3:
                    return orders.get(rowIndex).getClientName();
                case 4:
                    return orders.get(rowIndex).getStartPointAdress();
                case 5:
                    return orders.get(rowIndex).getEndPointAdress();
                case 6:
                    return orders.get(rowIndex).getCourier().getName();
                case 7:
                    return orders.get(rowIndex).getTariff().getId();
                case 8:
                    return Tools.convertAndPowFromX(orders.get(rowIndex).getCostUahC(), 2);
                case 9:
                    return Tools.convertDateTimeFromMySql(orders.get(rowIndex).getOrderDate());
                case 10:
                    return Tools.convertDateTimeFromMySql(orders.get(rowIndex).getDoneDate());
                case 11:
                    return orders.get(rowIndex).getStatus();
                default:
                    return "Error";
            }
        }

        @Override
        public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
            if (columnIndex != 11) {
                return;
            }
            Order order = orders.get(rowIndex);
            if (order.getStatus().equals(OrderStatus.UNKNOWN)) {
                MainView.showErrorPane("Неможливо змінити статус замовлення.",
                        new IllegalArgumentException("Замовлення не підтверджено."));
                updateTableOrders();
                return;
            }
            order.setStatus(aValue.toString());
            if (orderController.updateOrder(order)) {
                orders.set(rowIndex, order);
            } else {
                MainView.showErrorPane("Неможливо змінити статус замовлення.",
                        new IllegalArgumentException("Змініть статус кур'єра."));
                updateTableOrders();
            }
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

        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();

        setTitle("Замовлення");
        setFrameIcon(null);
        setName(""); // NOI18N
        try {
            setSelected(true);
        } catch (java.beans.PropertyVetoException e1) {
            e1.printStackTrace();
        }

        jButton1.setText("Додати замовлення");
        jButton1.setPreferredSize(new java.awt.Dimension(139, 32));
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("Видалити замовлення");
        jButton2.setToolTipText("");
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
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButton2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 508, Short.MAX_VALUE)
                        .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 434, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private int getSelectedRowInOrderTable() {
        if (orderTable.isColumnSelected(0) || orderTable.isColumnSelected(1)
                || orderTable.isColumnSelected(2) || orderTable.isColumnSelected(3)
                || orderTable.isColumnSelected(4) || orderTable.isColumnSelected(5)
                || orderTable.isColumnSelected(6) || orderTable.isColumnSelected(7)
                || orderTable.isColumnSelected(8) || orderTable.isColumnSelected(9)
                || orderTable.isColumnSelected(10) || orderTable.isColumnSelected(11)
                || orderTable.isColumnSelected(12)) {
            return orderTable.getSelectedRow();
        }
        return -1;
    }

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    AddOrderView addOrderView = AddOrderView.getAddOrderView(orderController);
                    addOrderView.setLocation(orderView.getLocation().x + orderView.getWidth() / 2 - addOrderView.getWidth() / 2,
                            orderView.getLocation().y + 30);
                    orderView.getParent().add(addOrderView);
                    addOrderView.toFront();
                    addOrderView.setSelected(true);
                } catch (Exception ex) {
                    MainView.showErrorPane("Помилка при додаванні запису в БД.", ex);
                }
            }
        });
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    if (getSelectedRowInOrderTable() != -1) {
                        int Id = Integer.parseInt(orderTable.getValueAt(getSelectedRowInOrderTable(), 1).toString());
                        orderController.deleteOrder(Id);
                        updateTableOrders();
                    } else {
                        MainView.showErrorPane("Виберіть замовлення, яке потрібно видалити.", new Exception());
                    }
                } catch (Exception ex) {
                    MainView.showErrorPane("Помилка при видаленні запису з БД.", ex);
                }
            }
        });
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                updateTableOrders();
            }
        });
    }//GEN-LAST:event_jButton3ActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JScrollPane jScrollPane2;
    // End of variables declaration//GEN-END:variables
}
