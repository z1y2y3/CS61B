package creatures;

import huglife.*;

import java.awt.*;
import java.util.List;
import java.util.Map;

public class Clorus extends Creature {
    /**
     * creates clorus with energy equal to E.
     * Cloruses have no restrictions on their maximum energy.
     */
    public Clorus(double e) {
        super("clorus");
        energy = e;
    }

    /**
     * creates a clorus with energy equal to 1.
     */
    public Clorus() {
        this(1);
    }

    /*The color() method for Cloruses should always return the color
    red = 34, green = 0, blue = 231.*/
    @Override
    public Color color() {
        return new Color(34, 0, 231);
    }

    /*Cloruses should lose 0.03 units of energy on a MOVE action.*/
    @Override
    public void move() {
        energy = energy - 0.03;
    }

    /*Cloruses should lose 0.01 units of energy on a STAY action.*/
    @Override
    public void stay() {
        energy = energy - 0.01;
    }

    /*
    If a Clorus attacks another creature, it should gain that creature’s energy.
    This should happen in the attack() method, not in chooseAction().
    You do not need to worry about making sure the attacked creature dies
    —the simulator does that for you.
    * */
    @Override
    public void attack(Creature c) {
        energy = energy + c.energy();
    }

    /*
    When a Clorus replicates, it keeps 50% of its energy.
    The other 50% goes to its offspring.
    No energy is lost in the replication process.
    * */
    @Override
    public Creature replicate() {
        energy = energy / 2;
        return new Clorus(energy);
    }


    /*
    Cloruses should obey exactly the following behavioral rules:
        1.If there are no empty squares, the Clorus will STAY
          (even if there are Plips nearby they could attack).
        2.Otherwise, if any Plips are seen, the Clorus will ATTACK one of them randomly.
        3.Otherwise, if the Clorus has energy greater than or equal to one,
          it will REPLICATE to a random empty square.
        4.Otherwise, the Clorus will MOVE to a random empty square.
    * */
    @Override
    public Action chooseAction(Map<Direction, Occupant> neighbors) {
        List<Direction> directionsOfEmpty = getNeighborsOfType(neighbors, "empty");
        if (directionsOfEmpty.isEmpty()) {
            return new Action(Action.ActionType.STAY);
        }
        List<Direction> directionsOfPlips = getNeighborsOfType(neighbors, "plip");
        if (!directionsOfPlips.isEmpty()) {
            Direction attackDir = HugLifeUtils.randomEntry(directionsOfPlips);
            return new Action(Action.ActionType.ATTACK, attackDir);
        }
        if (energy >= 1) {
            Direction replicateDir = HugLifeUtils.randomEntry(directionsOfEmpty);
            return new Action(Action.ActionType.REPLICATE, replicateDir);
        }

        Direction moveDir = HugLifeUtils.randomEntry(directionsOfEmpty);
        return new Action(Action.ActionType.MOVE, moveDir);
    }
}
