package fr.unice.polytech.si3.qgl.soyouz.classes.gameflow;

import fr.unice.polytech.si3.qgl.soyouz.classes.parameters.InitGameParameters;
import fr.unice.polytech.si3.qgl.soyouz.classes.parameters.NextRoundParameters;

/**
 * Class to save all parameters of the current game.
 */
public class GameState
{
    InitGameParameters ip;
    NextRoundParameters np;

    /**
     * Constructor.
     *
     * @param ip The parameters at the creation of the game.
     * @param np The parameters of the current round.
     */
    public GameState(InitGameParameters ip, NextRoundParameters np)
    {
        this.ip = ip;
        this.np = np;
    }

    public InitGameParameters getIp() {
        return ip;
    }

    public NextRoundParameters getNp() {
        return np;
    }
}
