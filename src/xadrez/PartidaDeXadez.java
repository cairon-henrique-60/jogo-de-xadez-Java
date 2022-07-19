package xadrez;

import boardgame.Position;
import boardgame.Tabuleiro;
import xadrez.pecas.Rei;
import xadrez.pecas.Torre;

public class PartidaDeXadez {
	private Tabuleiro tabuleiro;
	
	public PartidaDeXadez() {
		tabuleiro = new Tabuleiro(8, 8);
		iniciarPartida();
	}
	
	public PecaDeXadrez[][] getPecas(){
		PecaDeXadrez[][] mat = new PecaDeXadrez[tabuleiro.getLinhas()][tabuleiro.getColunas()];
		for (int i=0; i<tabuleiro.getLinhas(); i++) {
			for (int j=0; j<tabuleiro.getColunas(); j++) {
				mat[i][j] = (PecaDeXadrez) tabuleiro.peca(i, j);
			}
		}
		return mat;
	}
	
	private void iniciarPartida() {
		tabuleiro.lugarDaPeca(new Torre(tabuleiro, Cores.WHITE), new Position(2, 1));
		tabuleiro.lugarDaPeca(new Rei(tabuleiro, Cores.BLACK), new Position(0, 4));
		tabuleiro.lugarDaPeca(new Rei(tabuleiro, Cores.BLACK), new Position(7, 4));

	}
}
