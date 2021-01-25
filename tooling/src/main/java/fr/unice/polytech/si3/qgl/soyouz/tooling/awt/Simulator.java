package fr.unice.polytech.si3.qgl.soyouz.tooling.awt;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.unice.polytech.si3.qgl.soyouz.Cockpit;
import fr.unice.polytech.si3.qgl.soyouz.classes.actions.GameAction;
import fr.unice.polytech.si3.qgl.soyouz.classes.actions.OarAction;
import fr.unice.polytech.si3.qgl.soyouz.classes.geometry.Position;
import fr.unice.polytech.si3.qgl.soyouz.classes.marineland.entities.Entity;
import fr.unice.polytech.si3.qgl.soyouz.classes.marineland.entities.onboard.Rame;
import fr.unice.polytech.si3.qgl.soyouz.classes.parameters.InitGameParameters;
import fr.unice.polytech.si3.qgl.soyouz.classes.parameters.NextRoundParameters;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

public class Simulator extends Frame
{
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    public Simulator() throws IOException
    {
        setTitle("Soyouz Simulator");
        setLayout(new BorderLayout());
        setSize(600, 600);

        var model = OBJECT_MAPPER.readValue(Files.readString(Path.of("initGameLong.json")), InitGameParameters.class);
        var cockpit = new Cockpit();
        cockpit.initGame(OBJECT_MAPPER.writeValueAsString(model));

        var btnNext = new Button("Next");
        add(btnNext, BorderLayout.NORTH);

        var canvas = new SimulatorCanvas(model);
        add(canvas, BorderLayout.CENTER);

        addWindowListener(new WindowAdapter()
        {
            @Override
            public void windowClosing(WindowEvent e)
            {
                dispose();
            }
        });

        btnNext.addActionListener(event ->
        {
            var np = new NextRoundParameters(model.getShip(), new Entity[0]);
            try
            {
                var res = OBJECT_MAPPER.readValue(cockpit.nextRound(OBJECT_MAPPER.writeValueAsString(np)), GameAction[].class);
                var activeOars = new ArrayList<Rame>();
                for (GameAction act : res)
                {
                    if (act instanceof OarAction)
                    {
                        activeOars.add((Rame)model.getShip().getEntityHere(act.getSailor().getGridPosition()).get());
                    }
                }
                var noars = model.getShip().getNumberOar();
                var oarFactor = 165.0 * activeOars.size() / noars;
                var windSpeed = 0;
                var dirSpeed = oarFactor + windSpeed;
                var vx = dirSpeed * Math.cos(model.getShip().getPosition().getOrientation());
                var vy = dirSpeed * Math.sin(model.getShip().getPosition().getOrientation());
                var activeOarsLeft = activeOars.stream().filter(o -> o.getX() == 0).count();
                var activeOarsRight = activeOars.size() - activeOarsLeft;
                var oarRot = (activeOarsRight - activeOarsLeft) * Math.PI / noars;
                model.getShip().setPosition(model.getShip().getPosition().add(vx, vy, oarRot));
            }
            catch (JsonProcessingException e)
            {
                e.printStackTrace();
            }
        });
    }
}
