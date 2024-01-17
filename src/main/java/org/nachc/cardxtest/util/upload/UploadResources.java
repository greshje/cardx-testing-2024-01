package org.nachc.cardxtest.util.upload;

import java.io.File;
import java.util.ArrayList;

import org.nachc.cardxtest.params.CardxTestParams;
import org.nachc.cardxtest.util.fhir.GetIdFromObservationResponse;

import com.nach.core.util.file.FileUtil;
import com.nach.core.util.http.HttpRequestClient;
import com.nach.core.util.json.JsonUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class UploadResources {

	private static ArrayList<File> FAILS = new ArrayList<File>();
	
	private static final ArrayList<String> OBS_ID_LIST = new ArrayList<String>();
	
	private static final String OUTPUT_FILE_DIR = "src/main/resources/output";
	
	private static final String OUTPUT_FILE_NAME = "obsId.txt";
	
	public static void main(String[] args) {
		doUpload();
	}
	
	private static void doUpload() {
		uploadDir("SMBP Average Observation WITHOUT References Individual SMBP Readings");
		uploadDir("SMBP Heart Rate Readings");
		uploadDir("SMBP Individual Readings");
		writeIdFile();
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
		log.info("SUCCESSFUL UPLOADS: " + OBS_ID_LIST.size());
		log.info("Done.");
	}
	
	private static void uploadFile(File file) {
		String json = FileUtil.getAsString(file);
		json = JsonUtil.prettyPrint(json);
		String url = CardxTestParams.getObservationUrl();
		log.info("URL: \n" + url);
		HttpRequestClient client = CardxTestParams.getClient(url);
		client.addHeader("Content-Type", "application/fhir+json");
		client.doPost(json);
		int status = client.getStatusCode();
		String response = client.getResponse();
		log.info("---");
		log.info("File: " + file.getName());
		log.info("Status: " + status);
		log.info("---");
		log.info("Request: \n" + json);
		log.info("Response: \n" + response);
		log.info("---");
		log.info("File: " + file.getName());
		log.info("Status: " + status);
		log.info("---");
		if(status != 201) {
			FAILS.add(file);
		} else {
			String obsId = GetIdFromObservationResponse.getId(response);
			if(obsId != null) {
				OBS_ID_LIST.add(obsId);
			}
		}
	}
	
	private static void writeIdFile() {
		try {
			log.info("Writing output file...");
			String idList = getIdList();
			File dir = FileUtil.getFromProjectRoot(OUTPUT_FILE_DIR);
			FileUtil.mkdirs(dir);
			File file = new File(dir, OUTPUT_FILE_NAME);
			log.info("dir exists:  " + dir.exists());
			log.info("file exists: " + file.exists());
			if(file.exists() == true) {
				file.delete();
			}
			file.createNewFile();
			FileUtil.write(idList, file);
			log.info("Done creating output file.");
		} catch(Exception exp) {
			throw new RuntimeException(exp);
		}
	}
	
	private static String getIdList() {
		String rtn = "";
		for(String str : OBS_ID_LIST) {
			if("".equals(rtn) == false) {
				rtn += ",";
			}
			rtn += str;
		}
		return rtn;
	}
}
