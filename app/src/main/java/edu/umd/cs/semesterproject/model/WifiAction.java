package edu.umd.cs.semesterproject.model;

// An action that changes the Wifi settings of the device.
public class WifiAction extends Action {

    private boolean startAction, endAction;

    public WifiAction(boolean start, boolean end){
        super(Type.WIFI);

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
