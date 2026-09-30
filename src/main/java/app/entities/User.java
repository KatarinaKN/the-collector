package app.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
@Getter
@ToString
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    private String name;

    @Setter
    private String email;

    @Setter
    private String password;

    @Setter
    private String role;

    @Setter
    private LocalDate createdOn;

    @OneToMany(mappedBy = "users", cascade = CascadeType.REMOVE)
    private Set<Find> finds = new HashSet<>();

    public void addFind(Find find) {
        this.finds.add(find);
        if (find != null) {
            find.setUser(this);
        }
    }
}


