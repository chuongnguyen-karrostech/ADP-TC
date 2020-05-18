package com.ADP.service.Athena.ivin;

import com.ADP.service.Athena.AthenaService;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;


@Service
public class ZoneService extends AthenaService {

    public Object getZone() {
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(ivinUrl + "/zone");
        return getRestTemplate(builder.toUriString());
    }

    public Object getType() {
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(ivinUrl + "/type");
        return getRestTemplate(builder.toUriString());
    }
}
