package controller;

import db.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import model.Customer;

import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

public class ViewCustomerFormController implements Initializable {

    @FXML
    private TableColumn<Customer, String> colId;
    @FXML
    private TableColumn<Customer, String> colName;
    @FXML
    private TableColumn<Customer, String> colAddress;
    @FXML
    private TableColumn<Customer, String> colContact;
    @FXML
    private TableColumn<Customer, String> colDob;
    @FXML
    private TableView<Customer> tblCustomers;

    private ObservableList<Customer> customerObservableList = FXCollections.observableArrayList();

    @FXML
    void onClickGoHomePage(MouseEvent event) {
        ((Stage) ((Node) event.getSource()).getScene().getWindow()).close();
    }

    @FXML
    public void onClickViewTable(ActionEvent event) {
        updateTable();
    }

    private void updateTable() {
        List<Customer> customerList = DBConnection.getInstance().getConnection();
        customerObservableList.setAll(customerList);
        tblCustomers.setItems(customerObservableList);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colContact.setCellValueFactory(new PropertyValueFactory<>("contact"));
        colDob.setCellValueFactory(new PropertyValueFactory<>("dob"));
        updateTable();
    }
//    customerList.add(new Customer("C001","Miss","Dilshan","Ragama", LocalDate.of(2004,7,5),"0770095678"));
//        customerList.add(new Customer("C002","Master","Nimal","Panadura", LocalDate.of(2002,5,23),"0786567890"));
//        customerList.add(new Customer("C003","Master","Arun","Colombo", LocalDate.of(2000,3,9),"0723456666"));
//        customerList.add(new Customer("C004","Mr","Kamal","Galle", LocalDate.of(1990,8,17),"0756667890"));
//        customerList.add(new Customer("C005","Mrs","Ann","Wattala", LocalDate.of(1987,9,13),"0112453200"));
//        customerList.add(new Customer("C006","Mr","saman","Kandana", LocalDate.of(1992,10,7),"0112678096"));
}
