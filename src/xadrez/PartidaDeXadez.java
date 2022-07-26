package xadrez;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import boardgame.Peca;
import boardgame.Position;
import boardgame.Tabuleiro;
import xadrez.pecas.Bispo;
import xadrez.pecas.Cavalo;
import xadrez.pecas.Peao;
import xadrez.pecas.Rainha;
import xadrez.pecas.Rei;
import xadrez.pecas.Torre;

public class PartidaDeXadez {
	private int turno;
	private Cores jogadorAtual;
	private Tabuleiro tabuleiro;
	private boolean check;
	private boolean checkMate;
	private PecaDeXadrez enPassantVuneravel;
	private PecaDeXadrez promocao;
	
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
	
	public PecaDeXadrez getEnPassantVuneravel() {
		return enPassantVuneravel;
	}
	
	public PecaDeXadrez getPromocao() {
		return promocao;
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
		 * posicao de check, isso não é permitido no
		 * jogo de xadres
		 */		
		if (testarCheck(jogadorAtual)) {
			desfazerMovimento(partida, destino, capturaPeca);
			throw new ExcecoesXadrez("Você não pode se colocar em posição de check");
		}
		
		PecaDeXadrez pecaQueMoveu = (PecaDeXadrez)tabuleiro.peca(destino);
		
		//#Movimento especial promocao
		promocao = null;
		if (pecaQueMoveu instanceof Peao) {
			if ((pecaQueMoveu.getCores() == Cores.WHITE && destino.getLinha() == 0) ||(pecaQueMoveu.getCores() == Cores.BLACK && destino.getLinha() == 7)) {
				promocao = (PecaDeXadrez)tabuleiro.peca(destino);
				promocao = substituirPecaPromovida("R");
			}
		}
		
		//testando se o oponente está em posição de check
		check = (testarCheck(oponente(jogadorAtual))) ? true : false;
		
		if (testarCheckMate(oponente(jogadorAtual))) {
			checkMate = true;
		}
		else {
		proximaPartida();
		}
		
		// #movimento especial en passant
		if (pecaQueMoveu instanceof Peao && (destino.getLinha() == partida.getLinha()-2 || destino.getLinha() == partida.getLinha()+2)) {
			enPassantVuneravel = pecaQueMoveu;
		}
		else {
			enPassantVuneravel = null;
		}
		
		return (PecaDeXadrez)capturaPeca;
	}
	
	public PecaDeXadrez substituirPecaPromovida(String tipo) {
		if (promocao == null) {
			throw new IllegalStateException("Nao tem peca a ser promovida!");
		}
		if (!tipo.equals("B") && !tipo.equals("C") && !tipo.equals("T") && !tipo.equals("R")) {
			throw new InvalidParameterException("Tipo que deseja promover sua peca é invalida!");
		}
		
		Position pos = promocao.getPosicaoXadrez().toPosition();
		Peca p = tabuleiro.removerPeca(pos);
		pecasNoTabuleiro.remove(p);
		
		PecaDeXadrez newPeca = newPeca(tipo, promocao.getCores());
		tabuleiro.lugarDaPeca(newPeca, pos);
		pecasNoTabuleiro.add(newPeca);
		
		return newPeca;
	}
	
	//metodo auxiliar para instanciar uma nova peca na jogada especial promocao
	private PecaDeXadrez newPeca(String tipo, Cores cor) {
		if (tipo.equals("B")) return new Bispo(tabuleiro, cor);
		if (tipo.equals("C")) return new Cavalo(tabuleiro, cor);
		if (tipo.equals("R")) return new Rainha(tabuleiro, cor);
		return new Torre(tabuleiro, cor);
	}
	
	private Peca Mover(Position partida, Position destino) {
		PecaDeXadrez p = (PecaDeXadrez)tabuleiro.removerPeca(partida);
		p.aumentandoContatorDeMovimento();
		Peca capturaPeca = tabuleiro.removerPeca(destino);
		tabuleiro.lugarDaPeca(p, destino);
		//condicional para testar se o movimento capturou alguma peça adverssária
		if (capturaPeca != null) {
			pecasNoTabuleiro.remove(capturaPeca);
			pecasCapturadas.add(capturaPeca);
		}
		//#Moviemento especial Castling Rook pequeno
		if (p instanceof Rei && destino.getColuna() == partida.getColuna()+2) {
			Position origenDaTorre = new Position(partida.getLinha(), partida.getColuna()+3);
			Position destinoDaTorre = new Position(partida.getLinha(), partida.getColuna()+1);
			PecaDeXadrez torre = (PecaDeXadrez)tabuleiro.removerPeca(origenDaTorre);
			tabuleiro.lugarDaPeca(torre, destinoDaTorre);
			torre.aumentandoContatorDeMovimento();
		}
		//#Moviemento especial Castling Rook grande
		if (p instanceof Rei && destino.getColuna() == partida.getColuna()-2) {
			Position origenDaTorre = new Position(partida.getLinha(), partida.getColuna()-4);
			Position destinoDaTorre = new Position(partida.getLinha(), partida.getColuna()-1);
			PecaDeXadrez torre = (PecaDeXadrez)tabuleiro.removerPeca(origenDaTorre);
			tabuleiro.lugarDaPeca(torre, destinoDaTorre);
			torre.aumentandoContatorDeMovimento();
		}
		
		//#Tratamento especial para a jogada en passant
		if (p instanceof Peao) {
			if (partida.getColuna() != destino.getColuna() && capturaPeca == null) {
				Position posiPeao;
				if (p.getCores() == Cores.WHITE) {
					posiPeao = new Position(destino.getLinha()+1, destino.getColuna());
				}
				else {
					posiPeao = new Position(destino.getLinha()-1, destino.getColuna());
				}
				capturaPeca = tabuleiro.removerPeca(posiPeao);
				pecasCapturadas.add(capturaPeca);
				pecasNoTabuleiro.remove(capturaPeca);
			}
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
		
		//#Moviemento especial Castling Rook pequeno
		if (p instanceof Rei && destino.getColuna() == partida.getColuna()+2) {
			Position origenDaTorre = new Position(partida.getLinha(), partida.getColuna()+3);
			Position destinoDaTorre = new Position(partida.getLinha(), partida.getColuna()+1);
			PecaDeXadrez torre = (PecaDeXadrez)tabuleiro.removerPeca(destinoDaTorre);
			tabuleiro.lugarDaPeca(torre, origenDaTorre);
			torre.diminuindoContadoDeMovimento();;
		}
		//#Moviemento especial Castling Rook grande
		if (p instanceof Rei && destino.getColuna() == partida.getColuna()-2) {
			Position origenDaTorre = new Position(partida.getLinha(), partida.getColuna()-4);
			Position destinoDaTorre = new Position(partida.getLinha(), partida.getColuna()-1);
			PecaDeXadrez torre = (PecaDeXadrez)tabuleiro.removerPeca(destinoDaTorre);
			tabuleiro.lugarDaPeca(torre, origenDaTorre);
			torre.diminuindoContadoDeMovimento();
		}
		
		//#Tratamento especial para a jogada en passant
		if (p instanceof Peao) {
			if (partida.getColuna() != destino.getColuna() && capturaPeca == enPassantVuneravel) {
				PecaDeXadrez peao = (PecaDeXadrez)tabuleiro.removerPeca(destino);
				Position posiPeao;
				if (p.getCores() == Cores.WHITE) {
					posiPeao = new Position(3, destino.getColuna());
				}
				else {
					posiPeao = new Position(4, destino.getColuna());
				}
				tabuleiro.lugarDaPeca(peao, posiPeao);
			}
		}
	}
	
	private void validandoPosicao(Position position) {
		if (!tabuleiro.verificandoPeca(position)) {
			throw new ExcecoesXadrez("Não existe peca na posicao de origem");
		}
		if (jogadorAtual != ((PecaDeXadrez)tabuleiro.peca(position)).getCores()) {
			throw new ExcecoesXadrez("A peca escolhida não é sua");
		}
		if (!tabuleiro.peca(position).verificaSeHaUmPossivelMovimento()) {
			throw new ExcecoesXadrez("Não existe movimentos possiveis para a peca escolhida");
		}
	}
	
	private void validandoPosicaoDeDestino(Position partida, Position destino) {
		if (!tabuleiro.peca(partida).possibilidadesDeMovimentos(destino)) {
			throw new ExcecoesXadrez("A peca escolhida não pode se mover para a posicão de destino");
		}
	}
	
	private void proximaPartida() {
		turno++;
		jogadorAtual = (jogadorAtual == Cores.WHITE) ? Cores.BLACK : Cores.WHITE;
	}
	
	/*
	 * metodo para devolver o oponente de uma cor
	 * exemeplo: se a cor for White, o oponente é black
	 */	
	private Cores oponente(Cores cor) {
		return (cor == Cores.WHITE) ? Cores.BLACK : Cores.WHITE;
	}
	
	//metodo para varrer as peças do tabuleiro para encontrar o Rei
	private PecaDeXadrez Rei(Cores cor) {
		List<Peca> list = pecasNoTabuleiro.stream().filter(x -> ((PecaDeXadrez)x).getCores() == cor).collect(Collectors.toList());
		for(Peca p : list) {
			if (p instanceof Rei) {
				return (PecaDeXadrez)p;
			}
		}
		throw new IllegalStateException("Não o Rei da cor" + cor + " no tabuleiro!" );
	}
	
	//metodo para testar se o Rei está em situação de check
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
		
        colocandoNovaPeca('a', 1, new Torre(tabuleiro, Cores.WHITE));
        colocandoNovaPeca('b', 1, new Cavalo(tabuleiro, Cores.WHITE)); 
        colocandoNovaPeca('c', 1, new Bispo(tabuleiro, Cores.WHITE));
        colocandoNovaPeca('d', 1, new Rainha(tabuleiro, Cores.WHITE));
        colocandoNovaPeca('e', 1, new Rei(tabuleiro, Cores.WHITE, this));
        colocandoNovaPeca('f', 1, new Bispo(tabuleiro, Cores.WHITE));
        colocandoNovaPeca('g', 1, new Cavalo(tabuleiro, Cores.WHITE)); 
        colocandoNovaPeca('h', 1, new Torre(tabuleiro, Cores.WHITE));
        colocandoNovaPeca('a', 2, new Peao(tabuleiro, Cores.WHITE, this));
        colocandoNovaPeca('b', 2, new Peao(tabuleiro, Cores.WHITE, this));
        colocandoNovaPeca('c', 2, new Peao(tabuleiro, Cores.WHITE, this));
        colocandoNovaPeca('d', 2, new Peao(tabuleiro, Cores.WHITE, this));
        colocandoNovaPeca('e', 2, new Peao(tabuleiro, Cores.WHITE, this));
        colocandoNovaPeca('f', 2, new Peao(tabuleiro, Cores.WHITE, this));
        colocandoNovaPeca('g', 2, new Peao(tabuleiro, Cores.WHITE, this));
        colocandoNovaPeca('h', 2, new Peao(tabuleiro, Cores.WHITE, this));
       
        colocandoNovaPeca('a', 8, new Torre(tabuleiro, Cores.BLACK));
        colocandoNovaPeca('b', 8, new Cavalo(tabuleiro, Cores.BLACK)); 
        colocandoNovaPeca('c', 8, new Bispo(tabuleiro, Cores.BLACK));
        colocandoNovaPeca('d', 8, new Rainha(tabuleiro, Cores.BLACK));
        colocandoNovaPeca('e', 8, new Rei(tabuleiro, Cores.BLACK, this));
        colocandoNovaPeca('f', 8, new Bispo(tabuleiro, Cores.BLACK));
        colocandoNovaPeca('g', 8, new Cavalo(tabuleiro, Cores.BLACK)); 
        colocandoNovaPeca('h', 8, new Torre(tabuleiro, Cores.BLACK));
        colocandoNovaPeca('a', 7, new Peao(tabuleiro, Cores.BLACK, this));
        colocandoNovaPeca('b', 7, new Peao(tabuleiro, Cores.BLACK, this));
        colocandoNovaPeca('c', 7, new Peao(tabuleiro, Cores.BLACK, this));
        colocandoNovaPeca('d', 7, new Peao(tabuleiro, Cores.BLACK, this));
        colocandoNovaPeca('e', 7, new Peao(tabuleiro, Cores.BLACK, this));
        colocandoNovaPeca('f', 7, new Peao(tabuleiro, Cores.BLACK, this));
        colocandoNovaPeca('g', 7, new Peao(tabuleiro, Cores.BLACK, this));
        colocandoNovaPeca('h', 7, new Peao(tabuleiro, Cores.BLACK, this));
		}
}
