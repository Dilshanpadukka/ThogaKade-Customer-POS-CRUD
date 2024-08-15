package controller;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import db.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import model.Customer;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class UpdateCustomerFormController implements Initializable {

    @FXML
    private TextField txtSearchId;

    @FXML
    private JFXComboBox<String> cmbTittle;

    @FXML
    private DatePicker dob;

    @FXML
    private TextArea txtAddress;

    @FXML
    private JFXTextField txtContactNum;

    @FXML
    private JFXTextField txtName;

    private void clearFields() {
        txtSearchId.setText("");
        txtName.setText("");
        txtAddress.setText("");
        txtContactNum.setText("");
        dob.setValue(null);
        cmbTittle.setValue(null);
    }

    @FXML
    void onClickCancel(ActionEvent event) {
        clearFields();
    }

    @FXML
    void onClickGoHomePage(MouseEvent event) {
        ((Stage) ((Node) event.getSource()).getScene().getWindow()).close();
    }

    @FXML
    void onClickUpdateCustomer(ActionEvent event) {
        String searchId = txtSearchId.getText();

        if (searchId == null || searchId.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Input Error", "Please enter a Customer ID.");
            return;
        }

        if (txtName.getText().isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Input Error", "Name is empty...Please enter Name.");
            return;
        }

        if (txtAddress.getText().isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Input Error", "Address is empty...Please enter Address.");
            return;
        }

        if (txtContactNum.getText().isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Input Error", "Contact number is empty...Please enter Contact Number.");
            return;
        }

        if (dob.getValue() == null) {
            showAlert(Alert.AlertType.WARNING, "Input Error", "Date of birth is empty...Please add BirthDay.");
            return;
        }

        if (cmbTittle.getValue() == null) {
            showAlert(Alert.AlertType.WARNING, "Input Error", "Title is empty...Please select Title.");
            return;
        }

        List<Customer> customerList = DBConnection.getInstance().getConnection();
        Customer customerToUpdate = null;

        for (Customer customer : customerList) {
            if (customer.getId().equals(searchId)) {
                customerToUpdate = customer;
                break;
            }
        }

        if (customerToUpdate != null) {
            customerToUpdate.setName(txtName.getText());
            customerToUpdate.setAddress(txtAddress.getText());
            customerToUpdate.setContact(txtContactNum.getText());
            customerToUpdate.setDob(dob.getValue());
            customerToUpdate.setTittle(cmbTittle.getValue());

            showAlert(Alert.AlertType.INFORMATION, "Update Successful", "Customer details updated successfully!");
            showConfirmationDialog(event);
        } else {
            showAlert(Alert.AlertType.ERROR, "Customer Not Found", "Customer not found for ID: " + searchId);
        }
    }

    public void onKeyEnterSearch(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.ENTER) {
            searchCustomerById();
        }
    }

    private void searchCustomerById() {
        String searchId = txtSearchId.getText();
        if (searchId != null && !searchId.isEmpty()) {
            List<Customer> customerList = DBConnection.getInstance().getConnection();
            boolean customerFound = false;
            for (Customer customer : customerList) {
                if (customer.getId().equals(searchId)) {
                    String fullName = customer.getName();
                    String[] nameParts = fullName.split("\\.");
                    txtName.setText((nameParts[1]));
                    txtAddress.setText(customer.getAddress());
                    txtContactNum.setText(customer.getContact());
                    dob.setValue(customer.getDob());
                    cmbTittle.setValue(customer.getTittle());
                    customerFound = true;
                    break;
                }
            }

            if (!customerFound) {
                showAlert(Alert.AlertType.ERROR, "Customer Not Found", "Customer not found for ID: " + searchId);
            }
        } else {
            showAlert(Alert.AlertType.WARNING, "Input Error", "Please enter a Customer ID.");
        }
    }

    private void showAlert(Alert.AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private void showConfirmationDialog(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Search Again?");
        alert.setHeaderText(null);
        alert.setContentText("Do you want to update another customer?");

        alert.showAndWait().ifPresent(response -> {
            switch (response.getButtonData()) {
                case OK_DONE:
                    clearFields();
                    break;
                case CANCEL_CLOSE:
                    clearFields();
                    showAlert(Alert.AlertType.INFORMATION, "Action Canceled", "Returning to home page...");
                    ((Stage) ((Node) event.getSource()).getScene().getWindow()).close();
                    break;
                default:
                    break;
            }
        });
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<String> titles = FXCollections.observableArrayList("Mr.", "Mrs.");
        cmbTittle.setItems(titles);
    }
}
