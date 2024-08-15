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
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import model.Customer;

import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

public class AddCustomerFormController implements Initializable {
    private static int count = 1;

    @FXML
    private Label lblGenId;

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

    public static String generateId() {
        return String.format("C%03d", count);
    }

    private void incrementId() {
        count++;
    }

    void clearFields() {
        txtName.setText("");
        txtAddress.setText("");
        txtContactNum.setText("");
        cmbTittle.setValue(null);
        dob.setValue(null);
    }

    private void showAlert(Alert.AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private void showConfirmationDialog() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Add Another Customer?");
        alert.setHeaderText(null);
        alert.setContentText("Customer added successfully! Do you want to add another customer?");

        alert.showAndWait().ifPresent(response -> {
            switch (response.getButtonData()) {
                case OK_DONE:
                    clearFields();
                    break;
                case CANCEL_CLOSE:
                    showAlert(Alert.AlertType.INFORMATION, "Action Canceled", "Returning to home page...");
                    ((Stage) lblGenId.getScene().getWindow()).close();
                    break;
                default:
                    break;
            }
        });
    }

    @FXML
    void onClickAddCustomer(ActionEvent event) {
        String name = txtName.getText();
        String address = txtAddress.getText();
        String contact = txtContactNum.getText();
        String tittle = cmbTittle.getValue()+"";
        LocalDate localDate = dob.getValue();

        if (name.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Input Error", "Name is empty...Please enter Name.");
            return;
        }
        if (address.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Input Error", "Address is empty...Please enter Address.");
            return;
        }
        if (contact.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Input Error", "Contact number is empty...Please enter Contact Number.");
            return;
        }
        if (tittle == null) {
            showAlert(Alert.AlertType.ERROR, "Input Error", "Title is empty...Please select Title.");
            return;
        }
        if (localDate == null) {
            showAlert(Alert.AlertType.ERROR, "Input Error", "Date of birth is empty...Please add BirthDay.");
            return;
        }

        String id = lblGenId.getText();
        Customer customer = new Customer(id, tittle, name, address, contact, localDate);
        List<Customer> customerList = DBConnection.getInstance().getConnection();

        if (customerList.add(customer)) {
            incrementId();
            lblGenId.setText(generateId());
            showConfirmationDialog();
        } else {
            showAlert(Alert.AlertType.ERROR, "Add Customer Failed", "Failed to add customer. Please try again.");
        }
        System.out.println(customerList);
    }

    @FXML
    void onClickClear(ActionEvent event) {
        clearFields();
    }

    @FXML
    void onClickGoHomePage(MouseEvent event) {
        ((Stage) ((Node) event.getSource()).getScene().getWindow()).close();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<String> titles = FXCollections.observableArrayList("Mr.", "Mrs.");
        cmbTittle.setItems(titles);
        lblGenId.setText(generateId());
    }
}
