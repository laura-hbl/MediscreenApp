package com.mediscreen.patientAssessment.util;

import com.mediscreen.patientAssessment.util.AgeCalculator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.*;

public class AgeCalculatorTest {

    private static final AgeCalculator ageCalculator = new AgeCalculator();

    @Test
    @Tag("ValidAge")
    @DisplayName("If birthDate corresponds to a ten years old person, age should be ten")
    public void givenATenYearsOldBirthDate_whenGetAge_thenReturnCorrectAge() {
        LocalDate birthDate = LocalDate.now().minusYears(10);

        int age = ageCalculator.getAge(birthDate);

        assertThat(age).isEqualTo(10);
    }

    @Test
    @Tag("ValidAge")
    @DisplayName("If birthDate corresponds to a 6 month old baby, age should be one")
    public void givenABabyBirthDate_whenGetAge_thenAgeShouldBeEqualToOne() {
        LocalDate birthDate = LocalDate.now().minusMonths(5);

        int age = ageCalculator.getAge(birthDate);

        assertThat(age).isEqualTo(1);
    }

    @Test
    @Tag("Exception")
    @DisplayName("If birthDate is in future, getAge should raise an IllegalArgumentException")
    public void givenAFutureBirthDate_whenGetAge_thenIllegalArgumentExceptionIsThrown() {
        int age = 5;
        LocalDate birthDate = LocalDate.now().plusYears(age);

        assertThatIllegalArgumentException().isThrownBy(() -> ageCalculator.getAge(birthDate));
    }

    @Test
    @Tag("Exception")
    @DisplayName("For a negative birthDate, getAge should raise an IllegalArgumentException")
    public void givenANegativeBirthDate_whenGetAge_thenIllegalArgumentExceptionIsThrown() {
        int age = -5;
        LocalDate birthDate = LocalDate.now().minusYears(age);

        assertThatIllegalArgumentException().isThrownBy(() -> ageCalculator.getAge(birthDate));
    }

    @Test
    @Tag("Exception")
    @DisplayName("For a null birthDate, getAge should raise an NullPointerException")
    public void givenANullBirthDate_whenGetAge_thenNullPointerExceptionIsThrown() {
        LocalDate birthDate = null;

        assertThatNullPointerException().isThrownBy(() -> ageCalculator.getAge(birthDate));
    }
}
