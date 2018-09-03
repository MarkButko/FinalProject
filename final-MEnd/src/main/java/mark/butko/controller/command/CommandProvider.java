package mark.butko.controller.command;

import java.util.HashMap;
import java.util.Map;

import mark.butko.model.service.CommentService;
import mark.butko.model.service.ProposalService;
import mark.butko.model.service.UserService;

public class CommandProvider {
	private static Map<String, Command> commands = new HashMap<>();

	static {
		commands.put("login", new LoginCommand(new UserService()));
		commands.put("logout", new LogoutCommand());
		commands.put("leaveProposal", new LeaveProposalCommand(new ProposalService()));
		commands.put("comments", new CommentsCommand(new CommentService()));
		commands.put("comment", new CommentCommand(new ProposalService()));
		commands.put("proposals", new CustomerPageCommand(new ProposalService()));
		commands.put("sign-up", new SignUpCommand());
		commands.put("welcomePage", new WelcomeServletPageCommand());
		commands.put("users", new AdminPageCommand(new UserService()));
		commands.put("change_role", new ChangeRoleCommand(new UserService()));
		commands.put("acceptedProposals", new MasterPageCommand(new ProposalService()));
		commands.put("perform", new PerformProposalCommand(new ProposalService(), new UserService()));
		commands.put("managerProposals", new ManagerPageCommand(new ProposalService()));
		commands.put("accept", new AcceptProposalCommand(new ProposalService()));
		commands.put("reject", new RejectProposalCommand(new ProposalService()));
	}

	private CommandProvider() {
	};

	public static Command getCommand(String key) {
		return commands.get(key);
	}

	public static Command getCommandOrWelcomePage(String key) {
		return commands.containsKey(key) ? commands.get(key) : commands.get("welcomePage");
	}

}
