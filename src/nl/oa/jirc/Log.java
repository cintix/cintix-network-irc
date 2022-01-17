package nl.oa.jirc;

import java.io.IOException;

/**
 *
 * @author Michael Martinsen
 */
public class Log {

    private final String name;

    public Log(String name) {
        this.name = name;
    }

    public boolean isDebugEnabled() {
        return true;
    }

    public void debug(String string) {
        System.out.println("[DEBUG][" + name + "]: " + string);
    }

    public void info(String ready_to_recieve_messages) {
        System.out.println("[INFO][" + name + "]: " + ready_to_recieve_messages);
    }

    public boolean isErrorEnabled() {
        return true;
    }

    public void error(String we_are_interrupted, InterruptedException e) {
        System.err.println("[ERROR][" + name + "]: " + we_are_interrupted);
        e.printStackTrace();
    }

    public void error(String no_servername_andor_port_defined) {
        System.err.println("[ERROR][" + name + "]: " + no_servername_andor_port_defined);
    }

    public void error(String one_of_the_sockets_was_not_reachable, IOException e) {
        System.err.println("[ERROR][" + name + "]: " + one_of_the_sockets_was_not_reachable);
        e.printStackTrace();
    }

}
