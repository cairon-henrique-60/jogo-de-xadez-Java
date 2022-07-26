package xadrez;

import boardgame.Peca;
import boardgame.Position;
import boardgame.Tabuleiro;

public abstract class PecaDeXadrez extends Peca{
	private Cores cores;
	private int contadorDeMovimentos;

	public PecaDeXadrez(Tabuleiro tabuleiro, Cores cores) {
		super(tabuleiro);
		this.cores = cores;
	}

	public Cores getCores() {
		return cores;
	}
	
	public int getContadorDeMovimentos() {
		return contadorDeMovimentos;
	}
	
	public void aumentandoContatorDeMovimento() {
		contadorDeMovimentos++;
	}
	
	public void diminuindoContadoDeMovimento() {
		contadorDeMovimentos--;
	}

	// convertento o position para PosicaoXadrez 
	public PosicaoXadrez getPosicaoXadrez() {
		return PosicaoXadrez.haPosicao(position);
	}
	
	protected boolean verificaSeHaPecaAdversarioNaPosicao(Position position) {
		PecaDeXadrez p = (PecaDeXadrez)getTabuleiro().peca(position);
		return p != null && p.getCores() != cores;
	}
	
}
