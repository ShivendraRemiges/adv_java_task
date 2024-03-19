package com.remiges.adv_java_task.json;

import java.util.Map;

import lombok.Data;

@Data
public class JasonRequest {

    private String token;
    private Map<String, Object> data;
    private String _reqid;
    private String _client_ts;
    private String _client_type;

}
