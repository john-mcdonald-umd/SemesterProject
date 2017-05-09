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
        NORMAL, SILENT, VIBRATE
    }

}
