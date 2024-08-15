package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class DashFormController {

    private Stage addCustomerStage;
    private Stage deleteCustomerStage;
    private Stage searchCustomerStage;
    private Stage updateCustomerStage;
    private Stage viewCustomerStage;

    @FXML
    void onClickAddCustomer(ActionEvent event) {
        if (addCustomerStage == null || !addCustomerStage.isShowing()) {
            addCustomerStage = new Stage();
            try {
                addCustomerStage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/add_customer_form.fxml"))));
                addCustomerStage.show();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            addCustomerStage.toFront();
        }
    }

    @FXML
    void onClickDeleteCustomer(ActionEvent event) {
        if (deleteCustomerStage == null || !deleteCustomerStage.isShowing()) {
            deleteCustomerStage = new Stage();
            try {
                deleteCustomerStage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/delete_customer_form.fxml"))));
                deleteCustomerStage.show();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            deleteCustomerStage.toFront();
        }
    }

    @FXML
    void onClickSearchCustomer(ActionEvent event) {
        if (searchCustomerStage == null || !searchCustomerStage.isShowing()) {
            searchCustomerStage = new Stage();
            try {
                searchCustomerStage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/search_customer_form.fxml"))));
                searchCustomerStage.show();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            searchCustomerStage.toFront();
        }
    }

    @FXML
    void onClickUpdateCustomer(ActionEvent event) {
        if (updateCustomerStage == null || !updateCustomerStage.isShowing()) {
            updateCustomerStage = new Stage();
            try {
                updateCustomerStage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/update_customer_form.fxml"))));
                updateCustomerStage.show();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            updateCustomerStage.toFront();
        }
    }

    @FXML
    void onClickViewCustomer(ActionEvent event) {
        if (viewCustomerStage == null || !viewCustomerStage.isShowing()) {
            viewCustomerStage = new Stage();
            try {
                viewCustomerStage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/view_customer_form.fxml"))));
                viewCustomerStage.show();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            viewCustomerStage.toFront();
        }
    }

    public void onClickLogout(MouseEvent mouseEvent) {
        Stage currentStage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        currentStage.close();
    }
}
