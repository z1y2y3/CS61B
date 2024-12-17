package creatures;

import huglife.*;
import org.junit.Test;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class TestClorus {

    @Test
    public void testName() {
        /*
        All Cloruses must have a name equal to exactly “clorus” (no capitalization or spaces).
        */
        String expected = "clorus";
        List<Clorus> clorusList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            clorusList.add(new Clorus());
        }
        for (Clorus clorus : clorusList) {
            assertEquals(expected, clorus.name());
        }
    }

    @Test
    public void testBasics() {
        /*
        * Cloruses should lose 0.03 units of energy on a MOVE action.
          Cloruses should lose 0.01 units of energy on a STAY action.
        * */
        Clorus p = new Clorus(2);
        assertEquals(2, p.energy(), 0.01);
        assertEquals(new Color(34, 0, 231), p.color());
        p.move();
        assertEquals(1.97, p.energy(), 0.01);
        p.move();
        assertEquals(1.94, p.energy(), 0.01);
        p.stay();
        assertEquals(1.93, p.energy(), 0.01);
        p.stay();
        assertEquals(1.92, p.energy(), 0.01);
        p.move();
        assertEquals(1.89, p.energy(), 0.01);
        assertEquals(new Color(34, 0, 231), p.color());
    }

    @Test
    public void testChoose() {
        /*
        Cloruses should obey exactly the following behavioral rules:
            1.If there are no empty squares, the Clorus will STAY
              (even if there are Plips nearby they could attack).
            2.Otherwise, if any Plips are seen, the Clorus will ATTACK one of them randomly.
            3.Otherwise, if the Clorus has energy greater than or equal to one,
              it will REPLICATE to a random empty square.
            4.Otherwise, the Clorus will MOVE to a random empty square.
        * */
        // for rule 1
        Clorus p = new Clorus(10);
        HashMap<Direction, Occupant> surrounded = new HashMap<Direction, Occupant>();
        surrounded.put(Direction.TOP, new Impassible());
        surrounded.put(Direction.BOTTOM, new Impassible());
        surrounded.put(Direction.LEFT, new Impassible());
        surrounded.put(Direction.RIGHT, new Impassible());
        Action atualAction = p.chooseAction(surrounded);
        Action expctedAction = new Action(Action.ActionType.STAY);
        assertEquals(expctedAction, atualAction);

        HashMap<Direction, Occupant> surrounded11 = new HashMap<Direction, Occupant>();
        surrounded11.put(Direction.TOP, new Plip());
        surrounded11.put(Direction.BOTTOM, new Plip());
        surrounded11.put(Direction.LEFT, new Plip());
        surrounded11.put(Direction.RIGHT, new Plip());
        Action atualAction11 = p.chooseAction(surrounded11);
        Action expctedAction11 = new Action(Action.ActionType.STAY);
        assertEquals(expctedAction11, atualAction11);

        HashMap<Direction, Occupant> surrounded111 = new HashMap<Direction, Occupant>();
        surrounded111.put(Direction.TOP, new SampleCreature());
        surrounded111.put(Direction.BOTTOM, new Plip());
        surrounded111.put(Direction.LEFT, new Impassible());
        surrounded111.put(Direction.RIGHT, new Clorus());
        Action atualAction111 = p.chooseAction(surrounded111);
        Action expctedAction111 = new Action(Action.ActionType.STAY);
        assertEquals(expctedAction111, atualAction111);
        // for rule 2
        HashMap<Direction, Occupant> surrounded2 = new HashMap<Direction, Occupant>();
        surrounded2.put(Direction.TOP, new Empty());
        surrounded2.put(Direction.BOTTOM, new Impassible());
        surrounded2.put(Direction.LEFT, new Plip(2));
        surrounded2.put(Direction.RIGHT, new Impassible());
        Action atualAction2 = p.chooseAction(surrounded2);
        Action expctedAction2 = new Action(Action.ActionType.ATTACK, Direction.LEFT);
        assertEquals(expctedAction2, atualAction2);

        HashMap<Direction, Occupant> surrounded22 = new HashMap<Direction, Occupant>();
        surrounded22.put(Direction.TOP, new Plip(2));
        surrounded22.put(Direction.BOTTOM, new Empty());
        surrounded22.put(Direction.LEFT, new SampleCreature());
        surrounded22.put(Direction.RIGHT, new Clorus());
        Action atualAction22 = p.chooseAction(surrounded22);
        Action expctedAction22 = new Action(Action.ActionType.ATTACK, Direction.TOP);
        assertEquals(expctedAction22, atualAction22);
        // for rule 3
        HashMap<Direction, Occupant> surrounded3 = new HashMap<Direction, Occupant>();
        surrounded3.put(Direction.TOP, new Empty());
        surrounded3.put(Direction.BOTTOM, new Impassible());
        surrounded3.put(Direction.LEFT, new Clorus());
        surrounded3.put(Direction.RIGHT, new SampleCreature());
        Action atualAction3 = p.chooseAction(surrounded3);
        Action expctedAction3 = new Action(Action.ActionType.REPLICATE, Direction.TOP);
        assertEquals(expctedAction3, atualAction3);

        HashMap<Direction, Occupant> surrounded33 = new HashMap<Direction, Occupant>();
        surrounded33.put(Direction.TOP, new Clorus());
        surrounded33.put(Direction.BOTTOM, new SampleCreature());
        surrounded33.put(Direction.LEFT, new Empty());
        surrounded33.put(Direction.RIGHT, new SampleCreature());
        Action atualAction33 = p.chooseAction(surrounded33);
        Action expctedAction33 = new Action(Action.ActionType.REPLICATE, Direction.LEFT);
        assertEquals(expctedAction33, atualAction33);

        // for rule 4
        Clorus p4 = new Clorus(0.1);
        HashMap<Direction, Occupant> surrounded4 = new HashMap<Direction, Occupant>();
        surrounded4.put(Direction.TOP, new Empty());
        surrounded4.put(Direction.BOTTOM, new Impassible());
        surrounded4.put(Direction.LEFT, new Clorus());
        surrounded4.put(Direction.RIGHT, new SampleCreature());
        Action atualAction4 = p4.chooseAction(surrounded4);
        Action expctedAction4 = new Action(Action.ActionType.MOVE, Direction.TOP);
        assertEquals(expctedAction4, atualAction4);

        HashMap<Direction, Occupant> surrounded44 = new HashMap<Direction, Occupant>();
        surrounded44.put(Direction.TOP, new Clorus());
        surrounded44.put(Direction.BOTTOM, new SampleCreature());
        surrounded44.put(Direction.LEFT, new Empty());
        surrounded44.put(Direction.RIGHT, new SampleCreature());
        Action atualAction44 = p4.chooseAction(surrounded44);
        Action expctedAction44 = new Action(Action.ActionType.MOVE, Direction.LEFT);
        assertEquals(expctedAction44, atualAction44);
    }

}
