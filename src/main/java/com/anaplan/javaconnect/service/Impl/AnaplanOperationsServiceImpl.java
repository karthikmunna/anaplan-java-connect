package com.anaplan.javaconnect.service.Impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.anaplan.javaconnect.service.AnaplanOperationsService;

@Service
public class AnaplanOperationsServiceImpl implements AnaplanOperationsService {

	@Value("${plan.username}")
	private String username;

	@Value("${plan.password}")
	private String password;

	@Value("${plan.workspaceId}")
	private String workspaceId;

	@Value("${plan.modelId}")
	private String modelId;

	private static final String baseUrl = "https://api.anaplan.com/1/3/workspaces/";

	private static final Logger logger = LogManager.getLogger(AnaplanOperationsServiceImpl.class);

	@Override
	public String runExportAction(String exportId) {

		final String exportUrl = baseUrl + workspaceId + "/models/" + modelId + "/exports/" + exportId + "/tasks";
		final String chunkUrl = baseUrl + workspaceId + "/models/" + modelId + "/files/" + exportId;

		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders fileHeaders = new HttpHeaders();
		HttpHeaders exportHeaders = new HttpHeaders();
		exportHeaders.setBasicAuth(username, password);
		fileHeaders.setBasicAuth(username, password);

		exportHeaders.add("Content-Type", "application/json");

		HttpEntity<String> exportRequest = new HttpEntity<>("{\"localeName\": \"en_US\"}", exportHeaders);

		ResponseEntity<?> exportResponse = restTemplate.exchange(exportUrl, HttpMethod.POST, exportRequest,
				String.class);

		String exportData = (String) exportResponse.getBody();

		logger.info("Export has Run successfully");

		fileHeaders.add("Accept", "application/octet-stream");

		HttpEntity<String> fileRequest = new HttpEntity<>(fileHeaders);

		ResponseEntity<?> fileResponse = restTemplate.exchange(chunkUrl, HttpMethod.GET, fileRequest, String.class);

		String fileData = (String) fileResponse.getBody();

		logger.info("File Download successfully");

		return fileData;
	}

	@Override
	public String runImportAction(String importId) {

		final String importUrl = baseUrl + workspaceId + "/models/" + modelId + "/imports/" + importId + "/tasks";

		String result;

		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders importHeaders = new HttpHeaders();
		importHeaders.setBasicAuth(username, password);

		importHeaders.add("Content-Type", "application/json");

		HttpEntity<String> importRequest = new HttpEntity<>("{\"localeName\": \"en_US\"}", importHeaders);

		ResponseEntity<?> importResponse = restTemplate.exchange(importUrl, HttpMethod.POST, importRequest,
				String.class);

		String importData = (String) importResponse.getBody();

		if (importResponse.getStatusCodeValue() == 200)
			result = "Success";
		else
			result = "Failure";

		return result;
	}

	@Override
	public String runProcess(String processId) {

		final String processUrl = baseUrl + workspaceId + "/models/" + modelId + "/processes/" + processId + "/tasks";

		String result;

		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders processHeaders = new HttpHeaders();
		processHeaders.setBasicAuth(username, password);
		processHeaders.add("Content-Type", "application/json");

		HttpEntity<String> processRequest = new HttpEntity<>("{\"localeName\": \"en_US\"}", processHeaders);

		ResponseEntity<?> processResponse = restTemplate.exchange(processUrl, HttpMethod.POST, processRequest,
				String.class);

		String processResult = (String) processResponse.getBody();

		logger.info("Process Run Successfully");
		
		if (processResponse.getStatusCodeValue() == 200)
			result = "Success";
		else
			result = "Failure";

		return result;

	}

	@Override
	public String uploadFile(String fileId, String fileStream) {

		final String fileUploadUrl = baseUrl + workspaceId + "/models/" + modelId + "/files/" + fileId;

		String result;

		logger.info(fileUploadUrl);

		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders fileHeaders = new HttpHeaders();
		fileHeaders.setBasicAuth(username, password);
		fileHeaders.add("Content-Type", "application/octet-stream");

		HttpEntity<String> fileRequest = new HttpEntity<>(fileStream, fileHeaders);

		ResponseEntity<?> fileResponse = restTemplate.exchange(fileUploadUrl, HttpMethod.PUT, fileRequest,
				String.class);

		if (fileResponse.getStatusCodeValue() == 204)
			result = "Success";
		else
			result = "Failure";

		return result;
	}

}
