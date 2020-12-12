package com.fmi.fengshuipdfgenerator.services;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import org.apache.commons.io.FileUtils;

import com.fmi.fengshuipdfgenerator.pdf.PdfGenerator;
import com.itextpdf.text.DocumentException;

@Path("/pdf")
public class PdfService {

	private static final String PDF_NAME = "Feng Shui Details.pdf";

	@GET
	@Path("/download")
	public Response downloadPdf() throws IOException, DocumentException, URISyntaxException {
		PdfGenerator pdfGenerator = new PdfGenerator();
		pdfGenerator.generatePdfDocument();

		File file = new File(PDF_NAME);
		byte[] pdfBytes = FileUtils.readFileToByteArray(file);

		FileUtils.writeByteArrayToFile(new File(PDF_NAME), pdfBytes);

		ResponseBuilder response = Response.status(200).entity((Object) pdfBytes);
		response.header("Content-Disposition", "attachment; filename=\"" + PDF_NAME + "\"");

		return response.build();
	}

}
