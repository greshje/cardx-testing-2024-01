package org.nachc.cardxtest.util.fhir;

import org.hl7.fhir.r4.model.Observation;

import com.ibm.icu.util.StringTokenizer;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class GetIdFromObservationResponse {

	public static String getId(String responseJson) {
		try {
			log.info("Getting id for observation...");
			Observation obs = FhirJsonParser.parse(responseJson, Observation.class);
			String id = obs.getId();
			StringTokenizer toke = new StringTokenizer(id, "/");
			String rtn = null;
			rtn = toke.nextToken();
			rtn = toke.nextToken();
			log.info("Observation: " + obs);
			log.info("Got id");
			return rtn;
		} catch(Exception exp) {
			return null;
		}
	}
	
}
