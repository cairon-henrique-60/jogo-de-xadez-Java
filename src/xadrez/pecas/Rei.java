package xadrez.pecas;

import boardgame.Position;
import boardgame.Tabuleiro;
import xadrez.Cores;
import xadrez.PartidaDeXadez;
import xadrez.PecaDeXadrez;

public class Rei extends PecaDeXadrez{
	
	private PartidaDeXadez partidaDeXadez;

	public Rei(Tabuleiro tabuleiro, Cores cores, PartidaDeXadez partidaDeXadez) {
		super(tabuleiro, cores);
		this.partidaDeXadez = partidaDeXadez;
	}

	@Override
	public String toString() {
		return "K";
	}
	
	private boolean podeMover(Position position) {
		PecaDeXadrez p = (PecaDeXadrez)getTabuleiro().peca(position);
		return p == null || p.getCores() != getCores();
	}
	
	//metodo para testar se a torre está apta para a jogada Rook 
	private boolean testTorreParaRock(Position position) {
		PecaDeXadrez p = (PecaDeXadrez)getTabuleiro().peca(position);
		return p != null && p instanceof Torre && p.getCores() == getCores() && p.getContadorDeMovimentos() == 0;
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
		
		// #jogada especial Castling
		if (getContadorDeMovimentos() == 0 && !partidaDeXadez.getCheck()) {
			//#Jogada especial do lado do Rei
			Position posTorre1 = new Position(position.getLinha(), position.getColuna()+3);
			if (testTorreParaRock(posTorre1)) {
				Position p1 = new Position(position.getLinha(), position.getColuna()+1);
				Position p2 = new Position(position.getLinha(), position.getColuna()+2);
				if (getTabuleiro().peca(p1) == null && getTabuleiro().peca(p2) == null) {
					mat[position.getLinha()][position.getColuna()+2] = true;
				}
			}
			//#Jogada especial do lado da Rainha
			Position posTorre2 = new Position(position.getLinha(), position.getColuna()-4);
			if (testTorreParaRock(posTorre2)) {
				Position p1 = new Position(position.getLinha(), position.getColuna()-1);
				Position p2 = new Position(position.getLinha(), position.getColuna()-2);
				Position p3 = new Position(position.getLinha(), position.getColuna()-3);
				if (getTabuleiro().peca(p1) == null && getTabuleiro().peca(p2) == null && getTabuleiro().peca(p3) == null) {
					mat[position.getLinha()][position.getColuna()-2] = true;
				}
			}
		}
		
		return mat;
	}
}
