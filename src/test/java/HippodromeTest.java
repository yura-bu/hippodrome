import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.*;
import org.mockito.Mockito;
import java.time.Duration;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class HippodromeTest{
    @ParameterizedTest
    @NullSource
    public void isHorsesIsNull(List<Horse> horses){
        Throwable exception = assertThrows(
                IllegalArgumentException.class,
                () -> new Hippodrome(horses)
        );
        assertEquals("Horses cannot be null.", exception.getMessage());
    }

    @ParameterizedTest
    @EmptySource
    public void isHorsesIsEmpty(List<Horse> horses){
        Throwable exception = assertThrows(
                IllegalArgumentException.class,
                () -> new Hippodrome(horses)
        );
        assertEquals("Horses cannot be empty.", exception.getMessage());
    }
    @ParameterizedTest
    @MethodSource("listHorsesArgs")
    public void getHorses(List<Horse> horses){
        Hippodrome hippodrome = new Hippodrome(horses);
        assertEquals(horses, hippodrome.getHorses());
    }
    static Stream<Arguments> listHorsesArgs(){
        List<Horse> horses = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            horses.add(new Horse(String.valueOf(i), 1));
        }
        return Stream.of(arguments(horses));
    }


    @ParameterizedTest
    @MethodSource("listMocHorsesArgs")
    public void CallMoveAllMockHorses(List<Horse> horses){

        for (Horse horse: horses){
            horse.move();
            Mockito.verify(horse).move();
        }
    }
    static Stream<Arguments> listMocHorsesArgs(){
        List<Horse> horses = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            horses.add(Mockito.mock(Horse.class));
        }
        return Stream.of(arguments(horses));
    }
    static Stream<Arguments> listHorsesArgsMax(){
        List<Horse> horses = new ArrayList<>();

        for (int i = 0; i < 30; i++) {

            int randomNum = ThreadLocalRandom.current().nextInt(0, 100);
            horses.add(new Horse(String.valueOf(i), 1, i+ randomNum));
        }
        return Stream.of(arguments(horses));
    }
    @ParameterizedTest
    @MethodSource("listHorsesArgsMax")
    public void getWinnerHorse(List<Horse> horses){
        Hippodrome hippodrome = new Hippodrome(horses);
        String errorMax = "This is not Max number";
        double maxDistance;
        maxDistance = horses.stream().map(Horse::getDistance)
                .max(Double::compareTo)
                .orElseThrow(() -> new IllegalStateException(errorMax));
        assertEquals(hippodrome.getWinner().getDistance(), maxDistance);
    }
    @Disabled
    @Test
    public void timeOutMain(){
        assertTimeout(Duration.ofSeconds(22), () -> Main.main(null));
    }
}
