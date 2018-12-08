package teamFarnsworth.Application.Controllers;

import java.util.Set;

import teamFarnsworth.Domain.Users.Person;

public interface UserController<E> {

	public Set<E> getUsers();
	public Person getUser(Person person);
	public Person getUser(String ID);
	public Person addUser(Person person);
	public Person removeUser(Person person);
	
	@Override
	public String toString();
	public String getAllUsers();
	
}
