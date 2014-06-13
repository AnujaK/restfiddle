package com.restfiddle.dao.util;

import com.restfiddle.dto.ItemDTO;
import com.restfiddle.dto.RfRequestDTO;
import com.restfiddle.entity.Item;
import com.restfiddle.entity.RfRequest;
import com.restfiddle.entity.RfResponse;

//TODO : Need to use Spring Object Mapping : http://docs.spring.io/spring/previews/mapping.html
public class ItemConverter {

    public static Item convertToEntity(RfRequestDTO requestDto, String body) {
	Item item = new Item();

	RfRequest request = new RfRequest();
	item.setRfRequest(request);
	request.setApiUrl(requestDto.getApiUrl());
	request.setMethodType(requestDto.getMethodType());

	// Note : By default we should not save response. Will plan to make it configurable.
	RfResponse response = new RfResponse();
	item.setRfResponse(response);
	if (!body.isEmpty()) {
	    response.setBody(body.getBytes());
	}

	return item;

    }

    public static ItemDTO convertToDTO(Item item) {
	ItemDTO itemDTO = new ItemDTO();

	RfRequestDTO rfRequestDTO = new RfRequestDTO();
	RfRequest rfRequest = item.getRfRequest();
	rfRequestDTO.setApiBody(rfRequest.getApiBody());
	rfRequestDTO.setApiUrl(rfRequest.getApiUrl());
	rfRequestDTO.setMethodType(rfRequest.getMethodType());

	return itemDTO;
    }

}
