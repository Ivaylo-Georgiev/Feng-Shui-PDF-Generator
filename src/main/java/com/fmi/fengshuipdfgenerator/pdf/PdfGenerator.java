package com.fmi.fengshuipdfgenerator.pdf;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.fmi.fengshuipdfgenerator.FengShuiDetails;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Rectangle;

public class PdfGenerator {

	private static final String PDF_NAME = "Feng Shui Details.pdf";

	private static final int IMAGE_SCALE = 20;
	private static final int PAGE_BORDER_WIDTH = 10;

	private static final Font TITLE_FONT = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 22, BaseColor.RED);
	private static final Font KEY_FONT = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12, BaseColor.BLACK);
	private static final Font VALUE_FONT = FontFactory.getFont(FontFactory.HELVETICA, 12, BaseColor.BLACK);
	private static final Paragraph EMPTY_PARAGRAPH = new Paragraph(new Chunk(" ", TITLE_FONT));

	private FengShuiDetails fengShuiDetails;

	public PdfGenerator(FengShuiDetails fengShuiDetails) {
		this.fengShuiDetails = fengShuiDetails;
	}

	public void generatePdfDocument() throws DocumentException, MalformedURLException, URISyntaxException, IOException {
		Document document = new Document();
		PdfWriter.getInstance(document, new FileOutputStream(PDF_NAME));

		document.open();
		setPageFrame(document);
		setTitle(document);
		setChineseYearSign(document, 1998);
		setImage(document, "src\\main\\resources\\tiger.png");
		setChineseHourSign(document, 5);
		setImage(document, "src\\main\\resources\\rabbit.png");
		setAstrologyAllies(document);
		setImage(document, "src\\main\\resources\\horse.png");
		setImage(document, "src\\main\\\\resources\\dog.png");
		setSecretFriend(document);
		setImage(document, "src\\main\\resources\\pig.png");
		setAstrologyEnemy(document);
		setImage(document, "src\\main\\resources\\monkey.png");
		setPeachBlossomAnimal(document);
		setImage(document, "src\\main\\resources\\rabbit.png");
		setKuaNumber(document, "MALE");
		setFourBestDirections(document);
		setFourWorstDirections(document);
		document.close();
	}

	private void setChineseYearSign(Document document, int year) throws DocumentException {
		String keyStr = "Chinese year sign for " + year + ": ";
		String valueStr = fengShuiDetails.getChineseYearSign();
		setKeyValuePair(document, keyStr, valueStr);
	}

	private void setChineseHourSign(Document document, int hour) throws DocumentException {
		String keyStr = "Chinese hour sign for " + hour + ": ";
		String valueStr = fengShuiDetails.getChineseHourSign();
		setKeyValuePair(document, keyStr, valueStr);
	}

	private void setKuaNumber(Document document, String gender) throws DocumentException {
		String keyStr = "KUA number for " + gender + ": ";
		String valueStr = String.valueOf(fengShuiDetails.getKuaNumber());
		setKeyValuePair(document, keyStr, valueStr);
		document.add(EMPTY_PARAGRAPH);
	}

	private void setAstrologyAllies(Document document) throws DocumentException {
		String keyStr = "Astrology allies: ";
		String valueStr = fengShuiDetails.getAstrologyAllies().replace(",", ", ");
		setKeyValuePair(document, keyStr, valueStr);
	}

	private void setSecretFriend(Document document) throws DocumentException {
		String keyStr = "Secret friend: ";
		String valueStr = fengShuiDetails.getSecretFriend();
		setKeyValuePair(document, keyStr, valueStr);
	}

	private void setAstrologyEnemy(Document document) throws DocumentException {
		String keyStr = "Astrology enemy: ";
		String valueStr = fengShuiDetails.getAstrologyEnemy();
		setKeyValuePair(document, keyStr, valueStr);
	}

	private void setPeachBlossomAnimal(Document document) throws DocumentException {
		String keyStr = "Peach blossom animal: ";
		String valueStr = fengShuiDetails.getPeachBlossomAnimal();
		setKeyValuePair(document, keyStr, valueStr);
	}

	private void setFourBestDirections(Document document) throws DocumentException {
		String keyStr = "Four best directions: ";
		String valueStr = fengShuiDetails.getFourBestDirections().replace("_", "-").replace(",", ", ");
		setKeyValuePair(document, keyStr, valueStr);
		document.add(EMPTY_PARAGRAPH);
	}

	private void setFourWorstDirections(Document document) throws DocumentException {
		String keyStr = "Four worst directions: ";
		String valueStr = fengShuiDetails.getFourWorstDirections().replace("_", "-").replace(",", ", ");
		setKeyValuePair(document, keyStr, valueStr);
		document.add(EMPTY_PARAGRAPH);
	}

	private void setPageFrame(Document document) throws DocumentException {
		Rectangle rect = new Rectangle(document.getPageSize());
		rect.setBorder(Rectangle.BOX);
		rect.setBorderColor(BaseColor.RED);
		rect.setBorderWidth(PAGE_BORDER_WIDTH);
		document.add(rect);
	}

	private void setTitle(Document document) throws DocumentException {
		Chunk text = new Chunk("Feng Shui Details");
		text.setFont(TITLE_FONT);

		Paragraph title = new Paragraph(text);
		title.setAlignment(Paragraph.ALIGN_CENTER);

		document.add(title);
		document.add(EMPTY_PARAGRAPH);
	}

	private void setKeyValuePair(Document document, String keyStr, String valueStr) throws DocumentException {
		Chunk key = new Chunk(keyStr);
		key.setFont(KEY_FONT);

		Chunk value = new Chunk(valueStr);
		value.setFont(VALUE_FONT);

		Paragraph keyParagraph = new Paragraph();
		keyParagraph.add(key);
		keyParagraph.setAlignment(Paragraph.ALIGN_CENTER);
		document.add(keyParagraph);

		Paragraph valueParagraph = new Paragraph();
		valueParagraph.add(value);
		valueParagraph.setAlignment(Paragraph.ALIGN_CENTER);
		document.add(valueParagraph);
	}

	private void setImage(Document document, String imageName)
			throws URISyntaxException, MalformedURLException, IOException, DocumentException {
		Path path = Paths.get(imageName);
		Image img = Image.getInstance(path.toAbsolutePath().toString());
		img.scalePercent(IMAGE_SCALE);
		img.setAlignment(Image.ALIGN_CENTER);
		document.add(img);
	}

}
