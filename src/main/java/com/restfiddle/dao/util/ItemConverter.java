package com.restfiddle.dao.util;

import com.restfiddle.dto.RfRequestDTO;
import com.restfiddle.entity.Item;
import com.restfiddle.entity.RfRequest;
import com.restfiddle.entity.RfResponse;

public class ItemConverter {

    public static Item convertDTOtoEntity(RfRequestDTO requestDto, String body) {
	Item item = new Item();

	RfRequest request = new RfRequest();
	item.setRfRequest(request);
	request.setApiUrl(requestDto.getApiUrl());
	request.setMethodType(requestDto.getMethodType());

	// Note : By default we should not save response. Will plan to make it configurable.
	RfResponse response = new RfResponse();
	item.setRfResponse(response);
	response.setBody(body);

	return item;

    }

}
