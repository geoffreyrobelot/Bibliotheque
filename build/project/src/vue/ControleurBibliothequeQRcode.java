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
	 * r�f�rence sur l'objet de type Livre m�thode qui permet de r�cup�rer les
	 * champs champsauteur et champslivre ici r�cup�re le pr�nom, le nom et la
	 * nationalit� de l'auteur ici r�cup�re le titre et l'ann�e du livre de l'auteur
	 * 
	 * @param livre1
	 */

	public void setLivre(Livre livre1) {
		this.livre = livre1;
		if (livre1 != null) {
			// r�cup�re le nom, prenom et nationalit� de l'auteur du livre
			champsauteur.setText(livre.getAuteur().getPrenom() + " " + livre.getAuteur().getNom() + " / "
					+ livre.getAuteur().getNationalite());
			champstitre.setText(livre.getTitre() + " / " + livre.getAnnee());
			champstexte.setText("");
			try {
				String filepath;
				String imageFormat = "png";
				filepath = "C:\\Users\\Public\\Documents\\ProjetBibliJpa\\images\\" + livre.getIsbn() + "."
						+ imageFormat;
				// cr�e un objet File avec filepath comme argument
				File file = new File(filepath);
				// si le chemin d'acc�s existe
				if (file.exists()) {
					// le qrcode pr�cedement cr�e est charg� dans l'imageview
					imageqrcode.setImage(new Image(new FileInputStream(file)));
					// remplis le champs texte du formulaire
					champstexte.setText("QRCode d�j� cr�e");

				} else {
					champstexte.setText("Aucun QRCode pour cette oeuvre");
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}

		}

	}

	/*
	 * m�thode qui permet de g�n�rer le QRCode
	 */

	@SuppressWarnings("unchecked")
	@FXML
	private void genererQRCode() throws WriterException, IOException, NotFoundException {
		// d�claration des variables
		String qrCodeData = affichetexte.getText();
		String imageFormat = "png";
		String filePath = "C:\\Users\\Public\\Documents\\ProjetBibliJpa\\images\\" + livre.getIsbn() + "."
				+ imageFormat;
		String charset = "UTF-8";

		@SuppressWarnings("rawtypes")
		Map hintMap = new HashMap();
		hintMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
		// appelle de la m�thode createQRCode avec les param�tres d�finis lors de la
		// cr�ation de cette m�thode
		createQRCode(qrCodeData, filePath, charset, hintMap, 200, 200);

		System.out.println("QRCode image cr�e avec succ�s");
		System.out.println("Contenu du QRCode :" + readQRCode(filePath, charset, hintMap));
		// affecte le QRCode cr�e � l'imageview
		imageqrcode.setImage(new Image(new FileInputStream(filePath)));

	}

	/*
	 * m�thode qui permet de cr�er le QRCode
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
		// cr�ation d'un nouvel objet de type File 
		File file = new File(filePath);
		if (file.exists()) {
			// cr�ation d'un nouvel objet de type Image 
			// r�cup�re la destination du fichier � imprimer 
			Image image = new Image("file:" + filePath);
			// imprime l'image r�cup�r�e
			printImage(image);
		}
	}

	/*
	 * m�thode qui ouvre une fen�tre pour imprimer l'image
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
