package behavioral.state;

public class DoorClosedState implements ElevatorState {

    @Override
    public void requestFloor(Elevator elevator, int floor) {

        System.out.println("Starting towards floor " + floor);

        elevator.setTargetFloor(floor);

        elevator.setState(new MovingState());
    }

    @Override
    public void openDoor(Elevator elevator) {

        System.out.println("Opening door.");

        elevator.setState(new DoorOpenState());
    }

    @Override
    public void closeDoor(Elevator elevator) {
        System.out.println("Door already closed.");
    }

    @Override
    public void arrive(Elevator elevator) {
        System.out.println("Not moving.");
    }
}
