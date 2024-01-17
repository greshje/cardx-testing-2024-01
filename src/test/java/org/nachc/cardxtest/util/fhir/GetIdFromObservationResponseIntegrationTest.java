package org.nachc.cardxtest.util.fhir;

import static org.junit.Assert.assertTrue;

import java.io.File;

import org.junit.Test;

import com.nach.core.util.file.FileUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class GetIdFromObservationResponseIntegrationTest {

	@Test
	public void shouldGetId() {
		log.info("Doing observation id test...");
		log.info("Getting file");
		File file = FileUtil.getFile("./integrationtest/ObservationResponse.json");
		log.info("File exists: " + file.exists());
		String json = FileUtil.getAsString(file);
		String id = GetIdFromObservationResponse.getId(json);
		log.info("Got id: " + id);
		assertTrue("15341".equals(id));
		log.info("Done.");
	}
	
}
