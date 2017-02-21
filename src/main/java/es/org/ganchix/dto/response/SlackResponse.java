package es.org.ganchix.dto.response;

import es.org.ganchix.dto.SlackAttachments;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SlackResponse {

    private String response_type;
    private String text;
    private List<SlackAttachments> attachments;
}
