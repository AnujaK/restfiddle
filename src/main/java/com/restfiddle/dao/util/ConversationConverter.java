package com.restfiddle.dao.util;

import java.util.ArrayList;
import java.util.List;

import com.restfiddle.dto.AssertionDTO;
import com.restfiddle.dto.BodyAssertDTO;
import com.restfiddle.dto.ConversationDTO;
import com.restfiddle.dto.FormDataDTO;
import com.restfiddle.dto.RfHeaderDTO;
import com.restfiddle.dto.RfRequestDTO;
import com.restfiddle.dto.RfResponseDTO;
import com.restfiddle.dto.UrlParamDTO;
import com.restfiddle.entity.Assertion;
import com.restfiddle.entity.BasicAuth;
import com.restfiddle.entity.BodyAssert;
import com.restfiddle.entity.Conversation;
import com.restfiddle.entity.DigestAuth;
import com.restfiddle.entity.FormParam;
import com.restfiddle.entity.RfHeader;
import com.restfiddle.entity.RfRequest;
import com.restfiddle.entity.RfResponse;
import com.restfiddle.entity.UrlParam;

//TODO : Need to use Spring Object Mapping : http://docs.spring.io/spring/previews/mapping.html
public class ConversationConverter {

    public static Conversation convertToEntity(RfRequestDTO rfRequestDTO, RfResponseDTO responseDTO) {
	Conversation conversation = new Conversation();

	RfRequest rfRequest = new RfRequest();
	if (rfRequestDTO != null) {
	    rfRequest.setApiUrl(rfRequestDTO.getApiUrl());
	    rfRequest.setMethodType(rfRequestDTO.getMethodType());

	    List<FormDataDTO> formDataDTOs = rfRequestDTO.getFormParams();
	    List<FormParam> formParams = new ArrayList<FormParam>();
	    
	    if(rfRequestDTO.getBasicAuthDTO() != null){
	    	BasicAuth basicAuth = new BasicAuth();
	    	basicAuth.setUsername(rfRequestDTO.getBasicAuthDTO().getUsername());
	    	basicAuth.setPassword(rfRequestDTO.getBasicAuthDTO().getPassword());
	    	rfRequest.setBasicAuth(basicAuth);
	    }
	    
	    if(rfRequestDTO.getDigestAuthDTO() != null){
	    	DigestAuth digestAuth = new DigestAuth();
	    	digestAuth.setUsername(rfRequestDTO.getDigestAuthDTO().getUsername());
	    	digestAuth.setPassword(rfRequestDTO.getDigestAuthDTO().getPassword());
	    	rfRequest.setDigestAuth(digestAuth);
	    }

	    if (rfRequestDTO.getApiBody() != null) {
		rfRequest.setApiBody(rfRequestDTO.getApiBody());
	    } else if (formDataDTOs != null && !formDataDTOs.isEmpty()) {
		FormParam formParam = null;
		for (FormDataDTO formDataDTO : formDataDTOs) {
		    formParam = new FormParam();
		    formParam.setParamKey(formDataDTO.getKey());
		    formParam.setParamValue(formDataDTO.getValue());
		    formParams.add(formParam);
		}
		rfRequest.setFormParams(formParams);
	    }

	    List<UrlParamDTO> urlParamDTOs = rfRequestDTO.getUrlParams();
	    List<UrlParam> urlParams = new ArrayList<UrlParam>();

	    if (urlParamDTOs != null && !urlParamDTOs.isEmpty()) {
		UrlParam urlParam = null;
		for (UrlParamDTO urlParamDTO : urlParamDTOs) {
		    urlParam = new UrlParam();
		    urlParam.setParamKey(urlParamDTO.getKey());
		    urlParam.setParamValue(urlParamDTO.getValue());
		    urlParams.add(urlParam);
		}
		rfRequest.setUrlParams(urlParams);
	    }

	    List<RfHeaderDTO> headerDTOs = rfRequestDTO.getHeaders();
	    List<RfHeader> headers = new ArrayList<RfHeader>();
	    RfHeader header = null;
	    if (headerDTOs != null && !headerDTOs.isEmpty()) {
		for (RfHeaderDTO rfHeaderDTO : headerDTOs) {
		    header = new RfHeader();
		    header.setHeaderName(rfHeaderDTO.getHeaderName());
		    header.setHeaderValue(rfHeaderDTO.getHeaderValue());
		    headers.add(header);
		}
		rfRequest.setRfHeaders(headers);
	    }
	}
	conversation.setRfRequest(rfRequest);

	// TODO : We should have the option to configure whether to save response or not.
	RfResponse response = new RfResponse();
	conversation.setRfResponse(response);

	if(responseDTO == null && !rfRequestDTO.getApiUrl().isEmpty()){
		response.setBody("Could not connect to "+rfRequestDTO.getApiUrl());
	} else {
	    if (responseDTO.getBody() != null && !responseDTO.getBody().isEmpty()) {
		response.setBody(responseDTO.getBody());
	    }
	    AssertionDTO assertionDTO = rfRequestDTO != null ? rfRequestDTO.getAssertionDTO() : null;
	    if (assertionDTO != null && assertionDTO.getBodyAssertDTOs() != null) {
		List<BodyAssertDTO> bodyAssertDTOs = assertionDTO.getBodyAssertDTOs();
		List<BodyAssert> bodyAsserts = new ArrayList<BodyAssert>();
		for (BodyAssertDTO bodyAssertDTO : bodyAssertDTOs) {
		    BodyAssert bodyAssert = new BodyAssert();
		    bodyAssert.setComparator(bodyAssertDTO.getComparator());
		    bodyAssert.setExpectedValue(bodyAssertDTO.getExpectedValue());
		    bodyAssert.setPropertyName(bodyAssertDTO.getPropertyName());
		    bodyAssert.setActualValue(bodyAssertDTO.getActualValue());
		    bodyAssert.setSuccess(bodyAssertDTO.isSuccess());
		    bodyAsserts.add(bodyAssert);
		}
		Assertion assertion = new Assertion();
		response.setAssertion(assertion);
	    }
	    
	    List<RfHeaderDTO> headerDTOs = responseDTO.getHeaders();
	    List<RfHeader> headers = new ArrayList<RfHeader>();
	    RfHeader header = null;
	    if (headerDTOs != null && !headerDTOs.isEmpty()) {
		for (RfHeaderDTO rfHeaderDTO : headerDTOs) {
		    header = new RfHeader();
		    header.setHeaderName(rfHeaderDTO.getHeaderName());
		    header.setHeaderValue(rfHeaderDTO.getHeaderValue());
		    headers.add(header);
		}
		response.setRfHeaders(headers);
	    }   
	}

	return conversation;

    }

    public static ConversationDTO convertToDTO(Conversation item) {
	ConversationDTO itemDTO = new ConversationDTO();

	RfRequestDTO rfRequestDTO = new RfRequestDTO();
	RfRequest rfRequest = item.getRfRequest();

	rfRequestDTO.setApiBody(rfRequest.getApiBody());

	rfRequestDTO.setApiUrl(rfRequest.getApiUrl());
	rfRequestDTO.setMethodType(rfRequest.getMethodType());

	return itemDTO;
    }

}
