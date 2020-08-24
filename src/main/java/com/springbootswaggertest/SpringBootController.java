package com.springbootswaggertest;

import java.io.IOException;
import java.io.InputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/visaApplication")
public class SpringBootController {
	
	@Autowired
    ResourceLoader resourceLoader;
	
	@GetMapping(value = "/getImage/{dmsId}/{locale}", produces = {MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE, MediaType.APPLICATION_PDF_VALUE})
	public ResponseEntity<Object> getByteDataByDmsId(@PathVariable("locale") String locale,
			@PathVariable("dmsId") String dmsId) throws IOException {
		Resource resource = null;
		InputStream inputStream = null;
		HttpHeaders headers = new HttpHeaders();
		if ("image".equalsIgnoreCase(dmsId)) {
			resource = resourceLoader.getResource("classpath:test.jpg");
	        inputStream = resource.getInputStream();
	        headers.setContentDispositionFormData("Content-disposition", "test.jpg");
		} else if ("pdf".equalsIgnoreCase(dmsId)) {
			resource = resourceLoader.getResource("classpath:10000458256.pdf");
	        inputStream = resource.getInputStream();
	        headers.setContentDispositionFormData("Content-disposition", "10000458256.pdf");
		}
		byte[] bdata = FileCopyUtils.copyToByteArray(inputStream);
		return new ResponseEntity<>(bdata, headers, HttpStatus.OK);
	}

}
