package edu.umd.cs.semesterproject.model;

// An action that changes the volume settib
public class VolumeAction extends Action {

    private int mStartVolume;
    private int mEndVolume;
    private VolumeMode mStartMode;
    private VolumeMode mEndMode;

    public VolumeAction() {
        super(Type.VOLUME);
    }

    public VolumeAction(int startVolume, int endVolume, VolumeMode startMode, VolumeMode endMode) {
        super(Type.VOLUME);

        mStartVolume = startVolume;
        mEndVolume = endVolume;
        mStartMode = startMode;
        mEndMode = endMode;
    }

    public int getStartVolume() {
        return mStartVolume;
    }

    public void setStartVolume(int startVolume) {
        mStartVolume = startVolume;
    }

    public int getEndVolume() {
        return mEndVolume;
    }

    public void setEndVolume(int endVolume) {
        mEndVolume = endVolume;
    }

    public VolumeMode getStartMode() {
        return mStartMode;
    }

    public void setStartMode(VolumeMode startMode) {
        mStartMode = startMode;
    }

    public VolumeMode getEndMode() {
        return mEndMode;
    }

    public void setEndMode(VolumeMode endMode) {
        mEndMode = endMode;
    }

    public enum VolumeMode {
        VIBRATE, NOVIBRATE
    }

    public void setStartVibrate(boolean vib){
        if (vib){
            mStartMode = VolumeMode.VIBRATE;
        }
        else{
            mStartMode = VolumeMode.NOVIBRATE;
        }
    }

    public void setEndVibrate(boolean vib){
        if (vib){
            mEndMode = VolumeMode.VIBRATE;
        }
        else{
            mEndMode = VolumeMode.NOVIBRATE;
        }
    }

    public boolean getStartVibrate(){
        if (mStartMode.equals(VolumeMode.VIBRATE)){
            return true;
        }
        else if (mStartMode.equals(VolumeMode.NOVIBRATE)){
            return false;
        }
        else{
            return false;
        }
    }

    public boolean getEndVibrate(){
        if (mEndMode.equals(VolumeMode.VIBRATE)){
            return true;
        }
        else if (mEndMode.equals(VolumeMode.NOVIBRATE)){
            return false;
        }
        else{
            return false;
        }
    }

}
