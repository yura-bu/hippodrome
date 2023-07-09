
import org.junit.jupiter.api.Test;
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
                () -> new Horse(name, 0, 0)
        );
        assertEquals("Name cannot be null.", exception.getMessage());
    }
    @ParameterizedTest
    @ValueSource(strings = {"", " ", "\t","\n"})
    public void getHorseBlankName(String name){
        Throwable exception = assertThrows(
                IllegalArgumentException.class,
                () -> new Horse(name, 0, 0));
        assertEquals("Name cannot be blank.", exception.getMessage());
    }
    @ParameterizedTest
    @ValueSource(ints = {-1})
    public void getHorseNegativeSpeed(int speed){
        Throwable exception = assertThrows(
                IllegalArgumentException.class,
                () -> new Horse("Fast", speed, 0));
        assertEquals("Speed cannot be negative.", exception.getMessage());
    }
    @ParameterizedTest
    @ValueSource(ints = {-1})
    public void getHorseNegativeDistance(int distance){
        Throwable exception = assertThrows(
                IllegalArgumentException.class,
                () -> new Horse("Fast", 0, distance));
        assertEquals("Distance cannot be negative.", exception.getMessage());
    }

    @ParameterizedTest
    @CsvSource({"Jock, 30, 40"})
    public void getName(String name, int speed, int distance){
        Horse horse = new Horse(name, speed, distance);
        assertEquals("Jock", horse.getName());
    }
    @ParameterizedTest
    @CsvSource({"Jock, 30, 40"})
    public void getSpeed(String name, int speed, int distance){
        Horse horse = new Horse(name, speed, distance);
        assertEquals(30, horse.getSpeed());
    }
    @ParameterizedTest
    @CsvSource({"Jock, 30, 40"})
    public void getDistance(String name, int speed, int distance){
        Horse horse = new Horse(name, speed, distance);
        assertEquals(40, horse.getDistance());
    }
    @Test
    void givenStaticMethodWithArgs(){
        Horse horse = new Horse("Jock", 30, 40);
        try(MockedStatic<Horse> mockStatic = Mockito.mockStatic(Horse.class)){
            mockStatic.when(()-> Horse.getRandomDouble(0.2d, 0.9d)).thenReturn(0.5);
            horse.move();
            mockStatic.verify(()-> Horse.getRandomDouble(0.2d, 0.9d));
        }

    }
}
