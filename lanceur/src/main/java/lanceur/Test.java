package lanceur;

import java.util.HashMap;

import core.Face;
import core.Ressource;

public class Test {

	public class Thread1 extends Thread {

		private Face arg;
		private Thread parentThread;

		public Thread1(Face arg) {
			super();
			parentThread = Thread.currentThread();
			this.arg = arg;
		}

		public void run() {
			System.out.println("TestThread : J'ai été lancé !");
			System.out.println("TestThread : L'ID de mon objet est : " + arg.hashCode());
			synchronized (arg) {
				HashMap<Ressource, Integer> ressources = new HashMap<Ressource, Integer>();
				ressources.put(Ressource.lunaryStone, 1);
				//arg.setRessourceGranted(ressources);
				arg.notify();
			}
			System.out.println("TestThread : End for me");
		}
	}

	public static void main(String args[]) {
		Face obj = new Face(Ressource.gold, false, 1);

		System.out.println("Main : J'ai crée mon objet : " + obj.hashCode());
		Test.Thread1 t = new Test().new Thread1(obj);
		System.out.println("Main : Création de mon fils");
		t.start();

		synchronized (obj) {
			try {
				obj.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		// attente que le thread se termine
		try {
			t.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		// Print du resultat final de obj
		System.out.println(obj);

		return;
	}
}
