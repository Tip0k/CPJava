/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author PEOPLE
 */
public class DaoException extends Exception {

    public DaoException() {
        super();
    }

    public DaoException(String msg) {
        super(msg);
    }

    public DaoException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public DaoException(Throwable cause) {
        super(cause);
    }
}
