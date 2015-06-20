package com.restfiddle.util;

import java.util.ArrayList;
import java.util.List;

import com.restfiddle.dto.BaseDTO;
import com.restfiddle.dto.BasicAuthDTO;
import com.restfiddle.dto.ConversationDTO;
import com.restfiddle.dto.DigestAuthDTO;
import com.restfiddle.dto.FormDataDTO;
import com.restfiddle.dto.RfHeaderDTO;
import com.restfiddle.dto.RfRequestDTO;
import com.restfiddle.dto.RfResponseDTO;
import com.restfiddle.dto.UrlParamDTO;
import com.restfiddle.dto.UserDTO;
import com.restfiddle.entity.BaseEntity;
import com.restfiddle.entity.BasicAuth;
import com.restfiddle.entity.Conversation;
import com.restfiddle.entity.DigestAuth;
import com.restfiddle.entity.FormParam;
import com.restfiddle.entity.NamedEntity;
import com.restfiddle.entity.RfHeader;
import com.restfiddle.entity.RfRequest;
import com.restfiddle.entity.RfResponse;
import com.restfiddle.entity.UrlParam;
import com.restfiddle.entity.User;

public class EntityToDTO {

    public static BaseDTO toDTO(BaseEntity entity) {
	if (entity == null)
	    return null;

	BaseDTO dto = new BaseDTO();

	copyBaseData(dto, entity);

	return dto;
    }

    public static BaseDTO toDTO(NamedEntity entity) {
	if (entity == null)
	    return null;

	BaseDTO dto = toDTO(entity);

	copyBaseData(dto, entity);

	return dto;
    }

    public static UserDTO toDTO(User entity) {

	if (entity == null)
	    return null;

	UserDTO dto = new UserDTO();
	dto.setId(entity.getId());
	dto.setName(entity.getName());

	return dto;
    }

    public static ConversationDTO toDTO(Conversation entity) {

	if (entity == null)
	    return null;

	ConversationDTO dto = new ConversationDTO();

	copyBaseData(dto, entity);

	dto.setRfRequestDTO(toDTO(entity.getRfRequest()));
	dto.setRfResponseDTO(toDTO(entity.getRfResponse()));
	dto.setDuration(entity.getDuration());

	return dto;
    }

    public static RfRequestDTO toDTO(RfRequest entity) {

	if (entity == null)
	    return null;

	RfRequestDTO dto = new RfRequestDTO();

	copyBaseData(dto, entity);

	dto.setApiUrl(entity.getApiUrlString());
	dto.setMethodType(entity.getMethodType());
	dto.setApiBody(entity.getApiBody() != null ? new String(entity.getApiBody()) : null);
	dto.setHeaders(toListOfRfHeaderDTO(entity.getRfHeaders()));
	dto.setUrlParams(toListOfUrlParamDTO(entity.getUrlParams()));
	dto.setFormParams(toListOfFormDataDTO(entity.getFormParams()));
	dto.setBasicAuthDTO(toDTO(entity.getBasicAuth()));
	dto.setDigestAuthDTO(toDTO(entity.getDigestAuth()));

	return dto;
    }

    public static RfResponseDTO toDTO(RfResponse entity) {

	if (entity == null)
	    return null;

	RfResponseDTO dto = new RfResponseDTO();

	copyBaseData(dto, entity);

	dto.setBody(entity.getBody() != null ? new String(entity.getBody()) : null);
	dto.setHeaders(toListOfRfHeaderDTO(entity.getRfHeaders()));

	return dto;
    }

    public static BasicAuthDTO toDTO(BasicAuth entity) {
	if (entity == null)
	    return null;

	BasicAuthDTO dto = new BasicAuthDTO();

	dto.setUsername(entity.getUsername());
	dto.setPassword(entity.getPassword());

	return dto;
    }

    public static DigestAuthDTO toDTO(DigestAuth entity) {
	if (entity == null)
	    return null;

	DigestAuthDTO dto = new DigestAuthDTO();

	dto.setUsername(entity.getUsername());
	dto.setPassword(entity.getPassword());

	return dto;
    }

    public static List<UrlParamDTO> toListOfUrlParamDTO(List<UrlParam> entity) {

	if (entity == null)
	    return null;

	List<UrlParamDTO> dto = new ArrayList<UrlParamDTO>();
	for (UrlParam item : entity) {
	    dto.add(toDTO(item));
	}

	return dto;
    }

    public static List<FormDataDTO> toListOfFormDataDTO(List<FormParam> entity) {

	if (entity == null)
	    return null;

	List<FormDataDTO> dto = new ArrayList<FormDataDTO>();
	for (FormParam item : entity) {
	    dto.add(toDTO(item));
	}

	return dto;
    }

    private static FormDataDTO toDTO(FormParam entity) {
	if (entity == null)
	    return null;

	FormDataDTO dto = new FormDataDTO();
	dto.setKey(entity.getParamKey());
	dto.setValue(entity.getParamValue() != null ? new String(entity.getParamValue()) : null);

	return dto;
    }

    private static UrlParamDTO toDTO(UrlParam entity) {
	if (entity == null)
	    return null;

	UrlParamDTO dto = new UrlParamDTO();
	dto.setKey(entity.getParamKey());
	dto.setValue(entity.getParamValue() != null ? new String(entity.getParamValue()) : null);

	return dto;
    }

    public static List<RfHeaderDTO> toListOfRfHeaderDTO(List<RfHeader> entity) {

	if (entity == null)
	    return null;

	List<RfHeaderDTO> dto = new ArrayList<RfHeaderDTO>();
	for (RfHeader item : entity) {
	    dto.add(toDTO(item));
	}

	return dto;
    }

    public static RfHeaderDTO toDTO(RfHeader entity) {

	if (entity == null)
	    return null;

	RfHeaderDTO dto = new RfHeaderDTO();
	dto.setHeaderName(entity.getHeaderName());
	dto.setHeaderValue(entity.getHeaderValue() != null ? new String(entity.getHeaderValue()) : null);

	return dto;
    }

    private static void copyBaseData(BaseDTO dto, BaseEntity entity) {
	dto.setId(entity.getId());
	dto.setCreatedDate(entity.getCreatedDate());
	dto.setCreatedBy(toDTO(entity.getCreatedBy()));
	dto.setLastModifiedDate(entity.getLastModifiedDate());
	dto.setLastModifiedBy(toDTO(entity.getLastModifiedBy()));
	dto.setStatus(entity.getStatus());
    }

    private static void copyBaseData(BaseDTO dto, NamedEntity entity) {

	dto.setId(entity.getId());
	dto.setCreatedDate(entity.getCreatedDate());
	dto.setCreatedBy(toDTO(entity.getCreatedBy()));
	dto.setLastModifiedDate(entity.getLastModifiedDate());
	dto.setLastModifiedBy(toDTO(entity.getLastModifiedBy()));
	dto.setStatus(entity.getStatus());

	dto.setName(entity.getName());
	dto.setDescription(entity.getDescription());
    }
}
