package pl.p32.app.statstics;

import lombok.RequiredArgsConstructor;
import lombok.ToString;

@RequiredArgsConstructor
@ToString
public class ExaminationResult {

    public final String methodName;
    public final int k;
    public final int successes;
    public final int attempts;

    public double successRatio() {
        return successes / (double) attempts;
    }
}
