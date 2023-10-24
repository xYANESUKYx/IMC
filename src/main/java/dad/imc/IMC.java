package dad.imc;

import java.math.BigDecimal;
import java.math.RoundingMode;
import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

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
			
			if (nv.equals("(peso * altura ^ 2)")) {
				calificacion.set("Bajo peso | Normal | Sobrepeso | Obeso");
			}
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
		calificacionText.textProperty().bindBidirectional(calificacion);
		imcText.textProperty().bindBidirectional(imc);

		imc.bind(Bindings.createStringBinding(() -> {
			if (altura.get().length() == 0 || peso.get().length() == 0 || altura.get().equals("0") || peso.get().equals("0") ) {
		        return "(peso * altura ^ 2)"; 
		    } else {
		    	
            double p = Double.parseDouble(peso.get());
            double a = Double.parseDouble(altura.get());
            double suma = p / ((a / 100) * (a / 100));
            BigDecimal roundedValue = BigDecimal.valueOf(suma).setScale(2, RoundingMode.HALF_UP);
            return roundedValue.toPlainString();
		    }}, peso, altura));
		
		/*
		imc.bind(Bindings.when(peso.isEqualTo(0).or(altura.isEqualTo(0))).then(0.0)  
        .otherwise(peso.divide((altura.divide(100)).multiply(altura.divide(100)))));
		
		
		DecimalFormat df = new DecimalFormat("#.##");
		
		imc.bind(Bindings.createStringBinding(() -> {
		    if (peso.get() == 0 || altura.get() == 0) {
		        return "(peso * altura ^ 2)"; 
		    } else {
		    	double alturaget = altura.get();
		    	double pesoaget = peso.get();
		    	
		    	double imcfinal = (pesoaget / ((alturaget / 100)* (alturaget / 100)));
		        return "" + df.format(imcfinal);
		    }
		}, peso, altura));
		*/
		
	}
}