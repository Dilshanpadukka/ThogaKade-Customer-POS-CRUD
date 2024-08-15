package controller;

import com.jfoenix.controls.JFXTextField;
import db.DBConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
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

import java.util.List;

public class DeleteCustomerFormController {

    public TextField txtSearchId;
    @FXML
    private DatePicker dob;

    @FXML
    private TextArea txtAddress;

    @FXML
    private JFXTextField txtContactNum;

    @FXML
    private JFXTextField txtName;

    @FXML
    void onClickCancel(ActionEvent event) {
        clearFields();
    }

    @FXML
    void onClickDeleteCustomer(ActionEvent event) {
        String searchId = txtSearchId.getText();
        if (searchId != null && !searchId.isEmpty()) {
            List<Customer> customerList = DBConnection.getInstance().getConnection();
            Customer customerToDelete = customerList.stream().filter(customer -> customer.getId().equals(searchId)).findFirst().orElse(null);

            if (customerToDelete != null) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Delete Customer?");
                alert.setHeaderText(null);
                alert.setContentText("Do you want to delete this customer?");

                alert.showAndWait().ifPresent(response -> {
                    if (response.getButtonData().isDefaultButton()) {
                        customerList.remove(customerToDelete);
                        showAlert(Alert.AlertType.INFORMATION, "Customer Deleted", "Customer with ID: " + searchId + " has been deleted.");
                        showConfirmationDialog();
                    } else {
                        showAlert(Alert.AlertType.INFORMATION, "Action Canceled", "Customer deletion canceled.");
                    }
                });
            } else {
                showAlert(Alert.AlertType.ERROR, "Customer Not Found", "Customer not found for ID: " + searchId);
            }
        } else {
            showAlert(Alert.AlertType.WARNING, "Input Error", "Please enter a Customer ID.");
        }
    }

    @FXML
    void onClickGoHomePage(MouseEvent event) {
        ((Stage) ((Node) event.getSource()).getScene().getWindow()).close();
    }

    public void onKeyEnterSearch(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.ENTER) {
            String searchId = txtSearchId.getText();
            if (searchId != null && !searchId.isEmpty()) {
                List<Customer> customerList = DBConnection.getInstance().getConnection();
                boolean customerFound = false;

                for (Customer customer : customerList) {
                    if (customer.getId().equals(searchId)) {
                        txtName.setText(customer.getName());
                        txtAddress.setText(customer.getAddress());
                        txtContactNum.setText(customer.getContact());
                        dob.setValue(customer.getDob());
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
    }

    private void showAlert(Alert.AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private void showConfirmationDialog() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete Another?");
        alert.setHeaderText(null);
        alert.setContentText("Do you want to delete another customer?");

        alert.showAndWait().ifPresent(response -> {
            switch (response.getButtonData()) {
                case OK_DONE:
                    clearFields();
                    break;
                case CANCEL_CLOSE:
                    clearFields();
                    showAlert(Alert.AlertType.INFORMATION, "Action Canceled", "Returning to home page...");
                    break;
                default:
                    break;
            }
        });
    }

    private void clearFields() {
        txtSearchId.setText("");
        txtName.setText("");
        txtAddress.setText("");
        txtContactNum.setText("");
        dob.setValue(null);
    }
}
