package com.fmi.fengshuipdfgenerator.services;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.util.concurrent.TimeoutException;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import org.apache.commons.io.FileUtils;

import com.fmi.fengshuipdfgenerator.enums.Gender;
import com.fmi.fengshuipdfgenerator.pdf.PdfGenerator;
import com.fmi.fengshuipdfgenerator.pojo.FengShuiDetails;
import com.fmi.fengshuipdfgenerator.pojo.RPCParameters;
import com.fmi.fengshuipdfgenerator.rpc.RPCCLientSingleton;
import com.google.gson.Gson;
import com.itextpdf.text.DocumentException;

@Path("/pdf")
public class PdfService {

	private static final String PDF_NAME = "Feng Shui Details.pdf";

	@GET
	@Path("/download/{year}/{hour}/{gender}")
	public Response downloadPdf(@PathParam("year") int year, @PathParam("hour") int hour,
			@PathParam("gender") Gender gender)
			throws IOException, DocumentException, URISyntaxException, InterruptedException, TimeoutException {
		try {
			FengShuiDetails fengShuiDetails = getFengShuiDetails(year, hour, gender);
			PdfGenerator pdfGenerator = new PdfGenerator(fengShuiDetails, year, hour, gender);
			pdfGenerator.generatePdfDocument();

			File file = new File(PDF_NAME);
			byte[] pdfBytes = FileUtils.readFileToByteArray(file);

			FileUtils.writeByteArrayToFile(new File(PDF_NAME), pdfBytes);

			ResponseBuilder response = Response.status(Response.Status.OK).entity((Object) pdfBytes);
			response.header("Content-Disposition", "attachment; filename=\"" + PDF_NAME + "\"");
			
			//allow UI to connect to Pdf Service
			response.header("Access-Control-Allow-Origin", "http://13.59.137.69:3000");

			return response.build();
		} catch (IOException | TimeoutException | InterruptedException e) {
			return Response.status(Response.Status.NOT_FOUND)
					.entity("Could not load feng shui details: remote connection error").build();
		} finally {
			Files.deleteIfExists(new File(PDF_NAME).toPath());
		}
	}

	private FengShuiDetails getFengShuiDetails(int year, int hour, Gender gender)
			throws IOException, TimeoutException, InterruptedException {
		String responseContent = null;
		RPCCLientSingleton rpcClient = RPCCLientSingleton.getInstance();
		RPCParameters params = new RPCParameters();
		params.setYear(year);
		params.setHour(hour);
		params.setGender(gender);

		Gson gson = new Gson();
		responseContent = rpcClient.call(gson.toJson(params));

		return gson.fromJson(responseContent, FengShuiDetails.class);
	}

}
