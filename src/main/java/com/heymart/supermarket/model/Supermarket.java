package com.heymart.supermarket.model;

import java.util.Set;
import java.util.UUID;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Supermarket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;
    private String name;
    private String color;
    private String urlName;
    private Set<String> managers;

    public Supermarket() {
        this.id = UUID.randomUUID().toString();
    }

    public void setName(String name) {
        if (name.isBlank()) {
            throw new IllegalArgumentException("Name cannot be empty");
        }
        this.name = name;
    }

    public void setUrlName(String urlName) {
        if (urlName.isBlank()) {
            throw new IllegalArgumentException("URL Name cannot be empty");
        }

        String newUrlName = urlName.toLowerCase();
        char[] characters = newUrlName.toCharArray();
        for (char c : characters) {
            if (c == ' ') {
                throw new IllegalArgumentException("URL Name cannot contain spaces");
            }
        }

        this.urlName = newUrlName;
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
