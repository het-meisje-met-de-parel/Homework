package common.solutions.utils;

import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;

@EqualsAndHashCode
@RequiredArgsConstructor
public class Tup4 <T1, T2, T3, T4> {
    
    public final T1 T1;
    public final T2 T2;
    public final T3 T3;
    public final T4 T4;
    
    public static <T1, T2, T3, T4> Tup4 <T1, T2, T3, T4> of (T1 t1, T2 t2, T3 t3, T4 t4) {
        return new Tup4 <> (t1, t2, t3, t4);
    }
    
}
