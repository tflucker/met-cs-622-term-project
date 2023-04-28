package com.gims;

import java.io.File;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.gims.constants.CommonConstants;
import com.gims.controller.FileController;
import com.gims.controller.GreetingController;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * Main class of the Grocery Inventory Management System (GIMS), which runs and
 * prints information to the console to introduce the user and to the available
 * commands to interact with the system.
 * 
 * @author Tim Flucker
 *
 */
@SuppressWarnings("restriction")
@SpringBootApplication
public class GIMSApp extends Application {

	private static String filepath = "";

	/**
	 * Main method of application. Calls the Application.launch() method to create
	 * JavaFX welcome screen for user.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		SpringApplication.run(GIMSApp.class, args);

		// prints greeting to user and asks for user input.
		launch(args);

		// using filepath from JavaFX, determine datasource (default will use whatever
		// is in DB or a custom file)
		if (filepath != "") {
			FileController.importData(filepath);
		}

		// method where user will select commands to manipulate data
		GreetingController.getUserInput();
	}

	/**
	 * Creates a JavaFX welcome screen to display text to the user and prompt them
	 * to choose how data will be imported into the application.
	 */
	@Override
	public void start(Stage primaryStage) throws Exception {
		VBox vbx = new VBox(25);
		vbx.setAlignment(Pos.CENTER);

		// define text elements
		Text headerText = new Text(CommonConstants.APP_TITLE);
		headerText.setFont(Font.font("Verdana", FontWeight.BOLD, FontPosture.REGULAR, 24));
		Text welcomeText = new Text(CommonConstants.WELCOME_TEXT);
		welcomeText.setTextAlignment(TextAlignment.CENTER);

		// define buttons
		Button defaultDataBtn = new Button("Use Current Data");
		Button chooseFileBtn = new Button("Choose File...");

		// set button behavior
		// 1. set filepath
		// 2. disable buttons to prevent further user selection
		// 3. show confirmation text and direct user to console for further action
		chooseFileBtn.setOnAction(e -> {
			File file = new FileChooser().showOpenDialog(primaryStage);
			filepath = file.getAbsolutePath();
			defaultDataBtn.setDisable(true);
			chooseFileBtn.setDisable(true);

			primaryStage.close();
		});

		defaultDataBtn.setOnAction(e -> {
			defaultDataBtn.setDisable(true);
			chooseFileBtn.setDisable(true);
			primaryStage.close();
		});

		// add all element to the VBox
		vbx.getChildren().addAll(headerText, welcomeText, chooseFileBtn, defaultDataBtn);

		// set the scene and stage!
		Scene scene = new Scene(vbx, 750, 500);
		primaryStage.setTitle("GIMS Application");
		primaryStage.setScene(scene);
		primaryStage.show();

	}
}