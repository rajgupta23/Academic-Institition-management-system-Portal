import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class adminTest {

    @Test
    void assignAdminDetail() {
        admin ad = new admin();
        assertEquals(0,ad.assignAdminDetail());
    }

    @Test
    void changePassword() {
        admin ad = new admin();
        assertEquals(0,ad.changePassword("pass1"));
    }

    @Test
    void changeMobile() {
        admin ad = new admin();
        assertEquals(0,ad.changeMobile("pass1"));
    }


    @Test
    void startSemester() {
        admin ad = new admin();
        assertEquals(0,ad.startSemester());
    }

    @Test
    void getGrades() {
        admin ad = new admin();
        assertEquals(0,ad.getGrades("wronginput"));
    }
}