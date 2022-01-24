package entities;

import java.time.LocalTime;
import java.util.Date;

public class CabineComando implements Runnable {
	Pista p;

	public boolean decolar;
	public Date data_nascimento;
	public Date data_fim;

	boolean pistaLivre = true;

	CabineComando(Pista p) {
		this.p = p;
	}

	@Override
	public void run() {
		Start();
	}

	public synchronized void Start() {
		if (p.avioesDecolaram.size() >= 9) {
			decolar = false;
		} else if (p.avioesAterrissaram.size() >= 9) {
			decolar = true;
		}

		if (decolar) {
			System.out.println("---------------------------------------------------------");

			data_nascimento = new Date(System.currentTimeMillis());
			System.out.print(Thread.currentThread().getName() + ": ");
			System.out.print(data_nascimento + ": ");
			System.out.println("Este avião vai decolar!");
			System.out.println("---------------------------------------------------------");

		} else {
			System.out.println("---------------------------------------------------------");

			data_nascimento = new Date(System.currentTimeMillis());
			System.out.print(Thread.currentThread().getName() + ": ");
			System.out.print(data_nascimento + ": ");
			System.out.println("Este avião vai aterrissar!");
			System.out.println("---------------------------------------------------------");

		}

		if (pistaLivre) {

			System.out.println("Pista livre");
		}

		while (!pistaLivre) {
			try {
				System.out.println(Thread.currentThread().getName() + ": esperando a pista!");

				p.PistaAerea.add(this);

				wait();

				pistaLivre = false;
				p.isFree = false;
				notifyAll();

				System.out.println(Thread.currentThread().getName() + ": saiu da espera da pista!");

				for (int i = 0; i < p.PistaAerea.size(); i++) {
					if (p.PistaAerea.get(i) == this) {
						p.PistaAerea.remove(this);
					}
				}
			} catch (InterruptedException e) {
				System.out.println("Erro na inicialização: " + e);
			}
		}

		if (decolar) {
			Decolando();
		} else {
			NoAr();
		}
	}

	public synchronized void Decolando() {
		try {
			OcupaPista();
			Thread.sleep(5000);

			for (int i = 0; i < p.pistaDeTaxiamento.size(); i++) {
				if (p.pistaDeTaxiamento.get(i) == this) {
					p.pistaDeTaxiamento.remove(this);
				}
			}

			for (int i = 0; i < p.PistaAerea.size(); i++) {
				if (p.PistaAerea.get(i) == this) {
					p.PistaAerea.remove(this);
				}
			}

			System.out.println("=========================================================");
			data_fim = new Date(System.currentTimeMillis());
			System.out.print(Thread.currentThread().getName() + ": ");
			System.out.print(data_fim + ": ");
			System.out.println("Decolou");
			System.out.println("=========================================================");
			p.avioesDecolaram.add(this);

			Aviao a = new Aviao(Thread.currentThread().getName(), true, data_nascimento, data_fim);

			p.todosOsAvioes.add(a);
			LiberaPista();
		} catch (InterruptedException e) {
			System.out.println("Erro na decolagem: " + e);
		}
	}

	public synchronized void NoAr() {
		if (pistaLivre) {
			Aterrissando();
		} else {
			try {
				Thread.sleep(30000);

				if (pistaLivre) {
					Aterrissando();
				} else {
					p.acidente = true;
				}
			} catch (InterruptedException e) {
				System.out.println("Erro no voo: " + e);
			}
		}
	}

	public synchronized void Aterrissando() {
		try {
			OcupaPista();

			Thread.sleep(10000);

			Thread.sleep(5000);

			for (int i = 0; i < p.pistaDeTaxiamento.size(); i++) {
				if (p.pistaDeTaxiamento.get(i) == this) {
					p.pistaDeTaxiamento.remove(this);
				}
			}

			for (int i = 0; i < p.PistaAerea.size(); i++) {
				if (p.PistaAerea.get(i) == this) {
					p.PistaAerea.remove(this);
				}
			}

			System.out.println("=========================================================");
			data_fim = new Date(System.currentTimeMillis());
			System.out.print(Thread.currentThread().getName() + ": ");
			System.out.print(data_fim + ": ");
			System.out.println("Aterrissou");
			System.out.println("=========================================================");

			p.avioesAterrissaram.add(this);

			Aviao a = new Aviao(Thread.currentThread().getName(), false, data_nascimento, data_fim);

			p.todosOsAvioes.add(a);

			LiberaPista();
		} catch (InterruptedException e) {
			System.out.println("Erro na aterrissagem: " + e);
		}
	}

	public synchronized void OcupaPista() {
		if (!pistaLivre) {
			p.acidente = true;
		}

		pistaLivre = false;
		p.isFree = false;
		notifyAll();

		System.out.println(Thread.currentThread().getName() + ": ocupou a pista!");
		if (!p.isFree) {

			System.out.println("Pista ocupada");
		}
	}

	public synchronized void LiberaPista() {
		pistaLivre = true;
		p.isFree = true;
		notifyAll();

		System.out.println(Thread.currentThread().getName() + ": liberou a pista!");
		if (pistaLivre) {

			System.out.println("Pista livre");
		}

		if (p.avioesDecolaram.size() >= 9 && p.avioesAterrissaram.size() >= 9 && p.pistaDeTaxiamento.size() <= 0) {
			System.out.println(
					"------------------------------------------------------------------------------------------------------------------");
			System.out.println("As operações foram concluídas!");
			System.out.println(
					"------------------------------------------------------------------------------------------------------------------");

			for (int i = 0; i < p.todosOsAvioes.size(); i++) {
				if (p.todosOsAvioes.get(i).decolar) {
					System.out.println(
							"------------------------------------------------------------------------------------------------------------------");
					System.out.print("Nome: " + p.todosOsAvioes.get(i).nome);
					System.out.print(" Decolou. ");
					System.out.print("Nascimento: " + p.todosOsAvioes.get(i).data_nascimento);
					System.out.println(" Fim: " + p.todosOsAvioes.get(i).data_fim);
					System.out.println(
							"------------------------------------------------------------------------------------------------------------------");
				} else {
					System.out.println(
							"------------------------------------------------------------------------------------------------------------------");
					System.out.print("Nome: " + p.todosOsAvioes.get(i).nome);
					System.out.print(" Aterrissou. ");
					System.out.print("Inicío: " + p.todosOsAvioes.get(i).data_nascimento);
					System.out.println(" Fim: " + p.todosOsAvioes.get(i).data_fim);
					System.out.println(
							"------------------------------------------------------------------------------------------------------------------");
				}
			}

			System.exit(0);
		}
	}
}