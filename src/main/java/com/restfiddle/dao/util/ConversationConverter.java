package com.restfiddle.dao.util;

import com.restfiddle.dto.ConversationDTO;
import com.restfiddle.dto.RfRequestDTO;
import com.restfiddle.dto.RfResponseDTO;
import com.restfiddle.entity.Conversation;
import com.restfiddle.entity.RfRequest;
import com.restfiddle.entity.RfResponse;

//TODO : Need to use Spring Object Mapping : http://docs.spring.io/spring/previews/mapping.html
public class ConversationConverter {

    public static Conversation convertToEntity(RfRequestDTO requestDto, RfResponseDTO responseDto) {
	Conversation item = new Conversation();

	RfRequest request = new RfRequest();
	item.setRfRequest(request);
	request.setApiUrl(requestDto.getApiUrl());
	request.setMethodType(requestDto.getMethodType());

	// Note : We can plan to make it configurable.
	RfResponse response = new RfResponse();
	item.setRfResponse(response);
	if (!responseDto.getBody().isEmpty()) {
	    response.setBody(responseDto.getBody().getBytes());
	}

	return item;

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
