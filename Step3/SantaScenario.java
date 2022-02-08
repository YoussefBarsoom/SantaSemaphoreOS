import java.util.ArrayList;
import java.util.List;



public class SantaScenario {

	public Santa santa;
	public List<Elf> elves;
	public List<Reindeer> reindeers;
	public boolean isDecember;
	public int day =0;
	public List<Elf> elvesWaiting = new ArrayList<Elf>();
	public static void main(String args[]) {
		SantaScenario scenario = new SantaScenario();
		scenario.isDecember = false;
		// create the participants
		// Santa
		scenario.santa = new Santa(scenario);
		Thread th = new Thread(scenario.santa);
		th.start();
		// The elves: in this case: 10
		scenario.elves = new ArrayList<>();
		for(int i = 0; i != 10; i++) {
			Elf elf = new Elf(i+1, scenario);
			scenario.elves.add(elf);
			th = new Thread(elf);
			th.start();
		}
		// The reindeer: in this case: 9
		scenario.reindeers = new ArrayList<>();
		for(int i=0; i != 9; i++) {
			Reindeer reindeer = new Reindeer(i+1, scenario);
			scenario.reindeers.add(reindeer);
			th = new Thread(reindeer);
			th.start();
		}
		// now, start the passing of time
		for(; scenario.day < 500; scenario.day++) {
			// wait a day
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			if(scenario.elvesWaiting.size()>2)
			{
				if(scenario.santa.elvesAtDoor.isEmpty())
				{
					for(int i=0;i<3;i++)
					{
					scenario.elvesWaiting.get(0).setState(Elf.ElfState.AT_SANTAS_DOOR);
					scenario.santa.gotoDoor(scenario.elvesWaiting.remove(0));	
					}
//					scenario.santa.wakeUpSanta();
				}
			}
			// turn on December
			
		
			if (scenario.day > (365 - 31)) {
				scenario.isDecember = true;
			}
			if(scenario.day==370)
			{
				scenario.santa.stop();
				for (Elf elf : scenario.elves) {
					elf.stop();
				}
				for (Reindeer reindeer  : scenario.reindeers) {
					reindeer.stop();
				}
			}
			// print out the state:
			System.out.println("***********  Day " + scenario.day+ " *************************");
			scenario.santa.report();
			for(Elf elf: scenario.elves) {
				elf.report();
			}
			for(Reindeer reindeer: scenario.reindeers) {
				reindeer.report();
			}
		}
	}
	
	
	
}
