/* Name: Jaejo Antony Manjila
 * Date: Friday, October 15th, 2021
 * Course Code: ICS 4U1
 * Brief Description: This program lets the user order pizza from Little Caesars. The user has the choice to pick the pizza size, toppings, and drinks that 
 * they would like although they are forced to pick a pizza size to continue the program. Then, the program outputs the total cost of each category in a text
 * field underneath each category as the user picks their choices. Next, the user presses the calculate button to know the costs of the whole combo such as 
 * the sub total or tax etc. Finally, the user is to hit the checkout button in which where an alert will display whether what they chose was correct. If the 
 * user is satisfied with their choices, they press YES and an alert is outputted to finally end the program.
 */
package application;
	
import java.text.DecimalFormat;
import java.util.Optional;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;


public class Little_Caesars extends Application 
{
	//Initializing variables used throughout the whole program
	double sizeCost, topCost, drinkCost, subtotalCost, deliveryCost, hstCost, grandCost;
	int topCount, cokeTotal, spriteTotal, orangeTotal, beerTotal;
	boolean sizeCheck = false;
	String finalPizza, finalTopping, finalDrink, finalString; 
	RadioButton small, medium, large, party;
	ComboBox<Integer> coke, sprite, orange, beer;
	Button calc, clear, checkout, exit;
	String[] toppinglbl = new String[] {"Mushrooms" , "Green Peppers", "Onions", "Hot Pepper", "Pepperoni", "Bacon", "Tomato", "Extra Cheese"};
	CheckBox[] topBox = new CheckBox[8];
	TextField[] fieldBox = new TextField[7];
	ToggleGroup togglegroup;
	DecimalFormat df = new DecimalFormat(".00");
	Font font = Font.font(null, FontWeight.BOLD, 12);
	
	//Method for setting the layout of the scene
	public void start(Stage primaryStage) 
	{
		try 
		{
			//Creating pane using GridPane
			GridPane root = new GridPane();
			//root.setGridLinesVisible(true);
			root.setVgap(10);
			root.setHgap(10);
			root.setPadding(new Insets(10, 10, 10, 10));
			Scene scene = new Scene(root, 600, 500);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			
			//If user clicks the x button to exit
			primaryStage.setOnCloseRequest(event -> 
			{
				//Alert asking if user wants to exit
				Alert alert = new Alert(AlertType.CONFIRMATION);
				alert.setHeaderText(null);
				alert.setContentText("Are you sure you want to exit?");
				alert.setTitle("Little Caesars");
				alert.getButtonTypes().clear();
				alert.getButtonTypes().addAll(ButtonType.YES, ButtonType.NO);
				Optional<ButtonType> result = alert.showAndWait();
				
				//If user hits yes, close program
				if(result.get() == ButtonType.YES)
				{
					Alert alert1 = new Alert(AlertType.INFORMATION);
					alert1.setHeaderText(null);
					alert1.setContentText("Thank you for choosing Little Caesars!");
					alert1.setTitle("Little Caesars");
					alert1.showAndWait();
					Platform.exit();
					System.exit(0);
				}
				else if(result.get() == ButtonType.NO)
				{
					event.consume();
				}
			});
			
			//Images, layouts, text fields, button, labels initialized in the pane
			Image img1 = new Image("file:littlecaesarslogo.png");
			ImageView logo = new ImageView(img1);
			GridPane.setColumnSpan(logo, 3);
			GridPane.setHalignment(logo, HPos.CENTER);
			
			//Pane for pizza size category
			TitledPane pizzaSize = new TitledPane();
			pizzaSize.setCollapsible(false);
			pizzaSize.setText("SIZE");
			pizzaSize.setFont(Font.font(null, FontWeight.BOLD, 12));
			
			//VBox added to the pizza size TitledPane with RadioButtons
			VBox sizeChoice = new VBox();
			sizeChoice.setSpacing(10);
			sizeChoice.setPadding(new Insets(10, 50, 10, 10));
			
			small = new RadioButton();
			small.setText("Small");
			small.setOnAction(e -> radio_size());
			
			medium = new RadioButton();
			medium.setText("Medium");
			medium.setOnAction(e -> radio_size());
			
			large = new RadioButton();
			large.setText("Large");
			large.setOnAction(e -> radio_size());
			
			party = new RadioButton();
			party.setText("Party");
			party.setOnAction(e -> radio_size());
			
			togglegroup = new ToggleGroup();
			togglegroup.getToggles().addAll(small, medium, large, party);
			sizeChoice.getChildren().addAll(small, medium, large, party);
			pizzaSize.setContent(sizeChoice);
			
			//Pane for toppings category
			TitledPane toppings = new TitledPane();
			toppings.setCollapsible(false);
			toppings.setText("TOPPINGS");
			toppings.setFont(Font.font(null, FontWeight.BOLD, 12));
			
			//TilePane added to the toppings TitledPane with CheckBoxes
			TilePane topChoice = new TilePane();
			topChoice.setPrefColumns(2);
			topChoice.setPrefRows(4);
			topChoice.setHgap(10);
			topChoice.setVgap(10);
			topChoice.setAlignment(Pos.CENTER);
			topChoice.setOrientation(Orientation.VERTICAL);
			
			//For loop to create the check boxes
			for(int j = 0;  j < toppinglbl.length; j++)
			{
				topBox[j] = new CheckBox();
				topBox[j].setText(toppinglbl[j]);
				topBox[j].setPrefSize(120, 10);
				topBox[j].setOnAction(e -> checkBox_top());
			}
			topChoice.getChildren().addAll(topBox);
			toppings.setContent(topChoice);
			
			//Pane for the drinks category
			TitledPane drinks = new TitledPane();
			drinks.setCollapsible(false);
			drinks.setText("BEVERAGES");
			drinks.setFont(Font.font(null, FontWeight.BOLD, 12));
			
			//Pane added to drinks category TitledPane with combo boxes and labels
			TilePane drinkChoice = new TilePane();
			drinkChoice.setPrefColumns(2);
			drinkChoice.setPrefRows(4);
			drinkChoice.setHgap(10);
			drinkChoice.setAlignment(Pos.TOP_LEFT);
			
			Label drink1 = new Label();
			drink1.setText("Coke");
			TilePane.setAlignment(drink1, Pos.CENTER_LEFT);
			Label drink2 = new Label();
			drink2.setText("Sprite");
			drink2.setAlignment(Pos.TOP_LEFT);
			TilePane.setAlignment(drink2, Pos.CENTER_LEFT);
			Label drink3 = new Label();
			drink3.setText("Orange");
			drink3.setAlignment(Pos.TOP_LEFT);
			TilePane.setAlignment(drink3, Pos.CENTER_LEFT);
			Label drink4 = new Label();
			drink4.setText("Root Beer");
			drink4.setAlignment(Pos.TOP_LEFT);
			TilePane.setAlignment(drink4, Pos.CENTER_LEFT);
			
			coke = new ComboBox<Integer>();
			coke.getItems().addAll(0, 1, 2, 3, 4, 5, 6);
			coke.setVisibleRowCount(3);
			coke.setValue(0);
			coke.setOnAction(e -> comboBox_drinks());
			sprite = new ComboBox<Integer>();
			sprite.getItems().addAll(0, 1, 2, 3, 4, 5, 6);
			sprite.setVisibleRowCount(3);
			sprite.setValue(0);
			sprite.setOnAction(e -> comboBox_drinks());
			orange = new ComboBox<Integer>();
			orange.getItems().addAll(0, 1, 2, 3, 4, 5, 6);
			orange.setVisibleRowCount(3);
			orange.setValue(0);
			orange.setOnAction(e -> comboBox_drinks());
			beer = new ComboBox<Integer>();
			beer.getItems().addAll(0, 1, 2, 3, 4, 5, 6);
			beer.setVisibleRowCount(3);
			beer.setValue(0);
			beer.setOnAction(e -> comboBox_drinks());
			drinkChoice.getChildren().addAll(drink1, coke, drink2, sprite, drink3, orange, drink4, beer);
			drinks.setContent(drinkChoice);
			
			//Label for indicating info to user
			Label free = new Label();
			free.setText("First three (3) toppings are free!");
			free.setFont(font);
			free.setAlignment(Pos.CENTER);
			GridPane.setHalignment(free, HPos.CENTER);
			
			//Creating the text field used in the program through an array 
			for(int k = 0; k < fieldBox.length; k++)
			{
				fieldBox[k] = new TextField();
				fieldBox[k].setEditable(false);
				fieldBox[k].setText("$0.00");
				fieldBox[k].setFont(font);
				fieldBox[k].setAlignment(Pos.CENTER);
				fieldBox[k].setPrefSize(120, 20);
			}
			
			//Setting the max size of the category text fields and their alignment within the pane
			fieldBox[0].setMaxSize(120, 20);
			GridPane.setHalignment(fieldBox[0], HPos.CENTER);
			fieldBox[1].setMaxSize(120, 20);
			GridPane.setHalignment(fieldBox[1], HPos.CENTER);
			fieldBox[2].setMaxSize(120, 20);
			GridPane.setHalignment(fieldBox[2], HPos.CENTER);
			
			
			//Image of payment options
			Image img2 = new Image("file:PaymentOptions.png");
			ImageView payment = new ImageView(img2);
			GridPane.setHalignment(payment, HPos.CENTER);
			
			//Tile Pane for the price outputs to user of sub total, delivery, HST, and grand total
			TilePane output = new TilePane();
			output.setPadding(new Insets(10, 20, 10, 20));
			output.setPrefColumns(2);
			output.setPrefRows(4);
			output.setHgap(1);
			output.setVgap(1);
			
			Label subtotaltxt = new Label();
			subtotaltxt.setText("SUBTOTAL: ");
			TilePane.setAlignment(subtotaltxt, Pos.CENTER_LEFT);
			Label deliverytxt = new Label();
			deliverytxt.setText("DELIVERY FEE: ");
			TilePane.setAlignment(deliverytxt, Pos.CENTER_LEFT);
			Label hsttxt = new Label();
			hsttxt.setText("HST: ");
			TilePane.setAlignment(hsttxt, Pos.CENTER_LEFT);
			Label totaltxt = new Label();
			totaltxt.setText("GRAND TOTAL: ");
			TilePane.setAlignment(totaltxt, Pos.CENTER_LEFT);
			
			//Inserting the text field from the for loop and the labels
			output.getChildren().addAll(subtotaltxt, fieldBox[3], deliverytxt, fieldBox[4], hsttxt, fieldBox[5], totaltxt, fieldBox[6]);
			
			//Buttons for calculating the costs, clearing everything, checking out, and exiting
			VBox buttons = new VBox();
			buttons.setSpacing(10);
			buttons.setPadding(new Insets(0, 20, 0, 20));
			calc = new Button("CALCULATE"); 
			calc.setPrefSize(100, 30);
			calc.setOnAction(e -> btn_calc());
			clear = new Button("CLEAR"); 
			clear.setPrefSize(100, 30);
			clear.setOnAction(e -> btn_clear());
			checkout = new Button("CHECKOUT"); 
			checkout.setPrefSize(100, 30);
			checkout.setDisable(true);
			checkout.setOnAction(e -> btn_checkout());
			exit = new Button("EXIT"); 
			exit.setPrefSize(100, 30);
			exit.setOnAction(e -> btn_exit());
			buttons.getChildren().addAll(calc, clear, checkout, exit);
			
			//Adding all the components above in the panes to the GridPane
			root.add(logo, 0, 0);
			root.add(pizzaSize, 0, 1);
			root.add(toppings, 1, 1);
			root.add(drinks, 2, 1);
			root.add(free, 1, 2);
			root.add(fieldBox[0], 0, 3);
			root.add(fieldBox[1], 1, 3);
			root.add(fieldBox[2], 2, 3);
			root.add(payment, 0, 4);
			root.add(output, 1, 4);
			root.add(buttons, 2, 4);
			
			primaryStage.setScene(scene);
			primaryStage.show();
		} 
		catch(Exception e) 
		{
			e.printStackTrace();
		}
	}
	
	//Launches the start method
	public static void main(String[] args) 
	{
		launch(args);
	}
	
	//Method for when pizza category is selected and setting the text field accordingly
	private void radio_size()
	{
		if(small.isSelected())
		{
			fieldBox[0].setText("$7.99");
			sizeCost = 7.99;
			finalPizza = "- Small";
		}
		else if(medium.isSelected())
		{
			fieldBox[0].setText("$8.99");
			sizeCost = 8.99;
			finalPizza = "- Medium";
		}
		else if(large.isSelected())
		{
			fieldBox[0].setText("$9.99");
			sizeCost = 9.99;
			finalPizza = "- Large";
		}
		else if(party.isSelected())
		{
			fieldBox[0].setText("$10.99");
			sizeCost = 10.99;
			finalPizza = "- Party";
		}
		sizeCheck = true;
	}
	
	//Method for when toppings category is selected
	private void checkBox_top()
	{
		topCount = 0;
		finalTopping = "";
		//For loop to check if each button's selected
		for(int k  = 0; k < 8; k++)
		{
			if(topBox[k].isSelected())
			{
				topCount++;
				finalTopping = finalTopping + "\n- " + topBox[k].getText();
			}
		}
		//calculate to see if there are more than 3 toppings and setting the text fields accordingly
		topCount = topCount - 3;
		if(topCount <= 0)
		{
			topCount = 0;
			fieldBox[1].setText("$0.00");
		}
		else 
		{
			fieldBox[1].setText("$" + df.format(topCount));
		}
		topCost = topCount;
	}
	
	//Method for when drinks category is selected
	private void comboBox_drinks()
	{
		finalDrink = "";
		//calculating the total cost of drinks each time combo box is changed
		drinkCost = (coke.getValue() + sprite.getValue() + orange.getValue() + beer.getValue()) * 0.99;
		
		//Checking if the amount of drink is higher than 0 and if it is, add to final string for the alert
		if(coke.getValue() > 0)
		{
			finalDrink = "\n-" + String.valueOf((int)coke.getValue()) + "x" + " Coke";
		}
		if(sprite.getValue() > 0)
		{
			finalDrink = finalDrink + "\n-" + String.valueOf((int)sprite.getValue()) + "x" + " Sprite";
		}
		if(orange.getValue() > 0)
		{
			finalDrink = finalDrink + "\n-" + String.valueOf((int)orange.getValue()) + "x" + " Orange";
		}
		if(beer.getValue() > 0)
		{
			finalDrink = finalDrink + "\n-" + String.valueOf((int)beer.getValue()) + "x" + " Beer";
		}
		fieldBox[2].setText("$" + df.format(drinkCost));
	}
	
	//Method for calculating the price outputs to the user
	private void btn_calc()
	{
		//If the user hasn't picked a pizza size
		if(sizeCheck == false)
		{
			Alert error = new Alert(AlertType.ERROR);
			error.setHeaderText(null);
			error.setContentText("Your order could not be completed!\n Please select a pizza size");
			error.setTitle("Incomplete order");
			error.showAndWait();
		}
		else 
		{
			//Enabling the checkout button
			checkout.setDisable(false);
			
			//Calculating the sub total from all the category costs
			subtotalCost = sizeCost + topCost+ drinkCost;
			
			//To check if delivery is free or $5 according to the sub total
			if(subtotalCost < 15)
			{
				deliveryCost = 5;
				fieldBox[4].setText("$" + df.format(deliveryCost));
			}
			else
			{
				fieldBox[4].setStyle("-fx-background-color: green");
			}
			
			//Calculating the tax, grand total, and setting text in the text field of each section
			hstCost = .13 * subtotalCost;
			grandCost = subtotalCost + deliveryCost + hstCost;
			fieldBox[3].setText("$" + df.format(subtotalCost));
			fieldBox[5].setText("$" + df.format(hstCost));
			fieldBox[6].setText("$" + df.format(grandCost));
		}
	}
	
	//Method for clearing everything in the Pane
	private void btn_clear()
	{
		togglegroup.selectToggle(null);
		
		//For loops to set the check boxes to false and text fields to base text
		for(int i = 0; i < topBox.length; i++)
		{
			topBox[i].setSelected(false);
		}
		for(int k = 0; k < fieldBox.length; k++)
		{
			fieldBox[k].setText("$0.00");
		}
		
		//Setting value of combo box back to 0 and delivery background to white
		fieldBox[4].setStyle("-fx-background-color: white");
		coke.setValue(0);
		sprite.setValue(0);
		orange.setValue(0);
		beer.setValue(0);
		
		//Setting checkout button to true since size is now not selected
		checkout.setDisable(true);
		sizeCheck = false;
	}
	
	//Method for when the user decides to check out
	private void btn_checkout()
	{
		//Alert  for outputting the user's choice and making them decide either to change it or not
		Alert order = new Alert(AlertType.INFORMATION);
		order.setHeaderText(null);
		
		//Final string is made by combining the previous strings made in each category method
		finalString = "Is this order correct?\n\nPIZZA:\n" + finalPizza + "\n\nTOPPINGS:" + finalTopping + "\n\nBEVERAGES:" + finalDrink;
		order.setContentText(finalString);
		order.setTitle("Little Caesars");
		order.getButtonTypes().clear();
		order.getButtonTypes().addAll(ButtonType.YES, ButtonType.NO);
		Optional<ButtonType> result = order.showAndWait();
		
		//If user is satisfied with their choices, output alert and close program
		if(result.get() == ButtonType.YES)
		{
			Alert alert1 = new Alert(AlertType.INFORMATION);
			alert1.setHeaderText(null);
			alert1.setContentText("Thank you for ordering from Little Caesars!\nYour pizza will be delivered in 30 minutes or it's free!");
			alert1.setTitle("Little Caesars");
			alert1.setGraphic(new ImageView(new Image("file:littlecaesarsicon.png")));
			alert1.showAndWait();
			Platform.exit();
			System.exit(0);
		}
	}
	
	//Method for when the user decides to exit
	private void btn_exit()
	{
		//Alert asking if user wants to exit
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setHeaderText(null);
		alert.setContentText("Are you sure you want to exit?");
		alert.setTitle("Little Caesars");
		alert.getButtonTypes().clear();
		alert.getButtonTypes().addAll(ButtonType.YES, ButtonType.NO);
		Optional<ButtonType> result = alert.showAndWait();
		
		//If user hits yes, close program
		if(result.get() == ButtonType.YES)
		{
			Alert alert1 = new Alert(AlertType.INFORMATION);
			alert1.setHeaderText(null);
			alert1.setContentText("Thank you for choosing Little Caesars!");
			alert1.setTitle("Little Caesars");
			alert1.showAndWait();
			Platform.exit();
			System.exit(0);
		}
	}
}
