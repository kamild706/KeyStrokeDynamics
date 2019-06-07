package pl.p32.app.model;

import lombok.*;

import javax.persistence.*;
import java.util.Comparator;

import static java.util.Comparator.comparing;

@Data
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "keystroke")
public class KeyStroke {

    public static Comparator<KeyStroke> COMPARE_KEY_VALUE = comparing(KeyStroke::getKeyValue);

    @Id @GeneratedValue
    private Integer id;

    @EqualsAndHashCode.Include
    private int probeId;

    @ManyToOne @ToString.Exclude @JoinColumn(name = "person_id")
    private Person person;

    @EqualsAndHashCode.Include
    private String keyValue;

    @EqualsAndHashCode.Include
    private int dwellTime;

    public KeyStroke(String keyValue, int dwellTime) {
        this.keyValue = keyValue;
        this.dwellTime = dwellTime;
    }
}
