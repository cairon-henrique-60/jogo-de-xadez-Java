package aplicacao;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import xadrez.ExcecoesXadrez;
import xadrez.PartidaDeXadez;
import xadrez.PecaDeXadrez;
import xadrez.PosicaoXadrez;

public class Programa {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		PartidaDeXadez partidaDeXadez = new PartidaDeXadez();
		List<PecaDeXadrez> captura = new ArrayList<>();
		
		while (true) {
			try {
				UI.limpandoTerminal();
				UI.printXabrez(partidaDeXadez, captura);
				System.out.println();
				System.out.println();
				System.out.print("Partida: ");
				PosicaoXadrez partida = UI.LerPosicaoDoXadrez(sc);
				
				boolean[][] movimentosPossiveis = partidaDeXadez.movimentosPossiveis(partida);
				UI.limpandoTerminal();
				UI.printPartida(partidaDeXadez.getPecas(), movimentosPossiveis);
				System.out.println();
				System.out.println();
				System.out.print("Destino: ");
				PosicaoXadrez destino = UI.LerPosicaoDoXadrez(sc);
		
				PecaDeXadrez capturaPeca = partidaDeXadez.performaceParaMover(partida, destino);
				
				if (capturaPeca != null) {
					captura.add(capturaPeca);
				}
			}
			catch (ExcecoesXadrez e) {
				System.out.println(e.getMessage());
				sc.nextLine();
			}
			catch (InputMismatchException e) {
				System.out.println(e.getMessage());
				sc.nextLine();
			}
		}
	}

}
