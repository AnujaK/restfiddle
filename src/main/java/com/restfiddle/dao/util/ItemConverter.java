package com.restfiddle.dao.util;

import com.restfiddle.dto.RfRequestDTO;
import com.restfiddle.entity.Item;
import com.restfiddle.entity.RfRequest;
import com.restfiddle.entity.RfResponse;

public class ItemConverter {
	
	public static Item convertDTOtoEntity(RfRequestDTO requestDto, String body){
		Item item = new Item();
		RfRequest request = new RfRequest();
		RfResponse response = new RfResponse();
		item.setRfRequest(request);
		item.setRfResponse(response);
		
		
		request.setURI(requestDto.getApiUrl());
		request.setMethod(requestDto.getMethodType());
		
		response.setBody(body);
		
		return item;
		
	}

}
