package com.fmi.fengshuipdfgenerator.services;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import org.apache.commons.io.FileUtils;

import com.fmi.fengshuipdfgenerator.FengShuiDetails;
import com.fmi.fengshuipdfgenerator.pdf.PdfGenerator;
import com.google.gson.Gson;
import com.itextpdf.text.DocumentException;

@Path("/pdf")
public class PdfService {

	private static final String PDF_NAME = "Feng Shui Details.pdf";
	private static final String JSON_MOCK = "{\r\n" + "	\"chineseYearSign\": \"TIGER\",\r\n"
			+ "	\"chineseHourSign\": \"RABBIT\",\r\n" + "	\"astrologyAllies\": \"HORSE,DOG\",\r\n"
			+ "	\"secretFriend\": \"PIG\",\r\n" + "	\"astrologyEnemy\": \"MONKEY\",\r\n"
			+ "	\"peachBlossomAnimal\": \"RABBIT\",\r\n" + "	\"kuaNumber\": 3,\r\n"
			+ "	\"fourBestDirections\": \"NORTH,EAST,SOUTH,SOUTH_EAST\",\r\n"
			+ "	\"fourWorstDirections\": \"WEST,NORTH_EAST,NORTH_WEST,SOUTH_WEST\"\r\n" + "}";

	@GET
	@Path("/download")
	public Response downloadPdf() throws IOException, DocumentException, URISyntaxException {
		PdfGenerator pdfGenerator = new PdfGenerator(new Gson().fromJson(JSON_MOCK, FengShuiDetails.class));
		pdfGenerator.generatePdfDocument();

		File file = new File(PDF_NAME);
		byte[] pdfBytes = FileUtils.readFileToByteArray(file);

		FileUtils.writeByteArrayToFile(new File(PDF_NAME), pdfBytes);

		ResponseBuilder response = Response.status(200).entity((Object) pdfBytes);
		response.header("Content-Disposition", "attachment; filename=\"" + PDF_NAME + "\"");

		return response.build();
	}

}
