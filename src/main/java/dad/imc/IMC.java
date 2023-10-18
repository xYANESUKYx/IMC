package dad.imc;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.converter.NumberStringConverter;

public class IMC extends Application{

	@Override
	public void start(Stage primaryStage) throws Exception {
		//ui
		
		TextField pesoText = new TextField();
		TextField alturaText = new TextField();
		Text imcText = new Text();
		Text calificacionText = new Text();
		
		VBox root = new VBox();
		root.setAlignment(Pos.CENTER);
		root.setFillWidth(false);
		
		pesoText.setPrefWidth(80);
		alturaText.setPrefWidth(80);
		
		Scene scene = new Scene(root, 320, 200);
		
		primaryStage.setTitle("IMC.fxml");
		primaryStage.setScene(scene);
		primaryStage.show();
		primaryStage.getIcons().add(new Image("file:src/images/IMC.png"));
		
		HBox hbox1 = new HBox(new Label("Peso:  "), pesoText, new Label("  kg"));
		
		HBox hbox2 = new HBox(new Label("Altura:  "), alturaText, new Label("  cm"));
		
		HBox hbox3 = new HBox(new Label("IMC:  "), imcText);
		
		root.getChildren().addAll(hbox1, hbox2, hbox3, calificacionText);
		
		//binding
		
		StringProperty peso = new SimpleStringProperty(); peso.set("");
		StringProperty altura = new SimpleStringProperty(); altura.set("");
		StringProperty imc = new SimpleStringProperty();
		StringProperty calificacion = new SimpleStringProperty();
		calificacion.set("Bajo peso | Normal | Sobrepeso | Obeso");
		imc.set("(peso * altura ^ 2)");
		
		imc.addListener((o, ov, nv)-> {
			System.out.print("imc = " + nv);
			
			double imcnv = Double.parseDouble(nv); 
			
			calificacion.set("" + imcnv);
			if (imcnv < 18.5){
				calificacion.set("Bajo peso");
			}
			else if (imcnv >= 18.5 && imcnv < 25){
				calificacion.set("Normal");
			}
			else if (imcnv >= 25 && imcnv < 30){
				calificacion.set("Sobrepeso");
			}

			else if (imcnv >= 30){
				calificacion.set("Obeso");
			}
			
		});
		
		//bindeamos las variables a los cuadros de texto
		//Conectar strings
		pesoText.textProperty().bindBidirectional(peso);
		alturaText.textProperty().bindBidirectional(altura);
		
		//imcText.textProperty().bindBidirectional(imc, new NumberStringConverter());
		calificacionText.textProperty().bindBidirectional(calificacion);
				
		//conectamos el resultado con el conector
		//Conectar string con int 
		imcText.textProperty().bindBidirectional(imc);
		
		//peso.addListener((o, ov, nv)-> {a = Integer.parseInt(nv);});
		//altura.addListener((o, ov, nv)-> {p = Double.parseDouble(nv);});
		
		//DecimalFormat df = new DecimalFormat("#.##");
		//imc.bind(Double.parseDouble(peso.get()));
		
		DecimalFormat df = new DecimalFormat("#.##");
		
		
		
		imc.bind(Bindings.createStringBinding(() -> {
			if (altura.get().length() == 0 || peso.get().length() == 0 ) {
		        return "(peso * altura ^ 2)"; 
		    } else {
		    	
            double p = Double.parseDouble(peso.get());
            double a = Double.parseDouble(altura.get());
            double suma = p / ((a / 100) * (a / 100));
            BigDecimal roundedValue = BigDecimal.valueOf(suma).setScale(2, RoundingMode.HALF_UP);
            return roundedValue.toPlainString();
		    }}, peso, altura));
		
		//imc.bind(Bindings.when(peso.isEqualTo(0).or(altura.isEqualTo(0))).then(666666666)  
        //.otherwise(peso.divide((altura.divide(100)).multiply(altura.divide(100)))));
		
		/*
		
		//
		imc.bind(Bindings.createStringBinding(() -> {
		    if (altura.get().length() == 0 || peso.get().length() == 0 ) {
		        return "(peso * altura ^ 2)"; 
		    } else {
		    	
		    	double imcfinal = (Double.parseDouble(peso.get()) / ((Double.parseDouble(altura.get()) / 100)* (Double.parseDouble(altura.get()) / 100)));
		       return "" + df.format(imcfinal);
		    }
		}, peso, altura));
		
		*/
		

	

		
	}
}