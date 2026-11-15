package behavioral.command;


class Light {
    public void on() {
        IO.println("Light is ON");
    }

    public void off() {
        IO.println("Light is ON");
    }
}

class Fan {
    public void on() {
        IO.println("Fan is ON");
    }

    public void off() {
        IO.println("Fan is ON");
    }
}

interface Command {
    void execute();
}


// ---------- Concrete commands ----------
class LightOnCommand implements Command {
    private final Light light;
    LightOnCommand(Light light) { this.light = light; }
    @Override public void execute() { light.on(); }
}

class LightOffCommand implements Command {
    private final Light light;
    LightOffCommand(Light light) { this.light = light; }
    @Override public void execute() { light.off(); }
}

class FanOnCommand implements Command {
    private final Fan fan;
    FanOnCommand(Fan fan) { this.fan = fan; }
    @Override public void execute() { fan.on(); }
}

class FanOffCommand implements Command {
    private final Fan fan;
    FanOffCommand(Fan fan) { this.fan = fan; }
    @Override public void execute() { fan.off(); }
}

// ---------- Sender (invoker): holds a command, triggers it, never creates it ----------
class RemoteButton {
    private final Command command;

    RemoteButton(Command command) { this.command = command; }

    void press() { command.execute(); }
}

class CommandDemo {
    void main() {
        Light light = new Light();
        Fan fan = new Fan();
        // Client creates the commands and wires in the receivers
        RemoteButton lightOn  = new RemoteButton(new LightOnCommand(light));
        RemoteButton lightOff = new RemoteButton(new LightOffCommand(light));
        RemoteButton fanOn    = new RemoteButton(new FanOnCommand(fan));
        RemoteButton fanOff   = new RemoteButton(new FanOffCommand(fan));

        // Senders know only "Command", not Light or Fan
        lightOn.press();
        fanOn.press();
        lightOff.press();
        fanOff.press();
    }
}

