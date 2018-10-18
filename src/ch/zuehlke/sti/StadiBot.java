/**
 * Copyright (c) 2001-2018 Mathew A. Nelson and Robocode contributors
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://robocode.sourceforge.net/license/epl-v10.html
 */
package ch.zuehlke.sti;


import robocode.AdvancedRobot;
import robocode.HitRobotEvent;
import robocode.ScannedRobotEvent;

import java.awt.*;


/**
 * SpinBot - a sample robot by Mathew Nelson.
 * <p>
 * Moves in a circle, firing hard when an enemy is detected.
 *
 * @author Mathew A. Nelson (original)
 * @author Flemming N. Larsen (contributor)
 */
public class StadiBot extends AdvancedRobot {

    private Battleground battleground;

    /**
     * SpinBot's run method - Circle
     */
    public void run() {
        // Set colors
        setBodyColor(Color.black);
        setGunColor(Color.yellow);
        setRadarColor(Color.gray);
        setScanColor(Color.green);

        battleground = new Battleground(getBattleFieldHeight(), getBattleFieldWidth());

        // Loop forever
        while (true) {
            battleground.setMyPosition(new Position(getX(), getY()));
            // Tell the game that when we take move,
            // we'll also want to turn right... a lot.
            setTurnRight(100);
            // Limit our speed to 5
            setMaxVelocity(5);
            // Start moving (and turning)
            ahead(10);
            // Repeat.
        }
    }

    /**
     * onScannedRobot: Fire hard!
     */
    public void onScannedRobot(ScannedRobotEvent e) {
        battleground.reportSpottedEnemy(e.getName(), e.getBearing(), e.getDistance());
        if (e.isSentryRobot()) {
            // do nothing
        } else {
            fire(3);
        }
    }

    /**
     * onHitRobot:  If it's our fault, we'll stop turning and moving,
     * so we need to turn again to keep spinning.
     */
    public void onHitRobot(HitRobotEvent e) {
        if (e.getBearing() > -10 && e.getBearing() < 10) {
            fire(3);
        }
        if (e.isMyFault()) {
            turnRight(10);
        }
    }
}
