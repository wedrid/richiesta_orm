package com.caribu.richiesta_orm.model;

import java.util.List;

public class RequestsList {
    private List<RequestDTO> requests;

    public RequestsList(List<RequestDTO> requests) {
        this.requests = requests;
    }

    public List<RequestDTO> getRequests() {
        return requests;
    }

    public void setRequests(List<RequestDTO> requests) {
        this.requests = requests;
    }
}
