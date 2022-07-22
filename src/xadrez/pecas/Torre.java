package xadrez.pecas;

import boardgame.Position;
import boardgame.Tabuleiro;
import xadrez.Cores;
import xadrez.PecaDeXadrez;

public class Torre extends PecaDeXadrez{

	public Torre(Tabuleiro tabuleiro, Cores cores) {
		super(tabuleiro, cores);
	}

	@Override
	public String toString() {
		return "T";
	}
	
	@Override
	public boolean[][] movimentosPossiveis() {
		boolean[][] mat = new boolean[getTabuleiro().getLinhas()][getTabuleiro().getColunas()];
		
		Position p = new Position(0, 0);
		
		//verifica as posicoes para acima
		p.setValor(position.getLinha()-1, position.getColuna());
		while(getTabuleiro().posicaoExistente(p) && !getTabuleiro().verificandoPeca(p)){
			mat[p.getLinha()][p.getColuna()] = true;
			p.setLinha(p.getLinha()-1);
		}
		if (getTabuleiro().posicaoExistente(p) && verificaSeHaPecaAdversarioNaPosicao(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}
		
		//posicoes para esquerda
		p.setValor(position.getLinha(), position.getColuna()-1);
		while(getTabuleiro().posicaoExistente(p) && !getTabuleiro().verificandoPeca(p)){
			mat[p.getLinha()][p.getColuna()] = true;
			p.setColuna(p.getColuna()-1);
		}
		if (getTabuleiro().posicaoExistente(p) && verificaSeHaPecaAdversarioNaPosicao(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}
		
		//posicoes para direita
		p.setValor(position.getLinha(), position.getColuna()+1);
		while(getTabuleiro().posicaoExistente(p) && !getTabuleiro().verificandoPeca(p)){
			mat[p.getLinha()][p.getColuna()] = true;
			p.setColuna(p.getColuna()+1);
		}
		if (getTabuleiro().posicaoExistente(p) && verificaSeHaPecaAdversarioNaPosicao(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}
		
		//posicoes para baixo
		p.setValor(position.getLinha()+1, position.getColuna());
		while(getTabuleiro().posicaoExistente(p) && !getTabuleiro().verificandoPeca(p)){
			mat[p.getLinha()][p.getColuna()] = true;
			p.setLinha(p.getLinha()+1);
		}
		if (getTabuleiro().posicaoExistente(p) && verificaSeHaPecaAdversarioNaPosicao(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}

		return mat;
	}
}
