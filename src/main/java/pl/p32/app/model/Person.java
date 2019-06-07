package pl.p32.app.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "person")
public class Person {

    @Id @GeneratedValue @EqualsAndHashCode.Include
    private Integer id;
    @ToString.Include
    private String name;

    @OneToMany(mappedBy = "person", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private Set<KeyStroke> keystrokes = new HashSet<>();

    public void addKeyStroke(KeyStroke keyStroke) {
        keyStroke.setPerson(this);
        keystrokes.add(keyStroke);
    }

    public void removeKeyStrokes() {
        for (KeyStroke keyStroke : keystrokes)
            keyStroke.setPerson(null);
        keystrokes.clear();
    }
}
