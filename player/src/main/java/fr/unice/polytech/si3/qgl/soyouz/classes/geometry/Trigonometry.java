package fr.unice.polytech.si3.qgl.soyouz.classes.geometry;

import fr.unice.polytech.si3.qgl.soyouz.classes.utilities.Pair;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

/**
 * A class to do some math/trigonometric stuff. Ewww.
 */
public class Trigonometry {

    // TODO POSSIBILITE DE PASSER LA PAIR EN CLE POUR POUVOIR AVOIR DIVERSES VITESSES POSSIBLES AVEC DES ANGLES SIMILAIRES
    static HashMap<Pair<Integer, Integer>, Double> leftTurnPossibilities = new HashMap<>();
    static HashMap<Pair<Integer, Integer>, Double> rightTurnPossibilities = new HashMap<>();

    /**
     * Determine the orientation and the linear speed of the boat in a specific configuration.
     *
     * @param activeOarNb The number of active oar.
     * @param totalOarNb The total number of oar.
     * @return a pair that contains all the information.
     */
    //TODO maybe bad concept
    static Double oarLinearSpeed(int activeOarNb, int totalOarNb){
        return (double) (165 * activeOarNb / totalOarNb);
    }

    /**
     * Determine the angle of rotation of a specific combination of oar.
     *
     * @param activeLeftOar The number of active oar on the left side of the boat.
     * @param activeRightOar The number of active oar on the right side of the boat.
     * @param totalOarNb The total number of oar.
     * @return the angle of the rotation.
     */
    static Double rotatingSpeed(int activeLeftOar, int activeRightOar, int totalOarNb) {
        return Math.PI*(activeRightOar - activeLeftOar)/totalOarNb;
    }

    /**
     * Determine every angle of rotation possible according to the game parameters.
     * Then they are stocked into two maps, one for each side (right/left).
     *
     * @param nbSailor The number of sailor on the boat.
     * @param nbOarLeft The number of oar on the left side of the boat.
     * @param nbOarRight The number of oar on the rignt side of the boat.
     */
    //TODO OAR ONLY, does not take in count the future RUDDER
    //TODO OAR ARE SYMETRIC SO LEFT = RIGHT :: REFACTO
    static void setTurnPossibilities(int nbSailor, int nbOarLeft, int nbOarRight) {
        for (int i = 0; i <= nbOarRight && i <= nbSailor; i++) {
            for (int j = 0; j <= nbOarLeft && j <= nbSailor; j++) {
                    if (i > j && i + j <= nbSailor)
                        leftTurnPossibilities.put(Pair.of(j, i), rotatingSpeed(j, i, nbOarLeft + nbOarRight));
                    if (i < j && i + j <= nbSailor)
                        rightTurnPossibilities.put(Pair.of(j, i), rotatingSpeed(j, i, nbOarLeft + nbOarRight));
            }
        }
    }

    // TODO A REFACTO
    static void findOptOarConfig(int nbSailor, int nbOarLeft, int nbOarRight, Pair<Double, Double> opt) {
        Pair<Integer, Integer> optimal = null;
        Pair<Double, Double> config = null;
        double diff = 0.0;
        setTurnPossibilities(nbSailor, nbOarLeft, nbOarRight);
        if (opt.second > 0) {
            for (Map.Entry<Pair<Integer, Integer>, Double> entry : leftTurnPossibilities.entrySet()) {
                double vl = oarLinearSpeed(entry.getKey().first + entry.getKey().second, nbOarLeft + nbOarRight);
                double vr = entry.getValue();
                double difference = (opt.first - vl) + (opt.second - vr);
                if (diff == 0 || difference < diff) {
                    optimal = entry.getKey();
                    config = Pair.of(vl, vr);
                    diff = difference;
                }
            }
        } else {
            for (Map.Entry<Pair<Integer, Integer>, Double> entry : rightTurnPossibilities.entrySet()) {
                double vl = oarLinearSpeed(entry.getKey().first + entry.getKey().second, nbOarLeft + nbOarRight);
                double vr = entry.getValue();
                double difference = opt.first % vl + opt.second % vr;
                if (diff == 0 || difference < diff) {
                    optimal = entry.getKey();
                    config = Pair.of(vl, vr);
                    diff = difference;
                }
            }
        }
        System.out.println(optimal);
        System.out.println(config);
        System.out.println(diff);
    }
}
    