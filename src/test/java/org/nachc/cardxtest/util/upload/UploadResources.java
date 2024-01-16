package org.nachc.cardxtest.util.upload;

import java.io.File;
import java.util.ArrayList;

import org.nachc.cardxtest.params.CardxTestParams;

import com.nach.core.util.file.FileUtil;
import com.nach.core.util.http.HttpRequestClient;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class UploadResources {

	private static ArrayList<File> FAILS = new ArrayList<File>();
	
	private static int CNT;
	
	public static void main(String[] args) {
		doUpload();
	}
	
	private static void doUpload() {
		uploadDir("SMBP Average Observation WITHOUT References Individual SMBP Readings");
		uploadDir("SMBP Heart Rate Readings");
		uploadDir("SMBP Individual Readings");
	}
	
	private static void uploadDir(String subDirName) {
		String dirName = CardxTestParams.getTestFolderName();
		File dir = FileUtil.getFile(dirName);
		dir = new File(dir, subDirName);
		log.info("Dir: \n" + dir);
		log.info("Dir exists: " + dir.exists());
		File[] fileList = dir.listFiles();
		for(File file : fileList) {
			uploadFile(file);
		}
		log.info("NUMBER OF FAILS:    " + FAILS.size());
		for(File file : FAILS) {
			log.info(file.getName());
		}
		log.info("NUMBER OF FAILS:    " + FAILS.size());
		log.info("SUCCESSFUL UPLOADS: " + CNT);
		log.info("Done.");
	}
	
	private static void uploadFile(File file) {
		String json = FileUtil.getAsString(file);
		String url = CardxTestParams.getObservationUrl();
		log.info("URL: \n" + url);
		HttpRequestClient client = CardxTestParams.getClient(url);
		client.addHeader("Content-Type", "application/fhir+json");
		client.doPost(json);
		int status = client.getStatusCode();
		String response = client.getResponse();
		log.info("Status: " + status);
		if(status != 201) {
			FAILS.add(file);
		} else {
			CNT++;
		}
	}
	
}
