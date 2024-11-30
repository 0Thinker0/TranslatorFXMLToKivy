package logic;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import org.apache.commons.io.FileUtils;

import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.ScaleTransition;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;
import javafx.stage.DirectoryChooser;
import javafx.util.Duration;

//ChatGPT
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import controller.FFKtranslatorController;

public class FFKtranslatorLogic {
	
	public FFKtranslatorLogic(){
		translator = new TranslatorLogic();
		translator.setAppLogic(this);
	}

	//*******************************//
	//Variable//
	//*******************************//
	
    private TextArea textAreaCode;
    private TextArea sorceOutput;
    private TextArea convertionOutput;
    private VBox listConvertions;
    private Pane recentConvertions;
    private Pane convertionPane;
    private Pane moreOptionPane;
    private Text progressBarStatus;
    private int maxProgressBar;
    private ProgressBar progressBar;
    private TranslatorLogic translator;
    private Text textOutput;
    private Text nameFileKV;
    private Text fileNameOutput;
    private ImageView saveFile;
    private ImageView closeConvertion;
    private ImageView repeatConvertion;
    private File fileKV;
    private int numTraslatedFile;
    private ImageView filePython;
    private ImageView exportImage;
    private ImageView warningIcon;
    private TextArea warningTextArea;
    private Pane warningPane;
    private Label translatedFileNum;
    private HBox suggestions;
    private ImageView chatIcon;
    private ImageView messaggeComing;
    private Pane chatPane;
    private TextArea messaggeToSend;
    private Pane warningDarkPane;
    
    
    
    //*******************************//
  	//setComponents viene chiamato all'avvio del programma per passare alla parte di logica le componenti fondamentali//
  	//*******************************//
    
    public void setComponents(TextArea textAreaCode, TextArea sorceOutput, TextArea convertionOutput, VBox listConvertions, 
    		Pane recentConvertions, Text progressBarStatus, ProgressBar progressBar, Pane convertionPane, 
    		Text textOutput, Text nameFileKv, ImageView closeConvertion, ImageView repeatConvertion, 
    		ImageView saveFile, Pane moreOptionPane, Text fileNameOutput, ImageView filePython, ImageView exportImage, ImageView warningIcon, TextArea warningTextArea, Pane warningPane,
    		Label translatedFileNum, HBox suggestions, ImageView chatIcon, ImageView messaggeComing, Pane chatPane, TextArea messaggeToSend, Pane warningDarkPane) {
    	this.textAreaCode = textAreaCode;
    	this.sorceOutput = sorceOutput;
    	this.convertionOutput = convertionOutput;
    	this.listConvertions = listConvertions;
    	this.recentConvertions = recentConvertions;
    	this.progressBarStatus = progressBarStatus;
    	this.progressBar = progressBar;
    	this.convertionPane = convertionPane;
    	this.textOutput = textOutput;
    	this.nameFileKV = nameFileKv;
    	this.closeConvertion = closeConvertion;
    	this.repeatConvertion = repeatConvertion;
    	this.saveFile = saveFile;
    	this.moreOptionPane = moreOptionPane;
    	this.fileNameOutput = fileNameOutput;
    	this.filePython = filePython;
    	this.exportImage = exportImage;
    	this.warningIcon = warningIcon;
    	this.warningPane = warningPane;
    	this.warningTextArea = warningTextArea;
    	this.translatedFileNum = translatedFileNum;
    	this.suggestions = suggestions;
    	this.chatIcon = chatIcon;
    	this.messaggeComing = messaggeComing;
    	this.chatPane = chatPane;
    	this.messaggeToSend = messaggeToSend;
    	this.warningDarkPane = warningDarkPane;
    	
    	numTraslatedFile = 0;
    	
    }
    
    //*******************************//
  	//setComponents//
  	//*******************************//
	
    //*******************************//
  	//setProgressBar viene chiamato quando si avvia una conversione, questo setta il valore massimo della progress bar//
  	//*******************************//
    
    public void setProgressBar(Integer status) {
    	maxProgressBar = status;
    }
    
    //*******************************//
  	//setProgressBar//
    //*******************************//

    //*******************************//
  	//countNumFile viene chiamato quando si accede alla pagina dei feed per contare il numero di file tradotti//
    //*******************************//
    
	public void countNumFile() {
		if(new File("src/main/resources/KVFiles").exists())
			numTraslatedFile = (new File("src/main/resources/KVFiles")).listFiles().length;
    	
    	translatedFileNum.setText(String.valueOf(numTraslatedFile));
	}
	
    //*******************************//
  	//countNumFile//
    //*******************************//
 
	
    //*******************************//
  	//setActivity viene chiamato quando bisogna aggiungere al file delle attività un'operazione effettuata//
    //*******************************//
    	
	private void setActivity(String operation, String name) {
		DateTimeFormatter dtf5 = DateTimeFormatter.ofPattern("yyyy/MM/dd hh:mm");
		
		try {
			FileWriter file = new FileWriter("Activity.txt", true);
			
			switch(operation) {
				case "add":
					file.write(dtf5.format(LocalDateTime.now()) + " - Il file " + name + " è stato tradotto correttamente\n");
					break;
					
				case "remove":
					file.write(dtf5.format(LocalDateTime.now()) + " - Il file " + name + " è stato rimosso correttamente dal sistema\n");
					break;
					
				case "saveCloud":
					file.write(dtf5.format(LocalDateTime.now()) + " - Il file " + name + " è stato salvato in cloud correttamente\n");
					break;
					
				case "saveLocal":
					file.write(dtf5.format(LocalDateTime.now()) + " - Il file " + name + " è stato salvato in locale correttamente\n");
					break;
			}
			
			file.flush();
			file.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
    
    //*******************************//
  	//setActivity//
    //*******************************//
    	
    
    //*******************************//
  	//saveFile viene chiamato ogni volta che si decide di salvare il file su una cartella specifica//
    //*******************************//
    
    public boolean saveFile() {    	
    	DirectoryChooser chooser = new DirectoryChooser();

    	chooser.setTitle("JavaFX Projects");

    	File selectedDirectory = chooser.showDialog(null);
    	if(selectedDirectory != null) {
    		File filKV = new File(selectedDirectory.getAbsolutePath() + "/" + nameFileKV.getText());
	    	try {
				FileUtils.copyFile(new File("src/main/resources/KVFiles/" + nameFileKV.getText()), filKV);
				
				if(filePython.getImage().getUrl().contains("checked")) {
		    		File filePython = new File(selectedDirectory.getAbsolutePath() + "/mainKV.py");
		    		BufferedWriter writer = new BufferedWriter(new FileWriter(filePython));
		    		
		    		String content = 
		    				  "import kivy\r\n"
		    				+ "from kivy.app import App\r\n"
		    				+ "from kivy.uix.screenmanager import ScreenManager, Screen\r\n"
		    				+ "from kivy.core.window import Window\r\n"
		    				+ "from kivy.uix import *\r\n"
		    				+ "\r\n"
		    				+ "class MainScreen(Screen):\r\n";
		    				
    				if(translator.getMethods().isEmpty()) {
    					content +="\tpass\r\n"
    							+ "\r\n";
    				}
		    		for(String method : translator.getMethods()){
		    			if(!content.contains("def " + method + "(self):")) {
			    			content +="\tdef " + method + "(self):\r\n"
			    					+ "\t\tpass\r\n\r\n";
		    			}
		    		}
		    		
		    		content +="class " + nameFileKV.getText().substring(0, nameFileKV.getText().indexOf(".")) + "(App):\r\n"
		    				+ "    def build(self):\r\n"
		    				+ "        Window.size = (" + translator.getDimension() + ")\r\n"
		    				+ "        sm = ScreenManager()\r\n"
		    				+ "        sm.add_widget(MainScreen(name='main'))\r\n"
		    				+ "        return sm\r\n"
		    				+ "\r\n"
		    				+ "if __name__ == '__main__':\r\n"
		    				+ "    " + nameFileKV.getText().substring(0, nameFileKV.getText().indexOf(".")) + "().run()";
		    		
		    		writer.write(content);
		    		writer.close();
				}
				
				if(exportImage.getImage().getUrl().contains("checked")) {
					new File(selectedDirectory.getAbsolutePath() + "/" + translator.getImages().get(0).substring(0, translator.getImages().get(0).indexOf("/"))).mkdirs();
					String destFolder = selectedDirectory.getAbsolutePath() + "/" + translator.getImages().get(0).substring(0, translator.getImages().get(0).indexOf("/"));
					
					for(String img : translator.getImages()) {
						File srcFile = new File(FFKtranslatorController.uploaded.getAbsolutePath().substring(0,FFKtranslatorController.uploaded.getAbsolutePath().lastIndexOf("\\") + 1) + img);
						File destFile = new File(destFolder, img.substring(img.lastIndexOf("/") + 1, img.length()));
						
						FileUtils.copyFile(srcFile, destFile);
					}
				}
				
				setActivity("saveLocal", nameFileKV.getText());
				
				return true;
			} catch (IOException e) {
				e.printStackTrace();
				return false;
			}
    	}
    	return false;
    }
    
    //*******************************//
  	//saveFile//
    //*******************************//
    
    //*******************************//
  	//updateProgressBar viene chiamato si aggiorna la progressBar//
  	//*******************************//
    
    public void updateProgressBar(int update) {
    	
    	float status = (float)update / maxProgressBar;
    	status *= 100;
    	progressBar.setProgress(status);
    	String progress = String.valueOf((int)status);
    	progressBarStatus.setText(progress + "%");
    	
    }
    
    //*******************************//
  	//updateProgressBar//
    //*******************************//
    
    //*******************************//
  	//startConvertion viene chiamato quando si avvia la conversione, questo metodo, oltre a far comparire i pulsanti dedicati alla conversione di occupa di chiamare il traduttore che traduce il file//
    //*******************************//
    
    public void startConvertion(File file) {
    	Thread t = new Thread(new Runnable() {

			@Override
			public void run() {
				String newFileName = file.getName().substring(0, file.getName().indexOf("."));
				try {
										
					FileUtils.copyFile(file, new File("src/main/resources/FXMLFiles/" + newFileName + ".fxml"));
					convertionPane.setVisible(true);

					ScaleTransition scale = new ScaleTransition();
					scale.setDuration(Duration.millis(200));
					scale.setFromX(0);
					scale.setFromY(0);
					scale.setToX(1);
					scale.setToY(1);
					scale.setNode(progressBar);
					scale.play();
					
					textOutput.setVisible(true);
					
					Thread.sleep(200);
					
					scale.setNode(saveFile);
					scale.playFromStart();
					Thread.sleep(200);
					scale.setNode(closeConvertion);
					scale.playFromStart();
					Thread.sleep(200);
					scale.setNode(repeatConvertion);
					scale.playFromStart();
					Thread.sleep(200);
					
					} catch (Exception e) {
						e.printStackTrace();
					}
				
					Platform.runLater(new Runnable() {

						@Override
						public void run() {
							try {
								fileKV = translator.translateToKV(file);
																
								nameFileKV.setText(fileKV.getName());
								
								ReadingFile(fileKV);
								
								setActivity("add", fileKV.getName());
								
								FindRecents();
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					});
			}
		});
		t.start();
    }
    
    //*******************************//
  	//startConvertion//
    //*******************************//
    
    //*******************************//
  	//ReadingFile viene chiamato non appena si � scelto il file dal FileChooser scelto, questo permetter� di leggere il suo contenuto//
  	//*******************************//
    
    public void ReadingFile(File file) {
    	if(file != null) {
    		
    		if(translator.getWarnings() != null)
	    		if(!translator.getWarnings().isEmpty()) {
	    			warningIcon.setVisible(true);
    			
    			for(String warning : translator.getWarnings()) {
    				warningTextArea.setText(warningTextArea.getText() + warning + "\n");
    				createSuggestion(warning);
    			}
    				
    		}
    		
	    	textAreaCode.setText("");
	    	fileNameOutput.setText(file.getName());
	    	try {
				BufferedReader br = new BufferedReader(new FileReader(file));
				String line;
				while((line = br.readLine()) != null) {
					textAreaCode.appendText(line);
					textAreaCode.appendText("\n");
				}
				br.close();
			} catch (Exception e) {
				
				e.printStackTrace();
			}
    	}
    }
    
    //*******************************//
  	//ReadingFile//
  	//*******************************//
    
    private void createSuggestion(String warning) {
    	Pane suggestion = new Pane();
    	suggestion.setMaxHeight(25);
    	suggestion.setStyle("-fx-background-color: #fff; -fx-border-width: 1; -fx-border-color:  #2B2B2B; -fx-border-radius: 10; -fx-background-radius: 10;");
    	suggestion.setPadding(new Insets(2, 2, 2, 2));
    	
    	if(warning.contains("System")) {
    		Text text = new Text("Come posso sostituire il System-Font?");
    		text.setFill(Paint.valueOf("#2B2B2B"));
    		text.setLayoutY(16);
    		text.setLayoutX(5);
    		suggestion.getChildren().add(text);
    		suggestion.setPrefWidth(text.getBoundsInLocal().getWidth() + 10);
    		
    		suggestion.setCursor(Cursor.HAND);
    		suggestion.setOnMouseClicked(new EventHandler<MouseEvent>() {

				@Override
				public void handle(MouseEvent event) {
					warningPane.setVisible(false);
		    		warningDarkPane.setVisible(false);
		    		
					chatIcon.setOpacity(1);
			    	messaggeComing.setVisible(false);
			    	
			    	if(chatPane.getTranslateX() == 0) {
			    	Timeline timeline = new Timeline(
							new KeyFrame(Duration.millis(0),
			                		new KeyValue(chatPane.translateXProperty(), 0, Interpolator.EASE_BOTH)
			                ),
			                new KeyFrame(Duration.millis(400),
			                        new KeyValue(chatPane.translateXProperty(), 684, Interpolator.EASE_BOTH)
			                )
			        	);
			    	
			    	timeline.play();
			    	}
			    	
			    	messaggeToSend.setText("Come posso sostituire il default font System-Font di javaFX per tradurre in kivy?");
				}
    			
    		});
    	}
    	
    	if(warning.contains("onMousePressed") || warning.contains("onMouseMoved") || warning.contains("onMouseDragged") || warning.contains("onMouseClicked")) {
    		Text text = new Text("Quali metodi di input per il mouse di javaFX sono compatibili in kivy?");
    		text.setFill(Paint.valueOf("#2B2B2B"));
    		text.setLayoutY(16);
    		text.setLayoutX(5);
    		suggestion.getChildren().add(text);
    		suggestion.setPrefWidth(text.getBoundsInLocal().getWidth() + 10);
    		
    		suggestion.setCursor(Cursor.HAND);
    		suggestion.setOnMouseClicked(new EventHandler<MouseEvent>() {

				@Override
				public void handle(MouseEvent event) {
					warningPane.setVisible(false);
		    		warningDarkPane.setVisible(false);
		    		
					chatIcon.setOpacity(1);
			    	messaggeComing.setVisible(false);
			    	
			    	if(chatPane.getTranslateX() == 0) {
			    	Timeline timeline = new Timeline(
							new KeyFrame(Duration.millis(0),
			                		new KeyValue(chatPane.translateXProperty(), 0, Interpolator.EASE_BOTH)
			                ),
			                new KeyFrame(Duration.millis(400),
			                        new KeyValue(chatPane.translateXProperty(), 684, Interpolator.EASE_BOTH)
			                )
			        	);
			    	
			    	timeline.play();
			    	}
			    	
			    	messaggeToSend.setText("Quali metodi di input per il mouse di javaFX sono compatibili in kivy?");
				}
    			
    		});
    	}
    	
    	suggestions.getChildren().add(suggestion);
    }

    //*******************************//
  	//SearchConvertionsForHistory viene chiamato quando si apre la sezione history per cercare eventuali conversioni passate//
  	//*******************************//
    
    public void SearchConvertionsForHistory() throws IOException, URISyntaxException{
    	//Controllo esistenza file all'interno delle cartelle
    	java.nio.file.Files.exists(Paths.get("src/main/resources/FXMLFiles"));
    	java.nio.file.Files.exists(Paths.get("src/main/resources/KVFiles"));
    	
    	if(getClass().getResource("/FXMLFiles") != null && getClass().getResource("/KVFiles") != null) {
	    	File[] listOfFXMLFiles = (new File("src/main/resources/FXMLFiles")).listFiles();
	    	File[] listOfKVFiles = (new File("src/main/resources/KVFiles")).listFiles();
	    	
	    	listConvertions.getChildren().clear();
	    	if(listOfFXMLFiles.length != 0 && listOfKVFiles.length != 0) {
	    		for(int i = 0; i < listOfFXMLFiles.length; i++){
	    			Text nomeFXML = new Text(listOfFXMLFiles[i].getName());
	    			nomeFXML.setWrappingWidth(120);
	    			nomeFXML.setStyle("-fx-fill: #717475;");
	    			Text nomeKV = new Text(listOfKVFiles[i].getName());
	    			nomeKV.setWrappingWidth(120);
	    			nomeKV.setStyle("-fx-fill: #717475;");
	    			
	    			BasicFileAttributes attr = Files.readAttributes(Path.of(listOfKVFiles[i].getPath()), BasicFileAttributes.class);
	    		    FileTime fileTime = attr.creationTime();
	    		    
	    			Text dataKV = new Text(fileTime.toString().subSequence(0, 10).toString());
	    			dataKV.setWrappingWidth(110);
	    			dataKV.setStyle("-fx-fill: #717475;");
	    			Text oraKV = new Text(fileTime.toString().subSequence(11, 16).toString());
	    			oraKV.setWrappingWidth(40);
	    			oraKV.setStyle("-fx-fill: #717475;");
	    			
	    			HBox convertion = new HBox();
	    			convertion.setPadding(new Insets(0, 20, 0, 20));
	    			convertion.setSpacing(60);
	    			listConvertions.getChildren().add(convertion);
	    			convertion.setCursor(Cursor.HAND);
	    			convertion.setPrefHeight(54);
	    			convertion.setAlignment(Pos.CENTER_LEFT);
	    			convertion.getChildren().add(nomeFXML);
	    			convertion.getChildren().add(nomeKV);
	    			convertion.getChildren().add(dataKV);
	    			convertion.getChildren().add(oraKV);
	    			convertion.setStyle("-fx-border-color: transparent transparent #CDCDCD transparent; -fx-border-width: 0.5;");
	    			
	    			
	    			convertion.addEventFilter(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
	    			    @Override
	    			    public void handle(MouseEvent mouseEvent) {
	    			    	if(mouseEvent.isPrimaryButtonDown()) {
		    			    	sorceOutput.setText("");
		    			    	convertionOutput.setText("");
		    			    	String fileFXML = "/FXMLFiles/" + ((Text)convertion.getChildren().get(0)).getText();
		    			    	String fileKV = "/KVFiles/" + ((Text)convertion.getChildren().get(1)).getText();
		    			    	
		    			    	try {
		    			    		File sorce = new File(getClass().getResource(fileFXML).toURI());
		    			    		File convertion = new File(getClass().getResource(fileKV).toURI());
		    			    		
		    			    		((Text)((Pane)((Pane)sorceOutput.getParent()).getChildren().get(0)).getChildren().get(0)).setText(sorce.getName());
		    			    		((Text)((Pane)((Pane)sorceOutput.getParent()).getChildren().get(4)).getChildren().get(0)).setText(convertion.getName());
		    			    		
		    						BufferedReader br = new BufferedReader(new FileReader(sorce));
		    						BufferedReader br2 = new BufferedReader(new FileReader(convertion));
		    						String line;
		    						while((line = br.readLine()) != null) {
		    							sorceOutput.appendText(line);
		    							sorceOutput.appendText("\n");
		    						}
		    						line = null;
		    						while((line = br2.readLine()) != null) {
		    							convertionOutput.appendText(line);
		    							convertionOutput.appendText("\n");
		    						}
		    						br.close();
		    						br2.close();
		    					} catch (IOException | URISyntaxException e) {
		    						e.printStackTrace();
		    					}
	    			    	}else if(mouseEvent.getButton() == MouseButton.SECONDARY && moreOptionPane.isVisible()) {
	                    		moreOptionPane.setVisible(false);
	                    	}else if(mouseEvent.getButton() == MouseButton.SECONDARY && !moreOptionPane.isVisible()) {
	                    		moreOptionPane.setLayoutX(mouseEvent.getSceneX() - 2);
	                    		if(mouseEvent.getSceneY() < 540) {
	                    			moreOptionPane.setStyle("-fx-background-color: white; -fx-background-radius: 0 10 10 10; -fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.1), 6, 0, 0.0, 3.0)");
	                    			moreOptionPane.setLayoutY(mouseEvent.getSceneY() - 2);
	                    		}
	                    			
	                    		else {
	                    			moreOptionPane.setStyle("-fx-background-color: white; -fx-background-radius: 10 10 10 0; -fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.1), 6, 0, 0.0, 3.0)");
	                    			moreOptionPane.setLayoutY(mouseEvent.getSceneY() - 55);
	                    		}
	                    		
	                    		moreOptionPane.getChildren().get(1).setOnMouseClicked(new EventHandler<MouseEvent>() {
	                				@Override
	                				public void handle(MouseEvent event) {
                						File kv = new File("src/main/resources/KVFiles/" + ((Text)convertion.getChildren().get(1)).getText());
                						File fxml = new File("src/main/resources/FXMLFiles/" + ((Text)convertion.getChildren().get(0)).getText());
                						
                						if(kv.delete() && fxml.delete()) {
            								if(((Text)((Pane)((Pane)sorceOutput.getParent()).getChildren().get(4)).getChildren().get(0)).getText().equals(kv.getName()) && ((Text)((Pane)((Pane)sorceOutput.getParent()).getChildren().get(0)).getChildren().get(0)).getText().equals(fxml.getName())) {
            									sorceOutput.setText("");
            									convertionOutput.setText("");
            									
            									((Text)((Pane)((Pane)sorceOutput.getParent()).getChildren().get(0)).getChildren().get(0)).setText("");
            		    			    		((Text)((Pane)((Pane)sorceOutput.getParent()).getChildren().get(4)).getChildren().get(0)).setText("");
            								}
            								
            								if(nameFileKV.getText().equals(kv.getName())) {
            									closeConvertion.fireEvent(new MouseEvent(MouseEvent.MOUSE_CLICKED, 0, 0, 0, 0, null, 1, false, false, false, false, false, false, false, false, false, false, false, false, null));
            								}
            								
                							try {
                								setActivity("remove", (((Text)convertion.getChildren().get(1)).getText()));
                								SearchConvertionsForHistory();
                							}catch(Exception e) {
                								e.printStackTrace();
                							}
                						}
                						moreOptionPane.setVisible(false);
	                				}
	                		});
	                    		moreOptionPane.setVisible(true);
	                    	}
	    			    }
	    			});
	    		}
	    	}
    	}
    }
    
    //*******************************//
  	//SearchConvertionsForHistory//
  	//*******************************//
    
    //*******************************//
  	//FindRecents viene chiamato quando sulla home bisogna cercare le ultime 3 traduzioni//
  	//*******************************//
    
    public void FindRecents() throws Exception{
    	//Controllo esistenza file all'interno delle cartelle
    	java.nio.file.Files.exists(Paths.get("src/main/resources/FXMLFiles"));
    	java.nio.file.Files.exists(Paths.get("src/main/resources/KVFiles"));
    	
    	for(Node node:recentConvertions.getChildren()) {
    		node.setVisible(false);
    	}
    	
    	if(getClass().getResource("/FXMLFiles") != null && getClass().getResource("/KVFiles") != null) {
	    	File[] listOfFXMLFiles = (new File("src/main/resources/FXMLFiles")).listFiles();
	    	File[] listOfKVFiles = (new File("src/main/resources/KVFiles")).listFiles();
	    	
	    	if(listOfFXMLFiles.length != 0 && listOfKVFiles.length != 0) {
	    		
	    		for(int i = 0; i < listOfFXMLFiles.length; i++){
	    			
	    			Text nomeFXML = new Text(listOfFXMLFiles[i].getName());
	    			nomeFXML.setWrappingWidth(120);
	    			nomeFXML.setStyle("-fx-fill: #717475;");
	    			Text nomeKV = new Text(listOfKVFiles[i].getName());
	    			nomeKV.setWrappingWidth(120);
	    			nomeKV.setStyle("-fx-fill: #717475;");
	    			
	    			BasicFileAttributes attr = java.nio.file.Files.readAttributes(listOfKVFiles[i].toPath(), BasicFileAttributes.class);
	    		    FileTime fileTime = attr.creationTime();
	    		    
	    		    Text dataKV = new Text(fileTime.toString().subSequence(0, 10).toString());
	    			dataKV.setWrappingWidth(110);
	    			dataKV.setStyle("-fx-fill: #717475;");
	    			Text oraKV = new Text(fileTime.toString().subSequence(11, 16).toString());
	    			oraKV.setWrappingWidth(40);
	    			oraKV.setStyle("-fx-fill: #717475;");
	    			
	    			if(i == listOfFXMLFiles.length - 1) {
	    				recentConvertions.getChildren().get(0).setVisible(true);
	    				((Text)((HBox)((Pane)recentConvertions.getChildren().get(0)).getChildren().get(0)).getChildren().get(1)).setText(nomeFXML.getText());
	    				((Text)((HBox)((Pane)recentConvertions.getChildren().get(0)).getChildren().get(0)).getChildren().get(2)).setText(nomeKV.getText());
	    				((Text)((HBox)((Pane)recentConvertions.getChildren().get(0)).getChildren().get(0)).getChildren().get(3)).setText(dataKV.getText());
	    				((Text)((HBox)((Pane)recentConvertions.getChildren().get(0)).getChildren().get(0)).getChildren().get(4)).setText(oraKV.getText());
	    				
	    				((HBox)((Pane)recentConvertions.getChildren().get(0)).getChildren().get(0)).setCursor(Cursor.HAND);
	    				
	    				((HBox)((Pane)recentConvertions.getChildren().get(0)).getChildren().get(0)).setOnMouseClicked(new EventHandler<MouseEvent>() {
	                        @Override
	                        public void handle(MouseEvent event) {
	                            try {
	                            	if(event.getButton() == MouseButton.PRIMARY)
	                            		ReadingFile(new File(getClass().getResource("/KVFiles/" + ((Text)((HBox)((Pane)recentConvertions.getChildren().get(0)).getChildren().get(0)).getChildren().get(2)).getText()).toURI()));
	                            	else if(event.getButton() == MouseButton.SECONDARY && moreOptionPane.isVisible()) {
	                            		moreOptionPane.setVisible(false);
	                            	}else if(event.getButton() == MouseButton.SECONDARY && !moreOptionPane.isVisible()) {
	                            		moreOptionPane.setStyle("-fx-background-color: white; -fx-background-radius: 0 10 10 10; -fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.1), 6, 0, 0.0, 3.0)");
	                            		moreOptionPane.setLayoutX(event.getSceneX() - 2);
	                            		moreOptionPane.setLayoutY(event.getSceneY() - 2);
	                            		moreOptionPane.setVisible(true);
	                            		
	                            		moreOptionPane.getChildren().get(0).setOnMouseClicked(new EventHandler<MouseEvent>() {
	                        				@Override
	                        				public void handle(MouseEvent event) {
	                        					try {
	                        						ReadingFile(new File(getClass().getResource("/FXMLFiles/" + ((Text)((HBox)((Pane)recentConvertions.getChildren().get(0)).getChildren().get(0)).getChildren().get(1)).getText()).toURI()));
	                        						moreOptionPane.setVisible(false);
												} catch (URISyntaxException e) {
													e.printStackTrace();
												}
	                        				}
	                        		});
	                            		
	                            		moreOptionPane.getChildren().get(1).setOnMouseClicked(new EventHandler<MouseEvent>() {
	                        				@Override
	                        				public void handle(MouseEvent event) {
                        						File kv = new File("src/main/resources/KVFiles/" + ((Text)((HBox)((Pane)recentConvertions.getChildren().get(0)).getChildren().get(0)).getChildren().get(2)).getText());
                        						File fxml = new File("src/main/resources/FXMLFiles/" + ((Text)((HBox)((Pane)recentConvertions.getChildren().get(0)).getChildren().get(0)).getChildren().get(1)).getText());
                        						                        						
                        						if(kv.delete() && fxml.delete()) {
                    								setActivity("remove", ((Text)((HBox)((Pane)recentConvertions.getChildren().get(0)).getChildren().get(0)).getChildren().get(2)).getText());
                    								if(nameFileKV.getText().equals(kv.getName())) {
                    									closeConvertion.fireEvent(new MouseEvent(MouseEvent.MOUSE_CLICKED, 0, 0, 0, 0, null, 1, false, false, false, false, false, false, false, false, false, false, false, false, null));
                    								}
                    								
                    								if(fileNameOutput.getText().equals(kv.getName()) || fileNameOutput.getText().equals(fxml.getName())) {
                    									fileNameOutput.setText("");
                    									textAreaCode.setText("");
                    								}
                        							try {
                        								FindRecents();
                        							}catch(Exception e) {
                        								e.printStackTrace();
                        							}
                        						}
                        						moreOptionPane.setVisible(false);
	                        				}
	                        		});
	                            	}
								} catch (URISyntaxException e) {
									e.printStackTrace();
								}
	                        }
	
	                    });
	    			}
	    			
	    			if(i == listOfFXMLFiles.length - 2) {
	    				recentConvertions.getChildren().get(1).setVisible(true);
	    				((HBox)((Pane)((HBox)recentConvertions.getChildren().get(1)).getChildren().get(0)).getChildren().get(0)).getChildren().clear();
	    				((HBox)((Pane)((HBox)recentConvertions.getChildren().get(1)).getChildren().get(0)).getChildren().get(0)).getChildren().add(nomeFXML);
	    				((HBox)((Pane)((HBox)recentConvertions.getChildren().get(1)).getChildren().get(0)).getChildren().get(0)).getChildren().add(nomeKV);
	    				((HBox)((Pane)((HBox)recentConvertions.getChildren().get(1)).getChildren().get(0)).getChildren().get(0)).getChildren().add(dataKV);
	    				((HBox)((Pane)((HBox)recentConvertions.getChildren().get(1)).getChildren().get(0)).getChildren().get(0)).getChildren().add(oraKV);
	    				
	    				((HBox)((Pane)((HBox)recentConvertions.getChildren().get(1)).getChildren().get(0)).getChildren().get(0)).setCursor(Cursor.HAND);
	    				
	    				((HBox)((Pane)((HBox)recentConvertions.getChildren().get(1)).getChildren().get(0)).getChildren().get(0)).setOnMouseClicked(new EventHandler<MouseEvent>() {
	                        @Override
	                        public void handle(MouseEvent event) {
	                            try {
	                            	if(event.getButton() == MouseButton.PRIMARY)
									ReadingFile(new File(getClass().getResource("/KVFiles/" + ((Text)((HBox)((Pane)((HBox)recentConvertions.getChildren().get(1)).getChildren().get(0)).getChildren().get(0)).getChildren().get(1)).getText()).toURI()));
									else if(event.getButton() == MouseButton.SECONDARY && moreOptionPane.isVisible()) {
	                            		moreOptionPane.setVisible(false);
	                            	}else if(event.getButton() == MouseButton.SECONDARY && !moreOptionPane.isVisible()) {
	                            		moreOptionPane.setStyle("-fx-background-color: white; -fx-background-radius: 0 10 10 10;");
	                            		moreOptionPane.setLayoutX(event.getSceneX() - 2);
	                            		moreOptionPane.setLayoutY(event.getSceneY() - 2);
	                            		
	                            		moreOptionPane.setVisible(true);
	                            		
	                            		moreOptionPane.getChildren().get(0).setOnMouseClicked(new EventHandler<MouseEvent>() {
	                        				@Override
	                        				public void handle(MouseEvent event) {
	                        					try {
													ReadingFile(new File(getClass().getResource("/FXMLFiles/" + ((Text)((HBox)((Pane)((HBox)recentConvertions.getChildren().get(1)).getChildren().get(0)).getChildren().get(0)).getChildren().get(0)).getText()).toURI()));
													moreOptionPane.setVisible(false);
	                        					} catch (URISyntaxException e) {
													e.printStackTrace();
												}
	                        				}
	                        		});
	                            		
	                            		moreOptionPane.getChildren().get(1).setOnMouseClicked(new EventHandler<MouseEvent>() {
	                        				@Override
	                        				public void handle(MouseEvent event) {
	                        						File kv = new File("src/main/resources/KVFiles/" + ((Text)((HBox)((Pane)((HBox)recentConvertions.getChildren().get(1)).getChildren().get(0)).getChildren().get(0)).getChildren().get(1)).getText());
	                        						File fxml = new File("src/main/resources/FXMLFiles/" + ((Text)((HBox)((Pane)((HBox)recentConvertions.getChildren().get(1)).getChildren().get(0)).getChildren().get(0)).getChildren().get(0)).getText());
	                        						
	                        						
	                        						if(kv.delete() && fxml.delete()) {
	                        							if(nameFileKV.getText().equals(kv.getName())) {
	                    									closeConvertion.fireEvent(new MouseEvent(MouseEvent.MOUSE_CLICKED, 0, 0, 0, 0, null, 1, false, false, false, false, false, false, false, false, false, false, false, false, null));
	                    								}
	                    								
	                    								if(fileNameOutput.getText().equals(kv.getName()) || fileNameOutput.getText().equals(fxml.getName())) {
	                    									fileNameOutput.setText("");
	                    									textAreaCode.setText("");
	                    								}
	                        							try {
	                        								setActivity("remove", (((Text)((HBox)((Pane)((HBox)recentConvertions.getChildren().get(1)).getChildren().get(0)).getChildren().get(0)).getChildren().get(1)).getText()));
	                        								FindRecents();
	                        								
	                        							}catch(Exception e) {
	                        								e.printStackTrace();
	                        							}
	                        						}
	                        						moreOptionPane.setVisible(false);
	                        				}
	                        		});
	                            	}
								} catch (URISyntaxException e) {
									e.printStackTrace();
								}
	                        }
	
	                    });
	    				
	    				((Pane)((HBox)recentConvertions.getChildren().get(1)).getChildren().get(1)).setCursor(Cursor.HAND);
	    				
	    				((Pane)((HBox)recentConvertions.getChildren().get(1)).getChildren().get(1)).setOnMouseClicked(new EventHandler<MouseEvent>() {
	                        @Override
	                        public void handle(MouseEvent event) {
	                            try {
	                            	File file = new File(getClass().getResource("/FXMLFiles/" + ((Text)((HBox)((Pane)((HBox)recentConvertions.getChildren().get(1)).getChildren().get(0)).getChildren().get(0)).getChildren().get(0)).getText()).toURI());
									startConvertion(file);
									FFKtranslatorController.uploaded = file;
								} catch (URISyntaxException e) {
									e.printStackTrace();
								}
	                        }
	
	                    });
	    			}
	    			
	    			if(i == listOfFXMLFiles.length - 3) {
	    				recentConvertions.getChildren().get(2).setVisible(true);
	    				((HBox)((Pane)((HBox)recentConvertions.getChildren().get(2)).getChildren().get(0)).getChildren().get(0)).getChildren().clear();
	    				((HBox)((Pane)((HBox)recentConvertions.getChildren().get(2)).getChildren().get(0)).getChildren().get(0)).getChildren().add(nomeFXML);
	    				((HBox)((Pane)((HBox)recentConvertions.getChildren().get(2)).getChildren().get(0)).getChildren().get(0)).getChildren().add(nomeKV);
	    				((HBox)((Pane)((HBox)recentConvertions.getChildren().get(2)).getChildren().get(0)).getChildren().get(0)).getChildren().add(dataKV);
	    				((HBox)((Pane)((HBox)recentConvertions.getChildren().get(2)).getChildren().get(0)).getChildren().get(0)).getChildren().add(oraKV);
	    				
	    				((HBox)((Pane)((HBox)recentConvertions.getChildren().get(2)).getChildren().get(0)).getChildren().get(0)).setCursor(Cursor.HAND);
	    				
	    				((HBox)((Pane)((HBox)recentConvertions.getChildren().get(2)).getChildren().get(0)).getChildren().get(0)).setOnMouseClicked(new EventHandler<MouseEvent>() {
	                        @Override
	                        public void handle(MouseEvent event) {
	                            try {
	                            	if(event.getButton() == MouseButton.PRIMARY)
									ReadingFile(new File(getClass().getResource("/KVFiles/" + ((Text)((HBox)((Pane)((HBox)recentConvertions.getChildren().get(2)).getChildren().get(0)).getChildren().get(0)).getChildren().get(1)).getText()).toURI()));
	                            	else if(event.getButton() == MouseButton.SECONDARY && moreOptionPane.isVisible()) {
	                            		moreOptionPane.setVisible(false);
	                            	}else if(event.getButton() == MouseButton.SECONDARY && !moreOptionPane.isVisible()) {
	                            		moreOptionPane.setLayoutX(event.getSceneX() - 2);
	                            		if(event.getSceneY() < 540) {
	                            			moreOptionPane.setStyle("-fx-background-color: white; -fx-background-radius: 0 10 10 10;");
	                            			moreOptionPane.setLayoutY(event.getSceneY() - 2);
	                            		}
	                            			
	                            		else {
	                            			moreOptionPane.setStyle("-fx-background-color: white; -fx-background-radius: 10 10 10 0;");
	                            			moreOptionPane.setLayoutY(event.getSceneY() - 55);
	                            		}
	                            			
	                            		moreOptionPane.getChildren().get(0).setOnMouseClicked(new EventHandler<MouseEvent>() {
	                            				@Override
	                            				public void handle(MouseEvent event) {
	                            					try {
														ReadingFile(new File(getClass().getResource("/FXMLFiles/" + ((Text)((HBox)((Pane)((HBox)recentConvertions.getChildren().get(2)).getChildren().get(0)).getChildren().get(0)).getChildren().get(0)).getText()).toURI()));
														moreOptionPane.setVisible(false);
	                            					} catch (URISyntaxException e) {
														e.printStackTrace();
													}
	                            				}
	                            		});
	                            		
	                            		moreOptionPane.getChildren().get(1).setOnMouseClicked(new EventHandler<MouseEvent>() {
	                        				@Override
	                        				public void handle(MouseEvent event) {
                        						File kv = new File("src/main/resources/KVFiles/" + ((Text)((HBox)((Pane)((HBox)recentConvertions.getChildren().get(1)).getChildren().get(0)).getChildren().get(0)).getChildren().get(1)).getText());
                        						File fxml = new File("src/main/resources/FXMLFiles/" + ((Text)((HBox)((Pane)((HBox)recentConvertions.getChildren().get(1)).getChildren().get(0)).getChildren().get(0)).getChildren().get(0)).getText());
                        						
                        						
                        						if(kv.delete() && fxml.delete()) {
                        							if(nameFileKV.getText().equals(kv.getName())) {
                    									closeConvertion.fireEvent(new MouseEvent(MouseEvent.MOUSE_CLICKED, 0, 0, 0, 0, null, 1, false, false, false, false, false, false, false, false, false, false, false, false, null));
                    								}
                    								
                    								if(fileNameOutput.getText().equals(kv.getName()) || fileNameOutput.getText().equals(fxml.getName())) {
                    									fileNameOutput.setText("");
                    									textAreaCode.setText("");
                    								}
                        							try {
                        								setActivity("remove", ((Text)((HBox)((Pane)((HBox)recentConvertions.getChildren().get(1)).getChildren().get(0)).getChildren().get(0)).getChildren().get(1)).getText());
                        								FindRecents();
                        							}catch(Exception e) {
                        								e.printStackTrace();
                        							}
                        						}
                        						moreOptionPane.setVisible(false);
	                        				}
	                        		});
	                            		moreOptionPane.setVisible(true);
	                            	}
								} catch (URISyntaxException e) {
									e.printStackTrace();
								}
	                        }
	                    });
	    				
	    				((Pane)((HBox)recentConvertions.getChildren().get(2)).getChildren().get(1)).setCursor(Cursor.HAND);
	    				
	    				((Pane)((HBox)recentConvertions.getChildren().get(2)).getChildren().get(1)).setOnMouseClicked(new EventHandler<MouseEvent>() {
	                        @Override
	                        public void handle(MouseEvent event) {
	                            try {
	                            	File file = new File(getClass().getResource("/FXMLFiles/" + ((Text)((HBox)((Pane)((HBox)recentConvertions.getChildren().get(2)).getChildren().get(0)).getChildren().get(0)).getChildren().get(0)).getText()).toURI());
									startConvertion(file);
	                            	FFKtranslatorController.uploaded = file;
	                            } catch (URISyntaxException e) {
									e.printStackTrace();
								}
	                        }
	
	                    });
	    			}
	    		}
	    	}else {
	    		recentConvertions.getChildren().get(3).setVisible(true);
	    	}
	    }
    }
    
    //*******************************//
  	//findRecents//
  	//*******************************//
    
    //*******************************//
  	//getTextFromGithub viene chiamato quando si accede alla schermata delle info del programma. Questo permetter� di estrarre il contenuto dal file READMI.txt//
    //presente su github e scaricare il changelog e la versione del programma disponibile su github//
  	//*******************************//
    
    public static String getTextFromGithub(String link) {
        URL Url = null;
        try {
            Url = new URL(link);
        } catch (MalformedURLException e1) {
            e1.printStackTrace();
        }
        HttpURLConnection Http = null;
        try {
            Http = (HttpURLConnection) Url.openConnection();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        Map<String, List<String>> Header = Http.getHeaderFields();
        
        for (String header : Header.get(null)) {
            if (header.contains(" 302 ") || header.contains(" 301 ")) {
                link = Header.get("Location").get(0);
                try {
                    Url = new URL(link);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
                try {
                    Http = (HttpURLConnection) Url.openConnection();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Header = Http.getHeaderFields();
            }
        }
        InputStream Stream = null;
        try {
            Stream = Http.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String Response = null;
        try {
            Response = GetStringFromStream(Stream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Response;
    }
    
    //*******************************//
  	//getTextFromGithub//
  	//*******************************//
    

    //*******************************//
  	//GetStringFromStream viene chiamato dal metodo getTextFromGithub questo permette di convertire lo stream in string//
  	//*******************************//
    
    private static String GetStringFromStream(InputStream Stream) throws IOException {
        if (Stream != null) {
            Writer Writer = new StringWriter();

            char[] Buffer = new char[2048];
            try {
                Reader Reader = new BufferedReader(new InputStreamReader(Stream, "UTF-8"));
                int counter;
                while ((counter = Reader.read(Buffer)) != -1) {
                    Writer.write(Buffer, 0, counter);
                }
            } finally {
                Stream.close();
            }
            return Writer.toString();
        } else {
            return "No Contents";
        }
    }
    
    //*******************************//
  	//GetStringFromStream//
  	//*******************************//
    
    
    //*******************************//
  	//chatGPT//
  	//*******************************//
    public String chatGPT(String prompt, int operation) {
    	try {
    		
    	String generatedText = "";
    		
    	//Significa che l'utente sta incollando la propria API Key
    	if(operation == 1) {
    		BufferedWriter filewApiKey = new BufferedWriter(new FileWriter(new File("Key Open AI.txt")));
    		filewApiKey.write(prompt);
    		
    		filewApiKey.close();
    		
    		generatedText = "La tua API Key è stata registrata, come posso aiutarti?";
    	}else {
    		//Lettura ApiKey
        	BufferedReader fileApiKey = new BufferedReader(new FileReader(new File("Key Open AI.txt")));
        	String apiKey = fileApiKey.readLine();
        	
        	
        	generatedText = "Per poter risponderti ho bisogno che tu inserisca la tua API Key, inviala pure qui in chat. (Il tuo prossimo messaggio verrà interpretato come Api Key, assicurati di averne una prima di usare Assistant. Se ancora non disponi della tua Api Key, torna quando ne avrai una a disposizione)";
        	
    	    	if(apiKey != null) {
    	    		String url = "https://api.openai.com/v1/chat/completions";
    	            String model = "gpt-3.5-turbo";
    	
    	            
    	                URL obj = new URL(url);
    	                HttpURLConnection connection = (HttpURLConnection) obj.openConnection();
    	                connection.setRequestMethod("POST");
    	                connection.setRequestProperty("Authorization", "Bearer " + apiKey);
    	                connection.setRequestProperty("Content-Type", "application/json");
    	                
    	                prompt = prompt.replaceAll("\"", "'");
    	                prompt = prompt.replaceAll("\n", " ");
    	                prompt = prompt.replaceAll("\t", " ");
    	                
    	                String body = "{\"model\": \"" + model + "\", \"messages\": [{\"role\": \"user\", \"content\": \"" + prompt + "\"}]}";
    	                connection.setDoOutput(true);
    	                OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());
    	                writer.write(body);
    	                writer.flush();
    	                writer.close();
    	
    	                // Response from ChatGPT
    	                BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
    	                String line;
    	
    	                StringBuffer response = new StringBuffer();
    	
    	                while ((line = br.readLine()) != null) {
    	                    response.append(line);
    	                }
    	                br.close();
    	                
    	                JsonObject jsonResponse = JsonParser.parseString(response.toString()).getAsJsonObject();
    	
    	                generatedText = jsonResponse.getAsJsonArray("choices")
    	                	    .get(0)
    	                	    .getAsJsonObject()
    	                	    .getAsJsonObject("message")
    	                	    .get("content")
    	                	    .getAsString();
    	
    	                
    	    	}
        	
        	fileApiKey.close();
    	}
    		
    	
    	return generatedText;
    	
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    
    //*******************************//
  	//chatGPT//
  	//*******************************//
    
}
