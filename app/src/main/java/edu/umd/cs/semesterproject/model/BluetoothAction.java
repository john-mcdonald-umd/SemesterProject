package edu.umd.cs.semesterproject.model;

// An action that edits the Bluetooth settings.
public class BluetoothAction extends Action {

    private boolean startAction, endAction;

    public BluetoothAction() {
        super(Type.BLUETOOTH);
    }

    public BluetoothAction(boolean start, boolean end){
        super(Type.VOLUME);

        startAction = start;
        endAction = end;
    }

    public void setStartAction(boolean start){
        startAction = start;
    }

    public boolean getStartAction(){
        return startAction;
    }

    public void setEndAction(boolean end){
        endAction = end;
    }

    public boolean getEndAction(){
        return endAction;
    }
}
