package com.restfiddle.util;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.codec.binary.Base64;

import com.restfiddle.dto.AssertionDTO;
import com.restfiddle.dto.BaseDTO;
import com.restfiddle.dto.BasicAuthDTO;
import com.restfiddle.dto.BodyAssertDTO;
import com.restfiddle.dto.ColorDTO;
import com.restfiddle.dto.ConversationDTO;
import com.restfiddle.dto.DigestAuthDTO;
import com.restfiddle.dto.FormDataDTO;
import com.restfiddle.dto.GenericEntityDTO;
import com.restfiddle.dto.GenericEntityDataDTO;
import com.restfiddle.dto.GenericEntityFieldDTO;
import com.restfiddle.dto.NodeDTO;
import com.restfiddle.dto.ProjectDTO;
import com.restfiddle.dto.RfHeaderDTO;
import com.restfiddle.dto.RfRequestDTO;
import com.restfiddle.dto.RfResponseDTO;
import com.restfiddle.dto.TagDTO;
import com.restfiddle.dto.UrlParamDTO;
import com.restfiddle.dto.UserDTO;
import com.restfiddle.dto.WorkspaceDTO;
import com.restfiddle.entity.Assertion;
import com.restfiddle.entity.BaseEntity;
import com.restfiddle.entity.BaseNode;
import com.restfiddle.entity.BasicAuth;
import com.restfiddle.entity.BodyAssert;
import com.restfiddle.entity.Color;
import com.restfiddle.entity.Conversation;
import com.restfiddle.entity.DigestAuth;
import com.restfiddle.entity.FormParam;
import com.restfiddle.entity.GenericEntity;
import com.restfiddle.entity.GenericEntityData;
import com.restfiddle.entity.GenericEntityField;
import com.restfiddle.entity.NamedEntity;
import com.restfiddle.entity.Project;
import com.restfiddle.entity.RfHeader;
import com.restfiddle.entity.RfRequest;
import com.restfiddle.entity.RfResponse;
import com.restfiddle.entity.Tag;
import com.restfiddle.entity.UrlParam;
import com.restfiddle.entity.User;
import com.restfiddle.entity.Workspace;

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

	dto.setApiUrl(entity.getApiUrl());
	dto.setMethodType(entity.getMethodType());
	dto.setApiBody(entity.getApiBody());
	dto.setHeaders(toListOfRfHeaderDTO(entity.getRfHeaders()));
	dto.setUrlParams(toListOfUrlParamDTO(entity.getUrlParams()));
	dto.setFormParams(toListOfFormDataDTO(entity.getFormParams()));
	dto.setBasicAuthDTO(toDTO(entity.getBasicAuth()));
	dto.setDigestAuthDTO(toDTO(entity.getDigestAuth()));
	dto.setAssertionDTO(toDTO(entity.getAssertion()));

	return dto;
    }

    public static AssertionDTO toDTO(Assertion entity) {
	
	if (entity == null)
	    return null;

	AssertionDTO dto = new AssertionDTO();
	
	dto.setStatusCode(entity.getStatusCode());
	dto.setResponseSize(entity.getResponseSize());
	dto.setResponseTime(entity.getResponseSize());
	dto.setBodyContentType(entity.getBodyContentType());
	dto.setBodyAssertDTOs(toListOfBodyAssertDTO(entity.getBodyAsserts()));
	
	return dto;
    }
    
    public static BodyAssertDTO toDTO(BodyAssert entity) {
	
	if (entity == null)
	    return null;

	BodyAssertDTO dto = new BodyAssertDTO();
	
	dto.setPropertyName(entity.getPropertyName());
	dto.setComparator(entity.getComparator());
	dto.setExpectedValue(entity.getExpectedValue());
	dto.setActualValue(entity.getActualValue());
	dto.setSuccess(entity.isSuccess());
	
	return dto;
    }

    public static RfResponseDTO toDTO(RfResponse entity) {

	if (entity == null)
	    return null;

	RfResponseDTO dto = new RfResponseDTO();

	copyBaseData(dto, entity);

	dto.setBody(entity.getBody());
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

    public static FormDataDTO toDTO(FormParam entity) {
	if (entity == null)
	    return null;

	FormDataDTO dto = new FormDataDTO();
	dto.setKey(entity.getParamKey());
	dto.setValue(entity.getParamValue());

	return dto;
    }

    public static UrlParamDTO toDTO(UrlParam entity) {
	if (entity == null)
	    return null;

	UrlParamDTO dto = new UrlParamDTO();
	dto.setKey(entity.getParamKey());
	dto.setValue(entity.getParamValue());

	return dto;
    }

    public static RfHeaderDTO toDTO(RfHeader entity) {

	if (entity == null)
	    return null;

	RfHeaderDTO dto = new RfHeaderDTO();
	dto.setHeaderName(entity.getHeaderName());
	dto.setHeaderValue(entity.getHeaderValue());

	return dto;
    }

    public static NodeDTO toDTO(BaseNode entity) {

	if (entity == null)
	    return null;

	NodeDTO dto = new NodeDTO();

	copyBaseData(dto, entity);

	dto.setNodeType(entity.getNodeType());
	dto.setParentId(entity.getParentId());
	dto.setProjectId(entity.getProjectId());
	dto.setPosition(entity.getPosition());
	dto.setStarred(entity.getStarred());
	dto.setMethod(entity.getMethod());
	dto.setTags(toListOfTagDTO(entity.getTags()));
	dto.setConversationDTO(toDTO(entity.getConversation()));
	dto.setGenericEntityDTO(toDTO(entity.getGenericEntity()));
	if(entity.getConversation()!=null && entity.getConversation().getRfRequest()!=null){
	    dto.setApiURL(entity.getConversation().getRfRequest().getApiUrl());
	}

	return dto;

    }

    public static GenericEntityDTO toDTO(GenericEntity entity) {

	if (entity == null)
	    return null;

	GenericEntityDTO dto = new GenericEntityDTO();

	copyBaseData(dto, entity);
	
	dto.setEntityDataList(toListOfGenericEntityDataDTO(entity.getEntityDataList()));
	dto.setFields(toListOfGenericEntityFieldDTO(entity.getFields()));

	return dto;

    }

    public static GenericEntityFieldDTO toDTO(GenericEntityField entity) {

	if (entity == null)
	    return null;

	GenericEntityFieldDTO dto = new GenericEntityFieldDTO();

	dto.setName(entity.getName());
	dto.setType(entity.getType());

	return dto;
    }

    public static GenericEntityDataDTO toDTO(GenericEntityData entity) {

	if (entity == null)
	    return null;

	GenericEntityDataDTO dto = new GenericEntityDataDTO();

	copyBaseData(dto, entity);

	dto.setData(entity.getData());

	return dto;

    }

    public static TagDTO toDTO(Tag entity) {

	if (entity == null)
	    return null;

	TagDTO dto = new TagDTO();

	copyBaseData(dto, entity);

	dto.setColor(toDTO(entity.getColor()));
	dto.setWorkspace(toDTO(entity.getWorkspace()));

	return dto;
    }

    private static ColorDTO toDTO(Color entity) {

	if (entity == null)
	    return null;

	ColorDTO dto = new ColorDTO();

	dto.setDisplayName(entity.getDisplayName());
	dto.setColorCode(entity.getColorCode());

	return dto;
    }

    public static WorkspaceDTO toDTO(Workspace entity) {

	if (entity == null)
	    return null;

	WorkspaceDTO dto = new WorkspaceDTO();

	copyBaseData(dto, entity);

	dto.setProjects(toListOfProjectDTO(entity.getProjects()));

	return dto;
    }

    public static ProjectDTO toDTO(Project entity) {

	if (entity == null)
	    return null;

	ProjectDTO dto = new ProjectDTO();
	dto.setProjectRef(toDTO(entity.getProjectRef()));

	return dto;
    }
    
    public static List<BodyAssertDTO> toListOfBodyAssertDTO(List<BodyAssert> entity) {

	if (entity == null)
	    return null;

	List<BodyAssertDTO> dto = new ArrayList<BodyAssertDTO>();
	for (BodyAssert item : entity) {
	    dto.add(toDTO(item));
	}

	return dto;
    }

    public static List<GenericEntityFieldDTO> toListOfGenericEntityFieldDTO(List<GenericEntityField> entity) {

	if (entity == null)
	    return null;

	List<GenericEntityFieldDTO> dto = new ArrayList<GenericEntityFieldDTO>();
	for (GenericEntityField item : entity) {
	    dto.add(toDTO(item));
	}

	return dto;
    }

    public static List<GenericEntityDataDTO> toListOfGenericEntityDataDTO(List<GenericEntityData> entity) {

	if (entity == null)
	    return null;

	List<GenericEntityDataDTO> dto = new ArrayList<GenericEntityDataDTO>();
	for (GenericEntityData item : entity) {
	    dto.add(toDTO(item));
	}

	return dto;
    }

    public static List<TagDTO> toListOfTagDTO(List<Tag> entity) {

	if (entity == null)
	    return null;

	List<TagDTO> dto = new ArrayList<TagDTO>();
	for (Tag item : entity) {
	    dto.add(toDTO(item));
	}

	return dto;
    }

    public static List<ProjectDTO> toListOfProjectDTO(List<Project> entity) {

	if (entity == null)
	    return null;

	List<ProjectDTO> dto = new ArrayList<ProjectDTO>();
	for (Project item : entity) {
	    dto.add(toDTO(item));
	}

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
