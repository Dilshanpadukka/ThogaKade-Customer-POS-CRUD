package controller;

import com.jfoenix.controls.JFXTextField;
import db.DBConnection;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
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
import javafx.util.Duration;
import model.Customer;

import java.util.List;

public class SearchCustomerFormController {

    public TextField txtSearchId;
    @FXML
    private DatePicker dob;

    @FXML
    private TextArea txtAddress;

    @FXML
    private JFXTextField txtContactNum;

    @FXML
    private JFXTextField txtName;

    void clearFields(){
        txtSearchId.setText("");
        txtName.setText("");
        txtAddress.setText("");
        txtContactNum.setText("");
        dob.setValue(null);
    }

    @FXML
    void onClickClear(ActionEvent event) {
        clearFields();
    }

    @FXML
    void onClickGoHomePage(MouseEvent event) {
        ((Stage) ((Node) event.getSource()).getScene().getWindow()).close();
    }

    @FXML
    void onClickSearchCustomer(ActionEvent event) {
        searchCustomer();
    }

    public void onKeyEnterSearch(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.ENTER) {
            searchCustomer();
        }
    }

    private void searchCustomer() {
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
            } else {
                PauseTransition pause = new PauseTransition(Duration.seconds(10));
                pause.setOnFinished(event -> Platform.runLater(this::showConfirmationDialog));
                pause.play();
            }
        } else {
            showAlert(Alert.AlertType.WARNING, "Input Error", "Please enter a Customer ID.");
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
        alert.setTitle("Search Again?");
        alert.setHeaderText(null);
        alert.setContentText("Do you want to search another customer?");

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
}
