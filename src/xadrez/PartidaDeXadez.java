package xadrez;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import boardgame.Peca;
import boardgame.Position;
import boardgame.Tabuleiro;
import xadrez.pecas.Rei;
import xadrez.pecas.Torre;

public class PartidaDeXadez {
	private int turno;
	private Cores jogadorAtual;
	private Tabuleiro tabuleiro;
	private boolean check;
	private boolean checkMate;
	
	private List<Peca> pecasNoTabuleiro = new ArrayList<>();
	private List<Peca> pecasCapturadas = new ArrayList<>();

	
	public PartidaDeXadez() {
		tabuleiro = new Tabuleiro(8, 8);
		turno = 1;
		jogadorAtual = Cores.WHITE;
		iniciarPartida();
	}
	
	public int getTurno() {
		return turno;
	}
	
	public Cores getJogadorAtual() {
		return jogadorAtual;
	}
	
	public boolean getCheck() {
		return check;
	}
	
	public boolean getCheckMate() {
		return checkMate;
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
	
	public boolean[][] movimentosPossiveis(PosicaoXadrez posicaoDePatida) {
		Position position = posicaoDePatida.toPosition();
		validandoPosicao(position);
		return tabuleiro.peca(position).movimentosPossiveis();
	}
	
	public PecaDeXadrez performaceParaMover(PosicaoXadrez posicaoDeOrigem, PosicaoXadrez posicaoDeDestino) {
		Position partida = posicaoDeOrigem.toPosition();
		Position destino = posicaoDeDestino.toPosition();
		validandoPosicao(partida);
		validandoPosicaoDeDestino(partida, destino);
		Peca capturaPeca = Mover(partida, destino);
		/*
		 * condicional para testar se o movimento 
		 * do jogador colocou o mesmo em
		 * posicao de check, isso n�o � permitido no
		 * jogo de xadres
		 */		
		if (testarCheck(jogadorAtual)) {
			desfazerMovimento(partida, destino, capturaPeca);
			throw new ExcecoesXadrez("Voc� n�o pode se colocar em posi��o de check");
		}
		//testando se o oponente est� em posi��o de check
		check = (testarCheck(oponente(jogadorAtual))) ? true : false;
		
		if (testarCheckMate(oponente(jogadorAtual))) {
			checkMate = true;
		}
		else {
		proximaPartida();
		}
		
		return (PecaDeXadrez)capturaPeca;
	}
	
	private Peca Mover(Position partida, Position destino) {
		PecaDeXadrez p = (PecaDeXadrez)tabuleiro.removerPeca(partida);
		p.aumentandoContatorDeMovimento();
		Peca capturaPeca = tabuleiro.removerPeca(destino);
		tabuleiro.lugarDaPeca(p, destino);
		//condicional para testar se o movimento capturou alguma pe�a adverss�ria
		if (capturaPeca != null) {
			pecasNoTabuleiro.remove(capturaPeca);
			pecasCapturadas.add(capturaPeca);
		}
		
		return  capturaPeca;
	}
	
	//metodo para desfazer o movimento
	private void desfazerMovimento(Position partida, Position destino, Peca capturaPeca) {
		PecaDeXadrez p = (PecaDeXadrez)tabuleiro.removerPeca(destino);
		p.diminuindoContadoDeMovimento();
		tabuleiro.lugarDaPeca(p, partida);
		
		if(capturaPeca != null) {
			tabuleiro.lugarDaPeca(capturaPeca, destino);
			pecasCapturadas.remove(capturaPeca);
			pecasNoTabuleiro.add(capturaPeca);
		}
	}
	
	private void validandoPosicao(Position position) {
		if (!tabuleiro.verificandoPeca(position)) {
			throw new ExcecoesXadrez("N�o existe peca na posicao de origem");
		}
		if (jogadorAtual != ((PecaDeXadrez)tabuleiro.peca(position)).getCores()) {
			throw new ExcecoesXadrez("A peca escolhida n�o � sua");
		}
		if (!tabuleiro.peca(position).verificaSeHaUmPossivelMovimento()) {
			throw new ExcecoesXadrez("N�o existe movimentos possiveis para a peca escolhida");
		}
	}
	
	private void validandoPosicaoDeDestino(Position partida, Position destino) {
		if (!tabuleiro.peca(partida).possibilidadesDeMovimentos(destino)) {
			throw new ExcecoesXadrez("A peca escolhida n�o pode se mover para a posic�o de destino");
		}
	}
	
	private void proximaPartida() {
		turno++;
		jogadorAtual = (jogadorAtual == Cores.WHITE) ? Cores.BLACK : Cores.WHITE;
	}
	
	/*
	 * metodo para devolver o oponente de uma cor
	 * exemeplo: se a cor for White, o oponente � black
	 */	
	private Cores oponente(Cores cor) {
		return (cor == Cores.WHITE) ? Cores.BLACK : Cores.WHITE;
	}
	
	//metodo para varrer as pe�as do tabuleiro para encontrar o Rei
	private PecaDeXadrez Rei(Cores cor) {
		List<Peca> list = pecasNoTabuleiro.stream().filter(x -> ((PecaDeXadrez)x).getCores() == cor).collect(Collectors.toList());
		for(Peca p : list) {
			if (p instanceof Rei) {
				return (PecaDeXadrez)p;
			}
		}
		throw new IllegalStateException("N�o o Rei da cor" + cor + " no tabuleiro!" );
	}
	
	//metodo para testar se o Rei est� em situa��o de check
	private boolean testarCheck(Cores cor) {
		Position posicaoDoRei = Rei(cor).getPosicaoXadrez().toPosition();
		List<Peca> pecasDoOponente = pecasNoTabuleiro.stream().filter(x -> ((PecaDeXadrez)x).getCores() == oponente(cor)).collect(Collectors.toList());
		for (Peca p : pecasDoOponente) {
			boolean[][] mat = p.movimentosPossiveis();
			if (mat[posicaoDoRei.getLinha()][posicaoDoRei.getColuna()]) {
				return true;
			}
		}
		return false;
	}
	
	//metodo para testar um check mate
	private boolean testarCheckMate(Cores cor) {
		if (!testarCheck(cor)) {
			return false;
		}
		List<Peca> list = pecasNoTabuleiro.stream().filter(x -> ((PecaDeXadrez)x).getCores() == cor).collect(Collectors.toList());
		for(Peca p : list) {
			boolean[][] mat = p.movimentosPossiveis();
			for(int i=0; i<tabuleiro.getLinhas(); i++) {
				for(int j=0; j<tabuleiro.getColunas(); j++) {
					if (mat[i][j]) {
						Position partida = ((PecaDeXadrez)p).getPosicaoXadrez().toPosition();
						Position destino = new Position(i, j);
						Peca capturaPeca = Mover(partida, destino);
						boolean testarCheck = testarCheck(cor);
						desfazerMovimento(partida, destino, capturaPeca);
						if (!testarCheck) {
							return false;
						}
					}
				}
			}
		}
		return true;
	}
	
	private void colocandoNovaPeca(char coluna, int linha, PecaDeXadrez peca) {
		tabuleiro.lugarDaPeca(peca, new PosicaoXadrez(coluna, linha).toPosition());
		pecasNoTabuleiro.add(peca);
	}
	
	private void iniciarPartida() {
		colocandoNovaPeca('h', 7, new Torre(tabuleiro, Cores.WHITE));
        colocandoNovaPeca('d', 1, new Torre(tabuleiro, Cores.WHITE));
        colocandoNovaPeca('e', 1, new Rei(tabuleiro, Cores.WHITE));

        colocandoNovaPeca('b', 8, new Torre(tabuleiro, Cores.BLACK));
        colocandoNovaPeca('a', 8, new Rei(tabuleiro, Cores.BLACK));
		}
}
