package entities;

public class AirCraft {
	Pista p;

	public boolean decolar;

	AirCraft(Pista p) {
		this.p = p;
	}

	CabineComando cabine;

	public synchronized void Start() {
		if (p.pistaDeTaxiamento.size() >= 3) {
			while (p.pistaDeTaxiamento.size() > 0 && p.pistaDeTaxiamento.size() <= 3) {
				if (decolar) {
					try {
						Thread.sleep(7000);

						cabine = new CabineComando(p);

						cabine.decolar = true;

						p.avioesPraDecolar.add(cabine);

						if (p.pistaDeTaxiamento.size() > 0) {
							p.pistaDeTaxiamento.remove(0);
						}

						Thread t = new Thread(cabine);
						t.start();
					} catch (InterruptedException e) {
						System.out.println("Erro na aircraft: " + e);
					}
				} else {
					try {
						Thread.sleep(7000);

						cabine = new CabineComando(p);

						cabine.decolar = false;

						p.avioesPraAterrissar.add(cabine);

						if (p.pistaDeTaxiamento.size() > 0) {
							p.pistaDeTaxiamento.remove(0);
						}

						Thread t = new Thread(cabine);
						t.start();
					} catch (InterruptedException e) {
						System.out.println("Erro na aircraft: " + e);
					}
				}
			}
		} else {
			if (decolar) {
				try {
					Thread.sleep(7000);

					cabine = new CabineComando(p);

					cabine.decolar = true;

					p.avioesPraDecolar.add(cabine);

					p.pistaDeTaxiamento.add(cabine);

					Thread t = new Thread(cabine);
					t.start();
				} catch (InterruptedException e) {
					System.out.println("Erro na aircraft: " + e);
				}
			} else {
				try {
					Thread.sleep(7000);

					cabine = new CabineComando(p);

					cabine.decolar = false;

					p.avioesPraAterrissar.add(cabine);

					p.pistaDeTaxiamento.add(cabine);

					Thread t = new Thread(cabine);
					t.start();
				} catch (InterruptedException e) {
					System.out.println("Erro na aircraft: " + e);
				}
			}
		}
	}
}
