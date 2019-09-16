package se.kits.soroush.alexa.client.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class Station {
    private int id;
    private String name;
    private String code;
    private String slug;
    private double lat;
    private double lng;
}


