package teamFarnsworth.Application.Controllers;

import java.util.Set;

import teamFarnsworth.Domain.GymHours;
import teamFarnsworth.Domain.Person;
import teamFarnsworth.Domain.Qualification;
import teamFarnsworth.Domain.Trainer;
import teamFarnsworth.Handlers.MemberHandler;

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
	

}
