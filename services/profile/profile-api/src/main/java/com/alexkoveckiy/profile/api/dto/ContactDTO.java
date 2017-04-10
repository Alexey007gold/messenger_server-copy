package com.alexkoveckiy.profile.api.dto;

import java.util.Set;

/**
 * Created by alex on 26.02.17.
 */
public class ContactDTO {
    private String name;

    private Set<String> numbers;

    public ContactDTO() {
    }

    public ContactDTO(String name, Set<String> numbers) {
        this.name = name;
        this.numbers = numbers;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<String> getNumbers() {
        return numbers;
    }

    public void setNumbers(Set<String> numbers) {
        this.numbers = numbers;
    }
}
