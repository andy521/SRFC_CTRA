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

package net.sf.jinsim.request;

import java.nio.ByteBuffer;


import net.sf.jinsim.PacketType;
import net.sf.jinsim.response.CameraPositionResponse;
import net.sf.jinsim.types.InSimVector;


/**
 * @author Rob Heiser (jinsim@kerf.org)
 * @since 0.001
 */
public class SetDirectCameraRequest extends InSimRequest {
    static public final short ISS_SHIFTU        = 8;   // in SHIFT+U mode
    static public final short ISS_SHIFTU_HIGH   = 16;  // HIGH view
    static public final short ISS_SHIFTU_FOLLOW = 32;  // following car
    static public final short ISS_VIEW_OVERRIDE = 8192; // override user view

    private InSimVector       position;                // Position vector
    private short             heading;                 // 0 points along Y axis
    private short             pitch;                   // 0 means looking at horizon
    private short             roll;                    // 0 means no roll
    private byte              playerIndex;             // Player Index of car to view (0 = pole...)
    private byte              cameraType;              // InGameCam (as reported in StatePack)
    private float             fov;                     // 4-byte float : FOV in radians
    private short             time;                    // Time to get there (0 means instant + reset)
    private short             flags;                   // State Flags

    public SetDirectCameraRequest() {
    	super(PacketType.CAMERA_POSITION, 32);
    	position = new InSimVector(0,0,0);
    }

    public SetDirectCameraRequest(CameraPositionResponse cameraResponse) {
        this();
        this.setCameraType(cameraResponse.getCameraType());
        this.setFlags(cameraResponse.getFlags());
        this.setFov(cameraResponse.getFov());
        this.setHeading(cameraResponse.getHeading());
        this.setPitch(cameraResponse.getPitch());
        this.setPlayerIndex(cameraResponse.getPlayerIndex());
        this.setPosition(cameraResponse.getPosition());
        this.setRoll(cameraResponse.getRoll());
        this.setTime(cameraResponse.getTime());

    }

    public void assemble(ByteBuffer data) {
        super.assemble(data);
        data.put((byte)0);
        data.putInt(getPosition().getX());
        data.putInt(getPosition().getY());
        data.putInt(getPosition().getZ());
        data.putShort(getHeading());
        data.putShort(getPitch());
        data.putShort(getRoll());
        data.put(getPlayerIndex());
        data.put(getCameraType());
        data.putFloat(getFov());
        data.putShort(getTime());
        data.putShort(getFlags());
    }

    public String toString() {
        String retval = super.toString();

        retval += "Position: " + getPosition() + "\n";
        retval += "Heading: " + getHeading() + "\n";
        retval += "Pitch: " + getPitch() + "\n";
        retval += "Roll: " + getRoll() + "\n";
        retval += "Player index: " + getPlayerIndex() + "\n";
        retval += "Camera type: " + getCameraType() + "\n";
        retval += "Field of view: " + getFov() + "\n";
        retval += "Time: " + getTime() + "\n";
        retval += "Flags: " + getFlags() + "\n";

        return retval;
    }

    public byte getCameraType() {
        return cameraType;
    }

    public void setCameraType(byte cameraType) {
        this.cameraType = cameraType;
    }

    public short getFlags() {
        return flags;
    }

    public void setFlags(short flags) {
        this.flags = flags;
    }

    public float getFov() {
        return fov;
    }

    public void setFov(float fov) {
        this.fov = fov;
    }

    public short getHeading() {
        return heading;
    }

    public void setHeading(short heading) {
        this.heading = heading;
    }

    public short getPitch() {
        return pitch;
    }

    public void setPitch(short pitch) {
        this.pitch = pitch;
    }

    public byte getPlayerIndex() {
        return playerIndex;
    }

    public void setPlayerIndex(byte playerIndex) {
        this.playerIndex = playerIndex;
    }

    public InSimVector getPosition() {
        return position;
    }

    public void setPosition(InSimVector position) {
        this.position = position;
    }

    public short getRoll() {
        return roll;
    }

    public void setRoll(short roll) {
        this.roll = roll;
    }

    public short getTime() {
        return time;
    }

    public void setTime(short time) {
        this.time = time;
    }

}
