package org.nachc.cardxtest.util.fhir;

import java.io.File;

import org.hl7.fhir.r4.model.Observation;
import org.junit.Test;

import com.nach.core.util.file.FileUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FhirJsonParserIntegrationTest {

	@Test
	public void shouldCreateJavaObject() {
		File file = FileUtil.getFile("./Test-Package-14772/SMBP Average Observation WITHOUT References Individual SMBP Readings/Average-SMBP-WITHOUT.json");
		log.info("File exists: " + file.exists());
		String json = FileUtil.getAsString(file);
		Observation obs = FhirJsonParser.parse(json, Observation.class);
		log.info("Done.");
	}

}
