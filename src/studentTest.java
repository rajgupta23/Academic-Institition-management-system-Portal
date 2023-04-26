import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class studentTest {

    @Test
    void changeMobile() {
        student s = new student();
        assertEquals(1,s.changeMobile("655"));
    }
    @Test
    void changePassword() {
        student s = new student();
        assertEquals(1,s.changePassword("655"));
    }

    @Test
    void assignStudentDetail() {
        student s = new student();
        assertEquals(1,s.assignStudentDetail());
    }

    @Test
    void getCredits() {
        student s = new student();
        assertEquals(0.0,s.getCredits(2020,2)); // zero is required since a new student haven;t enroled for anything
    }

    @Test
    void getGradePoint() {
        student s = new student();
        assertEquals(0,s.getGradePoint(1990,1));
    }

    @Test
    void getCurrentSemCredits() {
        student s = new student();
        assertEquals(0,s.getCurrentSemCredits());
    }

    @Test
    void getCreditLimit() {
        student s = new student();
        assertEquals(0, s.getCreditLimit());
    }
    @Test
    void showCourses() {
        student s = new student();
        assertEquals(1,s.showCourses());
    }

}