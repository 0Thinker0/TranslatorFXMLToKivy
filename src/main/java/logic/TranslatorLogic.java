package logic;

import java.awt.Color;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class TranslatorLogic {
	//Parte logica dell'applicazione
	private FFKtranslatorLogic appLogic;
	
	//Variabile per l'aggiunta delle tabulazioni
	private int tb;
	
	//Variabili di servizio per il conteggio delle righe ed il set della progress bar
    private int nodeCount = 0;
    private int nNode = 0;
    
    //Variabili di servizio per il set delle posizioni dei figli dei layout con la proprietà alignment attiva
    private float xChild = 0.0f;
    private float xParent = 0.0f;
    private float yChild = 0.0f;
    private float yParent = 0.0f;
	
    //Variabile per la verifica della presenza di una gradient color
    private boolean gradientColor = false;
	
    //Liste per memorizzare le immagini presenti, i metodi utilizzati ed i messaggi di errore riscontrati
    private List<String> imgs;
    private List<String> methods;
    private List<String> warnings;
    
    //Variabili di servizio per la scrittura della traduzione all'interno del file kv
    private String dimension;
    private String KV = "";
	
    
    //*******************************//
  	//Setter//
  	//*******************************//
    
	public void setAppLogic(FFKtranslatorLogic appLogic) {
		this.appLogic = appLogic;
	}
	
	//*******************************//
  	//Getter//
  	//*******************************//
	
    public String getDimension() {
    	return dimension;
    }
	
    public List<String> getImages() {
    	return imgs;
    }
    
    public List<String> getMethods() {
    	return methods;
    }
    
    public List<String> getWarnings() {
    	return warnings;
    }
	
    
    //*******************************//
  	//translateToKV viene chiamato quando bisogna tradurre un file//
  	//*******************************//
    
	public File translateToKV(File file) throws Exception {
		//Reset Variabili di servizio
		
		//----------Liste----------//
		imgs = new ArrayList<String>();
		methods = new ArrayList<String>();
		warnings = new ArrayList<String>();
		
		//----------Stringhe----------//
		dimension = "";
		KV = "";
		
		//----------int----------//
		tb = 0;
		nodeCount = 0;
		nNode = 0;
		
		//----------boolean----------//
		gradientColor = false;
		
		
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document doc = builder.parse(file);
		
		//Metodo per il conteggio dei nodi presenti nel file
	    countNodes(doc);
	    
	    NodeList nodeList = doc.getChildNodes();
	    
	    //Aggiornamento della progress bar
	    appLogic.setProgressBar(nodeCount - 1);
	    
	    //Aggiunta della prima riga necessaria per l'esecuzione del file kv
	    KV += "<MainScreen>:\n";
	    tb += 1;
	    
	    //Esplorazione dell'albero dei nodi presenti nel file
        for (int i = 0; i < nodeList.getLength(); i++) {
        	Node node = nodeList.item(i);
        	if(node != null) {
	        	checkNode(node);
	        	processNode(node);	
        	}
        }
        
        //Creazione del nuovo file kv
        String newFileName = file.getName().substring(0, file.getName().indexOf("."));
		File fileKV = new File("src/main/resources/KVFiles/" + newFileName + "_KV.kv");
		BufferedWriter writer = new BufferedWriter(new FileWriter(fileKV));
		
		//Scrittura all'interno del file
		writer.write(KV);
		writer.close();
		
		//Sovrascrittura del file in caso di presenza di gradienti, queste due righe servono per permettere al codice di funzionare con i gradient
		if(gradientColor) {
		    List<String> lines = Files.readAllLines(fileKV.toPath(), StandardCharsets.UTF_8);
		    lines.add(0, "#:import get_color_from_hex kivy.utils.get_color_from_hex\r\n"
		    			+"#:import Gradient kivy_gradient.Gradient\r\n");
		    Files.write(fileKV.toPath(), lines, StandardCharsets.UTF_8);
		}
		
		//Ricerca dei warning
		findWarning();
		        
	    return fileKV;
	}
	
    //*******************************//
  	//findWarning viene chiamato quando bisogna cercare i warning all'interno della traduzione//
  	//*******************************//
    	
	private void findWarning() {
		
		if(KV.contains("System")) {
			addWarning("-Per questioni di incompatibilità non è possibile tradurre System-Font da javaFX a kivy.");
		}
		
		if(KV.contains("GridLayout")) {
			addWarning("-Per questioni di incompatibilità ti suggeriamo di riempire il grid layout con altri layout vuoti.");
		}
	}
	
	
    //*******************************//
  	//countNodes viene chiamato per contare i nodi presenti nel file//
  	//*******************************//
    
    public void countNodes(Node node) {
    	if(node != null) {
	        nodeCount++;
	        NodeList children = node.getChildNodes();
	        for (int i = 0; i < children.getLength(); i++) {
	            countNodes(children.item(i));
	        }
    	}
    }
    
    
    //*******************************//
  	//processNode è un metodo che viene chiamato per processare e controllare tutti i nodi presenti//
  	//*******************************//
    
	void processNode(Node node) {
	    NodeList children = node.getChildNodes();
	    nNode++;
        appLogic.updateProgressBar(nNode);
        
        //L'aggiunta e la rimozione di una tabulazione in kivy, permette di specificare la gerarchia presente nel file
        if(node.getNodeType() != 3 && !node.getNodeName().equals("children") && !node.getNodeName().equals("items") && !node.getNodeName().equals("content")) {
        	tb += 1;
        }
        
	    for (int i = 0; i < children.getLength(); i++) {
	        Node child = children.item(i);
	        if(!child.getNodeName().equals("#text") && child != null)
	        	checkNode(child);
	        processNode(child);
	    }
	    
    	if(node.getNodeType() != 3 && !node.getNodeName().equals("children") && !node.getNodeName().equals("items") && !node.getNodeName().equals("content")) {
    		tb -= 1;
    	}
	}
	
    //*******************************//
  	//findRowsColumns è un metodo che viene chiamato per stabilire da quante colonne e righe è composto un gridpane//
  	//*******************************//
    	
	private void findRowsColumns(Node node, int rows, int columns, int pos){
		String dimensions = "";
    	int number = 0;
    	if(node.getChildNodes().item(pos).getChildNodes() != null)
        	for(int i = 0; i < node.getChildNodes().item(pos).getChildNodes().getLength(); i++) {
	        	if(node.getChildNodes().item(pos).getChildNodes().item(i).getNodeName().equals("ColumnConstraints")) {
	        		if(node.getChildNodes().item(pos).getChildNodes().item(i).getAttributes().getNamedItem("prefWidth") != null) {
	        			if(number != 0)
	        				dimensions += "," + number + ": " + node.getChildNodes().item(pos).getChildNodes().item(i).getAttributes().getNamedItem("prefWidth").getNodeValue();
	        			else
	        				dimensions += number + ": " + node.getChildNodes().item(pos).getChildNodes().item(i).getAttributes().getNamedItem("prefWidth").getNodeValue();
	        			number ++;
	        		}
	        		columns ++;
	        	}
	        	if(node.getChildNodes().item(pos).getChildNodes().item(i).getNodeName().equals("RowConstraints")) {
	        		if(node.getChildNodes().item(pos).getChildNodes().item(i).getAttributes().getNamedItem("prefHeight") != null) {
	        			if(number != 0)
	        				dimensions += "," + number + ": " + node.getChildNodes().item(pos).getChildNodes().item(i).getAttributes().getNamedItem("prefHeight").getNodeValue();
	        			else
	        				dimensions += number + ": " + node.getChildNodes().item(pos).getChildNodes().item(i).getAttributes().getNamedItem("prefHeight").getNodeValue();
	        			number ++;
	        		}
	        		rows ++;
	        	}
	        }
    	
    	if(!dimensions.equals("")) {
    		if(rows != 0) {
    			addTab();
    			KV += "\trow_force_default: True\n"; 
    			addTab();
    			KV += "\trows_minimum: {" + dimensions + "}\n";
    		}else {
    			addTab();
    			KV += "\tcol_force_default: True\n"; 
    			addTab();
    			KV += "\tcols_minimum: {" + dimensions + "}\n";
    		}
    	}
	}
	
	void checkNode(Node node) {
		//-------------------------------------//
		//--------------Layout-----------------//
		//-------------------------------------//
		
		if(node.getParentNode().getNodeName().equals("items") && !node.isSameNode(node.getParentNode().getChildNodes().item(node.getParentNode().getChildNodes().getLength() - 2))) {
			addTab();
			KV += "Splitter:\n";
			
			addTab();
			KV += "\tsizable_from: \"bottom\"\n";
			
			addTab();
			KV += "\tmin_size: 7\n";
			
			addTab();
			KV += "\tmax_size: self.parent.height\n";
			
			addTab();
			KV += "\tstrip_size: 6\n";
			
			tb += 1;
			
			
		}else if(node.getParentNode().getNodeName().equals("items") && node.isSameNode(node.getParentNode().getChildNodes().item(node.getParentNode().getChildNodes().getLength() - 2))) {
			tb -= 1;
		}
		
		if (node.getNodeName().equals("HBox")) {
			addTab();
			KV += "BoxLayout:\n";
			addTab();
			KV += "\torientation: 'horizontal'\n";
			checkProperties(node);
		}
		
		if (node.getNodeName().equals("Pane")) {
	        addTab();
	        KV += "FloatLayout:\n";
	        checkProperties(node);
	    }
		
		if (node.getNodeName().equals("VBox")) {
	        addTab();
	        KV += "BoxLayout:\n";
	        addTab();
	        KV += "\torientation: 'vertical'\n";
	        checkProperties(node);
	    }
		
		if (node.getNodeName().equals("ScrollPane")) {
	        addTab();
	        KV += "ScrollView:\n";
	        checkProperties(node);
	    }
		
		if (node.getNodeName().equals("AnchorPane")) {
	        addTab();
	        KV += "AnchorLayout:\n";
	        checkProperties(node);
	    }
		
		if (node.getNodeName().equals("BorderPane")) {
	        addTab();
	        KV += "BoxLayout:\n";
	        checkProperties(node);
	    }
		
		if (node.getNodeName().equals("StackPane")) {
	        addTab();
	        KV += "FloatLayout:\n";
	        checkProperties(node);
	    }
		
		if (node.getNodeName().equals("SplitPane")) {
	        addTab();
	        KV += "BoxLayout:\n";
	        
	        if(node.getAttributes().getNamedItem("orientation") != null) {
	        	addTab();
	        	KV += "\torientation: \"vertical\"\n";
	        }else {
	        	addTab();
	        	KV += "\torientation: \"horizontal\"\n";
	        }
	        
	        checkProperties(node);
	    }
		
		if (node.getNodeName().equals("GridPane")) {
	        addTab();
	        KV += "GridLayout:\n";

	        int rows = 0;
	        int columns = 0;
	        
	        if(node.getChildNodes() != null) {
	        	findRowsColumns(node, rows, columns, 1);
	        	findRowsColumns(node, rows, columns, 3);	
	        }
	        
	        addTab();
	        KV += "\trows: " + rows + "\n";
	        addTab();
	        KV += "\tcols: " + columns + "\n";
	        checkProperties(node);
	    }
		
		if (node.getNodeName().equals("FlowPane")) {
	        addTab();
	        KV += "BoxLayout:\n";
	        addTab();
			KV += "\torientation: 'horizontal'\n";
	        checkProperties(node);
	    }
		
		
		//-------------------------------------//
		//--------------Element----------------//
		//-------------------------------------//
		
		if (node.getNodeName().equals("Button")) {
	        addTab();
	        KV += "Button:\n";
	        checkProperties(node);
	    }
		
		if (node.getNodeName().equals("CheckBox")) {
			addTab();
	        KV += "AnchorLayout:\n";
	        
            addTab();
            KV += "\tanchor_x: \"left\"\n";
            addTab();
            KV += "\tanchor_y: \"center\"\n";
	        
            String height = "None";
			String width = "None";
            
	        if(node.getAttributes().getNamedItem("prefHeight") != null & node.getAttributes().getNamedItem("prefWidth") != null) {
	        	height = node.getAttributes().getNamedItem("prefHeight").getNodeValue();
				width = node.getAttributes().getNamedItem("prefWidth").getNodeValue();
				addTab();
	        	KV += "\tsize_hint: None,None\n";
	        	addTab();
				KV += "\tsize: " + width + "," + height + "\n";
	        }
	        	
	        if(node.getAttributes().getNamedItem("layoutX") != null & node.getAttributes().getNamedItem("layoutY") != null) {
	        	addTab();
				String layoutX = node.getAttributes().getNamedItem("layoutX").getNodeValue();
				String layoutY = node.getAttributes().getNamedItem("layoutY").getNodeValue();
				KV += "\tpos_hint: {'x': " + layoutX + " / self.parent.width, 'y': 1 - ((" + layoutY + " + self.height) / self.parent.height)}\n";
	        }
	        
	        addTab();
            KV += "\tCheckBox:\n";
            addTab();
            KV += "\t\tsize_hint: None,None\n";
        	addTab();
			KV += "\t\tsize: 17.0,17.0\n";
			
			if(node.getAttributes().getNamedItem("text") != null) {
				addTab();
				String text = node.getAttributes().getNamedItem("text").getNodeValue();
				KV += "\tLabel:\n";
				addTab();
	        	KV += "\t\tsize_hint: None,None\n";
	        	addTab();
				KV += "\t\tsize: " + (Float.valueOf(width) - 17) + "," + height + "\n";
				addTab();
				KV += "\t\ttext: " + "\"" + text + "\"\n";
			}
	    }
		
		if (node.getNodeName().equals("ComboBox")) {
	        addTab();
	        KV += "Spinner:\n";
	        checkProperties(node);
	    }
		
		if (node.getNodeName().equals("Text")) {
	        addTab();
	        KV += "Label:\n";
	        checkProperties(node);
	    }
		
		if (node.getNodeName().equals("PasswordField")) {
	        addTab();
	        KV += "TextInput:\n";
	        addTab();
	        KV += "\tpassword: True\n";
	        checkProperties(node);
	    }
		
		if (node.getNodeName().equals("TextField")) {
	        addTab();
	        KV += "TextInput:\n";
	        checkProperties(node);
	    }
		
		if (node.getNodeName().equals("TextArea")) {
	        addTab();
	        KV += "TextInput:\n";
	        checkProperties(node);
	    }
		
		if (node.getNodeName().equals("ToggleButton")) {
	        addTab();
	        KV += "ToggleButton:\n";
	        checkProperties(node);
	    }
		
		if (node.getNodeName().equals("RadioButton")) {
	        addTab();
	        KV += "AnchorLayout:\n";
	        
            addTab();
            KV += "\tanchor_x: \"left\"\n";
            addTab();
            KV += "\tanchor_y: \"center\"\n";
	        
            String height = "None";
			String width = "None";
            
	        if(node.getAttributes().getNamedItem("prefHeight") != null & node.getAttributes().getNamedItem("prefWidth") != null) {
	        	height = node.getAttributes().getNamedItem("prefHeight").getNodeValue();
				width = node.getAttributes().getNamedItem("prefWidth").getNodeValue();
				addTab();
	        	KV += "\tsize_hint: None,None\n";
	        	addTab();
				KV += "\tsize: " + width + "," + height + "\n";
	        }
	        	
	        if(node.getAttributes().getNamedItem("layoutX") != null & node.getAttributes().getNamedItem("layoutY") != null) {
	        	addTab();
				String layoutX = node.getAttributes().getNamedItem("layoutX").getNodeValue();
				String layoutY = node.getAttributes().getNamedItem("layoutY").getNodeValue();
				KV += "\tpos_hint: {'x': " + layoutX + " / self.parent.width, 'y': 1 - ((" + layoutY + " + self.height) / self.parent.height)}\n";
	        }
	        
	        addTab();
            KV += "\tToggleButton:\n";
            addTab();
            KV += "\t\tsize_hint: None,None\n";
        	addTab();
			KV += "\t\tsize: 17.0,17.0\n";
			
			if(node.getAttributes().getNamedItem("text") != null) {
				addTab();
				String text = node.getAttributes().getNamedItem("text").getNodeValue();
				KV += "\tLabel:\n";
				addTab();
	        	KV += "\t\tsize_hint: None,None\n";
	        	addTab();
				KV += "\t\tsize: " + (Float.valueOf(width) - 17) + "," + height + "\n";
				addTab();
				KV += "\t\ttext: " + "\"" + text + "\"\n";
			}
	    }
		
		if (node.getNodeName().equals("Slider")) {
	        addTab();
	        KV += "Slider:\n";
	        checkProperties(node);
	    }
		
		if (node.getNodeName().equals("Label")) {
	        addTab();
	        KV += "Label:\n";
	        checkProperties(node);
	    }
		
		if (node.getNodeName().equals("ProgressBar")) {
	        addTab();
	        KV += "ProgressBar:\n";
	        checkProperties(node);
	    }
		
		if (node.getNodeName().equals("ImageView")) {
	        addTab();
	        KV += "Image:\n";
	        checkProperties(node);
	    }
		
		if (node.getNodeName().equals("Font")) {
			tb -= 2;
			if(node.getAttributes().getNamedItem("size") != null) {
				addTab();
	        	KV += "\tfont_size: " + node.getAttributes().getNamedItem("size").getNodeValue() + "\n";
			}else {
				addTab();
	        	KV += "\tfont_size: 12\n";
			}
			
			if(node.getAttributes().getNamedItem("name") != null) {
				addTab();
	        	KV += "\tfont_name: \"" + node.getAttributes().getNamedItem("name").getNodeValue().replaceAll(" ", "-") + "\"\n";
	        	if(node.getAttributes().getNamedItem("name").getNodeValue().contains("Bold")) {
					addTab();
		        	KV += "\tbold: True\n";
	        	}
	        	
	        	if(node.getAttributes().getNamedItem("name").getNodeValue().contains("Italic")) {
					addTab();
		        	KV += "\titalic: True\n";
	        	}
			}else {
				addTab();
				KV += "\tfont_name: \"System-Regular\"\n";
			}
	        tb += 2;
	    }
		
		if ((node.getNodeName().equals("Label") || node.getNodeName().equals("Text")) && node.getChildNodes().getLength() == 0) {
			addTab();
        	KV += "\tfont_size: 12.0\n";
        	addTab();
        	KV += "\tfont_name: \"System-Regular\"\n";
	    }
		
		if (node.getNodeName().equals("Image")) {
			tb -= 2;
			
			try {
				String image = "";
				
				if(node.getAttributes().getNamedItem("url").getNodeValue().contains("@")) {
					image = java.net.URLDecoder.decode(node.getAttributes().getNamedItem("url").getNodeValue().substring(1, node.getAttributes().getNamedItem("url").getNodeValue().length()), "UTF-8");
				}else {
					image = java.net.URLDecoder.decode(node.getAttributes().getNamedItem("url").getNodeValue(), "UTF-8");
				}
				
				if(image.contains("file:/"))
					image.replace("file:/", "");
				
				addTab();
				KV += "\tsource: \"" + image + "\"\n";
				imgs.add(image);
				
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			} catch (DOMException e) {
				e.printStackTrace();
			}
			
			tb += 2;
	    }		
	}
	
    //*******************************//
  	//checkProperties è un metodo che viene chiamato per controllare le proprietà di tutti i nodi//
  	//*******************************//
    
	void checkProperties(Node node) {
		xChild = 0.0f;
		xParent = 0.0f;
		yChild = 0.0f;
		yParent = 0.0f;
		
		//Aggiunta warning margin, a causa di problemi di compatibilità kivy non possiede il concetto di margine, quindi non può essere tradotto
		if(node.getAttributes().getNamedItem("margin") != null) {
			addWarning("-Il concetto di margine in kivy non esiste, per tanto la conversione dei marginin non è possibile");
		}
		
		if(node.getChildNodes().item(node.getChildNodes().getLength() - 2) != null && node.getChildNodes().item(node.getChildNodes().getLength() - 2).getNodeName().equals("padding"))
			if ((node.getChildNodes().item(node.getChildNodes().getLength() - 2).getChildNodes().item(1).getNodeName().equals("Insets"))) {
				
				String left = "0";
				String right = "0";
				String bottom = "0";
				String top = "0";
				
				if(node.getChildNodes().item(node.getChildNodes().getLength() - 2).getChildNodes().item(1).getAttributes().getNamedItem("left") != null) {
					left = node.getChildNodes().item(node.getChildNodes().getLength() - 2).getChildNodes().item(1).getAttributes().getNamedItem("left").getNodeValue();
				}
				if(node.getChildNodes().item(node.getChildNodes().getLength() - 2).getChildNodes().item(1).getAttributes().getNamedItem("right") != null) {
					right = node.getChildNodes().item(node.getChildNodes().getLength() - 2).getChildNodes().item(1).getAttributes().getNamedItem("right").getNodeValue();		
				}
				if(node.getChildNodes().item(node.getChildNodes().getLength() - 2).getChildNodes().item(1).getAttributes().getNamedItem("bottom") != null) {
					bottom = node.getChildNodes().item(node.getChildNodes().getLength() - 2).getChildNodes().item(1).getAttributes().getNamedItem("bottom").getNodeValue();
				}
				if(node.getChildNodes().item(node.getChildNodes().getLength() - 2).getChildNodes().item(1).getAttributes().getNamedItem("top") != null) {
					top = node.getChildNodes().item(node.getChildNodes().getLength() - 2).getChildNodes().item(1).getAttributes().getNamedItem("top").getNodeValue();
				}
				
				addTab();
				KV += "\tpadding: [" + left + "," + top + "," + right + "," + bottom + "]\n";
			}
		
		if(node.getAttributes().getNamedItem("fx:id") != null) {
			addTab();
			String id = node.getAttributes().getNamedItem("fx:id").getNodeValue();
			KV += "\tid: " + id + "\n";
		}
		
		String layoutX = "0.0";
		String layoutY = "0.0";
				
		if(node.getParentNode().getParentNode() != null)
			if(node.getParentNode().getParentNode().getAttributes() != null)
				if(node.getParentNode().getParentNode().getAttributes().getNamedItem("alignment") != null) {
					switch(node.getParentNode().getParentNode().getAttributes().getNamedItem("alignment").getNodeValue()) {
						case "CENTER":
							addTab();
							if(node.getAttributes().getNamedItem("prefWidth") != null)
								xChild = Float.valueOf(node.getAttributes().getNamedItem("prefWidth").getNodeValue());
							else if(node.getAttributes().getNamedItem("fitWidth") != null)
								xChild = Float.valueOf(node.getAttributes().getNamedItem("fitWidth").getNodeValue());
							xParent = Float.valueOf(node.getParentNode().getParentNode().getAttributes().getNamedItem("prefWidth").getNodeValue());
							
							if(node.getAttributes().getNamedItem("prefHeight") != null)
								yChild = Float.valueOf(node.getAttributes().getNamedItem("prefHeight").getNodeValue());
							else if(node.getAttributes().getNamedItem("fitHeight") != null)
								yChild = Float.valueOf(node.getAttributes().getNamedItem("fitHeight").getNodeValue());
							yParent = Float.valueOf(node.getParentNode().getParentNode().getAttributes().getNamedItem("prefHeight").getNodeValue());
							
							KV += "\tpos_hint: {'x': " + (0.5 - ((xChild / 2) / xParent)) + ", 'y': " + (0.5 - ((yChild / 2) / yParent)) + "}\n";
							break;
							
						case "CENTER_LEFT":
							addTab();
							
							if(node.getAttributes().getNamedItem("prefHeight") != null)
								yChild = Float.valueOf(node.getAttributes().getNamedItem("prefHeight").getNodeValue());
							else if(node.getAttributes().getNamedItem("fitHeight") != null)
								yChild = Float.valueOf(node.getAttributes().getNamedItem("fitHeight").getNodeValue());
							yParent = Float.valueOf(node.getParentNode().getParentNode().getAttributes().getNamedItem("prefHeight").getNodeValue());
							
							KV += "\tpos_hint: {'x': " + (0.0) + ", 'y': " + (0.5 - ((yChild / 2) / yParent)) + "}\n";
							break;
							
						case "CENTER_RIGHT":
							addTab();
							if(node.getAttributes().getNamedItem("prefWidth") != null)
								xChild = Float.valueOf(node.getAttributes().getNamedItem("prefWidth").getNodeValue());
							else
								xChild = Float.valueOf(node.getAttributes().getNamedItem("fitWidth").getNodeValue());
							xParent = Float.valueOf(node.getParentNode().getParentNode().getAttributes().getNamedItem("prefWidth").getNodeValue());
							
							if(node.getAttributes().getNamedItem("prefHeight") != null)
								yChild = Float.valueOf(node.getAttributes().getNamedItem("prefHeight").getNodeValue());
							else if(node.getAttributes().getNamedItem("fitHeight") != null)
								yChild = Float.valueOf(node.getAttributes().getNamedItem("fitHeight").getNodeValue());
							yParent = Float.valueOf(node.getParentNode().getParentNode().getAttributes().getNamedItem("prefHeight").getNodeValue());
							
							KV += "\tpos_hint: {'x': " + (1 - ((xChild / 2) / xParent)) + ", 'y': " + (0.5 - ((yChild / 2) / yParent)) + "}\n";
							break;
							
						case "TOP_CENTER":
							addTab();
							if(node.getAttributes().getNamedItem("prefWidth") != null)
								xChild = Float.valueOf(node.getAttributes().getNamedItem("prefWidth").getNodeValue());
							else if(node.getAttributes().getNamedItem("fitWidth") != null)
								xChild = Float.valueOf(node.getAttributes().getNamedItem("fitWidth").getNodeValue());
							xParent = Float.valueOf(node.getParentNode().getParentNode().getAttributes().getNamedItem("prefWidth").getNodeValue());
														
							KV += "\tpos_hint: {'x': " + (0.5 - ((xChild / 2) / xParent)) + ", 'y': " + (0.0) + "}\n";
							break;
							
						case "TOP_LEFT":
							addTab();		
							KV += "\tpos_hint: {'x': " + (0.0) + ", 'y': " + (0.0) + "}\n";
							break;
							
						case "TOP_RIGHT":
							addTab();
							if(node.getAttributes().getNamedItem("prefWidth") != null)
								xChild = Float.valueOf(node.getAttributes().getNamedItem("prefWidth").getNodeValue());
							else if(node.getAttributes().getNamedItem("fitWidth") != null)
								xChild = Float.valueOf(node.getAttributes().getNamedItem("fitWidth").getNodeValue());
							xParent = Float.valueOf(node.getParentNode().getParentNode().getAttributes().getNamedItem("prefWidth").getNodeValue());
														
							KV += "\tpos_hint: {'x': " + (1 - ((xChild / 2) / xParent)) + ", 'y': " + (0.0) + "}\n";
							break;
							
						case "BOTTOM_CENTER":
							addTab();
							if(node.getAttributes().getNamedItem("prefWidth") != null)
								xChild = Float.valueOf(node.getAttributes().getNamedItem("prefWidth").getNodeValue());
							else if(node.getAttributes().getNamedItem("fitWidth") != null)
								xChild = Float.valueOf(node.getAttributes().getNamedItem("fitWidth").getNodeValue());
							xParent = Float.valueOf(node.getParentNode().getParentNode().getAttributes().getNamedItem("prefWidth").getNodeValue());
							
							if(node.getAttributes().getNamedItem("prefHeight") != null)
								yChild = Float.valueOf(node.getAttributes().getNamedItem("prefHeight").getNodeValue());
							else if(node.getAttributes().getNamedItem("fitHeight") != null)
								yChild = Float.valueOf(node.getAttributes().getNamedItem("fitHeight").getNodeValue());
							yParent = Float.valueOf(node.getParentNode().getParentNode().getAttributes().getNamedItem("prefHeight").getNodeValue());
							
							KV += "\tpos_hint: {'x': " + (0.5 - ((xChild / 2) / xParent)) + ", 'y': " + (1 - ((yChild / 2) / yParent)) + "}\n";
							break;
							
						case "BOTTOM_LEFT":
							addTab();							
							if(node.getAttributes().getNamedItem("prefHeight") != null)
								yChild = Float.valueOf(node.getAttributes().getNamedItem("prefHeight").getNodeValue());
							else if(node.getAttributes().getNamedItem("fitHeight") != null)
								yChild = Float.valueOf(node.getAttributes().getNamedItem("fitHeight").getNodeValue());
							yParent = Float.valueOf(node.getParentNode().getParentNode().getAttributes().getNamedItem("prefHeight").getNodeValue());
							
							KV += "\tpos_hint: {'x': " + (0.0) + ", 'y': " + (1 - ((yChild / 2) / yParent)) + "}\n";
							break;
							
						case "BOTTOM_RIGHT":
							addTab();
							if(node.getAttributes().getNamedItem("prefWidth") != null)
								xChild = Float.valueOf(node.getAttributes().getNamedItem("prefWidth").getNodeValue());
							else if(node.getAttributes().getNamedItem("fitWidth") != null)
								xChild = Float.valueOf(node.getAttributes().getNamedItem("fitWidth").getNodeValue());
							xParent = Float.valueOf(node.getParentNode().getParentNode().getAttributes().getNamedItem("prefWidth").getNodeValue());
							
							if(node.getAttributes().getNamedItem("prefHeight") != null)
								yChild = Float.valueOf(node.getAttributes().getNamedItem("prefHeight").getNodeValue());
							else if(node.getAttributes().getNamedItem("fitHeight") != null)
								yChild = Float.valueOf(node.getAttributes().getNamedItem("fitHeight").getNodeValue());
							yParent = Float.valueOf(node.getParentNode().getParentNode().getAttributes().getNamedItem("prefHeight").getNodeValue());
							
							KV += "\tpos_hint: {'x': " + (1 - ((xChild / 2) / xParent)) + ", 'y': " + (1 - ((yChild / 2) / yParent)) + "}\n";
							break;
							
					}
				}else {
					if(node.getAttributes().getNamedItem("layoutX") != null || node.getAttributes().getNamedItem("layoutY") != null) {
						if(node.getAttributes().getNamedItem("layoutX") != null)
							layoutX = node.getAttributes().getNamedItem("layoutX").getNodeValue();
						if(node.getAttributes().getNamedItem("layoutY") != null)
							layoutY = node.getAttributes().getNamedItem("layoutY").getNodeValue();
					}
					addTab();
					if(node.getParentNode().getNodeName().equals("items") && !node.isSameNode(node.getParentNode().getChildNodes().item(node.getParentNode().getChildNodes().getLength() - 2)))
						KV += "\tpos_hint: {'x': 0, 'y': 0}\n";
					else
						KV += "\tpos_hint: {'x': " + layoutX + " / self.parent.width, 'y': 1 - ((" + layoutY + " + self.height) / self.parent.height)}\n";
				}
		
		if(node.getAttributes().getNamedItem("prefHeight") != null & node.getAttributes().getNamedItem("prefWidth") != null) {
			
			String height = node.getAttributes().getNamedItem("prefHeight").getNodeValue();
			String width = node.getAttributes().getNamedItem("prefWidth").getNodeValue();
			
			if(dimension.equals("")) {
				dimension = width + ", " + height;
			}
			
			if(!node.getParentNode().getNodeName().equals("items")) {
				addTab();
				KV += "\tsize_hint: None, None\n";
			}
			
			addTab();
			KV += "\tsize: " + width + "," + height + "\n";
		}else if(node.getAttributes().getNamedItem("fitHeight") != null & node.getAttributes().getNamedItem("fitWidth") != null) {
			String height = node.getAttributes().getNamedItem("fitHeight").getNodeValue();
			String width = node.getAttributes().getNamedItem("fitWidth").getNodeValue();
			if(!node.getParentNode().getNodeName().equals("items")) {
				addTab();
				KV += "\tsize_hint: None, None\n";
			}
			addTab();
			KV += "\tsize: " + width + "," + height + "\n";
		}else if((node.getAttributes().getNamedItem("prefHeight") == null & node.getAttributes().getNamedItem("prefWidth") == null)){
			if(!node.getParentNode().getNodeName().equals("items")) {
				addTab();
				KV += "\tsize_hint: None, None\n";
			}
			addTab();
			KV += "\tsize: self.texture_size\n";
		}
		
		if(node.getAttributes().getNamedItem("toggleGroup") != null) {
			addTab();
			String group = node.getAttributes().getNamedItem("toggleGroup").getNodeValue();
			KV += "\tgroup: " + group.substring(1, group.length()) + "\n";
		}
		
		if(node.getAttributes().getNamedItem("selected") != null) {
			addTab();
			String selected = node.getAttributes().getNamedItem("selected").getNodeValue();
			if(selected.equals("true"))
				KV += "\tstate: down\n";
			else
				KV += "\tstate: normal\n";
		}
		
		if(node.getAttributes().getNamedItem("spacing") != null) {
			addTab();
			String spacing = node.getAttributes().getNamedItem("spacing").getNodeValue();
			KV += "\tspacing: " + spacing + "\n";
		}
		
		if(node.getAttributes().getNamedItem("wrappingWidth") != null) {
			addTab();
			KV += "\tsize_hint: None, None\n";
			addTab();
			String wrappingWidth = node.getAttributes().getNamedItem("wrappingWidth").getNodeValue();
			KV += "\twidth: " + wrappingWidth.substring(0, wrappingWidth.indexOf(".") + 2) + "\n";
			addTab();
			KV += "\theight: 17.0\n";
		}
		
		if(node.getAttributes().getNamedItem("preserveRatio") != null) {
			addTab();
			String ratio = node.getAttributes().getNamedItem("preserveRatio").getNodeValue();
			KV += "\tkeep_ratio: " + ratio.replace(ratio.charAt(0), String.valueOf(ratio.charAt(0)).toUpperCase().toCharArray()[0]) + "\n";
		}
		
		if(node.getAttributes().getNamedItem("text") != null) {
			addTab();
			String text = node.getAttributes().getNamedItem("text").getNodeValue();
			KV += "\ttext: " + "\"" + text + "\"\n";
		}
		
		if(node.getAttributes().getNamedItem("underline") != null) {
			addTab();
			String underline = node.getAttributes().getNamedItem("underline").getNodeValue();
			KV += "\tunderline: " + "\"" + underline.replace(underline.charAt(0), String.valueOf(underline.charAt(0)).toUpperCase().toCharArray()[0]) + "\"\n";
		}
		
		if(node.getAttributes().getNamedItem("rotate") != null) {
			addTab();
			String degree = node.getAttributes().getNamedItem("rotate").getNodeValue();
			KV += "\tcanvas.before:\n";
			
			addTab();
			KV += "\t\tPushMatrix\n";
			
			addTab();
			KV += "\t\tRotate:\n";
			
			addTab();
			KV += "\t\t\tangle: " + degree + "\n";
			
			addTab();
			KV += "\t\t\torigin: self.center\n";
			
			addTab();
			KV += "\tcanvas.after:\n";
			
			addTab();
			KV += "\t\tPopMatrix\n";
		}
		
		if(node.getAttributes().getNamedItem("textFill") != null) {
			addTab();
			String textFill = node.getAttributes().getNamedItem("textFill").getNodeValue();
			Color color = Color.decode(textFill.toLowerCase());
			int red = color.getRed();
			int green = color.getGreen();
			int blue = color.getBlue();
			float alpha = 1;
			if(node.getAttributes().getNamedItem("opacity") != null)
				alpha = Float.valueOf(node.getAttributes().getNamedItem("opacity").getNodeValue());
			else
				alpha = (float) color.getAlpha() / 255;
			KV += "\tcolor: " + red / 255f + "," + green / 255f + "," + blue / 255f + "," + alpha + "\n";
		}
		
		if(node.getAttributes().getNamedItem("opacity") != null) {
			if(node.getAttributes().getNamedItem("textFill") == null) {
				addTab();
				String opacity = node.getAttributes().getNamedItem("opacity").getNodeValue();
				
				KV += "\tcolor: 0,0,0," + opacity + "\n";
			}
		}
		
		if(node.getAttributes().getNamedItem("wrapText") != null) {
			addTab();
			KV += "\tmultiline: True\n";
		}
		
		if(node.getAttributes().getNamedItem("promptText") != null) {
			addTab();
			KV += "\thint_text: \"" + node.getAttributes().getNamedItem("promptText").getNodeValue() + "\"\n";
		}
		
		
		if(node.getNodeName().equals("Text") || node.getNodeName().equals("Label"))
			if((node.getAttributes().getNamedItem("textFill") == null && node.getAttributes().getNamedItem("opacity") == null)) {
				if(node.getAttributes().getNamedItem("style") != null) {
					if (!node.getAttributes().getNamedItem("style").getNodeValue().contains("color")) {
						addTab();
						KV += "\tcolor: 0,0,0,1\n";
					}
				}else {
					addTab();
					KV += "\tcolor: 0,0,0,1\n";
				}
			}
		
		if(node.getAttributes().getNamedItem("style") != null) {
			
			String cornerRadius = "";
			String style = node.getAttributes().getNamedItem("style").getNodeValue();
			
			if(!node.getNodeName().equals("TextField")) {
				addTab();
				KV += "\tcanvas.before:\n";
			}
			
			if(style.contains("-fx-effect: dropshadow")) {
				addTab();
				KV += "\t\tColor:\n";
				addTab();
				String shadow = style.substring(style.indexOf("-fx-effect: dropshadow"), style.substring(style.indexOf("-fx-effect: dropshadow"), style.length()).indexOf(";") + 1 + style.substring(0, style.indexOf("-fx-effect: dropshadow")).length());
				shadow = shadow.substring(shadow.indexOf("rgba(") + 5, shadow.length());
				String rgba = shadow.substring(0, shadow.indexOf(")"));
				
				shadow = shadow.substring(shadow.indexOf("),") + 2, shadow.indexOf(")", shadow.indexOf("),") + 2));
				KV += "\t\t\trgba: " + rgba + "\n";
				addTab();
				KV += "\t\tBoxShadow:\n";
				addTab();
				KV += "\t\t\tsize: self.size\n";
				addTab();
				KV += "\t\t\tpos: self.pos\n";
				
				String[] info = shadow.split(",");
				addTab();
				KV += "\t\t\tblur_radius: "+ info[0] + "\n";
				
				addTab();
				KV += "\t\t\tspread_radius: "+ info[1] + "," + info[1] + "\n";

				addTab();
				KV += "\t\t\toffset: "+ (Float.valueOf(info[2]) * (-1)) + "," + (Float.valueOf(info[3]) * (-1)) + "\n";
				
				cornerRadius = "0,0,0,0";
				
				if(style.contains("-fx-background-radius:")) {
					String borderString = style.substring(style.indexOf("-fx-background-radius:"), style.substring(style.indexOf("-fx-background-radius:"), style.length()).indexOf(";") + 1 + style.substring(0, style.indexOf("-fx-background-radius:")).length());
					cornerRadius = findCorner(borderString);
				}
				
				addTab();
				KV += "\t\t\tborder_radius: "+ cornerRadius + "\n";
			}
			
			if(style.contains("-fx-background-color:")) {
				if(!style.contains("gradient")) {
					String colorString = style.substring(style.indexOf("-fx-background-color:"), style.substring(style.indexOf("-fx-background-color:"), style.length()).indexOf(";") + 1 + style.substring(0, style.indexOf("-fx-background-color:")).length());
					Color color = Color.decode(colorString.substring(colorString.indexOf(":") + 2, colorString.indexOf(";")).toLowerCase());
					int red = color.getRed();
					int green = color.getGreen();
					int blue = color.getBlue();
					float alpha = 1;
					if(node.getAttributes().getNamedItem("opacity") != null)
						alpha = Float.valueOf(node.getAttributes().getNamedItem("opacity").getNodeValue());
					else
						alpha = (float) color.getAlpha() / 255;
					
					if(node.getNodeName().equals("TextField")) {
						KV += "\tbackground_color: (" + red / 255f + "," + green / 255f + "," + blue / 255f + "," + alpha + ")\n";
					}else {
						addTab();
						KV += "\t\tColor:\n";
						addTab();
						KV += "\t\t\trgba: " + red / 255f + "," + green / 255f + "," + blue / 255f + "," + alpha + "\n";
						style.replace(colorString, " ");
					}
				}
				
				if(!style.contains("-fx-background-radius:")) {
					addTab();
					KV += "\t\tRectangle:\n";
					addTab();
					KV += "\t\t\tsize: self.size\n";
					addTab();
					KV += "\t\t\tpos: self.pos\n";
				}
			}
			
			if(style.contains("-fx-background-radius:")) {
				addTab();
				KV += "\t\tRoundedRectangle:\n";
				addTab();
				KV += "\t\t\tsize: self.size\n";
				addTab();
				KV += "\t\t\tpos: self.pos\n";
				addTab();
				
				String borderString = style.substring(style.indexOf("-fx-background-radius:"), style.substring(style.indexOf("-fx-background-radius:"), style.length()).indexOf(";") + 1 + style.substring(0, style.indexOf("-fx-background-radius:")).length());
				
				if(cornerRadius.equals("")) {
					cornerRadius = findCorner(borderString);
				}
				
				KV += "\t\t\tradius: [" + cornerRadius + "]\n";
				style.replace(borderString, " ");
				
			}
			
			if(style.contains("gradient")) {
				gradientColor = true;
				String gradient = style.substring(style.indexOf("-fx-background-color:"), style.substring(style.indexOf("-fx-background-color:"), style.length()).indexOf(";") + 1 + style.substring(0, style.indexOf("-fx-background-color:")).length());
				String[] colors = gradient.substring(style.indexOf("(") + 1, style.indexOf(")")).split(",");
				
				if(gradient.contains("top")) {
					addTab();
					KV += "\t\t\ttexture: Gradient.vertical(";
					KV += "get_color_from_hex(\""+ colors[1].substring(colors[1].indexOf("#") + 1, colors[1].length()) + "\")";	
					
					for(int i = 2; i < colors.length; i++) {
						KV += ",get_color_from_hex(\""+ colors[i].substring(colors[i].indexOf("#") + 1, colors[i].length()) + "\")";
					}
					
					KV += ")\n";
				}else if(gradient.contains("bottom")){
					addTab();
					KV += "\t\t\ttexture: Gradient.vertical(";
					KV += "get_color_from_hex(\""+ colors[colors.length - 1].substring(colors[colors.length - 1].indexOf("#") + 1, colors[colors.length - 1].length()) + "\")";	
					
					for(int i = colors.length - 2; i > 0; i--) {
						KV += ",get_color_from_hex(\""+ colors[i].substring(colors[i].indexOf("#") + 1, colors[i].length()) + "\")";
					}
					
					KV += ")\n";
				}else if(gradient.contains("right")) {
					addTab();
					KV += "\t\t\ttexture: Gradient.horizontal(";
					KV += "get_color_from_hex(\""+ colors[1].substring(colors[1].indexOf("#") + 1, colors[1].length()) + "\")";	
					
					for(int i = 2; i < colors.length; i++) {
						KV += ",get_color_from_hex(\""+ colors[i].substring(colors[i].indexOf("#") + 1, colors[i].length()) + "\")";
					}
					
					KV += ")\n";
				}else if(gradient.contains("left")) {
					addTab();
					KV += "\t\t\ttexture: Gradient.horizontal(";
					KV += "get_color_from_hex(\""+ colors[colors.length - 1].substring(colors[colors.length - 1].indexOf("#") + 1, colors[colors.length - 1].length()) + "\")";	
					
					for(int i = colors.length - 2; i > 0; i--) {
						KV += ",get_color_from_hex(\""+ colors[i].substring(colors[i].indexOf("#") + 1, colors[i].length()) + "\")";
					}
					
					KV += ")\n";
				}
			}
			
			if(style.contains("-fx-background-image:")) {
				addTab();
				KV += "\t\tRectangle:\n";
				if(!style.contains("-fx-background-size:")) {
					addTab();
					KV += "\t\t\tsize: self.size\n";
				}else {
					String size = style.substring(style.indexOf("-fx-background-size:"), style.substring(style.indexOf("-fx-background-size:"), style.length()).indexOf(";") + 1 + style.substring(0, style.indexOf("-fx-background-size:")).length());
					addTab();
					KV += "\t\t\tsize: " + size.substring(size.indexOf(":") + 1, size.indexOf(";")) + "\n";
				}
				addTab();
				KV += "\t\t\tpos: self.pos\n";
				addTab();
				String image = style.substring(style.indexOf("-fx-background-image:"), style.substring(style.indexOf("-fx-background-image:"), style.length()).indexOf(";") + 1 + style.substring(0, style.indexOf("-fx-background-image:")).length());
				
				if(image.contains("file:/"))
					image.replace("file:/", "");
				
				try {
					KV += "\t\t\tsource: " + java.net.URLDecoder.decode(image.substring(image.indexOf("(") + 1, image.indexOf(")")), "UTF-8") + "\n";
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				} catch (DOMException e) {
					e.printStackTrace();
				}
				style.replace(image, " ");
			}
			
			if(style.contains("-fx-border-color:")) {
				addTab();
				String colorString = style.substring(style.indexOf("-fx-border-color:"), style.substring(style.indexOf("-fx-border-color:"), style.length()).indexOf(";") + 1 + style.substring(0, style.indexOf("-fx-border-color:")).length());
				Color color = Color.decode(colorString.substring(colorString.indexOf(":") + 2, colorString.indexOf(";")).toLowerCase());
				int red = color.getRed();
				int green = color.getGreen();
				int blue = color.getBlue();
				float alpha = 1;
				
				KV += "\t\tColor:\n";
				
				addTab();
				if(node.getAttributes().getNamedItem("opacity") != null)
					alpha = Float.valueOf(node.getAttributes().getNamedItem("opacity").getNodeValue());
				else
					alpha = (float) color.getAlpha() / 255;
				KV += "\t\t\trgba: " + red / 255f + "," + green / 255f + "," + blue / 255f + "," + alpha + "\n";
				style.replace(colorString, " ");
				
				if(style.contains("-fx-border-width:")) {
					
					String width = style.substring(style.indexOf("-fx-border-width:"), style.substring(style.indexOf("-fx-border-width:"), style.length()).indexOf(";") + 1 + style.substring(0, style.indexOf("-fx-border-width:")).length());
					width = width.substring(width.indexOf(":") + 1, width.indexOf(";"));
					
					addTab();
					KV += "\t\tLine:\n";
					
					addTab();
					KV += "\t\t\twidth: " + width + "\n";
					
				}else {
					addTab();
					KV += "\t\tLine:\n";
					
					addTab();
					KV += "\t\t\twidth: 1\n";
				}
				
				if(style.contains("-fx-border-radius:")) {
					String radius = style.substring(style.indexOf("-fx-border-radius:"), style.substring(style.indexOf("-fx-border-radius:"), style.length()).indexOf(";") + 1 + style.substring(0, style.indexOf("-fx-border-radius:")).length());
					addTab();
					KV += "\t\t\trounded_rectangle: (self.x, self.y, self.width, self.height, " + findCorner(radius) + ")\n";
				}else {
					addTab();
					KV += "\t\t\trectangle: (self.x, self.y, self.width, self.height)\n";
				}
			}
		}
		
		if (node.getAttributes().getNamedItem("alignment") != null && node.getNodeName().equals("Label")) {
	        String alignment = node.getAttributes().getNamedItem("alignment").getNodeValue();
	        
	        if((node.getAttributes().getNamedItem("prefHeight") != null & node.getAttributes().getNamedItem("prefWidth") != null)){
		        addTab();
	            KV += "\ttext_size: self.size\n";
	        }
	        
	        if (alignment.equals("CENTER_LEFT")) {
	            addTab();
	            KV += "\thalign: \"left\"\n";
	            addTab();
	            KV += "\tvalign: \"center\"\n";
	        } else if (alignment.equals("CENTER_RIGHT")) {
	        	addTab();
	            KV += "\thalign: \"right\"\n";
	            addTab();
	            KV += "\tvalign: \"center\"\n";
	        } else if (alignment.equals("CENTER")) {
	        	addTab();
	            KV += "\thalign: \"center\"\n";
	            addTab();
	            KV += "\tvalign: \"center\"\n";
	        }else if (alignment.equals("TOP_CENTER")) {
	        	addTab();
	            KV += "\thalign: \"center\"\n";
	            addTab();
	            KV += "\tvalign: \"top\"\n";
	        }else if (alignment.equals("TOP_RIGHT")) {
	        	addTab();
	            KV += "\thalign: \"right\"\n";
	            addTab();
	            KV += "\tvalign: \"top\"\n";
	        }else if (alignment.equals("TOP_LEFT")) {
	        	addTab();
	            KV += "\thalign: \"left\"\n";
	            addTab();
	            KV += "\tvalign: \"top\"\n";
	        }else if (alignment.equals("BOTTOM_CENTER")) {
	        	addTab();
	            KV += "\thalign: \"center\"\n";
	            addTab();
	            KV += "\tvalign: \"bottom\"\n";
	        }else if (alignment.equals("BOTTOM_RIGHT")) {
	        	addTab();
	            KV += "\thalign: \"right\"\n";
	            addTab();
	            KV += "\tvalign: \"bottom\"\n";
	        }else if (alignment.equals("BOTTOM_LEFT")) {
	        	addTab();
	            KV += "\thalign: \"left\"\n";
	            addTab();
	            KV += "\tvalign: \"bottom\"\n";
	        }
	    }else if(node.getAttributes().getNamedItem("alignment") == null && node.getNodeName().equals("Label")) {
	    	if((node.getAttributes().getNamedItem("prefHeight") != null & node.getAttributes().getNamedItem("prefWidth") != null)){
		        addTab();
	            KV += "\ttext_size: self.size\n";
	        }
	    	addTab();
            KV += "\thalign: \"left\"\n";
            addTab();
            KV += "\tvalign: \"center\"\n";
	    }
		
		if (node.getAttributes().getNamedItem("onMousePressed") != null) {
			String event = node.getAttributes().getNamedItem("onMousePressed").getNodeValue();
			
			addTab();
            KV += "\ton_touch_down: root." + event.substring(1, event.length()) + "()\n";
            
            addWarning("-Rilevato onMousePressed: questo metodo di input, insieme a onMouseClicked, in kivy vengono tradotto con lo stesso metodo on_touch_down");
            
            methods.add(event.substring(1, event.length()));
		}
		if (node.getAttributes().getNamedItem("onMouseReleased") != null) {
			String event = node.getAttributes().getNamedItem("onMouseReleased").getNodeValue();
			
			addTab();
            KV += "\ton_touch_up: root." + event.substring(1, event.length()) + "()\n";
            
            methods.add(event.substring(1, event.length()));
		}
		if (node.getAttributes().getNamedItem("onMouseMoved") != null) {
			String event = node.getAttributes().getNamedItem("onMouseMoved").getNodeValue();
			
			addTab();
            KV += "\ton_touch_move: root." + event.substring(1, event.length()) + "()\n";
            
            addWarning("-Rilevato onMouseMoved: questo metodo di input, insieme a onMouseDragged, in kivy vengono tradotti con lo stesso metodo on_touch_move");
            
            methods.add(event.substring(1, event.length()));
		}
		if (node.getAttributes().getNamedItem("onMouseDragged") != null) {
			String event = node.getAttributes().getNamedItem("onMouseDragged").getNodeValue();
			
			addTab();
            KV += "\ton_touch_move: root." + event.substring(1, event.length()) + "()\n";
            
            addWarning("-Rilevato onMouseDragged: questo metodo di input, insieme a onMouseMoved, in kivy vengono tradotto con lo stesso metodo on_touch_move");
            
            methods.add(event.substring(1, event.length()));
		}
		if (node.getAttributes().getNamedItem("onMouseClicked") != null) {
			String event = node.getAttributes().getNamedItem("onMouseClicked").getNodeValue();
			
			addTab();
            KV += "\ton_touch_down: root." + event.substring(1, event.length()) + "()\n";
            
            addWarning("-Rilevato onMouseClicked: questo metodo di input, insieme a onMousePressed, in kivy vengono tradotto con lo stesso metodo on_touch_down");
            
            methods.add(event.substring(1, event.length()));
		}
		if (node.getAttributes().getNamedItem("onKeyPressed") != null) {
			String event = node.getAttributes().getNamedItem("onKeyPressed").getNodeValue();
			
			addTab();
            KV += "\ton_key_down: root." + event.substring(1, event.length()) + "()\n";
            
            methods.add(event.substring(1, event.length()));
		}
		if (node.getAttributes().getNamedItem("onKeyReleased") != null) {
			String event = node.getAttributes().getNamedItem("onKeyReleased").getNodeValue();
			
			addTab();
            KV += "\ton_key_up: root." + event.substring(1, event.length()) + "()\n";
            
            methods.add(event.substring(1, event.length()));
		}
		if (node.getAttributes().getNamedItem("onKeyTyped") != null) {
			String event = node.getAttributes().getNamedItem("onKeyTyped").getNodeValue();
			
			addTab();
            KV += "\ton_text: root." + event.substring(1, event.length()) + "()\n";
		}
		if (node.getAttributes().getNamedItem("onScroll") != null) {
			String event = node.getAttributes().getNamedItem("onScroll").getNodeValue();
			
			addTab();
            KV += "\ton_scroll_start: root." + event.substring(1, event.length()) + "()\n";
            
            methods.add(event.substring(1, event.length()));
		}
		if (node.getAttributes().getNamedItem("onZoom") != null) {
			String event = node.getAttributes().getNamedItem("onZoom").getNodeValue();
			
			addTab();
            KV += "\ton_zoom_start: root." + event.substring(1, event.length()) + "()\n";
            
            methods.add(event.substring(1, event.length()));
		}
		if (node.getAttributes().getNamedItem("onRotate") != null) {
			String event = node.getAttributes().getNamedItem("onRotate").getNodeValue();
			
			addTab();
            KV += "\ton_rotation: root." + event.substring(1, event.length()) + "()\n";
            
            methods.add(event.substring(1, event.length()));
		}
	}
	
    //*******************************//
  	//findCorner è un metodo che viene chiamato per catturare la larghezza degli spigoli//
  	//*******************************//
    	
	private String findCorner(String style) {
		String[] corner = null;
		if(style.substring(style.indexOf(":") + 2, style.indexOf(";")).contains(" "))
			corner = style.substring(style.indexOf(":") + 2, style.indexOf(";")).split(" ");
		
		String cornerRadius = "";
		
		if(corner != null) {
			for(int i = 0; i < 4; i++) {
				if(i < corner.length) {
					if(i < 3)
						cornerRadius += corner[i] + ",";
					else
						cornerRadius += corner[i];
				}else {
					if(i < 3)
						cornerRadius += "0,";
					else
						cornerRadius += "0";
				}
			}
		}else {
			for(int i = 0; i < 4; i++) {
				if(i < 3)
					cornerRadius += style.substring(style.indexOf(":") + 1, style.indexOf(";")) + ",";
				else
					cornerRadius += style.substring(style.indexOf(":") + 1, style.indexOf(";"));
			}
		}
		
		return cornerRadius;
	}
	
    //*******************************//
  	//addTab è quel metodo chiamato prima di ogni aggiunta alla stringa KV per specificare la gerarchia del file, di fatto aggiunge alla stringa delle tabulazioni che in kivy sono importanti per la gerarchia//
  	//*******************************//
    		
	private void addTab() {
		for(int i = 0; i < tb; i++) {
			KV += "\t";
		}
	}
	
	
	private void addWarning(String warning) {
		if(warnings.isEmpty())
			warnings.add(warning);
		
		for(int i = 0; i < warnings.size(); i++) {
			if(!warnings.get(i).equals(warning))
				warnings.add(warning);
		}
	}
}
