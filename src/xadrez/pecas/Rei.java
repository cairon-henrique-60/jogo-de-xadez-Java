package xadrez.pecas;

import boardgame.Position;
import boardgame.Tabuleiro;
import xadrez.Cores;
import xadrez.PecaDeXadrez;

public class Rei extends PecaDeXadrez{

	public Rei(Tabuleiro tabuleiro, Cores cores) {
		super(tabuleiro, cores);
	}

	@Override
	public String toString() {
		return "R";
	}
	
	private boolean podeMover(Position position) {
		PecaDeXadrez p = (PecaDeXadrez)getTabuleiro().peca(position);
		return p == null || p.getCores() != getCores();
	}

	@Override
	public boolean[][] movimentosPossiveis() {
		boolean[][] mat = new boolean[getTabuleiro().getLinhas()][getTabuleiro().getColunas()];
		Position p = new Position(0, 0);
		
		//posicoes para cima
		p.setValor(position.getLinha()-1, position.getColuna());
		if(getTabuleiro().posicaoExistente(p) && podeMover(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}
		
		//posicoes para baixo
		p.setValor(position.getLinha()+1, position.getColuna());
		if(getTabuleiro().posicaoExistente(p) && podeMover(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}
		
		//posicoes para esquerda
		p.setValor(position.getLinha(), position.getColuna()-1);
		if(getTabuleiro().posicaoExistente(p) && podeMover(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}
		
		//posicoes para esquerda
		p.setValor(position.getLinha(), position.getColuna()+1);
		if(getTabuleiro().posicaoExistente(p) && podeMover(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}
		
		//posicoes para nw
		p.setValor(position.getLinha()-1, position.getColuna()-1);
		if(getTabuleiro().posicaoExistente(p) && podeMover(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}
	
		//posicoes para ne
		p.setValor(position.getLinha()-1, position.getColuna()+1);
		if(getTabuleiro().posicaoExistente(p) && podeMover(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}
		
		//posicoes para ne
		p.setValor(position.getLinha()-1, position.getColuna()-1);
		if(getTabuleiro().posicaoExistente(p) && podeMover(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}
		
		//posicoes para sw
		p.setValor(position.getLinha()+1, position.getColuna()-1);
		if(getTabuleiro().posicaoExistente(p) && podeMover(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}
		
		//posicoes para sw
		p.setValor(position.getLinha()+1, position.getColuna()+1);
		if(getTabuleiro().posicaoExistente(p) && podeMover(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}
		
		return mat;
	}
}
