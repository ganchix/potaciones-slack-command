package es.org.ganchix.dto;

import lombok.Data;
import lombok.NonNull;

@Data
public class SlackAttachments {
    @NonNull
    private String text;
}
