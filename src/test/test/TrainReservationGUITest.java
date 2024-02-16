package src.test;

import org.junit.Assert;
import org.junit.Test;
import src.voyageur.TrainReservationGUI;

import java.lang.reflect.Method;

public class TrainReservationGUITest {

    @Test
    public void getDiscountRate_Student_ReturnsExpectedRate() throws Exception {
        TrainReservationGUI gui = new TrainReservationGUI();
        Method method = TrainReservationGUI.class.getDeclaredMethod("getDiscountRate", String.class);
        method.setAccessible(true); // Rend la méthode accessible
        double rate = (double) method.invoke(gui, "Student");
        Assert.assertEquals("Le taux de réduction pour les étudiants devrait être de 30%", 0.30, rate, 0.001);
    }
}
