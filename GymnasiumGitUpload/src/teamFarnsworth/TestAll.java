package teamFarnsworth;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import junit.framework.JUnit4TestAdapter;

import teamFarnsworth.Testing.Application.Accessors.PersonFactoryTest;
import teamFarnsworth.Testing.Application.Builders.PersonBuilderTest;
import teamFarnsworth.Testing.Application.Controllers.CustomerControllerTest;
import teamFarnsworth.Testing.Application.Controllers.EquipmentControllerTest;
import teamFarnsworth.Testing.Application.Controllers.ExerciseControllerTest;
import teamFarnsworth.Testing.Application.Controllers.ManagerControllerTest;
import teamFarnsworth.Testing.Application.Controllers.RoutineControllerTest;
import teamFarnsworth.Testing.Application.Controllers.TrainerControllerTest;
import teamFarnsworth.Testing.Application.Controllers.UserControllerFactoryTest;
import teamFarnsworth.Testing.Application.Controllers.WorkoutClassControllerTest;
import teamFarnsworth.Testing.Domain.AddressTest;
import teamFarnsworth.Testing.Domain.CustomerTest;
import teamFarnsworth.Testing.Domain.DurationTest;
import teamFarnsworth.Testing.Domain.ExerciseTest;
import teamFarnsworth.Testing.Domain.GymHoursTest;
import teamFarnsworth.Testing.Domain.HealthInsuranceProviderTest;
import teamFarnsworth.Testing.Domain.ManagerTest;
import teamFarnsworth.Testing.Domain.PasswordTest;
import teamFarnsworth.Testing.Domain.QualificationTest;
import teamFarnsworth.Testing.Domain.RoutineTest;
import teamFarnsworth.Testing.Domain.TrainerTest;
import teamFarnsworth.Testing.Domain.WorkoutClassTest;
import teamFarnsworth.Testing.Domain.WorkoutSetTest;
import teamFarnsworth.Testing.Handlers.EquipmentHandlerTest;
import teamFarnsworth.Testing.Handlers.ExerciseHandlerTest;
import teamFarnsworth.Testing.Handlers.LoginHandlerTest;
import teamFarnsworth.Testing.Handlers.MemberHandlerTest;
import teamFarnsworth.Testing.Handlers.RoutineHandlerTest;
import teamFarnsworth.Testing.Handlers.WorkoutClassHandlerTest;

// This section declares all of the test classes in the program.
@RunWith (Suite.class)
@Suite.SuiteClasses ({ 
	PersonFactoryTest.class,
	PersonBuilderTest.class,
	CustomerControllerTest.class,
	EquipmentControllerTest.class,
	ExerciseControllerTest.class,
	ManagerControllerTest.class,
	RoutineControllerTest.class,
	TrainerControllerTest.class,
	UserControllerFactoryTest.class,
	WorkoutClassControllerTest.class,
	AddressTest.class,
	CustomerTest.class,
	DurationTest.class,
	ExerciseTest.class,
	GymHoursTest.class,
	HealthInsuranceProviderTest.class,
	ManagerTest.class,
	PasswordTest.class,
	QualificationTest.class,
	RoutineTest.class,
	TrainerTest.class,
	WorkoutClassTest.class,
	WorkoutSetTest.class,
	EquipmentHandlerTest.class,
	ExerciseHandlerTest.class,
	LoginHandlerTest.class,
	MemberHandlerTest.class,
	RoutineHandlerTest.class,
	WorkoutClassHandlerTest.class
})

public class TestAll {
	// Execution begins in main(). This test class executes a test runner that tells the tester if any fail.
	public static void main(String[] args) {
		junit.textui.TestRunner.run(suite());
	}
	
	// The suite() method helps when using JUnit 3 Test Runners or Ant.
	public static junit.framework.Test suite() {
		return new JUnit4TestAdapter(TestAll.class);
	}
}
