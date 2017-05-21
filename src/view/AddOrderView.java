/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.awt.Color;
import controller.OrderController;
import controller.RouteController;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Calendar;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
import javax.swing.Timer;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;
import model.Courier;
import model.Order;
import model.OrderItem;
import model.OrderStatus;
import model.Route;
import model.Tariff;
import model.Tools;

/**
 *
 * @author PEOPLE
 */
public class AddOrderView extends javax.swing.JInternalFrame {

    /**
     * Creates new form AddRoute
     */
    private static AddOrderView addOrderView;
    private static AddOrderView changeOrderView;
    private final OrderController orderController;
    private final RouteController routeController;
    private Order order;
    private ArrayList<Route> routes;
    private ArrayList<Courier> couriers;
    private ArrayList<Tariff> tariffs;
    private final Timer timer;

    private AddOrderView(OrderController orderController) {
        initComponents();
        this.order = new Order();
        order.setContent(new ArrayList<>());
        this.orderController = orderController;
        this.routeController = new RouteController();
        setClosable(true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setIconifiable(true);
        setVisible(true);

        jComboBox1.removeAllItems();
        jComboBox2.removeAllItems();
        jComboBox3.removeAllItems();
        jComboBox4.removeAllItems();
        jComboBox2.setEnabled(false);
        jComboBox4.setEnabled(false);

        fillJComboRoutes();
        fillJComboCouriers();
        fillJComboTariffs();

        timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jLabel16.setText(Tools.getDateTime(Calendar.getInstance()));
            }
        });
        timer.start();

        jComboBox3.addItem(OrderStatus.UNKNOWN);
        jComboBox3.addItem(OrderStatus.IS_PERFORMED);

        jSlider1.addChangeListener(
                new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e
            ) {
                if (jSlider1.getValue() == 1) {
                    jComboBox4.setEnabled(true);
                    jComboBox2.setEnabled(false);
                    jComboBox3.setEnabled(false);
                } else if (jSlider1.getValue() == 0) {
                    jComboBox3.setEnabled(true);
                    jComboBox2.setEnabled(false);
                    jComboBox4.setEnabled(false);
                } else {
                    jComboBox2.setEnabled(true);
                    jComboBox3.setEnabled(false);
                    jComboBox4.setEnabled(false);
                }
            }
        }
        );

        ListCellRenderer comboRouteRenderer = new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(
                    JList<?> list,
                    Object value,
                    int index,
                    boolean isSelected,
                    boolean cellHasFocus) {
                try {
                    setToolTipText(value.toString());
                } catch (Exception ex) {
                }
                return super.getListCellRendererComponent(list, value, index, isSelected,
                        cellHasFocus);
            }

        };
        jComboBox1.setRenderer(comboRouteRenderer);

        ListCellRenderer comboCourierRenderer = new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(
                    JList<?> list,
                    Object value,
                    int index,
                    boolean isSelected,
                    boolean cellHasFocus) {
                try {
                    setToolTipText(couriers.get(index).toString());
                } catch (Exception ex) {
                }
                return super.getListCellRendererComponent(list, value, index, isSelected,
                        cellHasFocus);
            }

        };
        jComboBox2.setRenderer(comboCourierRenderer);

        ListCellRenderer comboStatusRenderer = new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(
                    JList<?> list,
                    Object value,
                    int index,
                    boolean isSelected,
                    boolean cellHasFocus) {
                try {
                    setToolTipText(value.toString());
                } catch (Exception ex) {
                }
                return super.getListCellRendererComponent(list, value, index, isSelected,
                        cellHasFocus);
            }

        };
        jComboBox3.setRenderer(comboStatusRenderer);

        ListCellRenderer comboTariffRenderer = new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(
                    JList<?> list,
                    Object value,
                    int index,
                    boolean isSelected,
                    boolean cellHasFocus) {
                try {
                    setToolTipText(tariffs.get(index).toString());
                } catch (Exception ex) {
                }
                return super.getListCellRendererComponent(list, value, index, isSelected,
                        cellHasFocus);
            }

        };
        jComboBox4.setRenderer(comboTariffRenderer);

        jTable1.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                try {
                    if (e.isShiftDown()) {
                        order.addOrderItem(new OrderItem());
                        updateOrderContentTable();
                    } else if (e.getExtendedKeyCode() == KeyEvent.VK_DELETE) {
                        order.getContent().remove(jTable1.getSelectedRow());
                        updateOrderContentTable();
                    }
                } catch (Exception ex) {
                    updateOrderContentTable();
                }
            }
        });

        jComboBox1.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    setOrderData();
                    fillJComboCouriers();
                    fillDoneDateAndCost();
                }
            }
        }
        );

        jComboBox2.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    setOrderData();
                    fillDoneDateAndCost();
                }
            }
        }
        );

        jComboBox2.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    setOrderData();
                    fillJComboTariffs();
                }
            }
        }
        );

        jComboBox3.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    setOrderData();
                }
            }
        }
        );

        jComboBox4.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    setOrderData();
                    fillDoneDateAndCost();
                }
            }
        }
        );

        jTextField2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String value = jTextField2.getText();
                if (value.length() > 0) {
                    if (!order.getStartPointAdress().equals(value)) {
                        setOrderData();
                    }
                }
            }
        });

        jTextField3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String value = jTextField3.getText();
                if (value.length() > 0) {
                    if (!order.getEndPointAdress().equals(value)) {
                        setOrderData();
                    }
                }
            }
        });

        jTextField4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String value = jTextField4.getText();
                if (value.length() > 0) {
                    if (!order.getClientName().equals(value)) {
                        setOrderData();
                    }
                }
            }
        });

        jTextField5.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String value = jTextField5.getText();
                if (value.length() > 0) {
                    if (!order.getClientPhone().equals(value)) {
                        setOrderData();
                    }
                }
            }
        });
        setOrderData();
        fillDoneDateAndCost();
    }

    public static AddOrderView getAddOrderView(OrderController orderController) {
        if (addOrderView != null) {
            addOrderView.dispose();
        }
        addOrderView = new AddOrderView(orderController);
        return addOrderView;
    }

    public static AddOrderView getChangeOrderView(OrderController orderController, Order o) {
        if (changeOrderView != null) {
            changeOrderView.dispose();
        }
        changeOrderView = new AddOrderView(orderController);

        changeOrderView.jComboBox1.setSelectedItem(o.getRoute().toString());
        changeOrderView.jComboBox2.setSelectedItem(o.getCourier().getName());
        changeOrderView.jComboBox3.setSelectedItem(o.getStatus());
        changeOrderView.jComboBox4.setSelectedItem(o.getTariff().getName());

        changeOrderView.jTextField2.setText(o.getStartPointAdress());
        changeOrderView.jTextField3.setText(o.getEndPointAdress());
        changeOrderView.jTextField4.setText(o.getClientName());
        changeOrderView.jTextField5.setText(o.getClientPhone());

        changeOrderView.jLabel16.setText(Tools.convertDateTimeFromMySql(o.getOrderDate()));
        changeOrderView.jLabel17.setText(Tools.convertDateTimeFromMySql(o.getDoneDate()));

        changeOrderView.order = o;
        changeOrderView.updateOrderContentTable();
        changeOrderView.setTitle("Редагувати замовлення");

//
//        ArrayList<OrderItem> data = order.getContent();
//        for (int i = 0; i < data.size(); i++) {
//            changeOrderView.jTable1.getModel().setValueAt(i + 1, i, 0);
//            changeOrderView.jTable1.getModel().setValueAt(data.get(i).getItem(), i, 1);
//            changeOrderView.jTable1.getModel().setValueAt(Tools.convertAndPowFromX(data.get(i).getWg(), 3), i, 2);
//            changeOrderView.jTable1.getModel().setValueAt(Tools.convertAndPowFromX(data.get(i).getWcm(), 2), i, 3);
//            changeOrderView.jTable1.getModel().setValueAt(Tools.convertAndPowFromX(data.get(i).getHcm(), 2), i, 4);
//            changeOrderView.jTable1.getModel().setValueAt(Tools.convertAndPowFromX(data.get(i).getLcm(), 2), i, 5);
//        }
//        changeOrderView.setOrderData();
        return changeOrderView;
    }

    private void fillJComboRoutes() {
        try {
            jComboBox1.removeAllItems();
            routes = orderController.getRoutes();
            for (Route r : routes) {
                jComboBox1.addItem(r.toString());
            }
        } catch (Exception ex) {
            jComboBox1.addItem("None");
        } finally {
            try {
                setOrderData();
            } catch (Exception e) {

            }
            fillJComboCouriers();
        }
    }

    private void fillJComboCouriers() {
        try {
            jComboBox2.removeAllItems();
            couriers = orderController.getCouriers(order);
            for (Courier c : couriers) {
                jComboBox2.addItem(c.getName());
            }
        } catch (Exception ex) {
            jComboBox2.addItem("None");
        } finally {
            try {
                setOrderData();
            } catch (Exception e) {

            }
            fillJComboTariffs();
        }
    }

    private void fillJComboTariffs() {
        try {
            jComboBox4.removeAllItems();
            tariffs = orderController.getTariffs(order);
            for (Tariff t : tariffs) {
                jComboBox4.addItem(t.getName());
            }
        } catch (Exception ex) {
            jComboBox4.addItem("None");
        } finally {
            try {
                setOrderData();
            } catch (Exception e) {

            }
        }
    }

    private void fillDoneDateAndCost() {
        try {
            Calendar c = Calendar.getInstance();
            c.setTimeInMillis((long) (Calendar.getInstance().getTimeInMillis()
                    + (order.getRoute().getDistanceM() / 1000.0d
                    / order.getCourier().getTransport().getType().getKmPerH())
                    * 60 * 60 * 1000));
            jLabel17.setText(Tools.getDateTime(c));
        } catch (Exception ex) {
            jLabel17.setText("unknown");
        }
        try {
            Tariff t = order.getTariff();
            long l = (((long) t.getUahCPerKm() * (long) order.getRoute().getDistanceM()) / 100000)
                    + t.getUahCPerPoint() * 2 / 100 + t.getUahCAdditionalCosts() / 100;
            jLabel18.setText((new Integer((int) l)).toString());
        } catch (Exception ex) {
            jLabel18.setText("unknown");
        }
    }

    private void setOrderData() {
        try {
            order.setRoute(routes.get(jComboBox1.getSelectedIndex()));
        } catch (Exception ex) {
            order.setRoute(null);
        }
        try {
            order.setStartPointAdress(jTextField2.getText());
        } catch (Exception ex) {
            order.setStartPointAdress(null);
        }
        try {
            order.setEndPointAdress(jTextField3.getText());
        } catch (Exception ex) {
            order.setEndPointAdress(null);
        }
        try {
            order.setClientName(jTextField4.getText());
        } catch (Exception ex) {
            order.setClientName(null);
        }
        try {
            order.setClientPhone(jTextField5.getText());
        } catch (Exception ex) {
            order.setClientPhone(null);
        }
        try {
            order.setCourier(couriers.get(jComboBox2.getSelectedIndex()));
        } catch (Exception ex) {
            order.setCourier(null);
        }
        try {
            order.setTariff(tariffs.get(jComboBox4.getSelectedIndex()));
        } catch (Exception ex) {
            order.setTariff(null);
        }
        try {
            order.setStatus(jComboBox3.getSelectedItem().toString());
        } catch (Exception ex) {
            order.setStatus(null);
        }
        try {
            order.setOrderDate(Tools.convertDateTimeToMySql(Tools.getDateTime(Calendar.getInstance())));
        } catch (Exception ex) {
            order.setOrderDate(null);
        }
    }

    private void updateOrderContentTable() {
        jTable1.setModel(new OrderContentTableModel());
        jTable1.getColumnModel().getColumn(0).setMaxWidth(35);
        jTable1.getColumnModel().getColumn(0).setMinWidth(35);
        jTable1.getColumnModel().getColumn(2).setMaxWidth(60);
        jTable1.getColumnModel().getColumn(2).setMinWidth(60);
        jTable1.getColumnModel().getColumn(3).setMaxWidth(60);
        jTable1.getColumnModel().getColumn(3).setMinWidth(60);
        jTable1.getColumnModel().getColumn(4).setMaxWidth(60);
        jTable1.getColumnModel().getColumn(4).setMinWidth(60);
        jTable1.getColumnModel().getColumn(5).setMaxWidth(60);
        jTable1.getColumnModel().getColumn(5).setMinWidth(60);
        fillJComboCouriers();
        jTable1.updateUI();
    }

    public class OrderContentTableModel extends AbstractTableModel {

        public OrderContentTableModel() {
            super();
        }

        @Override
        public int getRowCount() {
            return order.getContent().size();
        }

        @Override
        public int getColumnCount() {
            return 6;
        }

        @Override
        public String getColumnName(int columnIndex) {
            switch (columnIndex) {
                case 0:
                    return "№";
                case 1:
                    return "Назва";
                case 2:
                    return "Ваг (кг)";
                case 3:
                    return "Шир (м)";
                case 4:
                    return "Вис (м)";
                case 5:
                    return "Дов (м)";
                default:
                    return "";
            }
        }

        @Override
        public Class<?> getColumnClass(int columnIndex) {
            return String.class;
        }

        @Override
        public boolean isCellEditable(int rowIndex, int columnIndex) {
            if (columnIndex != 0) {
                return true;
            } else {
                return false;
            }
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            if (rowIndex > order.getContent().size()) {
                return "";
            }
            int value;
            switch (columnIndex) {
                case 0:
                    return rowIndex + 1;
                case 1:
                    return order.getContent().get(rowIndex).getItem();
                case 2:
                    value = order.getContent().get(rowIndex).getWg();
                    if (value <= 0) {
                        return "";
                    } else {
                        return Tools.convertAndPowFromX(value, 3);
                    }
                case 3:
                    value = order.getContent().get(rowIndex).getWcm();
                    if (value <= 0) {
                        return "";
                    } else {
                        return Tools.convertAndPowFromX(value, 2);
                    }
                case 4:
                    value = order.getContent().get(rowIndex).getHcm();
                    if (value <= 0) {
                        return "";
                    } else {
                        return Tools.convertAndPowFromX(value, 2);
                    }
                case 5:
                    value = order.getContent().get(rowIndex).getLcm();
                    if (value <= 0) {
                        return "";
                    } else {
                        return Tools.convertAndPowFromX(value, 2);
                    }
                default:
                    return "Error";
            }
        }

        @Override
        public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
            String value = aValue.toString();
            if (columnIndex != 1) {
                try {
                    Double.parseDouble(value);
                } catch (Exception ex1) {
                    try {
                        Double.parseDouble(value.replace(",", "."));
                    } catch (Exception ex) {
                        return;
                    }
                }
            }
            try {
                switch (columnIndex) {
                    case 1:
                        order.getContent().get(rowIndex).setItem(value);
                        break;
                    case 2:
                        order.getContent().get(rowIndex).setWg(Tools.convertAndPowToX(value, 3));
                        break;
                    case 3:
                        order.getContent().get(rowIndex).setWcm(Tools.convertAndPowToX(value, 2));
                        break;
                    case 4:
                        order.getContent().get(rowIndex).setHcm(Tools.convertAndPowToX(value, 2));
                        break;
                    case 5:
                        order.getContent().get(rowIndex).setLcm(Tools.convertAndPowToX(value, 2));
                        break;
                    default:
                        break;
                }
                fillJComboCouriers();
            } catch (Exception ex) {
                return;
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

        jLabel1 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jComboBox1 = new javax.swing.JComboBox<>();
        jLabel7 = new javax.swing.JLabel();
        jComboBox2 = new javax.swing.JComboBox<>();
        jTextField3 = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        jTextField4 = new javax.swing.JTextField();
        jTextField5 = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jComboBox3 = new javax.swing.JComboBox<>();
        jLabel15 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jSlider1 = new javax.swing.JSlider();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jComboBox4 = new javax.swing.JComboBox<>();
        jLabel19 = new javax.swing.JLabel();
        jSeparator3 = new javax.swing.JSeparator();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Нове замовлення");
        setName(""); // NOI18N
        setRequestFocusEnabled(false);

        jLabel1.setText("Адреса відправ.");
        jLabel1.setToolTipText("");

        jLabel3.setText("Маршрут");

        jLabel4.setText("Клієнт");

        jLabel6.setFont(new java.awt.Font("Dialog", 3, 24)); // NOI18N
        jLabel6.setText("Замовник");

        jButton1.setText("ОК");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel7.setFont(new java.awt.Font("Dialog", 3, 24)); // NOI18N
        jLabel7.setText("Доставка");

        jComboBox2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel2.setText("Адреса прибуття");
        jLabel2.setToolTipText("");

        jButton2.setText("new");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jLabel5.setText("Телефон");

        jLabel8.setFont(new java.awt.Font("Dialog", 3, 24)); // NOI18N
        jLabel8.setText("Інформація");

        jLabel9.setFont(new java.awt.Font("Dialog", 3, 24)); // NOI18N
        jLabel9.setText("Вміст замовлення");

        jLabel10.setFont(new java.awt.Font("Dialog", 3, 24)); // NOI18N
        jLabel10.setText("Замовлення");

        jLabel11.setText("Кур'єр");

        jLabel12.setText("Дата та час замовлення");

        jLabel13.setText("Дата та час виконання");

        jLabel14.setText("Вартість");
        jLabel14.setToolTipText("");

        jSeparator1.setOrientation(javax.swing.SwingConstants.VERTICAL);

        jComboBox3.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel15.setText("Тариф");

        jScrollPane1.setToolTipText("");

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {"0", "натисніть", "shift", "+", "any", "key"}
            },
            new String [] {
                "№", "Назва", "Ваг (кг)", "Шир (м)", "Вис (м)", "Дов (м)"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable1.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(jTable1);

        jSlider1.setMajorTickSpacing(50);
        jSlider1.setMaximum(2);
        jSlider1.setOrientation(javax.swing.JSlider.VERTICAL);
        jSlider1.setValue(0);

        jComboBox4.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel19.setText("Статус");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(301, 301, 301)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(329, 329, 329))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                .addComponent(jLabel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(jLabel12))
                                            .addGroup(layout.createSequentialGroup()
                                                .addGap(60, 60, 60)
                                                .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel16, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(jLabel17, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(jLabel18, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                    .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                            .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, 111, Short.MAX_VALUE)
                                            .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(jTextField4, javax.swing.GroupLayout.DEFAULT_SIZE, 160, Short.MAX_VALUE)
                                            .addComponent(jTextField5)))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                        .addComponent(jButton2)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 111, Short.MAX_VALUE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(jTextField3, javax.swing.GroupLayout.DEFAULT_SIZE, 160, Short.MAX_VALUE)
                                            .addComponent(jTextField2)))
                                    .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                        .addGap(0, 0, Short.MAX_VALUE)
                                        .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 453, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                .addComponent(jScrollPane1)
                                                .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(jSlider1, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                    .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                    .addComponent(jComboBox2, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                    .addComponent(jComboBox4, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                    .addComponent(jComboBox3, javax.swing.GroupLayout.PREFERRED_SIZE, 270, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                        .addGap(0, 0, Short.MAX_VALUE))))
                            .addComponent(jSeparator3))
                        .addContainerGap())))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 194, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(12, 12, 12)
                        .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(12, 12, 12)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jComboBox4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jComboBox3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(jSlider1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGap(12, 12, 12)
                            .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(12, 12, 12)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGap(12, 12, 12)
                            .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(12, 12, 12)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jLabel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGap(6, 6, 6)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jLabel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 356, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton1)
                .addGap(12, 12, 12))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        final boolean insertUpdateFlag;

        if (this == AddOrderView.changeOrderView) {
            insertUpdateFlag = false;
        } else {
            insertUpdateFlag = true;
        }

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                setOrderData();
                boolean error = false;
                try {
                    if (order.getDelayMin() < 0) {
                        order.setDelayMin(0);
                    }
                    jTextField2.setBackground(Color.white);
                    jTextField3.setBackground(Color.white);
                    jTextField4.setBackground(Color.white);
                    jTextField5.setBackground(Color.white);

                    if (order.getStatus().equals(OrderStatus.IS_PERFORMED)) {
                        if (order.getStartPointAdress().length() < 3) {
                            jTextField2.setBackground(Color.red);
                            error = true;
                        }
                        if (order.getEndPointAdress().length() < 3) {
                            jTextField3.setBackground(Color.red);
                            error = true;
                        }
                    }
                    if (order.getClientPhone().length() < 3) {
                        jTextField5.setBackground(Color.red);
                        error = true;
                    }
                    if (order.getClientName().length() < 3) {
                        jTextField4.setBackground(Color.red);
                        error = true;
                    }
                    try {
                        order.setOrderDate(Tools.convertDateTimeToMySql(jLabel16.getText()));
                        order.setDoneDate(Tools.convertDateTimeToMySql(jLabel17.getText()));
                        order.setCostUahC(Integer.parseInt(jLabel18.getText()) * 100);
                    } catch (Exception ex) {
                    }
                    if (error) {
                        throw new Exception();
                    }

                    if (insertUpdateFlag) {
                        if (orderController.addOrder(order)) {
                            addOrderView.dispose();
                        } else {
                            MainView.showErrorPane("Дані введено некоректно", new IllegalArgumentException());
                        }
                    } else {
                        if (orderController.changeOrder(order)) {
                            changeOrderView.dispose();
                        } else {
                            MainView.showErrorPane("Дані введено некоректно", new IllegalArgumentException());
                        }
                    }
                } catch (Exception ex) {
                }
            }
        });
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        try {
            AddRouteView addRoute = AddRouteView.getAddRouteView(routeController);
            addRoute.setLocation(addOrderView.getLocation().x + addOrderView.getWidth() / 2 - addRoute.getWidth() / 2,
                    addOrderView.getLocation().y + 20);
            addOrderView.getParent().add(addRoute);
            addRoute.toFront();
            addRoute.setSelected(true);

            addRoute.addInternalFrameListener(new InternalFrameAdapter() {
                @Override
                public void internalFrameDeactivated(InternalFrameEvent e) {
                    fillJComboRoutes();
                }
            });
        } catch (Exception ex) {
            MainView.showErrorPane("Помилка при додаванні запису в БД.", ex);
        }
        fillJComboRoutes();
    }//GEN-LAST:event_jButton2ActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private volatile javax.swing.JComboBox<String> jComboBox1;
    private volatile javax.swing.JComboBox<String> jComboBox2;
    private volatile javax.swing.JComboBox<String> jComboBox3;
    private volatile javax.swing.JComboBox<String> jComboBox4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private volatile javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSlider jSlider1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JTextField jTextField5;
    // End of variables declaration//GEN-END:variables
}
