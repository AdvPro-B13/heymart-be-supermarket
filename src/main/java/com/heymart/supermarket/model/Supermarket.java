package com.heymart.supermarket.model;

import java.io.Serializable;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;


@Builder
@Setter
@Getter
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

    @ElementCollection(targetClass = String.class, fetch = FetchType.EAGER)
    @CollectionTable(name="supermarketmanager", joinColumns = @JoinColumn(name="supermarket_id"))
    private Set<String> managerIds;

    public Supermarket(SupermarketBuilder builder) {
        this.setName(builder.name);
        this.setColor(builder.color);
        this.setUrlName(builder.urlName);
        this.setManagerIds(builder.managerIds != null ? new HashSet<>(builder.managerIds) : new HashSet<>());
    }

    public Supermarket() {
    }

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

    public void addManagerId(String managerId) {
        if (managerId == null) {
            throw new NullPointerException();
        }
        managerIds.add(managerId);
    }

    public void removeManagerId(String managerId) {
        if (managerId == null) {
            throw new NullPointerException();
        }
        managerIds.remove(managerId);
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

    public static class SupermarketBuilder {
        @Setter private String name;
        @Setter private String color;
        @Setter private String urlName;
        @Setter private Set<String> managerIds;

        public SupermarketBuilder() {}
        public SupermarketBuilder(String name, String color, String urlName, Set<String> managerIds) {
            this.name = name;
            this.color = color;
            this.urlName = urlName;
            this.managerIds = managerIds;
        }

        public Supermarket build() {
            if (this.color == null) this.color = "#cccccc";
            if (this.name == null) throw new IllegalArgumentException();
            if (this.urlName == null) throw new IllegalArgumentException();

            return new Supermarket(this);
        }
    }
}