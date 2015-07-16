package com.restfiddle.handler;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.JsonPath;
import com.restfiddle.dto.AssertionDTO;
import com.restfiddle.dto.BodyAssertDTO;
import com.restfiddle.dto.RfResponseDTO;

@Component
public class AssertHandler {
    Logger logger = LoggerFactory.getLogger(AssertHandler.class);

    public void runAssert(RfResponseDTO rfResponseDTO) {
	AssertionDTO assertionDTO = rfResponseDTO.getAssertionDTO();
	if (assertionDTO == null)
	    return;
	
	List<BodyAssertDTO> bodyAssertDTOs = assertionDTO.getBodyAssertDTOs();;
	if (bodyAssertDTOs == null)
	    return;

	try {
	    BodyAssertTool tool = new BodyAssertTool(rfResponseDTO.getBody());
	    for (BodyAssertDTO bodyAssertDTO : bodyAssertDTOs) {
	        tool.bodyAssert(bodyAssertDTO);
	    }
	} catch (Exception e) {
	    logger.warn("Body is not json");
	}

	return;
    }

    
    private class BodyAssertTool {
	Object body;
	public BodyAssertTool(String body) {
	    this.body = Configuration.defaultConfiguration().jsonProvider().parse(body);
	}
	
	public void bodyAssert(BodyAssertDTO bodyAssertDTO) {
	    String propertyName = bodyAssertDTO.getPropertyName();
	    if(propertyName.startsWith("[")){
		propertyName = "$"+propertyName;
	    }else{
		propertyName = "$."+propertyName;
	    }
		
	    Object actualValue = JsonPath.read(body, propertyName);
	    Boolean success = evaluate(bodyAssertDTO.getExpectedValue(), bodyAssertDTO.getComparator(), actualValue.toString());
	    bodyAssertDTO.setActualValue(actualValue.toString());
	    bodyAssertDTO.setSuccess(success);
	}
	
	private boolean evaluate(String expectedValue, String comparator, String actualValue) {
	    boolean result = false;

	    switch (comparator) {
	    case "=":
		result = actualValue.equals(expectedValue);
		break;
	    case "!=":
		result = !actualValue.equals(expectedValue);
		break;
	    case "Contains":
		result = actualValue.contains(expectedValue);
		break;
	    case "! Contains":
		result = !actualValue.contains(expectedValue);
		break;
	    case "<":
		result = !actualValue.equals(expectedValue);
		break;
	    case "<=":
		result = actualValue.equals(expectedValue);
		break;
	    case ">":
		result = !actualValue.equals(expectedValue);
		break;
	    case ">=":
	    }

	    return result;
	}

	private boolean evaluate(String expectedValue, String comparator, Number actualValue) {
	    boolean result = false;

	    Integer expval = Integer.parseInt(expectedValue);

	    switch (comparator) {
	    case "=":
		result = actualValue.equals(expval);
		break;
	    case "!=":
		result = !actualValue.equals(expval);
		break;
	    case "<":
		result = !actualValue.equals(expval);
		break;
	    case "<=":
		result = actualValue.equals(expval);
		break;
	    case ">":
		result = !actualValue.equals(expval);
		break;
	    case ">=":
	    }

	    return result;
	}
    }
}
