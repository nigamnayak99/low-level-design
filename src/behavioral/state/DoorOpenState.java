package behavioral.state;

public class DoorOpenState implements ElevatorState {
    @Override
    public void requestFloor(Elevator elevator, int floor) {
        System.out.println("Please close the door first.");
    }

    @Override
    public void openDoor(Elevator elevator) {
        System.out.println("Door already open.");
    }

    @Override
    public void closeDoor(Elevator elevator) {
        System.out.println("Closing door.");

        elevator.setState(new DoorClosedState());
    }

    @Override
    public void arrive(Elevator elevator) {
        System.out.println("Already arrived.");
    }
}
