package db;

import model.Customer;

import java.sql.DatabaseMetaData;
import java.util.ArrayList;
import java.util.List;

public class DBConnection {

    private static DBConnection instance;
    private List<Customer> customerList;
    public List<Customer> getConnection(){
        return customerList;
    }

    private DBConnection(){
        this.customerList = new ArrayList<>();
    }

    public static DBConnection getInstance(){
        if (instance==null){
            return instance = new DBConnection();
        }
        return instance;
    }

}
