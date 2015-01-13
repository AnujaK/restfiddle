package com.restfiddle.dao.util;

import java.util.ArrayList;
import java.util.List;

import com.restfiddle.dto.ConversationDTO;
import com.restfiddle.dto.FormDataDTO;
import com.restfiddle.dto.RfHeaderDTO;
import com.restfiddle.dto.RfRequestDTO;
import com.restfiddle.dto.RfResponseDTO;
import com.restfiddle.entity.Conversation;
import com.restfiddle.entity.FormParam;
import com.restfiddle.entity.RfHeader;
import com.restfiddle.entity.RfRequest;
import com.restfiddle.entity.RfResponse;

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

	    if (rfRequestDTO.getApiBody() != null) {
		rfRequest.setApiBody(rfRequestDTO.getApiBody().getBytes());
	    } else if (formDataDTOs != null && !formDataDTOs.isEmpty()) {
		FormParam formParam = null;
		for (FormDataDTO formDataDTO : formDataDTOs) {
		    formParam = new FormParam();
		    formParam.setParamKey(formDataDTO.getKey());
		    formParam.setValueString(formDataDTO.getValue());
		    formParams.add(formParam);
		}
		rfRequest.setFormParams(formParams);
	    }

	    List<RfHeaderDTO> headerDTOs = rfRequestDTO.getHeaders();
	    List<RfHeader> headers = new ArrayList<RfHeader>();
	    RfHeader header = null;
	    if (headerDTOs != null && !headerDTOs.isEmpty()) {
		for (RfHeaderDTO rfHeaderDTO : headerDTOs) {
		    header = new RfHeader();
		    header.setHeaderName(rfHeaderDTO.getHeaderName());
		    header.setHeaderValueString(rfHeaderDTO.getHeaderValue());
		    headers.add(header);
		}
		rfRequest.setRfHeaders(headers);
	    }
	}
	conversation.setRfRequest(rfRequest);

	// TODO : We should have the option to configure whether to save response or not.
	RfResponse response = new RfResponse();
	conversation.setRfResponse(response);
	if (responseDTO.getBody() != null && !responseDTO.getBody().isEmpty()) {
	    response.setBody(responseDTO.getBody().getBytes());
	}

	return conversation;

    }

    public static ConversationDTO convertToDTO(Conversation item) {
	ConversationDTO itemDTO = new ConversationDTO();

	RfRequestDTO rfRequestDTO = new RfRequestDTO();
	RfRequest rfRequest = item.getRfRequest();

	rfRequestDTO.setApiBody(rfRequest.getApiBodyString());

	rfRequestDTO.setApiUrl(rfRequest.getApiUrl());
	rfRequestDTO.setMethodType(rfRequest.getMethodType());

	return itemDTO;
    }

}
