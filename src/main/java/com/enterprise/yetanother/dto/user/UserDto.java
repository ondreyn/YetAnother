package com.enterprise.yetanother.dto.user;

import com.enterprise.yetanother.enums.Roles;

import java.util.Objects;

/**
 *@author andrey
 */
public class UserDto {

    private Long id;
    private String firstName;
    private String lastName;
    private String position;
    private String phone;
    private Roles role;
    private String email;
    private String address;

    public UserDto() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Roles getRole() {
        return role;
    }

    public void setRole(Roles role) {
        this.role = role;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserDto userDto = (UserDto) o;
        return Objects.equals(id, userDto.id) &&
                Objects.equals(firstName, userDto.firstName) &&
                Objects.equals(lastName, userDto.lastName) &&
                Objects.equals(position, userDto.position) &&
                Objects.equals(phone, userDto.phone) &&
                Objects.equals(email, userDto.email) &&
                Objects.equals(address, userDto.address);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, firstName, lastName, position, phone, role, email, address);
    }

    @Override
    public String toString() {
        return "UserDto{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", position='" + position + '\'' +
                ", phone='" + phone + '\'' +
                ", role=" + role +
                ", email='" + email + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}
