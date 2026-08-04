package behavioral.state;

public interface ElevatorState {

    void requestFloor(Elevator elevator, int floor);

    void openDoor(Elevator elevator);

    void closeDoor(Elevator elevator);

    void arrive(Elevator elevator);

}
