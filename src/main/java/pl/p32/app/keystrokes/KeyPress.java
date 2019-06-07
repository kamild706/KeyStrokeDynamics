package pl.p32.app.keystrokes;

import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class KeyPress {

    @EqualsAndHashCode.Include
    public final char key;
    public final long timestamp;
}
