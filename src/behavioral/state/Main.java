package behavioral.state;

public class Main {

    void main() {
        Elevator elevator = new Elevator();

        elevator.requestFloor(5);

        elevator.arrive();

        elevator.closeDoor();

        elevator.requestFloor(2);

        elevator.arrive();

        elevator.openDoor();
    }
}