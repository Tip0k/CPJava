/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;

/**
 *
 * @author PEOPLE
 */
public class TestInputInDB {

    private Connection connection = model.DataBaseConnection.getConnection();
    
    private void goGogo() {
        try {
            for (int i = 0; i < 10000; i++) {
                PreparedStatement preparedStatement;
                preparedStatement = connection.prepareStatement(
                        "insert into cars values(null, ?, ?, ?, ?, ?, ?)");
                preparedStatement.setString(1, "aaa");
                preparedStatement.setString(2, "Вело");
                preparedStatement.setInt(3, 1);
                preparedStatement.setInt(4, 2);
                preparedStatement.setInt(5, 3);
                preparedStatement.setInt(6, 4);
                preparedStatement.executeUpdate();
            }
        } catch (Exception ex) {
            
        }

        try {
            for (int i = 0; i < 5000; i++) {
                PreparedStatement preparedStatement;
                preparedStatement = connection.prepareStatement(
                        "insert into routes values(null, ?, ?, ?)");
                preparedStatement.setString(1, "opacha");
                preparedStatement.setString(2, "epta");
                preparedStatement.setInt(3, 20);
                preparedStatement.executeUpdate();
            }
        } catch (Exception ex) {
        }
    }
    
    public static void main(String[] args) {
        //new TestInputInDB().goGogo();
    }
    
    
    
    //bred
//    public class RoutesTableListener implements TableModelListener {
//
//        @Override
//        public void tableChanged(TableModelEvent e) {
//            int column = e.getColumn();
//            int row = e.getFirstRow();
//            TableModel model = (TableModel) e.getSource();
//            String columnName = model.getColumnName(column);
//            Object data = model.getValueAt(row, column);
//
//            /////
//        }
//    }
}
