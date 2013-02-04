/* ***** BEGIN LICENSE BLOCK *****
 * Version: MPL 1.1/GPL 2.0/LGPL 2.1
 *
 * The contents of this file are subject to the Mozilla Public License Version
 * 1.1 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 * http://www.mozilla.org/MPL/
 *
 * Software distributed under the License is distributed on an "AS IS" basis,
 * WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License
 * for the specific language governing rights and limitations under the
 * License.
 *
 * The Original Code is the JInSim Library.
 *
 * The Initial Developer of the Original Code is Rob Heiser.
 *
 * Portions created by Rob Heiser are Copyright (C) 2006
 * Rob Heiser. All Rights Reserved.
 *
 * Contributor(s):
 *
 *   Rob Heiser <jinsim@kerf.org>
 *
 * Alternatively, the contents of this file may be used under the terms of
 * either the GNU General Public License Version 2 or later (the "GPL"), or
 * the GNU Lesser General Public License Version 2.1 or later (the "LGPL"),
 * in which case the provisions of the GPL or the LGPL are applicable instead
 * of those above. If you wish to allow use of your version of this file only
 * under the terms of either the GPL or the LGPL, and not to allow others to
 * use your version of this file under the terms of the MPL, indicate your
 * decision by deleting the provisions above and replace them with the notice
 * and other provisions required by the GPL or the LGPL. If you do not delete
 * the provisions above, a recipient may use your version of this file under
 * the terms of any one of the MPL, the GPL or the LGPL.
 *
 * ***** END LICENSE BLOCK ***** */

package net.sf.jinsim.response;

import java.nio.ByteBuffer;


import net.sf.jinsim.PacketType;
import net.sf.jinsim.types.InSimTime;


/**
 * @author Rob Heiser (jinsim@kerf.org)
 * @version 0.001
 */

public class ResultResponse extends PlayerResponse {
	
    private String          userName;
    private String          nickname;
    private String          plate;
    private String          carName;
    private InSimTime       totalTime;
    private InSimTime       bestLapTime;
    private short           lapsDone;
    private short           playerFlags;
    private byte            confirmationFlags;
    private byte            numberPitStops;
    private byte            resultPosition;
    private byte            totalResults;

    ResultResponse() {
    	super(PacketType.RESULT_CONFIRMED);
    }

    public void construct(ByteBuffer buffer) {
    	super.construct(buffer);
        userName = getString(buffer, 24);
        nickname = getString(buffer, 24);
        plate = getString(buffer, 8);
        carName = getString(buffer, 4);
        totalTime = new InSimTime(buffer);
        bestLapTime = new InSimTime(buffer);
        buffer.position(buffer.position()+1);
        numberPitStops = buffer.get();
        confirmationFlags = buffer.get();
        buffer.position(buffer.position()+1);
        lapsDone = buffer.getShort();
        playerFlags = buffer.getShort();
        resultPosition = buffer.get();
        totalResults = buffer.get();
        buffer.position(buffer.position()+2);
    }

    public String toString() {
        return super.toString() +
        ", User name: " + getUserName() +
        ", Nickname: " + getNickname() + 
        ", Plate: " + getPlate() +
        ", Car name: " + getCarName() +
        ", Laps completed: " + getLapsDone() +
        ", Flags: " + getPlayerFlags() +
        ", Confirmation flags: " + getConfirmationFlags() +
        ", Pit stops: " + getNumberPitStops() +
        ", Total race time: " + getTotalTime() +
        ", Best lap time: " + getBestLapTime() +
        ", Result position: " + getResultPosition() +
        ", Total results: " + getTotalResults();
    }

    public InSimTime getBestLapTime() {
        return bestLapTime;
    }


    public String getCarName() {
        return carName;
    }

    public byte getConfirmationFlags() {
        return confirmationFlags;
    }


    /**
     * Returns a short representation of the "player flags" set for the player that this ResultResponse represents.
     * 
     * @return The flags set for this player as a short value.
     * @see net.sf.jinsim.RaceTrackingResponse
     */
    public short getPlayerFlags() {
        return playerFlags;
    }

    public short getLapsDone() {
        return lapsDone;
    }

    public String getNickname() {
        return nickname;
    }

    public byte getNumberPitStops() {
        return numberPitStops;
    }

    public String getPlate() {
        return plate;
    }

    public byte getResultPosition() {
        return resultPosition;
    }


    public InSimTime getTotalTime() {
        return totalTime;
    }

    public byte getTotalResults() {
        return totalResults;
    }

    public String getUserName() {
        return userName;
    }

}
