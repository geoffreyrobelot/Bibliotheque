package vue;

import java.awt.print.PrinterException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import javafx.fxml.FXML;
import javafx.print.PrinterJob;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import modele.Livre;

public class ControleurBibliothequeQRcode {

	// constructeur sans arguments

	public ControleurBibliothequeQRcode() {

	}

	@FXML
	private Label champsauteur;

	@FXML
	private Label champstitre;

	@FXML
	private Label champstexte;

	@FXML
	private TextArea affichetexte;

	@FXML
	private ImageView imageqrcode;

	@FXML
	private Button imprimer;

	private Livre livre;

	@FXML
	private void initialize() {

	}

	/*
	 * référence sur l'objet de type Livre méthode qui permet de récupérer les
	 * champs champsauteur et champslivre ici récupère le prénom, le nom et la
	 * nationalité de l'auteur ici récupère le titre et l'année du livre de l'auteur
	 * 
	 * @param livre1
	 */

	public void setLivre(Livre livre1) {
		this.livre = livre1;
		if (livre1 != null) {
			// récupère le nom, prenom et nationalité de l'auteur du livre
			champsauteur.setText(livre.getAuteur().getPrenom() + " " + livre.getAuteur().getNom() + " / "
					+ livre.getAuteur().getNationalite());
			champstitre.setText(livre.getTitre() + " / " + livre.getAnnee());
			champstexte.setText("");
			try {
				String filepath;
				String imageFormat = "png";
				filepath = "C:\\Users\\Public\\Documents\\ProjetBibliJpa\\images\\" + livre.getIsbn() + "."
						+ imageFormat;
				// crée un objet File avec filepath comme argument
				File file = new File(filepath);
				// si le chemin d'accès existe
				if (file.exists()) {
					// le qrcode précedement crée est chargé dans l'imageview
					imageqrcode.setImage(new Image(new FileInputStream(file)));
					// remplis le champs texte du formulaire
					champstexte.setText("QRCode déjà crée");

				} else {
					champstexte.setText("Aucun QRCode pour cette oeuvre");
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}

		}

	}

	/*
	 * méthode qui permet de générer le QRCode
	 */

	@SuppressWarnings("unchecked")
	@FXML
	private void genererQRCode() throws WriterException, IOException, NotFoundException {
		// déclaration des variables
		String qrCodeData = affichetexte.getText();
		String imageFormat = "png";
		String filePath = "C:\\Users\\Public\\Documents\\ProjetBibliJpa\\images\\" + livre.getIsbn() + "."
				+ imageFormat;
		String charset = "UTF-8";

		@SuppressWarnings("rawtypes")
		Map hintMap = new HashMap();
		hintMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
		// appelle de la méthode createQRCode avec les paramètres définis lors de la
		// création de cette méthode
		createQRCode(qrCodeData, filePath, charset, hintMap, 200, 200);

		System.out.println("QRCode image crée avec succès");
		System.out.println("Contenu du QRCode :" + readQRCode(filePath, charset, hintMap));
		// affecte le QRCode crée à l'imageview
		imageqrcode.setImage(new Image(new FileInputStream(filePath)));

	}

	/*
	 * méthode qui permet de créer le QRCode
	 * 
	 * @param qrCodeData, filePath, charset, hintMap, qrCodeheight, qrCodeWidth
	 */
	@SuppressWarnings({ "deprecation", "unchecked", "rawtypes" })
	private void createQRCode(String qrCodeData, String filePath, String charset, Map hintMap, int qrCodeheight,
			int qrCodeWidth) throws WriterException, IOException {

		BitMatrix matrix = new MultiFormatWriter().encode(new String(qrCodeData.getBytes(charset), charset),
				BarcodeFormat.QR_CODE, qrCodeWidth, qrCodeheight, hintMap);
		MatrixToImageWriter.writeToFile(matrix, filePath.substring(filePath.lastIndexOf('.') + 1), new File(filePath));
	}

	@SuppressWarnings("rawtypes")
	private String readQRCode(String filePath, String charset, Map hintMap)
			throws FileNotFoundException, IOException, NotFoundException {
		
		BinaryBitmap binaryBitmap = new BinaryBitmap(
				new HybridBinarizer(new BufferedImageLuminanceSource(ImageIO.read(new FileInputStream(filePath)))));
		@SuppressWarnings("unchecked")
		Result qrCodeResult = new MultiFormatReader().decode(binaryBitmap, hintMap);
		return qrCodeResult.getText();
	}

	@FXML
	private void imprimerQRCode() throws FileNotFoundException, PrinterException {
		String imageFormat = "png";
		String filePath = "C:\\Users\\Public\\Documents\\ProjetBibliJpa\\images\\" + livre.getIsbn() + "."
				+ imageFormat;
		// création d'un nouvel objet de type File 
		File file = new File(filePath);
		if (file.exists()) {
			// création d'un nouvel objet de type Image 
			// récupère la destination du fichier à imprimer 
			Image image = new Image("file:" + filePath);
			// imprime l'image récupérée
			printImage(image);
		}
	}

	/*
	 * méthode qui ouvre une fenêtre pour imprimer l'image
	 * 
	 * @param image
	 */

	private void printImage(Image image) {
		PrinterJob printerJob = PrinterJob.createPrinterJob();
		if (printerJob != null && printerJob.showPrintDialog(imageqrcode.getScene().getWindow())) {
			Pane pane = new Pane();
			ImageView imageView = new ImageView(image);
			pane.getChildren().add(imageView);
			boolean valide = printerJob.printPage(imageView);
			if (valide) {
				printerJob.endJob();
			}
		}

	}

}
