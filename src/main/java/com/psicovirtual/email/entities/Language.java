package com.psicovirtual.email.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "LANGUAGES")
public class Language {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "language_seq")
    @SequenceGenerator(name = "language_seq", sequenceName = "languages_sequence", allocationSize = 1)
    private Long languageId;
    private String languageCode;
    private String languageName;
}
