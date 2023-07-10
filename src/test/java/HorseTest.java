import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class HorseTest{
    @ParameterizedTest
    @NullSource
    public void getHorseNullName(String name){
        Throwable exception = assertThrows(
                IllegalArgumentException.class,
                () -> new Horse(name, 0)
        );
        assertEquals("Name cannot be null.", exception.getMessage());
    }
    @ParameterizedTest
    @ValueSource(strings = {"", " ", "\t","\n"})
    public void getHorseBlankName(String name){
        Throwable exception = assertThrows(
                IllegalArgumentException.class,
                () -> new Horse(name, 0));
        assertEquals("Name cannot be blank.", exception.getMessage());
    }
    @ParameterizedTest
    @ValueSource(ints = {-1})
    public void getHorseNegativeSpeed(double speed){
        Throwable exception = assertThrows(
                IllegalArgumentException.class,
                () -> new Horse("Fast", speed));
        assertEquals("Speed cannot be negative.", exception.getMessage());
    }
    @ParameterizedTest
    @ValueSource(ints = {-1})
    public void getHorseNegativeDistance(double distance){
        Throwable exception = assertThrows(
                IllegalArgumentException.class,
                () -> new Horse("Fast", 0, distance));
        assertEquals("Distance cannot be negative.", exception.getMessage());
    }

    @ParameterizedTest
    @CsvSource({"Joke, 3"})
    public void getName(String name, double speed){
        Horse horse = new Horse(name, speed);
        assertEquals("Joke", horse.getName());
    }
    @ParameterizedTest
    @CsvSource({"Joke, 3"})
    public void getSpeed(String name, double speed){
        Horse horse = new Horse(name, speed);
        assertEquals(speed, horse.getSpeed());
    }
    @ParameterizedTest
    @CsvSource({"Joke, 3, 4"})
    public void getDistance(String name, double speed, double distance){
        Horse horse = new Horse(name, speed, distance);
        assertEquals(distance, horse.getDistance());
    }
    @ParameterizedTest
    @CsvSource({"Joke, 3"})
    void givenStaticMethodWithArgsAndFormulaDistancePlusSpeedMultiplyRandom(String name, double speed){
        Horse horse = new Horse(name, speed);
        try(MockedStatic<Horse> mockStatic = Mockito.mockStatic(Horse.class)){
            mockStatic.when(()-> Horse.getRandomDouble(0.2d, 0.9d)).thenReturn(0.5);
            horse.move();
            mockStatic.verify(()-> Horse.getRandomDouble(0.2d, 0.9d));
        }
        assertEquals(1.5, horse.getDistance());
    }

}
