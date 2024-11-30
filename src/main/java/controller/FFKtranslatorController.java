package controller;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.google.common.io.Files;
import javafx.animation.FadeTransition;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.ScaleTransition;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import javafx.util.Duration;
import logic.FFKtranslatorLogic;

public class FFKtranslatorController {
	
	//*******************************//
	//Logic//
	//*******************************//
	
	private FFKtranslatorLogic appLogic = new FFKtranslatorLogic();
	
	//*******************************//
  	//ProgressBar Component//
  	//*******************************//
	
	@FXML
	private ProgressBar progressBar;
	
	//*******************************//
  	//CheckBox Component//
  	//*******************************//
	
	
	
	//*******************************//
	//Text Component//
	//*******************************//
	
    @FXML
    private Text dragText;
    
    @FXML
    private Text uploadButton;
        
    @FXML
    private Text link;
    
    @FXML
    private Text verGitHub;
    
    @FXML
    private Text verDownloaded;
    
    @FXML
    private Text verChangelog;
    
    @FXML
    private Text nameFileKV;
    
    @FXML
    private Text textOutput;
    
    @FXML
    private Text progressBarStatus;
    
    @FXML
    private Text fileNameOutput;
    
    @FXML
    private Text fileNameOutput1;
    
    @FXML
    private Text fileNameOutput11;
    
    @FXML
    private Text skipFirstPane;
    
    @FXML
    private Text homeText;
    
    @FXML
    private Text historyText;
    
    @FXML
    private Text downloadsText;
    
    @FXML
    private Text infoText;
    
    @FXML
    private Text assistantText;

    //*******************************//
  	//ImageView Component//
  	//*******************************//
    
    @FXML
	private ImageView filePython;
	
	@FXML
	private ImageView exportImage;
	
    @FXML
    private ImageView historyIcon;
    
    @FXML
    private ImageView infoIcon;

    @FXML
    private ImageView selectedIcon;

    @FXML
    private ImageView translationIcon;
    
    @FXML
    private ImageView maximizeIcon;
    
    @FXML
    private ImageView toggleStyle;
        
    @FXML
    private ImageView deleteOutput;
    
    @FXML
    private ImageView closeApplicationIcon;
    
    @FXML
    private ImageView minimizeApplicationIcon;
        
    @FXML
    private ImageView arrowChangelog;
    
    @FXML
    private ImageView repeatConvertion;

    @FXML
    private ImageView saveFile;

    @FXML
    private ImageView closeConvertion;
    
    @FXML
    private ImageView firstImage;
    
    @FXML
    private ImageView userIcon;
    
    @FXML
    private ImageView passwordIcon;
    
    @FXML
    private ImageView warningIcon;
    
    @FXML
    private ImageView chatIcon;
    
    @FXML
    private ImageView closeChatPane;
    
    @FXML
    private ImageView messaggeComing;
    
    //*******************************//
  	//Pane Component//
  	//*******************************//
    
    @FXML
    private Pane chatPane;
    
    @FXML
    private Pane paneForDragging;

    @FXML
    private Pane historyPane;

    @FXML
    private Pane translationPane;
    
    @FXML
    private Pane historyPaneOutput;
    
    @FXML
    private Pane translatorPaneOutput;
    
    @FXML
    private Pane infoPane;
    
    @FXML
    private Pane allertPane;
    
    @FXML
    private Pane recentConvertions;
    
    @FXML
    private Pane convertionPane;
    
    @FXML
    private Pane moreOptionPane;
    
    @FXML
    private Pane nameOutputPane;
    
    @FXML
    private Pane nameOutputPane1;
    
    @FXML
    private Pane nameOutputPane11;
    
    @FXML
    private Pane firstPane;
    
    @FXML
    private Pane accessPane;
    
    @FXML
    private Pane userPane;
    
    @FXML
    private Pane repeatPasswordPane;
    
    @FXML
    private Pane warningPane;
    
    @FXML
    private Pane tutorial;
    
    @FXML
    private Pane warningDarkPane;
    
    @FXML
    private Pane sendMessagge;
    
    @FXML
    private Pane menuPane;
    
    //*******************************//
  	//Label Component//
  	//*******************************//
    
    @FXML
    private Label onLeftPaneText;

    @FXML
    private Label onRightPaneText;
    
    @FXML
    private Label translatedFileNum;
    
    @FXML
    private Label isWritingText;
    
    //*******************************//
  	//TextArea Component//
  	//*******************************//
    
    @FXML
    private TextArea textAreaCode;
    
    @FXML
    private TextArea sorceOutput;
    
    @FXML
    private TextArea changelog;
    
    @FXML
    private TextArea convertionOutput;
    
    @FXML
    private TextArea warningTextArea;
    
    @FXML
    private TextArea activityTextArea;
    
    @FXML
    private TextArea messaggeToSend;
    
    
    //*******************************//
  	//StackPane Component//
  	//*******************************//
    
    @FXML
    private StackPane stackPaneOutput;
    
    @FXML
    private StackPane saveOptionPane;
    
    //*******************************//
  	//StackPane Component//
  	//*******************************//
    
    
    
    //*******************************//
  	//VBox Component//
  	//*******************************//
    
    @FXML
    private VBox listConvertions;
    
    @FXML
    private VBox messaggeSection;
    
    @FXML
    private VBox listOfKvs;
    
    //*******************************//
  	//HBox Component//
  	//*******************************//
    
    @FXML
    private HBox suggestions;
    
    //*******************************//
  	//Circle Component//
  	//*******************************//
    
    @FXML
    private Circle firstPage;
    
    @FXML
    private Circle secondPage;
    
    @FXML
    private Circle thirdPage;
    
    @FXML
    private Circle circlePages;
    
    //*******************************//
  	//Rectangle Component//
  	//*******************************//
    
    @FXML
    private Rectangle accessRectangle;
    
    //*******************************//
  	//Global variable//
  	//*******************************//
    
    private double mouseX;
	private double mouseY;
	private String linkGitHub = "https://raw.githubusercontent.com/0Thinker0/translatorFXMLToKivy/main/src/main/resources/Changelog.txt";
	private String changelogString;
	public static File uploaded;
	
	//*******************************//
	//Text Component//
	//*******************************//

	//*******************************//
	//inizialize viene chiamato quando si avvia il programma//
	//*******************************//
	
	@FXML
	private void initialize() {
		appLogic.setComponents(textAreaCode, sorceOutput, convertionOutput, listConvertions, 
				recentConvertions, progressBarStatus, progressBar, convertionPane, 
				textOutput, nameFileKV, closeConvertion, repeatConvertion, 
				saveFile, moreOptionPane, fileNameOutput, filePython, exportImage, warningIcon, warningTextArea, warningPane, translatedFileNum, suggestions,
				chatIcon, messaggeComing, chatPane, messaggeToSend, warningDarkPane);
		
		File f = null;
		try {
			f = new File(getClass().getResource("/tutorial.mp4").toURI());
		} catch (URISyntaxException e1) {
			e1.printStackTrace();
		}

	    Media media = new Media(f.toURI().toString());
	    
	    MediaPlayer mp = new MediaPlayer(media);
	    mp.setAutoPlay(true);
	    ((MediaView)tutorial.getChildren().get(2)).setMediaPlayer(mp);
	    	    
		try {
			appLogic.FindRecents();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//*******************************//
	//inizialize//
	//*******************************//
	
	//*******************************//
	//HistoryIconClicked viene richiamato non appena si clicca sul pulsante per visualizzare lo storico delle traduzioni//
	//*******************************//
	
    @FXML
    void HistoryIconClicked(MouseEvent event) {
    	historyPane.setVisible(true);
    	infoPane.setVisible(false);
    	translationPane.setVisible(false);
    	userPane.setVisible(false);
    	
    	historyIcon.setOpacity(1);
    	historyText.setOpacity(1);
    	infoIcon.setOpacity(0.7);
    	infoText.setOpacity(0.7);
    	userIcon.setOpacity(0.7);
    	downloadsText.setOpacity(0.7);
    	homeText.setOpacity(0.7);
    	translationIcon.setOpacity(0.7);
    	
    	if(selectedIcon.getLayoutY() != 113) {
	    	ScaleTransition scale = new ScaleTransition();
	    	scale.setFromX(0);
	    	scale.setToX(1);
	    	scale.setFromY(0);
	    	scale.setToY(1);
	    	scale.setDuration(new javafx.util.Duration(200));
	    	scale.setNode(selectedIcon);
	    	selectedIcon.setLayoutY(113);
	    	scale.playFromStart();
    	}
    	
    	closingOutputPanes();
    	historyPaneOutput.setVisible(true);
    	translatorPaneOutput.setVisible(false);
    	
    	onRightPaneText.setText("Sorgente");
    	onLeftPaneText.setText("Lista Conversioni");
    	
    	try {
			appLogic.SearchConvertionsForHistory();
		} catch (IOException | URISyntaxException e) {
			e.printStackTrace();
		}
    }
    
    //*******************************//
  	//HistoryIconClicked//
  	//*******************************//
    
    //*******************************//
  	//TranslationIconClicked viene richiamato non appena si clicca sul pulsante per visualizzare invece la home, la sezione in cui � possibile caricare il file da tradurre//
  	//*******************************//

    @FXML
    void TranslationIconClicked(MouseEvent event) {
    	historyPane.setVisible(false);
    	infoPane.setVisible(false);
    	translationPane.setVisible(true);
    	userPane.setVisible(false);
    	
    	historyIcon.setOpacity(0.7);
    	historyText.setOpacity(0.7);
    	infoIcon.setOpacity(0.7);
    	infoText.setOpacity(0.7);
    	userIcon.setOpacity(0.7);
    	downloadsText.setOpacity(0.7);
    	homeText.setOpacity(1);
    	translationIcon.setOpacity(1);
    	
    	if(selectedIcon.getLayoutY() != 57) {
	    	ScaleTransition scale = new ScaleTransition();
	    	scale.setFromX(0);
	    	scale.setToX(1);
	    	scale.setFromY(0);
	    	scale.setToY(1);
	    	scale.setDuration(new javafx.util.Duration(200));
	    	scale.setNode(selectedIcon);
	    	selectedIcon.setLayoutY(57);
	    	scale.playFromStart();
    	}
    	
    	closingOutputPanes();
    	
    	historyPaneOutput.setVisible(false);
    	translatorPaneOutput.setVisible(true);
    	
    	onRightPaneText.setText("Output");
    	onLeftPaneText.setText("Input");
    	
    	try {
    		appLogic.FindRecents();
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
    
    //*******************************//
  	//TranslationIconClicked//
  	//*******************************//
    
    //*******************************//
  	//chatIconClicked viene richiamato non appena si clicca sul pulsante per aprire la chat con chatgpt//
  	//*******************************//

    @FXML
    void chatIconClicked(MouseEvent event) {
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
    }
    
    //*******************************//
  	//chatIconClicked//
  	//*******************************//
    
    //*******************************//
  	//closeChatPaneClicked viene richiamato non appena si clicca sul pulsante per chiudere la chat con chatgpt//
  	//*******************************//

    @FXML
    void closeChatPaneClicked(MouseEvent event) {
    	chatIcon.setOpacity(0.7);
    	messaggeComing.setVisible(false);
    	
    	if(chatPane.getTranslateX() == 684) {
    	Timeline timeline = new Timeline(
				new KeyFrame(Duration.millis(0),
                		new KeyValue(chatPane.translateXProperty(), 684, Interpolator.EASE_BOTH)
                ),
                new KeyFrame(Duration.millis(400),
                        new KeyValue(chatPane.translateXProperty(), 0, Interpolator.EASE_BOTH)
                )
        	);
    	
    	timeline.play();
    	}
    }
    
    //*******************************//
  	//closeChatPaneClicked//
  	//*******************************//
    
    //*******************************//
  	//FileDropped viene richiamato non appena l'utente inserisce (Tramite drag and drop) un file supportato all'interno dell'apposito Pane.//
  	//*******************************//
    
    @FXML
    void FileDropped(DragEvent event) {
    	if(Files.getFileExtension(event.getDragboard().getFiles().toString()).contains("fxml") || Files.getFileExtension(event.getDragboard().getFiles().toString()).contains("txt")) {
    		paneForDragging.setStyle("-fx-background-color: #ffffff; -fx-background-radius: 10px;; -fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.1), 6, 0, 0.0, 3.0)");
    		convertionPane.setStyle("-fx-background-color: #ffffff; -fx-background-radius: 10px");
    		uploaded = event.getDragboard().getFiles().get(0);
    		appLogic.startConvertion(uploaded);
    	}
    }
    
    //*******************************//
  	//Filedropped//
  	//*******************************//
    
    //*******************************//
  	//FileDrag viene richiamato quando l'utente seleziona un file, questo permetter� al Pane di attivare la sua modalit� di accettazione dei file e l'utente potr� caricare il file//
  	//*******************************//
    
    @FXML
    void FileDrag(DragEvent event) {
    	if(event.getDragboard().hasFiles()) {
    		event.acceptTransferModes(TransferMode.ANY);
    		if(!Files.getFileExtension(event.getDragboard().getFiles().toString()).contains("fxml") && !Files.getFileExtension(event.getDragboard().getFiles().toString()).contains("txt")) {
    			paneForDragging.setStyle("-fx-background-color: #FECCCC; -fx-background-radius: 10px; -fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.1), 6, 0, 0.0, 3.0); -fx-border-style: dashed; -fx-border-radius: 10; -fx-border-color: #2B2B2B; -fx-border-width: 2;");
    			convertionPane.setStyle("-fx-background-color: #FECCCC; -fx-background-radius: 10px");
    			uploadButton.setVisible(false);
    			dragText.setText("File non supportato");
    		}else {
    			paneForDragging.setStyle("-fx-background-color: #f2f2f2; -fx-background-radius: 10px; -fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.1), 6, 0, 0.0, 3.0)");
    			convertionPane.setStyle("-fx-background-color: #f2f2f2; -fx-background-radius: 10px");
    			uploadButton.setVisible(false);
    			dragText.setText("Rilascia qui il file");
    		}
    		
    	}
    }
    
    //*******************************//
  	//FileDrag//
  	//*******************************//
    
    //*******************************//
  	//FileDragExited viene richiamato quando il cursore del mouse lascia la zona dedicata al Drag And Drop, reimpostando lo stile del Pane al suo stile di partenza//
  	//*******************************//
    
    @FXML
    void FileDragExited(DragEvent event) {
    	paneForDragging.setStyle("-fx-background-color: #ffffff; -fx-background-radius: 10px; -fx-border-style: dashed; -fx-border-radius: 10; -fx-border-color: #2B2B2B; -fx-border-width: 2; -fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.1), 6, 0, 0.0, 3.0)");
    	convertionPane.setStyle("-fx-background-color: #ffffff; -fx-background-radius: 10px");
    	uploadButton.setVisible(true);
    	dragText.setText("Trascina qui il file");
    }
    
    //*******************************//
  	//FileDragExited//
  	//*******************************//
    
    //*******************************//
  	//OpenFile viene chiamato quando si clicca sul pulsante apposito per caricare un file scegliendo manualmente la cartella d'origine//
  	//*******************************//
    
    @FXML
    void OpenFile(MouseEvent event) {
    	 FileChooser fileChooser = new FileChooser();
    	 fileChooser.setTitle("Seleziona il file da tradurre");
    	 fileChooser.getExtensionFilters().addAll(
    	         new ExtensionFilter("FXML Files", "*.fxml"),
    	         new ExtensionFilter("Txt Files", "*.txt"));
    	 File file = fileChooser.showOpenDialog(null);
    	 if(file != null){
    		uploaded = file;
			appLogic.startConvertion(uploaded);
		}
    }
    
    //*******************************//
  	//OpenFile//
  	//*******************************//
    
    //*******************************//
  	//MouseHistoryEntered/MouseHistoryExited vengono chiamati quando il mouse entra o esce nell'aria di collisione dell'icona history//
  	//*******************************//
    
    @FXML
    void MouseHistoryEntered(MouseEvent event) {
    	historyIcon.setOpacity(1);
    	historyText.setOpacity(1);
    }

    @FXML
    void MouseHistoryExited(MouseEvent event) {
    	if(selectedIcon.getLayoutY() != 113)
    		historyIcon.setOpacity(0.7);
    		historyText.setOpacity(0.7);
    }
    
    //*******************************//
  	//MouseHistoryEntered/MouseHistoryExited//
  	//*******************************//

    //*******************************//
  	//MouseHomeEntered/MouseHomeExited vengono chiamati quando il mouse entra o esce dall'aria di collisione dell'icona home//
  	//*******************************//
    
    @FXML
    void MouseHomeEntered(MouseEvent event) {
    	translationIcon.setOpacity(1);
    	homeText.setOpacity(1);
    }

    @FXML
    void MouseHomeExited(MouseEvent event) {
    	if(selectedIcon.getLayoutY() != 57)
    		translationIcon.setOpacity(0.7);
    	homeText.setOpacity(0.7);
    }
    
    //*******************************//
  	//MouseHomeEntered/MouseHomeExited//
  	//*******************************//

    //*******************************//
  	//expandOutput viene chiamato quando si preme sul pulsante per espandere la sezione dedicata all'output del file. Attraverso la Timeline, � possibile gestire l'animazione dei vari componenti//
  	//*******************************//
    
    @FXML
    void expandOutput(MouseEvent event) {
    	Timeline timeline = new Timeline();
    	if(maximizeIcon.getImage().getUrl().contains("maximize")) {
    		timeline = new Timeline(
                    new KeyFrame(Duration.millis(0),
                            new KeyValue(stackPaneOutput.prefWidthProperty(), 297, Interpolator.EASE_BOTH),
                            new KeyValue(stackPaneOutput.layoutXProperty(), 689, Interpolator.EASE_BOTH),
                            new KeyValue(textAreaCode.prefWidthProperty(), 297, Interpolator.EASE_BOTH),
                            new KeyValue(sorceOutput.prefWidthProperty(), 297, Interpolator.EASE_BOTH),
                            new KeyValue(convertionOutput.prefWidthProperty(), 297, Interpolator.EASE_BOTH),
                            new KeyValue(onRightPaneText.layoutXProperty(), 700, Interpolator.EASE_BOTH),
                            new KeyValue(onLeftPaneText.layoutYProperty(), 54, Interpolator.EASE_BOTH),
                            new KeyValue(nameOutputPane.prefWidthProperty(), 297, Interpolator.EASE_BOTH),
                            new KeyValue(nameOutputPane1.prefWidthProperty(), 297, Interpolator.EASE_BOTH),
                            new KeyValue(nameOutputPane11.prefWidthProperty(), 297, Interpolator.EASE_BOTH)
                    ),
                    new KeyFrame(Duration.millis(400),
                    		new KeyValue(stackPaneOutput.prefWidthProperty(), 927, Interpolator.EASE_BOTH),
                            new KeyValue(stackPaneOutput.layoutXProperty(), 60, Interpolator.EASE_BOTH),
                            new KeyValue(textAreaCode.prefWidthProperty(), 927, Interpolator.EASE_BOTH),
                            new KeyValue(sorceOutput.prefWidthProperty(), 927, Interpolator.EASE_BOTH),
                            new KeyValue(convertionOutput.prefWidthProperty(), 927, Interpolator.EASE_BOTH),
                            new KeyValue(onRightPaneText.layoutXProperty(), 76, Interpolator.EASE_BOTH),
                            new KeyValue(onLeftPaneText.layoutYProperty(), 74, Interpolator.EASE_BOTH),
                            new KeyValue(nameOutputPane.prefWidthProperty(), 927, Interpolator.EASE_BOTH),
                            new KeyValue(nameOutputPane1.prefWidthProperty(), 927, Interpolator.EASE_BOTH),
                            new KeyValue(nameOutputPane11.prefWidthProperty(), 927, Interpolator.EASE_BOTH)
                    )
            		);
    		maximizeIcon.setImage(new Image(getClass().getResource("/application/RisorseGrafiche/minimize.png").toString()));
    	}else if(maximizeIcon.getImage().getUrl().contains("minimize")){
    		timeline = new Timeline(
    				new KeyFrame(Duration.millis(0),
                    		new KeyValue(stackPaneOutput.prefWidthProperty(), 927, Interpolator.EASE_BOTH),
                            new KeyValue(stackPaneOutput.layoutXProperty(), 60, Interpolator.EASE_BOTH),
                            new KeyValue(textAreaCode.prefWidthProperty(), 927, Interpolator.EASE_BOTH),
                            new KeyValue(sorceOutput.prefWidthProperty(), 927, Interpolator.EASE_BOTH),
                            new KeyValue(convertionOutput.prefWidthProperty(), 927, Interpolator.EASE_BOTH),
                            new KeyValue(onRightPaneText.layoutXProperty(), 76, Interpolator.EASE_BOTH),
                            new KeyValue(onLeftPaneText.layoutYProperty(), 74, Interpolator.EASE_BOTH),
                            new KeyValue(nameOutputPane.prefWidthProperty(), 927, Interpolator.EASE_BOTH),
                            new KeyValue(nameOutputPane1.prefWidthProperty(), 927, Interpolator.EASE_BOTH),
                            new KeyValue(nameOutputPane11.prefWidthProperty(), 927, Interpolator.EASE_BOTH)
                    ),
                    new KeyFrame(Duration.millis(400),
                            new KeyValue(stackPaneOutput.prefWidthProperty(), 297, Interpolator.EASE_BOTH),
                            new KeyValue(stackPaneOutput.layoutXProperty(), 689, Interpolator.EASE_BOTH),
                            new KeyValue(textAreaCode.prefWidthProperty(), 297, Interpolator.EASE_BOTH),
                            new KeyValue(sorceOutput.prefWidthProperty(), 297, Interpolator.EASE_BOTH),
                            new KeyValue(convertionOutput.prefWidthProperty(), 297, Interpolator.EASE_BOTH),
                            new KeyValue(onRightPaneText.layoutXProperty(), 700, Interpolator.EASE_BOTH),
                            new KeyValue(onLeftPaneText.layoutYProperty(), 54, Interpolator.EASE_BOTH),
                            new KeyValue(nameOutputPane.prefWidthProperty(), 297, Interpolator.EASE_BOTH),
                            new KeyValue(nameOutputPane1.prefWidthProperty(), 297, Interpolator.EASE_BOTH),
                            new KeyValue(nameOutputPane11.prefWidthProperty(), 297, Interpolator.EASE_BOTH)
                    )
            		);
    		maximizeIcon.setImage(new Image(getClass().getResource("/application/RisorseGrafiche/maximize.png").toString()));
    	}
        
        timeline.play();
    }
    
    //*******************************//
  	//expandOutput//
  	//*******************************//
    
    //*******************************//
  	//changeStyle viene chiamato quando si passa dalla darkmode alla lightmode della sezione output//
  	//*******************************//
    
    @FXML
    void changeStyle(MouseEvent event) {
    	textAreaCode.getStylesheets().clear();
    	sorceOutput.getStylesheets().clear();
    	convertionOutput.getStylesheets().clear();
    	if(toggleStyle.getImage().getUrl().contains("left")) {
    		textAreaCode.getStylesheets().add(getClass().getResource("/application/CSS/textAreaOutput.css").toString());
    		sorceOutput.getStylesheets().add(getClass().getResource("/application/CSS/textAreaOutput.css").toString());
    		convertionOutput.getStylesheets().add(getClass().getResource("/application/CSS/textAreaOutput.css").toString());
    		toggleStyle.setImage(new Image(getClass().getResource("/application/RisorseGrafiche/toggle-right.png").toString()));
    	}else {
    		textAreaCode.getStylesheets().add(getClass().getResource("/application/CSS/textAreaOutput2.css").toString());
    		sorceOutput.getStylesheets().add(getClass().getResource("/application/CSS/textAreaOutput2.css").toString());
    		convertionOutput.getStylesheets().add(getClass().getResource("/application/CSS/textAreaOutput2.css").toString());
    		toggleStyle.setImage(new Image(getClass().getResource("/application/RisorseGrafiche/toggle-left.png").toString()));
    	}
    }
    
    //*******************************//
  	//changeStyle//
  	//*******************************//
    
    //*******************************//
  	//deleteOutput viene chiamato quando si preme sull'icona per pulire la textarea che si occupa di mostrare il file//
  	//*******************************//
    
    @FXML
    void deleteOutput(MouseEvent event) {
    	textAreaCode.setText("");
    	fileNameOutput.setText("");
    	fileNameOutput1.setText("");
    	fileNameOutput11.setText("");
    	sorceOutput.setText("");
    	convertionOutput.setText("");
    }
    
    //*******************************//
  	//deleteOutput//
  	//*******************************//
    
    //*******************************//
  	//closeApplication viene chiamato quando attraverso la barra personalizzata si decide di chiudere il programma//
  	//*******************************//
    
    @FXML
    void closeApplication(MouseEvent event) {
    	Stage stage = (Stage) closeApplicationIcon.getScene().getWindow();
        stage.close();
    }
    
    //*******************************//
  	//closeApplication//
  	//*******************************//
    
    //*******************************//
  	//redIcon/blackIcon vengono chiamati quando il mouse effettua un'azione di hover sul pulsante x per chiudere il programma//
  	//*******************************//
    
    @FXML
    void redIcon(MouseEvent event) {
    	closeApplicationIcon.setImage(new Image(getClass().getResource("/application/RisorseGrafiche/xCloseRed.png").toString()));
    }
    
    @FXML
    void blackIcon(MouseEvent event) {
    	closeApplicationIcon.setImage(new Image(getClass().getResource("/application/RisorseGrafiche/xClose.png").toString()));
    }
    
    //*******************************//
  	//redIcon/blackIcon//
  	//*******************************//
    
    //*******************************//
  	//moveStage viene chiamato quando il mouse trascina il frame dalla barra personalizzata per spostarlo a proprio piacimento sullo schermo//
  	//*******************************//
    
    @FXML
    void moveStage(MouseEvent event) {
    	Stage stage = (Stage) closeApplicationIcon.getScene().getWindow();
    	
    	stage.setX(event.getScreenX() - mouseX);
    	stage.setY(event.getScreenY() - mouseY);
    }
    
    //*******************************//
  	//moveStage//
  	//*******************************//
    
    //*******************************//
  	//mousePosition viene chiamato non appena l'utente effettua un click sulla barra personalizzata, questo permetter� di salvare le coordinate del mouse utili per il movimento dello Stage//
  	//*******************************//
    
    @FXML
    void mousePosition(MouseEvent event) {
    	Stage stage = (Stage) closeApplicationIcon.getScene().getWindow();
    	
    	mouseX = event.getScreenX() - stage.getX();
    	mouseY = event.getScreenY() - stage.getY();
    }
    
    //*******************************//
  	//mousePosition//
  	//*******************************//
    
    //*******************************//
  	//minimizeApplication viene chiamato quando si clicca sul pulsante per ridurre ad icona il programma//
  	//*******************************//
    
    @FXML
    void minimizeApplication(MouseEvent event) {
    	Stage stage = (Stage) closeApplicationIcon.getScene().getWindow();
    	stage.setIconified(true);
    }
    
    //*******************************//
  	//minimizeApplication//
  	//*******************************//
    
    //*******************************//
  	//InfoClicked viene chiamato ogni volta che viene cliccata l'icona delle info//
  	//*******************************//
    
    @FXML
    void InfoClicked(MouseEvent event) {
    	infoPane.setVisible(true);
    	userPane.setVisible(false);
    	
    	if(selectedIcon.getLayoutY() != 490) {
	    	ScaleTransition scale = new ScaleTransition();
	    	scale.setFromX(0);
	    	scale.setToX(1);
	    	scale.setFromY(0);
	    	scale.setToY(1);
	    	scale.setDuration(new javafx.util.Duration(200));
	    	scale.setNode(selectedIcon);
	    	selectedIcon.setLayoutY(490);
	    	scale.playFromStart();
    	}
    	
    	closingOutputPanes();
    	translationIcon.setOpacity(0.7);
    	homeText.setOpacity(0.7);
    	historyIcon.setOpacity(0.7);
    	historyText.setOpacity(0.7);
    	userIcon.setOpacity(0.7);
    	downloadsText.setOpacity(0.7);
    	infoIcon.setOpacity(1);
    	infoText.setOpacity(1);
    	changelog.setText("");
    	
    	try {
			BufferedReader br = new BufferedReader(new FileReader(new File(getClass().getResource("/Changelog.txt").toURI())));
			String line;
			boolean ver = true;
			while((line = br.readLine()) != null) {
				if(line.contains("ver") && ver) {
					verDownloaded.setText(line.substring(line.indexOf("ver ") + 4, line.length()));
					verChangelog.setText(line.substring(line.indexOf("ver ") + 4, line.length()));
					ver = false;
				}
				changelog.appendText(line);
				changelog.appendText("\n");
			}
			br.close();
		} catch (IOException | URISyntaxException e) {
			e.printStackTrace();
		}
    	
    	arrowChangelog.setRotate(180);
    	
    	//Separare la ricerca del file su github dalle operazioni del thread principale
    	Thread t = new Thread (new Runnable() {

			@SuppressWarnings("static-access")
			@Override
			public void run() {
				changelogString = appLogic.getTextFromGithub(linkGitHub);
		    	verGitHub.setText(changelogString.substring(0, changelogString.indexOf("\n")).substring(changelogString.indexOf("ver") + 4, changelogString.indexOf("ver") + 7));
			}
    	});
    	t.start();
    	
    }
    
    //*******************************//
  	//InfoClicked//
  	//*******************************//

    //*******************************//
  	//MouseInfoEntered/MouseInfoExited vengono chiamati quando il mouse entra nell'area di collisione dell'icona info//
  	//*******************************//
    
    @FXML
    void MouseInfoEntered(MouseEvent event) {
    	infoIcon.setOpacity(1);
    	infoText.setOpacity(1);
    }

    @FXML
    void MouseInfoExited(MouseEvent event) {
    	if(selectedIcon.getLayoutY() != 490)
    		infoIcon.setOpacity(0.7);
    		infoText.setOpacity(0.7);
    }
    
    //*******************************//
  	//MouseInfoEntered/MouseInfoExited//
  	//*******************************//
    
    //*******************************//
  	//CopyLink viene chiamato quando si preme sul pulsante per copiare il link di github, facendo uscire un'animazione//
  	//*******************************//
    
    @FXML
    void CopyLink(MouseEvent event) {
    	StringSelection stringSelection = new StringSelection(link.getText());
    	Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
    	clipboard.setContents(stringSelection, null);
    	
    	AllertPane(288.0, 396.0, "Link copiato negli appunti");
    }
    
    //*******************************//
  	//CopyLink//
  	//*******************************//
    
    //*******************************//
  	//OpenLink viene chiamato quando si clicca sul pulsante per recarsi alla pagina di github//
  	//*******************************//
    
    @FXML
    void OpenLink(MouseEvent event) {
    	try {
			java.awt.Desktop.getDesktop().browse(URI.create(link.getText()));
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
    //*******************************//
  	//OpenLink//
  	//*******************************//

    //*******************************//
  	//ChangeLog viene chiamato quando si clicca sul pulsante per cambiare la versione del changelog//
  	//*******************************//
    
    @FXML
    void ChangeLog(MouseEvent event) {
    	changelog.setText("");
    	if(arrowChangelog.getRotate() == 0) {
    		try {
    			BufferedReader br = new BufferedReader(new FileReader(new File(getClass().getResource("/Changelog.txt").toURI())));
    			String line;
    			boolean ver = true;
    			while((line = br.readLine()) != null) {
    				if(line.contains("ver") && ver) {
    					verDownloaded.setText(line.substring(line.indexOf("ver ") + 4, line.length()));
    					verChangelog.setText(line.substring(line.indexOf("ver ") + 4, line.length()));
    					ver = false;
    				}
    				changelog.appendText(line);
    				changelog.appendText("\n");
    			}
    			br.close();
    		} catch (IOException | URISyntaxException e) {
    			e.printStackTrace();
    		}
    		
    		arrowChangelog.setRotate(180);
    	}else {
        	changelog.appendText(changelogString);
        	verChangelog.setText(changelogString.substring(0, changelogString.indexOf("\n")).substring(changelogString.indexOf("ver") + 4, changelogString.indexOf("ver") + 7));
        	arrowChangelog.setRotate(0);
    	}
    }
    
    //*******************************//
  	//ChangeLog//
  	//*******************************//
    
    //*******************************//
  	//CloseConvertion viene chiamato quando si preme il pulsante X per la chiusura di una conversione//
  	//*******************************//
    
    @FXML
    void CloseConvertion(MouseEvent event) {
    	warningIcon.setVisible(false);
    	warningPane.setVisible(false);
    	
    	Thread t = new Thread(new Runnable() {
			
			@Override
			public void run() {
				try {

				ScaleTransition scale = new ScaleTransition();
				
				scale.setDuration(Duration.millis(200));
				scale.setFromX(1);
				scale.setFromY(1);
				scale.setToX(0);
				scale.setToY(0);
				scale.setNode(progressBar);
				scale.play();
				
				textOutput.setVisible(false);
				nameFileKV.setText("");
				progressBarStatus.setText("");
				
				Thread.sleep(200);
				
				scale.setNode(saveFile);
				scale.playFromStart();
				Thread.sleep(200);
				scale.setNode(closeConvertion);
				scale.playFromStart();
				Thread.sleep(200);
				scale.setNode(repeatConvertion);
				scale.playFromStart();
				
				Thread.sleep(100);
				convertionPane.setVisible(false);
				deleteOutput(event);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		});
    	
    	t.start();
    	
    }
    
    //*******************************//
  	//CloseConvertion//
  	//*******************************//
    
    //*******************************//
  	//RepeatConvertion viene chiamato quando si preme il pulsante per ripetere l'ultima conversione//
  	//*******************************//
    
    @FXML
    void RepeatConvertion(MouseEvent event) {
    	appLogic.startConvertion(uploaded);
    }
    
    //*******************************//
  	//RepeatConvertion//
  	//*******************************//
    
    
    //*******************************//
  	//menuOnMouse vengono chiamati quando il mouse entra/esce dal pannello del menu//
  	//*******************************//
    
    @FXML
    void menuOnMouseEntered(MouseEvent event) {
    	Timeline timeline = new Timeline(new KeyFrame(Duration.millis(0),
        		new KeyValue(menuPane.prefWidthProperty(), menuPane.prefWidthProperty().get(), Interpolator.EASE_BOTH),
        		new KeyValue(homeText.layoutXProperty(), homeText.layoutXProperty().get(), Interpolator.EASE_BOTH),
        		new KeyValue(historyText.layoutXProperty(), historyText.layoutXProperty().get(), Interpolator.EASE_BOTH),
        		new KeyValue(downloadsText.layoutXProperty(), downloadsText.layoutXProperty().get(), Interpolator.EASE_BOTH),
        		new KeyValue(infoText.layoutXProperty(), infoText.layoutXProperty().get(), Interpolator.EASE_BOTH),
        		new KeyValue(assistantText.layoutXProperty(), assistantText.layoutXProperty().get(), Interpolator.EASE_BOTH)
    			),
        new KeyFrame(Duration.millis(400),
                new KeyValue(menuPane.prefWidthProperty(), 200, Interpolator.EASE_BOTH),
                new KeyValue(homeText.layoutXProperty(), 56, Interpolator.EASE_BOTH),
        		new KeyValue(historyText.layoutXProperty(), 56, Interpolator.EASE_BOTH),
        		new KeyValue(downloadsText.layoutXProperty(), 56, Interpolator.EASE_BOTH),
        		new KeyValue(infoText.layoutXProperty(), 56, Interpolator.EASE_BOTH),
        		new KeyValue(assistantText.layoutXProperty(), 56, Interpolator.EASE_BOTH)
                )
		);
    	timeline.play();
    }
    
    @FXML
    void menuOnMouseExited(MouseEvent event) {
    	Timeline timeline = new Timeline(new KeyFrame(Duration.millis(0),
        		new KeyValue(menuPane.prefWidthProperty(), menuPane.prefWidthProperty().get(), Interpolator.EASE_BOTH),
        		new KeyValue(homeText.layoutXProperty(), homeText.layoutXProperty().get(), Interpolator.EASE_BOTH),
        		new KeyValue(historyText.layoutXProperty(), historyText.layoutXProperty().get(), Interpolator.EASE_BOTH),
        		new KeyValue(downloadsText.layoutXProperty(), downloadsText.layoutXProperty().get(), Interpolator.EASE_BOTH),
        		new KeyValue(infoText.layoutXProperty(), infoText.layoutXProperty().get(), Interpolator.EASE_BOTH),
        		new KeyValue(assistantText.layoutXProperty(), assistantText.layoutXProperty().get(), Interpolator.EASE_BOTH)
    			),
        new KeyFrame(Duration.millis(400),
                new KeyValue(menuPane.prefWidthProperty(), 52, Interpolator.EASE_BOTH),
                new KeyValue(homeText.layoutXProperty(), -52, Interpolator.EASE_BOTH),
        		new KeyValue(historyText.layoutXProperty(), -52, Interpolator.EASE_BOTH),
        		new KeyValue(downloadsText.layoutXProperty(), -52, Interpolator.EASE_BOTH),
        		new KeyValue(infoText.layoutXProperty(), -52, Interpolator.EASE_BOTH),
        		new KeyValue(assistantText.layoutXProperty(), -52, Interpolator.EASE_BOTH)
                )
		);
    	timeline.play();
    }
    
    //*******************************//
  	//menuOnMouse//
  	//*******************************//
    
    //*******************************//
  	//checkBoxPython/checkBoxImage vengono chiamato quando si clicca sulle icone delle checkbox durante la fase di salvataggio//
  	//*******************************//
    
    @FXML
    void checkBoxPython(MouseEvent event) {
    	if(filePython.getImage().getUrl().contains("checked")) {
    		filePython.setImage(new Image(getClass().getResource("/application/RisorseGrafiche/uncheck.png").toString()));
    	}else {
    		filePython.setImage(new Image(getClass().getResource("/application/RisorseGrafiche/checked.png").toString()));
    	}
    }
    
    @FXML
    void checkBoxImage(MouseEvent event) {
    	if(exportImage.getImage().getUrl().contains("checked")) {
    		exportImage.setImage(new Image(getClass().getResource("/application/RisorseGrafiche/uncheck.png").toString()));
    	}else {
    		exportImage.setImage(new Image(getClass().getResource("/application/RisorseGrafiche/checked.png").toString()));
    	}
    }
    
    //*******************************//
  	//checkBoxPython/checkBoxImage//
  	//*******************************//
  
    
    //*******************************//
  	//warningPaneVisible/warningPaneInvisible vengono chiamati quando il mouse passa o perde il focus dall'iconcina del warning//
  	//*******************************//
    
    @FXML
    void warningPaneVisible(MouseEvent event) {
    	if(warningIcon.isVisible()) {
    		warningPane.setVisible(true);
    		warningDarkPane.setVisible(true);
    	}
    }
    
    @FXML
    void warningPaneInvisible(MouseEvent event) {
    	if(warningIcon.isVisible()) {
    		warningPane.setVisible(false);
    		warningDarkPane.setVisible(false);
    	}
    }
    
    //*******************************//
  	//warningPaneVisible/warningPaneInvisible//
  	//*******************************//
    
    //*******************************//
  	//SaveFile viene chiamato quando si vuole salvare il file tradotto in una cartella specifica//
  	//*******************************//

    @FXML
    void SaveFile(MouseEvent event) {
    	saveOptionPane.setVisible(true);
    	
    }
    
    //*******************************//
  	//SaveFile//
  	//*******************************//
    
    //*******************************//
  	//SaveFileAndAllAssets viene chiamato quando si vuole salvare il file ed esportare gli eventuali assets//
  	//*******************************//
    
    @FXML
    void SaveFileAndAllAssets(MouseEvent event) {
    	if(appLogic.saveFile()) {
    		saveOptionPane.setVisible(false);
    		AllertPane(248.0, 320.0, "File salvato correttamente");
    	}
    }
    
    //*******************************//
  	//SaveFileAndAllAssets//
  	//*******************************//
    
    //*******************************//
  	//closeSaveOptionPane viene chiamato quando si vuole chiudere il pannello delle specifiche per salvare il file//
  	//*******************************//
    
    @FXML
    void closeSaveOptionPane(MouseEvent event) {
    		saveOptionPane.setVisible(false);
    		
    }
    
    //*******************************//
  	//closeSaveOptionPane//
  	//*******************************//
    
    //*******************************//
  	//hideMoreOptionPane viene chiamato quando il mouse lascia il pannello//
  	//*******************************//
    
    @FXML
    void hideMoreOptionPane(MouseEvent event) {
    	moreOptionPane.setVisible(false);
    }
    
    //*******************************//
  	//hideMoreOptionPane//
  	//*******************************//
    
    @FXML
    void showFXMLHoverEntered(MouseEvent event) {
    	if(!historyPane.isVisible())
    		((Text)moreOptionPane.getChildren().get(0)).setOpacity(1);
    	
    	if(historyPane.isVisible())
    		((Text)moreOptionPane.getChildren().get(0)).setCursor(javafx.scene.Cursor.DEFAULT);
    	
    	if(!historyPane.isVisible())
    		((Text)moreOptionPane.getChildren().get(0)).setCursor(javafx.scene.Cursor.HAND);
    }

    @FXML
    void showFXMLHoverExited(MouseEvent event) {
    	if(!historyPane.isVisible())
    		((Text)moreOptionPane.getChildren().get(0)).setOpacity(0.7);
    }
    
    @FXML
    void deleteHoverEntered(MouseEvent event) {
    	((Text)moreOptionPane.getChildren().get(1)).setOpacity(1);
    }

    @FXML
    void deleteHoverExited(MouseEvent event) {
    	((Text)moreOptionPane.getChildren().get(1)).setOpacity(0.7);
    }
    
    @FXML
    void MouseUserEntered(MouseEvent event) {
    	userIcon.setOpacity(1);
    	downloadsText.setOpacity(1);
    }

    @FXML
    void MouseUserExited(MouseEvent event) {
    	if(selectedIcon.getLayoutY() != 169)
    		userIcon.setOpacity(0.7);
    		downloadsText.setOpacity(0.7);
    }
    
    @FXML
    void UserClicked(MouseEvent event) {
    	userPane.setVisible(true);
    	historyPane.setVisible(false);
    	infoPane.setVisible(false);
    	translationPane.setVisible(false);
    	
    	historyIcon.setOpacity(0.7);
    	historyText.setOpacity(0.7);
    	infoIcon.setOpacity(0.7);
    	infoText.setOpacity(0.7);
    	userIcon.setOpacity(1);
    	downloadsText.setOpacity(1);
    	homeText.setOpacity(0.7);
    	translationIcon.setOpacity(0.7);
    	
    	if(selectedIcon.getLayoutY() != 169) {
	    	ScaleTransition scale = new ScaleTransition();
	    	scale.setFromX(0);
	    	scale.setToX(1);
	    	scale.setFromY(0);
	    	scale.setToY(1);
	    	scale.setDuration(new javafx.util.Duration(200));
	    	scale.setNode(selectedIcon);
	    	selectedIcon.setLayoutY(169);
	    	scale.playFromStart();
    	}
    	
    	activityTextArea.setText("");
    	
    	try {
			BufferedReader br = new BufferedReader(new FileReader(new File("Activity.txt")));
			String line;
			while((line = br.readLine()) != null) {
				activityTextArea.appendText(line);
				activityTextArea.appendText("\n");
			}
			br.close();
			listConvertions.getChildren().clear();
			listOfKvs.getChildren().clear();
			if(new File("src/main/resources/KVFiles").exists()) {				
		    	File[] listOfKVFiles = (new File("src/main/resources/KVFiles")).listFiles();
		    			    	
		    	if(listOfKVFiles.length != 0) {
		    		
		    		for(int i = 0; i < listOfKVFiles.length; i++){
		    			Text nomeKV = new Text(listOfKVFiles[i].getName());
		    			nomeKV.setStyle("-fx-fill: #717475;");
		    			Image image = new Image(getClass().getResourceAsStream("/application/RisorseGrafiche/uncheck.png"));
		    			
		    			HBox imageBox = new HBox();
		    			imageBox.setMaxHeight(25);
		    			imageBox.setMaxWidth(25);
		    			ImageView imageCheck = new ImageView(image);
		    			imageCheck.setPreserveRatio(true);
		    			imageCheck.setSmooth(true);
		    			imageCheck.setFitHeight(25);
		    			imageCheck.setFitWidth(25);
		    			imageBox.setCursor(Cursor.HAND);
		    			imageBox.getChildren().add(imageCheck);
		    			
		    			imageBox.setOnMouseClicked(new EventHandler<MouseEvent>() {

							@Override
							public void handle(MouseEvent event) {
								if(imageCheck.isPreserveRatio()) {
									imageCheck.setImage(new Image(getClass().getResourceAsStream("/application/RisorseGrafiche/checked.png")));
									imageCheck.setPreserveRatio(false);
								}else {
									imageCheck.setImage(new Image(getClass().getResourceAsStream("/application/RisorseGrafiche/uncheck.png")));
									imageCheck.setPreserveRatio(true);
								}
							}
		    				
		    			});
		    			
		    			HBox file = new HBox();
		    			file.setAlignment(Pos.CENTER_LEFT);
		    			file.setSpacing(10);
		    			
		    			file.getChildren().add(imageBox);
		    			file.getChildren().add(nomeKV);
		    			
		    			listOfKvs.getChildren().add(file);
		    		}
		    	}
		    }
		} catch (IOException e) {
			e.printStackTrace();
		}
    	if(activityTextArea.getText().equals(""))
    		activityTextArea.setText("Nessuna operazione effettuata");
    	
    	appLogic.countNumFile();
    	
    	closingOutputPanes();
    }
    
    
    @FXML
    private void exportFiles() {
    	if(!listOfKvs.getChildren().isEmpty()) {
	    	DirectoryChooser chooser = new DirectoryChooser();
	    	chooser.setTitle("Seleziona una cartella");
	    	
	    	File selectedDirectory = chooser.showDialog(null);
	    	
	    	if(selectedDirectory != null) {
	    		
	    		for(Node box : listOfKvs.getChildren()) {
	    			if(!((ImageView)((HBox)((HBox) box).getChildren().get(0)).getChildren().get(0)).isPreserveRatio()) {
	    				File newFile = new File(selectedDirectory + "/" + ((Text)((HBox) box).getChildren().get(1)).getText());
		    			
		    			try {
							Files.copy(new File("src/main/resources/KVFiles/" + ((Text)((HBox) box).getChildren().get(1)).getText()), newFile);
						} catch (IOException e) {
							e.printStackTrace();
						}
		    			
		    			((ImageView)((HBox)((HBox) box).getChildren().get(0)).getChildren().get(0)).setImage(new Image(getClass().getResourceAsStream("/application/RisorseGrafiche/uncheck.png")));
		    			((ImageView)((HBox)((HBox) box).getChildren().get(0)).getChildren().get(0)).setPreserveRatio(true);
	    			}
	    		}
	    		
	    		AllertPane(550.0, 555.0, "Esportazione avvenuta con successo");
	    	}
    	}
	   	
    }
    
    //*******************************//
  	//AllertPane viene chiamato quando c'� la necessit� di mostrare il pannello per gli avvisi//
  	//*******************************//
    
    void AllertPane(Double x, Double y, String testo) {
    	allertPane.setLayoutX(x);
		allertPane.setLayoutY(y);
    	Thread t = new Thread(new Runnable() {

			@Override
			public void run() {
				
				allertPane.setOpacity(0);
				((Text)allertPane.getChildren().get(0)).setText(testo);
				FadeTransition ft = new FadeTransition(Duration.millis(300));
				ft.setFromValue(0);
				ft.setToValue(1);
				ft.setNode(allertPane);
				ft.playFromStart();
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				ft.setDuration(Duration.millis(300));
				ft.setFromValue(1);
				ft.setToValue(0);
				ft.playFromStart();
			}
    		
    	});
    	
    	t.start();
    }
    
    //*******************************//
  	//AllertPane//
  	//*******************************//

    //*******************************//
  	//closingOutputPanes viene chiamato in diverse occasioni quando bisogna gestire l'animazione di chiusura del pannello output//
  	//*******************************//
    
    private void closingOutputPanes() {
    	if(maximizeIcon.getImage().getUrl().contains("minimize")) {
    		Timeline timeline = new Timeline(
    				new KeyFrame(Duration.millis(0),
                    		new KeyValue(stackPaneOutput.prefWidthProperty(), 927, Interpolator.EASE_BOTH),
                            new KeyValue(stackPaneOutput.layoutXProperty(), 60, Interpolator.EASE_BOTH),
                            new KeyValue(textAreaCode.prefWidthProperty(), 927, Interpolator.EASE_BOTH),
                            new KeyValue(sorceOutput.prefWidthProperty(), 927, Interpolator.EASE_BOTH),
                            new KeyValue(convertionOutput.prefWidthProperty(), 927, Interpolator.EASE_BOTH),
                            new KeyValue(onRightPaneText.layoutXProperty(), 76, Interpolator.EASE_BOTH),
                            new KeyValue(onLeftPaneText.layoutYProperty(), 74, Interpolator.EASE_BOTH),
                            new KeyValue(nameOutputPane.prefWidthProperty(), 927, Interpolator.EASE_BOTH),
                            new KeyValue(nameOutputPane1.prefWidthProperty(), 927, Interpolator.EASE_BOTH),
                            new KeyValue(nameOutputPane11.prefWidthProperty(), 927, Interpolator.EASE_BOTH)
                    ),
                    new KeyFrame(Duration.millis(400),
                            new KeyValue(stackPaneOutput.prefWidthProperty(), 297, Interpolator.EASE_BOTH),
                            new KeyValue(stackPaneOutput.layoutXProperty(), 689, Interpolator.EASE_BOTH),
                            new KeyValue(textAreaCode.prefWidthProperty(), 297, Interpolator.EASE_BOTH),
                            new KeyValue(sorceOutput.prefWidthProperty(), 297, Interpolator.EASE_BOTH),
                            new KeyValue(convertionOutput.prefWidthProperty(), 297, Interpolator.EASE_BOTH),
                            new KeyValue(onRightPaneText.layoutXProperty(), 700, Interpolator.EASE_BOTH),
                            new KeyValue(onLeftPaneText.layoutYProperty(), 54, Interpolator.EASE_BOTH),
                            new KeyValue(nameOutputPane.prefWidthProperty(), 297, Interpolator.EASE_BOTH),
                            new KeyValue(nameOutputPane1.prefWidthProperty(), 297, Interpolator.EASE_BOTH),
                            new KeyValue(nameOutputPane11.prefWidthProperty(), 297, Interpolator.EASE_BOTH)
                    )
            		);
    		maximizeIcon.setImage(new Image(getClass().getResource("/application/RisorseGrafiche/maximize.png").toString()));
    		timeline.play();
    	}
    }
    
    //*******************************//
  	//closingOutputPanes//
  	//*******************************//

    //*******************************//
  	//tutorialHide viene chiamato quando bisogna nascondere il pane tutorial//
  	//*******************************//
    @FXML
    void tutorialHide(MouseEvent event) {
    	tutorial.setVisible(false);
    	((MediaView)tutorial.getChildren().get(2)).getMediaPlayer().stop();
    }
    //*******************************//
  	//tutorialHide//
  	//*******************************//
    
    //*******************************//
  	//sendReceiveMessagge viene chiamato quando bisogna inviare e ricevere il messaggio da ChatGPT//
  	//*******************************//
    @FXML
    void sendReceiveMessagge(MouseEvent event) {
    	if(messaggeToSend.getText() != "") {
    		HBox container = new HBox();
    		container.setSpacing(5);
    		container.setAlignment(Pos.CENTER_RIGHT);
    		
	    	VBox messaggePane = new VBox();
	    	messaggePane.setStyle("-fx-background-color: #2B2B2B;-fx-background-radius: 10 0 10 10;");
	    	messaggePane.setPrefWidth(480);
	    	messaggePane.setMinHeight(35);
	    	messaggePane.setPadding(new Insets(10, 10, 10, 10));
	    	
	    	Text messaggeSent = new Text(messaggeToSend.getText());
	    	messaggeSent.setFill(Paint.valueOf("#fff"));
	    	messaggeSent.setFont(Font.font("System-font", FontWeight.BOLD, 13));
	    	
	    	messaggePane.getChildren().add(messaggeSent);
	    	messaggeSent.setWrappingWidth(450);
	    	
	    	messaggePane.setMinHeight(messaggeSent.getBoundsInLocal().getHeight() + 20);
	    	
	    	SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");
	    	Date date = new Date(System.currentTimeMillis());
	    	Text currentTime = new Text(formatter.format(date));
	    	currentTime.setOpacity(0.5f);
	    	container.getChildren().add(currentTime);
	    	container.getChildren().add(messaggePane);
	    	
	    	messaggeSection.getChildren().add(container);
	    	messaggeToSend.setText("");
	    	editDimensions(null);
	    	
	    	
	    	
	    	Timeline timeline = new Timeline(
					new KeyFrame(Duration.millis(0),
	                		new KeyValue(isWritingText.opacityProperty(), 0.8, Interpolator.LINEAR)
	                ),
	                new KeyFrame(Duration.millis(800),
	                        new KeyValue(isWritingText.opacityProperty(), 0.4, Interpolator.LINEAR)
	                ),
	                new KeyFrame(Duration.millis(1600),
	                        new KeyValue(isWritingText.opacityProperty(), 0.8, Interpolator.LINEAR)
	                )
	        	);
	    	timeline.setCycleCount(Timeline.INDEFINITE);
	    	timeline.play();
	    	
	    	Thread t = new Thread(new Runnable() {
				
				@Override
				public void run() {
					try {
						Thread.sleep(400);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					
					isWritingText.setVisible(true);
					
														
					Platform.runLater(new Runnable() {

						@Override
						public void run() {
														
							HBox container = new HBox();
							container.setSpacing(5);
				    		container.setAlignment(Pos.CENTER_LEFT);
					    	VBox messaggePane = new VBox();
					    	messaggePane.setStyle("-fx-background-color: #10a37f;-fx-background-radius: 0 10 10 10;");
					    	messaggePane.setPrefWidth(480);
					    	messaggePane.setMinHeight(35);
					    	messaggePane.setPadding(new Insets(10, 10, 10, 10));
					    	
					    	int operation = 0;
							
							
							if(messaggeSection.getChildren().size() > 1) {
								if(((Text)((VBox)((HBox) messaggeSection.getChildren().get(messaggeSection.getChildren().size()-2)).getChildren().get(0)).getChildren().get(0)).getText() == "Per poter risponderti ho bisogno che tu inserisca la tua API Key, inviala pure qui in chat.") {
									operation = 1;
								}else {
									operation = 0;
								}
							}
													
							String response = appLogic.chatGPT(messaggeSent.getText(), operation);
					    	
					    	Text messaggeSent = new Text(response);
					    	messaggeSent.setFill(Paint.valueOf("#fff"));
					    	messaggeSent.setFont(Font.font("System-font", FontWeight.BOLD, 13));
					    	
					    	messaggePane.getChildren().add(messaggeSent);
					    	messaggeSent.setWrappingWidth(450);
					    	
					    	messaggePane.setMinHeight(messaggeSent.getBoundsInLocal().getHeight() + 20);
					    	
					    	SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");
					    	Date date = new Date(System.currentTimeMillis());
					    	Text currentTime = new Text(formatter.format(date));
					    	currentTime.setOpacity(0.5f);
					    	
					    	container.getChildren().add(messaggePane);
					    	container.getChildren().add(currentTime);
					    	
					    	isWritingText.setVisible(false);
					    	messaggeSection.getChildren().add(container);
					    	
					    	if(chatPane.getTranslateX() == 0) {
					    		messaggeComing.setVisible(true);
					    	}
						}
					});
					
				}
			});
	    	
	    	t.start();
    	}
    }
    //*******************************//
  	//sendReceiveMessagge//
  	//*******************************//
    
    @FXML
    void editDimensions(KeyEvent event){
    	if(messaggeToSend.getText() != "" && ((Pane) messaggeToSend.getParent()).getPrefHeight() == 44) {
	    	Timeline timeline = new Timeline(
					new KeyFrame(Duration.millis(0),
	                		new KeyValue(((Pane) messaggeToSend.getParent()).prefHeightProperty(), 44, Interpolator.LINEAR),
	                		new KeyValue(((Pane) messaggeToSend.getParent()).layoutYProperty(), 518, Interpolator.LINEAR),
	                		new KeyValue( messaggeToSend.prefHeightProperty(), 35, Interpolator.LINEAR),
	                		new KeyValue( sendMessagge.layoutYProperty(), 5, Interpolator.LINEAR)
	                ),
	                new KeyFrame(Duration.millis(400),
	                        new KeyValue(((Pane) messaggeToSend.getParent()).prefHeightProperty(), 64, Interpolator.LINEAR),
	                		new KeyValue(((Pane) messaggeToSend.getParent()).layoutYProperty(), 498, Interpolator.LINEAR),
	                		new KeyValue( messaggeToSend.prefHeightProperty(), 58, Interpolator.LINEAR),
	                		new KeyValue( sendMessagge.layoutYProperty(), 15, Interpolator.LINEAR)
	                		
	                )
	        	);
	    	timeline.play();
    	}else if(messaggeToSend.getText() == "" && ((Pane) messaggeToSend.getParent()).getPrefHeight() == 64){
    		Timeline timeline = new Timeline(
    				new KeyFrame(Duration.millis(0),
	                        new KeyValue(((Pane) messaggeToSend.getParent()).prefHeightProperty(), 64, Interpolator.LINEAR),
	                		new KeyValue(((Pane) messaggeToSend.getParent()).layoutYProperty(), 498, Interpolator.LINEAR),
	                		new KeyValue( messaggeToSend.prefHeightProperty(), 58, Interpolator.LINEAR),
	                		new KeyValue( sendMessagge.layoutYProperty(), 15, Interpolator.LINEAR)
	                		
	                ),
    				new KeyFrame(Duration.millis(400),
	                		new KeyValue(((Pane) messaggeToSend.getParent()).prefHeightProperty(), 44, Interpolator.LINEAR),
	                		new KeyValue(((Pane) messaggeToSend.getParent()).layoutYProperty(), 518, Interpolator.LINEAR),
	                		new KeyValue( messaggeToSend.prefHeightProperty(), 35, Interpolator.LINEAR),
	                		new KeyValue( sendMessagge.layoutYProperty(), 5, Interpolator.LINEAR)
	                )	                
	        	);
	    	timeline.play();
    	}
    }
}
