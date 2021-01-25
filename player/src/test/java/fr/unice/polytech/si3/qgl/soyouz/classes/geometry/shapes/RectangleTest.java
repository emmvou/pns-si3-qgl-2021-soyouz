package fr.unice.polytech.si3.qgl.soyouz.classes.geometry.shapes;

import fr.unice.polytech.si3.qgl.regatta.cockpit.ICockpit;
import fr.unice.polytech.si3.qgl.soyouz.Cockpit;
import fr.unice.polytech.si3.qgl.soyouz.classes.gameflow.goals.RegattaGoal;
import fr.unice.polytech.si3.qgl.soyouz.classes.parameters.InitGameParameters;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RectangleTest {

    Rectangle rectangle;

    @BeforeEach
    void init() {
        Cockpit cockpit = new Cockpit();

        cockpit.initGame("{\"goal\": {\n" +
                "    \"mode\": \"REGATTA\",\n" +
                "    \"checkpoints\": [\n" +
                "      {\n" +
                "        \"position\": {\n" +
                "          \"x\": 1000,\n" +
                "          \"y\": 0,\n" +
                "          \"orientation\": 0\n" +
                "        },\n" +
                "   \"shape\": {\n" +
                "       \"type\": \"rectangle\",\n" +
                "       \"width\": 3,\n" +
                "       \"height\": 6,\n" +
                "       \"orientation\": 0\n" +
                "        }\n" +
                "      }\n" +
                "    ]\n" +
                "  }}");

        InitGameParameters ip = cockpit.getIp();
        RegattaGoal rg = (RegattaGoal) ip.getGoal();
        rectangle = (Rectangle) rg.getCheckpoints()[0].getShape();
    }

    @Test
    void getWidthTest() {
        assertEquals(3, rectangle.getWidth());
    }

    @Test
    void getHeightTest() {
        assertEquals(6, rectangle.getHeight());
    }

    @Test
    void getOrientationTest() {
        assertEquals(0, rectangle.getOrientation());
    }
}