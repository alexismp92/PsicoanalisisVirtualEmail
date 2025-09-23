package com.psicovirtual.email.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class EmailTypes {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "email_type_seq")
    @SequenceGenerator(name = "email_type_seq", sequenceName = "email_type_sequence", allocationSize = 1)
    private Long emailTypeId;
    private String emailType;
}
