package com.enjoytrip.hotplace.controller;

import java.io.File;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/file")
public class FileDownloadController {

	private static final Logger logger = LoggerFactory.getLogger(FileDownloadController.class);

	@Value("${file.path}")
	private String uploadPath;

	@GetMapping("/download/{sfolder}/{ofile}/{sfile}")
	public ResponseEntity<Object> download(@PathVariable("sfolder") String sfolder, @PathVariable("ofile") String ofile,
			@PathVariable("sfile") String sfile) {
		logger.debug("download file info sfolder : {}, ofile : {}, sfile : {}", sfolder, ofile, sfile);
		String file = uploadPath + File.separator + sfolder + File.separator + sfile;

		try {
			Path filePath = Paths.get(file);
			Resource resource = new InputStreamResource(Files.newInputStream(filePath)); // 파일 resource 얻기

			HttpHeaders headers = new HttpHeaders();
			headers.setContentDisposition(ContentDisposition.builder("attachment").filename(URLEncoder.encode(ofile, "UTF-8").replaceAll("\\+", "%20")).build());
			headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);

			return new ResponseEntity<Object>(resource, headers, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<Object>(null, HttpStatus.CONFLICT);
		}
	}

}
 