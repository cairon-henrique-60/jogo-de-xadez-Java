package xadrez;

import boardgame.Peca;
import boardgame.Position;
import boardgame.Tabuleiro;

public abstract class PecaDeXadrez extends Peca{
	private Cores cores;

	public PecaDeXadrez(Tabuleiro tabuleiro, Cores cores) {
		super(tabuleiro);
		this.cores = cores;
	}

	public Cores getCores() {
		return cores;
	}

	protected boolean verificaSeHaPecaAdversarioNaPosicao(Position position) {
		PecaDeXadrez p = (PecaDeXadrez)getTabuleiro().peca(position);
		return p != null && p.getCores() != cores;
	}
	
}
