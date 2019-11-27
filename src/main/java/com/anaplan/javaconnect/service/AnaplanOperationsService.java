package com.anaplan.javaconnect.service;

public interface AnaplanOperationsService {
	
    String runExportAction(String exportId);
	
	String runImportAction(String importId);
	
	String runProcess(String processId);
	
	String uploadFile(String fileId,String fileData);
	

}
