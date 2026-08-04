package behavioral.state;


public class Elevator {

    private ElevatorState state;
    private int currentFloor = 0;
    private int targetFloor;

    public Elevator() {
        this.state = new IdleState();
    }

    public void setState(ElevatorState state) {
        this.state = state;
    }

    public int getCurrentFloor() {
        return currentFloor;
    }

    public void setCurrentFloor(int floor) {
        this.currentFloor = floor;
    }

    public int getTargetFloor() {
        return targetFloor;
    }

    public void setTargetFloor(int targetFloor) {
        this.targetFloor = targetFloor;
    }

    public void requestFloor(int floor) {
        state.requestFloor(this, floor);
    }

    public void openDoor() {
        state.openDoor(this);
    }

    public void closeDoor() {
        state.closeDoor(this);
    }

    public void arrive() {
        state.arrive(this);
    }

}
