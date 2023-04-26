import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class facultyTest {

    @Test
    void assignFacultyDetail() {
        faculty ft = new faculty();
        assertEquals(1,ft.assignFacultyDetail());
    }

    @Test
    void changePassword() {
        faculty ft = new faculty();
        assertEquals(1,ft.changePassword("654"));
    }

    @Test
    void changeMobile() {
        faculty ft = new faculty();
        assertEquals(1,ft.changeMobile("pass1"));
    }

    @Test
    void offerCourse() {
        faculty ft = new faculty();
        assertEquals(0,ft.offerCourse("cs554","2020","cs,ee",6.2));
    }


    @Test
    void giveGrades() {
    }

    @Test
    void getGrades() {
    }
}