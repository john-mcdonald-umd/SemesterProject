package edu.umd.cs.semesterproject.model;

/**
 * Created by James on 5/7/2017.
 */

public class BluetoothAction extends Action {

    private boolean startAction, endAction;

    public BluetoothAction(boolean start, boolean end){
        super();

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
