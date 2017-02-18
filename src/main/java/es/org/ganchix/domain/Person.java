package es.org.ganchix.domain;

import lombok.Data;
import lombok.NonNull;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Data
public class Person {

    @NonNull
    @Id
    private String id;

    @NonNull
    @Indexed
    private String name;

    private String icon;
}
