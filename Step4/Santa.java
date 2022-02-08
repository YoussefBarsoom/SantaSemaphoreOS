import java.util.ArrayList;

//import com.sun.org.apache.xml.internal.security.utils.HelperNodeList;


public class Santa implements Runnable {

	enum SantaState {SLEEPING, READY_FOR_CHRISTMAS, WOKEN_UP_BY_ELVES, WOKEN_UP_BY_REINDEER,EXIT};
	private SantaState state;
	public ArrayList <Elf>  elvesAtDoor= new ArrayList<Elf>();
	private boolean exit = false;
	public SantaScenario scenario;
	public Santa(SantaScenario scenario) {
		this.state = SantaState.SLEEPING;
		this.scenario=scenario;
	}
	
	
	@Override
	public void run() {
		while(!exit) {
			// wait a day...
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			switch(state) {
			case SLEEPING: // if sleeping, continue to sleep
				
				break;
			case WOKEN_UP_BY_ELVES: 
				// FIXME: help the elves who are at the door and go back to sleep
				try {
					this.scenario.goToDoor.acquire();
				
				while(!elvesAtDoor.isEmpty()){
					elvesAtDoor.remove(0).setState(Elf.ElfState.WORKING);
				
				}	
				state = state.SLEEPING;	
				this.scenario.goToDoor.release();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
			case WOKEN_UP_BY_REINDEER: 
				// FIXME: assemble the reindeer to the sleigh then change state to ready 
				break;
			case READY_FOR_CHRISTMAS: // nothing more to be done
				break;
			}
		}
		//state= SantaState.EXIT;
	}

	
	/**
	 * Report about my state
	 */
	public void report() {
		System.out.println("Santa : " + state);
	}
	
	public void stop() {
		exit=true;
		state=SantaState.EXIT;

	}
	public void gotoDoor(Elf elf) {
		elvesAtDoor.add(elf);
	}
	public void wakeUpSanta(){
		state=SantaState.WOKEN_UP_BY_ELVES;

	}
	
	
}
