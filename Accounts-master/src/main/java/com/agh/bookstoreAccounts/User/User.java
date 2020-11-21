package com.agh.bookstoreAccounts.User;


import com.agh.bookstoreAccounts.Role.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Data
@Entity
    @Table(name = "users")
    public class User {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        private String username;

        private String password;

        private String name;

        private String surname;

        private String email;

     //   private int numberOfReservations;

//        //jeden użytkownik może mieć wiele rezerwacji
//        @OneToMany(mappedBy = "reservationUser")
//        @JsonIgnore //żeby się nie zapętlił json
//        private List<Reservation> reservationList;

        @Transient
        private String passwordConfirm;

        @ManyToMany
        @JsonIgnore
        private Set<Role> roles;

        public User(){}

//        public void addOneReservation() {
//            this.numberOfReservations += 1;
//        }
//
//        public void subtractOneReservation() {
//            this.numberOfReservations -= 1;
//        }
}

