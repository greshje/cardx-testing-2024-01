package org.nachc.cardxtest.tests;

import org.nachc.cardxtest.params.CardxTestParams;

import com.nach.core.util.http.HttpRequestClient;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class T01_AllSmbp {

	public static void main(String[] args) {
		runTest();
	}
	
	public static void runTest() {
		// get the test strings and test patient ids
		Integer[] patientIdList = CardxTestParams.getPatientIdList();
		String[] testStringList = CardxTestParams.getTestStrings();
		for(Integer patientId : patientIdList) {
			log.info("---------------------------------------------------------------------------------");
			log.info("PATIENT_ID: " + patientId);
			for (String testString : testStringList) {
				doTest(testString, patientId);
			}
		}
		log.info("Done.");
	}

	private static void doTest(String testString, Integer patientId) {
		testString = CardxTestParams.setPatientId(testString);
		log.info(testString);
		// create the url
		String url = "";
		url += CardxTestParams.getBaseUrl();
		url += testString;
		log.info("URL: \n" + url);
		HttpRequestClient client = CardxTestParams.getClient(url);
		client.doGet();
		String response = client.getResponse();
		int status = client.getStatusCode();
		log.info("---");
		log.info("Status: " + status);
		log.info("---");
	}
	
	private static String getMsg(String testString) {
		String rtn = "";
		rtn += "\n\n\n";
		rtn += "\n* ---";
		rtn += "\n*";
		rtn += "\n* " + testString;
		rtn += "\n* ";
		rtn += "\n* ---";
		rtn += "\n\n\n";
		return rtn;
	}
	
}
