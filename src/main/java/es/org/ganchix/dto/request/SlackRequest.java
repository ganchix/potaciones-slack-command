package es.org.ganchix.dto.request;

import lombok.Data;

@Data
public class SlackRequest {

    private String token;
    private String team_id;
    private String team_domain;
    private String channel_id;
    private String channel_name;
    private String user_id;
    private String user_name;
    private String command;
    private String text;
    private String response_url;
}
