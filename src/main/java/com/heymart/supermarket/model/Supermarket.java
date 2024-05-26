package com.heymart.supermarket.model;

import java.io.Serializable;
import java.util.Arrays;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Entity
@Table(
        name = "supermarket",
        schema = "public",
        uniqueConstraints = {
                @UniqueConstraint(name = "unique_url", columnNames = {"urlName"})
        }
)
public class Supermarket implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "displayName", nullable = false)
    private String name;

    @Column(name = "accentColor", nullable = false, columnDefinition = "varchar(7) default '#cccccc'")
    private String color;

    @Column(name = "urlName", nullable = false)
    private String urlName;

    public void setUrlName(String newUrl) {
        String sanitizedString;
        char[] chars = newUrl.toLowerCase().toCharArray();

        for (int i=0; i<chars.length; i++) {
            if (chars[i] == ' ') {
                chars[i] = '-';
            }
        }

        sanitizedString = String.valueOf(chars);
        this.urlName = sanitizedString;
    }

    @Override
    public String toString() {
        return "Supermarket{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", color='" + color + '\'' +
                ", urlName='" + urlName + '\'' +
                '}';
    }
}