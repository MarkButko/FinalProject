package mark.butko.controller.command;

import java.util.HashMap;
import java.util.Map;

/**
 * Class that holds instances of commands saved in map where key is last part of
 * requested URI
 * 
 * @author markg
 *
 */
public class CommandProvider {
	private static Map<String, Command> commands = new HashMap<>();

	static {
		commands.put("login", new LoginCommand());
		commands.put("logout", new LogoutCommand());
		commands.put("leaveProposal", new LeaveProposalCommand());
		commands.put("comments", new CommentsCommand());
		commands.put("comment", new CommentCommand());
		commands.put("proposals", new CustomerPageCommand());
		commands.put("sign-up", new SignUpCommand());
		commands.put("welcomePage", new WelcomeServletPageCommand());
		commands.put("users", new AdminPageCommand());
		commands.put("change_role", new ChangeRoleCommand());
		commands.put("acceptedProposals", new MasterPageCommand());
		commands.put("perform", new PerformProposalCommand());
		commands.put("managerProposals", new ManagerPageCommand());
		commands.put("accept", new AcceptProposalCommand());
		commands.put("reject", new RejectProposalCommand());
	}

	private CommandProvider() {
	};

	/**
	 * 
	 * 
	 * @param key last part of requested URI
	 * @return
	 */
	public static Command getCommand(String key) {
		return commands.get(key);
	}

	/**
	 * returns {@link WelcomeServletPageCommand} if map does nt contain key passed
	 * as parameter
	 * 
	 * @param key
	 * @return
	 */
	public static Command getCommandOrWelcomePage(String key) {
		return commands.containsKey(key) ? commands.get(key) : commands.get("welcomePage");
	}

}
