package com.restfiddle.dto;

import java.util.List;

public class ReportDTO {
    
    private List<NodeStatusResponseDTO> nodeStatusResponse;

    public List<NodeStatusResponseDTO> getNodeStatusResponse() {
        return nodeStatusResponse;
    }

    public void setNodeStatusResponse(List<NodeStatusResponseDTO> nodeStatusResponse) {
        this.nodeStatusResponse = nodeStatusResponse;
    }

}
