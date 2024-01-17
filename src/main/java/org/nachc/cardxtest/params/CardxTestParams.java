package org.nachc.cardxtest.params;

import com.nach.core.util.http.HttpRequestClient;

import ca.uhn.fhir.context.FhirContext;

public class CardxTestParams {

	public static String getBaseUrl() {
		return "https://api.logicahealth.org/CardXHTNMG/open";
		// return "https://cobratestapimgt.azure-api.net/hecapreg/demo";
	}
	
	public static String getPatientUrl() {
		return getBaseUrl() + "/Patient";
	}
	
	public static String getObservationUrl() {
		return getBaseUrl() + "/Observation";
	}
	
	public static int getPatientId() {
		return 14772;
	}
	
	public static Integer[] getPatientIdList() {
		Integer[] rtn = {
			14772,
			14775
		};
		return rtn;
	}
	
	public static String setPatientId(String str) {
		String rtn = str.replace("<PATIENT_ID>", getPatientId() + "");
		return rtn;
	}
	
	public static String[] getTestStrings() {
		String[] rtn = {
			"/Observation?patient=<PATIENT_ID>&category=vital-signs&category=310858007",
			"/Observation?patient=<PATIENT_ID>&category=vital-signs&category=310858007&date=ge2024-01-01&date=le2024-01-08",
			"/Observation?patient=<PATIENT_ID>&category=vital-signs&category=310858007&code=85354-9",
			"/Observation?patient=<PATIENT_ID>&category=vital-signs&category=310858007&code=85354-9&date=ge2024-01-01&date=le2024-01-08",
			"/Observation?patient=<PATIENT_ID>&category=vital-signs&category=310858007&code=8867-4",
			"/Observation?patient=<PATIENT_ID>&category=vital-signs&category=310858007&code=8867-4&date=ge2024-01-01&date=le2024-01-08",
			"/Observation?patient=<PATIENT_ID>&category=vital-signs&category=310858007&code=96607-7",
			"/Observation?patient=<PATIENT_ID>&category=vital-signs&category=310858007&code=96607-7&date=ge2024-01-01&date=le2024-01-08"
		};
		return rtn;
	}
	
	public static String getTestFolderName() {
		return "./Test-Package-14772";
	}
	
	public static String getOauthToken() {
		return "76104a687e5e497e9466f660bdd30e7f";
	}
	
	public static HttpRequestClient getClient(String url) {
		HttpRequestClient client = new HttpRequestClient(url);
		client.addHeader("Ocp-Apim-Subscription-Key", "76104a687e5e497e9466f660bdd30e7f");
		return client;
	}
	
	public static FhirContext getFhirContext() {
		return FhirContext.forR4();
	}
	
}
