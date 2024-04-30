package orchestrator.utils;

import lombok.experimental.UtilityClass;

import java.time.LocalDate;
import java.time.LocalDateTime;

@UtilityClass
public class UniqueCodeGenerator {

    public static String generateCode(int lo, int hi){
        int n = rand(lo, hi);
        byte b[] = new byte[n];
        for (int i = 0; i < n; i++)
            b[i] = (byte)rand('a', 'z');
        return new String(b, 0);
    }

    private static int rand(int lo, int hi){
        java.util.Random rn = new java.util.Random();
        int n = hi - lo + 1;
        int i = rn.nextInt(n);
        return lo + i;
    }

    public static String generateCode(){
        return LocalDateTime.now().getMinute() + generateCode(7, 7).toUpperCase() + LocalDate.now().getDayOfYear();
    }

}
