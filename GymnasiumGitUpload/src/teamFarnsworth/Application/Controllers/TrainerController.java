package teamFarnsworth.Application.Controllers;

import java.util.Set;

import teamFarnsworth.Domain.Entities.GymHours;
import teamFarnsworth.Domain.Users.Customer;
import teamFarnsworth.Domain.Users.Person;
import teamFarnsworth.Domain.Users.Qualification;
import teamFarnsworth.Domain.Users.Trainer;
import teamFarnsworth.Handlers.UserHandlers.MemberHandler;

public class TrainerController implements UserController<Trainer> {
	
	private MemberHandler mHandler;
	
	public TrainerController() {
		mHandler = MemberHandler.getInstance();
	}
	

	@Override
	public Set<Trainer> getUsers() {
		return mHandler.getTrainers();
	}

	@Override
	public Trainer getUser(Person person) {
		for (Trainer t : mHandler.getTrainers()) {
			if (t.equals(person)) {
				return t;
			}
		}
		return null;
	}
	
	@Override
	public Trainer getUser(String ID) {
		for (Trainer t : mHandler.getTrainers()){
			if (t.getID().equals(ID)){
				return t;
			}
		}
		return null;
	}

	@Override
	public Trainer addUser(Person person) {
		if (person instanceof Trainer) {
			Trainer t = (Trainer) person;
			mHandler.addTrainer(t);
			return t;
		}
		return null;
	}

	@Override
	public Trainer removeUser(Person person) {
		if (person instanceof Trainer) {
			Trainer t = (Trainer) person;
			mHandler.removeTrainer(t);
			return t;
		}
		return null;
	}


	@Override
	public String getAllUsers() {
		String returnString = "";
		for (Trainer t : mHandler.getTrainers()) {
			returnString += t.toStringBrief() + "\n";
		}
		return returnString;
	}
	
	public void setSchedule(Trainer t, GymHours gh) {
		for (Trainer trainer : mHandler.getTrainers()) {
			if (trainer.equals(t)) {
				trainer.addWorkHoursToSchedule(gh.getStartTime(), gh.getEndTime(), gh.getDay());
			}
		}
	}
	
	public void addQualification(Trainer t, Qualification q) {
		for (Trainer trainer : mHandler.getTrainers()) {
			if (trainer.equals(t)) {
				t.addQualification(q);
			}
		}
	}
	
	public void enrollCustomerWithTrainer(Trainer t, Customer c) {
			t.addCustomerToTrainer(c);
			c.enrollWithTrainer(t);
	}
	
	public void unenrollCustomerFromTrainer(Trainer t, Customer c) {
		if (t.getCustomers().contains(c)) {
			t.removeCustomer(c);
			c.unenrollFromTrainer();
		}
	}
	

}
