package org.nachc.cardxtest.tests;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

import org.nachc.cardxtest.params.CardxTestParams;
import org.nachc.cardxtest.util.fhir.GetIdFromObservationResponse;

import com.nach.core.util.file.FileUtil;
import com.nach.core.util.http.HttpRequestClient;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class GetUploadedObservations {

	private static final String FILE_NAME = "./output/obsId-2024-01-16.txt";
	
	private static final Integer SUCCESS_COUNT = 57;
	
	private static final ArrayList<String> SUCCESS = new ArrayList<String>();

	private static final ArrayList<String> FAIL = new ArrayList<String>();

	public static void main(String[] args) {
		getUploadedObservations();
	}
	
	public static void getUploadedObservations() {
		ArrayList<String> obsIdList = getObsIdList();
		for(String obsId : obsIdList) {
			try {
				obsId = obsId.trim();
				log.info("----------------------------------------");
				log.info("Getting: " + obsId);
				String url = CardxTestParams.getObservationUrl();
				url += "/" + obsId;
				log.info("Url: \n" + url);
				HttpRequestClient client = new HttpRequestClient(url);
				client.doGet();
				String response = client.getResponse();
				String responseObsId = GetIdFromObservationResponse.getId(response);
				log.info("Response ID: " + responseObsId);
				if(obsId.equals(responseObsId)) {
					SUCCESS.add(obsId);
				} else {
					FAIL.add(obsId);
				}
			} catch(Exception exp) {
				FAIL.add(obsId);
			}
		}
		log.info("EXPECTED: " + SUCCESS_COUNT);
		log.info("SUCCESS:  " + SUCCESS.size());
		log.info("FAIL:     " + FAIL.size());
		log.info("Done.");
	}
	
	public static ArrayList<String> getObsIdList() {
		File file = FileUtil.getFile(FILE_NAME);
		String obsIdList = FileUtil.getAsString(file);
		String[] obsIdArray = obsIdList.split(",");
		ArrayList<String> rtn = new ArrayList<>(Arrays.asList(obsIdArray));
		return rtn;
	}
}
