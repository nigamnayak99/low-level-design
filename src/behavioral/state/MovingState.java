package behavioral.state;

public class MovingState implements ElevatorState {
    @Override
    public void requestFloor(Elevator elevator, int floor) {
        System.out.println("Already moving. Request ignored.");
    }

    @Override
    public void openDoor(Elevator elevator) {
        System.out.println("Cannot open door while moving.");
    }

    @Override
    public void closeDoor(Elevator elevator) {
        System.out.println("Door already closed.");
    }

    @Override
    public void arrive(Elevator elevator) {

        elevator.setCurrentFloor(elevator.getTargetFloor());

        System.out.println("Reached floor " + elevator.getCurrentFloor());

        elevator.setState(new DoorOpenState());

        elevator.openDoor();
    }
}
