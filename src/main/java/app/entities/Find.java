package app.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;

@Entity
@Table(name = "finds")
@Getter
@ToString
@NoArgsConstructor
public class Find {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; //Long (wrapper-klasse til long), fordi Long KAN være null. Når jeg opretter et nyt objekt,
    // har det endnu ikke fået et id fra databasen. Id'et genereres af JPA/Hibernate, når objektet
    // gemmes/persisteres. Hvis feltets type er primitiv (long), bliver værdien automatisk sat til 0,
    // og Hibernate kan ikke skelne mellem "Id er 0" og "Id er ikke sat endnu".
    // SÅ! Id-feltet er nødt til at kunne være null, for at det kan få tildelt den rigtige værdi,
    // når objektet gemmes i databasen.

    private LocalDate date;
    private String photoURL;
    private String note;
}
