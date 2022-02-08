import java.util.Random;

public class Elf implements Runnable {

	enum ElfState {
		WORKING, TROUBLE, AT_SANTAS_DOOR,EXIT
	};
	private boolean exit = false;

	private ElfState state;
	/**
	 * The number associated with the Elf
	 */
	private int number;
	private Random rand = new Random();
	private SantaScenario scenario;
	private boolean flagAdd= true;

	public Elf(int number, SantaScenario scenario) {
		this.number = number;
		this.scenario = scenario;
		this.state = ElfState.WORKING;
	}


	public ElfState getState() {
		return state;
	}

	/**
	 * Santa might call this function to fix the trouble
	 * @param state
	 */
	public void setState(ElfState state) {
		this.state = state;
	}


	@Override
	public void run() {
		while (!exit) {
      // wait a day
  		try {
  			Thread.sleep(100);
  		} catch (InterruptedException e) {
  			// TODO Auto-generated catch block
  			e.printStackTrace();
  		}
			switch (state) {
			case WORKING: {
				// at each day, there is a 1% chance that an elf runs into
				// trouble.
//				if(!(scenario.day==50))
//				{
//					
				
				if (rand.nextDouble() < 0.01) {
					state = ElfState.TROUBLE;
				}
//				}
//				else
//				{
//				
//				
//				if(scenario.day==50)
//				{
//				this.setState(Elf.ElfState.TROUBLE);
//				}
//				}
				flagAdd=true;
				
				break;
			}
			case TROUBLE:
				// FIXME: if possible, move to Santa's door
				if(flagAdd)
				{
				scenario.elvesWaiting.add(this);
				flagAdd=false;
				}
				break;
			case AT_SANTAS_DOOR:
				// FIXME: if feasible, wake up Santa
				//scenario.santa.wakeUpSanta();

				flagAdd=true;
				scenario.santa.wakeUpSanta();
				break;
			}
		}
	}

	/**
	 * Report about my state
	 */
	public void report() {
		System.out.println("Elf " + number + " : " + state);
	}
	public void stop() {
		exit=true;
		this.setState(state.EXIT);
	}

}
