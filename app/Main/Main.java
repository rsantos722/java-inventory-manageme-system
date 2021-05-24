

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


/**
 * <p>@author Rafael Santos</p>
 * <p>For WGU C482</p>
 * <p>Main class that starts program</p>
 * <p>Error I corrected: See mainFormController, partSearchAction</p>
 *
 * <p>Feature I would add: I would add a "cost to make" feature for the products.
 * This would take the cost of all associated Parts on the Product, and give an indicator of how much this Product would cost to build.</p>
 *
 */
public class Main extends Application {


    public static void main(String[] args) {
        launch(args);
    }


    @Override
    public void start(Stage stage) throws Exception {

        FXMLLoader fxmlloader = new FXMLLoader();
        fxmlloader.setLocation(Main.class.getResource("mainForm.fxml"));
        Parent root = fxmlloader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }
}
