package com.ADP.service.Athena.ivin;

import java.io.File;
import java.io.IOException;

import com.ADP.service.Athena.AthenaService;
import org.springframework.core.io.FileSystemResource;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class ImageService extends AthenaService {

	public Object saveImage(MultipartFile file) throws IOException {

		File tempFile = null;
		try {
			String transactionUrl = ivinUrl + "/image";
			UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(transactionUrl);
			HttpHeaders headers = createHttpHeaders();
			headers.setContentType(MediaType.MULTIPART_FORM_DATA);
			String originalFileNameAndExtension = file.getOriginalFilename();
			String tempFileName = originalFileNameAndExtension.substring(0,
					originalFileNameAndExtension.lastIndexOf("."));
			if(tempFileName.length() < 3)
				tempFileName = tempFileName + "_auto_expand";
			String tempFileExtensionPlusDot = originalFileNameAndExtension
					.substring(originalFileNameAndExtension.lastIndexOf("."));
			tempFile = File.createTempFile(tempFileName, tempFileExtensionPlusDot);
			file.transferTo(tempFile);
			MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
			HttpEntity<MultiValueMap<String, Object>> entity = new HttpEntity<>(body, headers);
			body.add("uploadfile", new FileSystemResource(tempFile));
			RestTemplate restTemplate = new RestTemplate();
			return restTemplate.postForObject(builder.toUriString(), entity, Object.class);
		} finally {
			if(tempFile != null && tempFile.exists())
			{
				tempFile.delete();
			}
		}
	}

	public Object getImage(Integer id) {
		UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(ivinUrl + "/image/" + id);
		return getRestTemplate(builder.toUriString());
	}
}
