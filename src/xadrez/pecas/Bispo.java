package xadrez.pecas;

import boardgame.Position;
import boardgame.Tabuleiro;
import xadrez.Cores;
import xadrez.PecaDeXadrez;

public class Bispo extends PecaDeXadrez{

	public Bispo(Tabuleiro tabuleiro, Cores cores) {
		super(tabuleiro, cores);
	}

	@Override
	public String toString() {
		return "B";
	}
	
	@Override
	public boolean[][] movimentosPossiveis() {
		boolean[][] mat = new boolean[getTabuleiro().getLinhas()][getTabuleiro().getColunas()];
		
		Position p = new Position(0, 0);
		
		//verifica as posicoes para NW
		p.setValor(position.getLinha()-1, position.getColuna()-1);
		while(getTabuleiro().posicaoExistente(p) && !getTabuleiro().verificandoPeca(p)){
			mat[p.getLinha()][p.getColuna()] = true;
			p.setValor(p.getLinha()-1, p.getColuna()-1);
		}
		if (getTabuleiro().posicaoExistente(p) && verificaSeHaPecaAdversarioNaPosicao(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}
		
		//posicoes para NE
		p.setValor(position.getLinha()-1, position.getColuna()+1);
		while(getTabuleiro().posicaoExistente(p) && !getTabuleiro().verificandoPeca(p)){
			mat[p.getLinha()][p.getColuna()] = true;
			p.setValor(p.getLinha()-1, p.getColuna()+1);
		}
		if (getTabuleiro().posicaoExistente(p) && verificaSeHaPecaAdversarioNaPosicao(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}
		
		//posicoes para SE
		p.setValor(position.getLinha()+1, position.getColuna()+1);
		while(getTabuleiro().posicaoExistente(p) && !getTabuleiro().verificandoPeca(p)){
			mat[p.getLinha()][p.getColuna()] = true;
			p.setValor(p.getLinha()+1, p.getColuna()+1);
		}
		if (getTabuleiro().posicaoExistente(p) && verificaSeHaPecaAdversarioNaPosicao(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}
		
		//posicoes para SW
		p.setValor(position.getLinha()+1, position.getColuna()-1);
		while(getTabuleiro().posicaoExistente(p) && !getTabuleiro().verificandoPeca(p)){
			mat[p.getLinha()][p.getColuna()] = true;
			p.setValor(p.getLinha()+1, p.getColuna()-1);
		}
		if (getTabuleiro().posicaoExistente(p) && verificaSeHaPecaAdversarioNaPosicao(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}

		return mat;
	}
}
