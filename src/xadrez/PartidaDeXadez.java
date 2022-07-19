package xadrez;

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
	
	private void colocandoNovaPeca(char coluna, int linha, PecaDeXadrez peca) {
		tabuleiro.lugarDaPeca(peca, new PosicaoXadrez(coluna, linha).toPosition());
	}
	
	private void iniciarPartida() {
		colocandoNovaPeca('b', 6, new Torre(tabuleiro, Cores.WHITE));
		colocandoNovaPeca('e', 8, new Rei(tabuleiro, Cores.BLACK));
		colocandoNovaPeca('e', 1, new Rei(tabuleiro, Cores.WHITE));
		}
}
