package meetroom.utils;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class DateUtil {
	
	public static LocalDate currentMonday;

    static {
        currentMonday = LocalDate.now().with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
    }
    
    public static List<Integer> getMinuteList() {
    	return Stream.iterate(0, n -> n + 1).limit(60).collect(Collectors.toList());
    }
}
