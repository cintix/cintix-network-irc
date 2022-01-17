/**
 * 
 */
package nl.oa.jirc.core.message;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import nl.oa.jirc.command.CommandFactory;
import nl.oa.jirc.command.CommandNotFoundException;
import nl.oa.jirc.command.InvalidCommand;
import nl.oa.jirc.core.ReservedWords;

/**
 * This class represents a recieved IRC message. This class starts the chain of
 * message parsing.
 * 
 * @author Marc de Kwant
 * 
 */
public class IRCMessage implements ICoreConstruct {

	private ICoreConstruct prefix;

	private ICoreConstruct command;

	private final Pattern ircMessageGlobalPattern = Pattern.compile(
			"^:([^:]*?) (.*?) (.*?)", Pattern.CASE_INSENSITIVE);

	private final Pattern ircMessageCommandOnlyPattern = Pattern.compile(
			"(.*?) (.*?)", Pattern.CASE_INSENSITIVE);

	private final Pattern ircMessageDDPrefixPattern = Pattern.compile("^:.*?",
			Pattern.CASE_INSENSITIVE);

	public IRCMessage() { }
	
	public IRCMessage(ICoreConstruct command) {
	    setCommand(command);
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see nl.oa.jirc.core.message.ICoreConstruct#parse(java.lang.String)
	 */
	public void parse(final String line) {
		// No need to go further is there is no line to parse
		if (line == null)
			return;
		if (ircMessageDDPrefixPattern.matcher(line).matches()) {
			// If the messageString is a full command string
			// then follow the if-branch.
			Matcher matcher = ircMessageGlobalPattern.matcher(line);
			if (matcher.matches()) {
				this.prefix = new IRCPrefix();
				this.prefix.parse(matcher.group(1));
				this.command = CommandFactory.getCommand(matcher.group(2));
				// We know what the command is. Now the command needs
				// to parse the command options.
				this.command.parse(matcher.group(3));
			}
		} else {
			Matcher matcher = ircMessageCommandOnlyPattern.matcher(line);
			if (matcher.matches()) {
				this.prefix = new IRCPrefix();
				try {
					this.command = CommandFactory.getCommand(matcher.group(1));
					if (this.command instanceof InvalidCommand) {
						// If it is an invalid command then do not process
						// and return the raw string.
					}
					this.command.parse(matcher.group(2));
				} catch (CommandNotFoundException e) {
					// if the command is not found then ignore this message...
				}
			}
		}
	}

	/**
	 * The parse method extracted a command. this command can be gotten through
	 * this method.
	 * 
	 * @return the command an ICoreConstruct
	 */
	public ICoreConstruct getCommand() {
		return this.command;
	}
	
	/**
     * @param command the command to set
     */
    public void setCommand(ICoreConstruct command) {
        this.command = command;
    }

    /**
	 * The parse method extracted a prefix. this prefix can be gotten through
	 * this method.
	 * 
	 * @return the prefix
	 */
	public ICoreConstruct getPrefix() {
		return this.prefix;
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		if (this.prefix == null && this.command == null) {
			return "";
		} else {
		    // Is this nessesary or is there always a prefix?
		    if (this.prefix == null) {
                return this.command.toString()
                + ReservedWords.CR + ReservedWords.LF;
		    } else {
		        return ReservedWords.DD + this.prefix.toString()
		        + ReservedWords.SPACE + this.command.toString()
		        + ReservedWords.CR + ReservedWords.LF;
		    }
		}
	}

}
