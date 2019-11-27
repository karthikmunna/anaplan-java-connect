package com.anaplan.javaconnect.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.anaplan.javaconnect.service.AnaplanOperationsService;



@RestController
public class AnaplanOperationsController {

private static final Logger LOGGER = LogManager.getLogger(AnaplanOperationsController.class);
	
	

	private AnaplanOperationsService anaplanOperationsService;

	public AnaplanOperationsController(AnaplanOperationsService anaplanOperationsService) {
		this.anaplanOperationsService = anaplanOperationsService;
	}
	
	@PostMapping("/export/{exportId}")
	public String runExportAction(@PathVariable String exportId){
		
		return anaplanOperationsService.runExportAction(exportId);
	}
	
	@PostMapping("/import/{importId}")
	public String runImportAction(@PathVariable String importId){
		
		return anaplanOperationsService.runImportAction(importId);
	}
	
	@PostMapping("/process/{processId}")
	public String runProcess(@PathVariable String processId){
		
		return anaplanOperationsService.runProcess(processId);
	
	}
	
	@PutMapping("/upload-file/{fileId}")
	public String uploadFile(@PathVariable String fileId,@RequestBody String fileData){
		
		return anaplanOperationsService.uploadFile(fileId,fileData);

	}
	
}
