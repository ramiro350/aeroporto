package entities;

import java.util.concurrent.ThreadLocalRandom;

public class AirTraffic {
	public synchronized void Start() {
		Pista p = new Pista();
		AirCraft aircraft = new AirCraft(p);

		boolean aeroportoAberto = true;
		boolean podeDecolar = true;
		boolean podeAterrissar = true;

		while (aeroportoAberto) {
			if (p.acidente) {
				System.out.println("------------------------------------------------------------------------------------------------------------------");
				System.out.println("Ocorreu um acidente!");
				System.out.println("------------------------------------------------------------------------------------------------------------------");

				System.exit(0);
			}

			boolean rand = ThreadLocalRandom.current().nextBoolean();
			if (rand) {
				if (podeDecolar) {
					if (p.avioesPraDecolar.size() < 9) {
						if (p.PistaAerea.size() <= 0) {
							aircraft.decolar = true;
							aircraft.Start();
						}
					} else {
						podeDecolar = false;
					}
				}
			} else {
				if (podeAterrissar) {
					if (p.avioesPraAterrissar.size() < 9) {
						if (p.PistaAerea.size() <= 0) {
							aircraft.decolar = false;
							aircraft.Start();
						}
					} else {
						podeAterrissar = false;
					}
				}
			}
		}
	}
}
