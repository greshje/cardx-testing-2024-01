package org.nachc.cardxtest.tests;

import org.junit.Test;
import org.nachc.cardxtest.params.CardxTestParams;

import com.nach.core.util.http.HttpRequestClient;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class T01_AllSmbp {

	@Test
	public void runTest() {
		// create the test string
		String[] testStringList = CardxTestParams.getTestStrings();
		for (String testString : testStringList) {
			testString = CardxTestParams.setPatientId(testString);
			log.info(getMsg(testString));
			// create the url
			String url = "";
			url += CardxTestParams.getBaseUrl();
			url += testString;
			log.info("URL: \n" + url);
			HttpRequestClient client = new HttpRequestClient(url);
			client.doGet();
			String response = client.getResponse();
			log.info("Response: \n" + response);
		}
		log.info("Done.");
	}

	private String getMsg(String testString) {
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
