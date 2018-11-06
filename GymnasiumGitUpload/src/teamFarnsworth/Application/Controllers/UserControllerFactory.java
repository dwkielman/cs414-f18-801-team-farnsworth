package teamFarnsworth.Application.Controllers;

public class UserControllerFactory {

	public UserController<?> createController(String accountType) {
		UserController<?> controller = null;
		
		if (accountType.equals("Manager")) {
			controller = new ManagerController();
		} else if (accountType.equals("Trainer")) {
			controller = new TrainerController();
		} else if (accountType.equals("Customer")) {
			controller = new CustomerController();
		}
		
		return controller;
	}
}
