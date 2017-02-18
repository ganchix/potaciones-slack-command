package es.org.ganchix.domain;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Document
@Data
public class Restaurant {

    @Id
    private String name;

    @Indexed
    private Set<Person> personsToGo = new HashSet<Person>();

    @Indexed(expireAfterSeconds = 86400)
    private Date createdDate = new Date();

}
