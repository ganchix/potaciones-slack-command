package es.org.ganchix.dto.response;

import es.org.ganchix.dto.SlackAttachments;
import lombok.Data;

import java.util.List;

@Data
public class SlackResponse {

    private String response_type;
    private String text;
    private List<SlackAttachments> attachments;
}
