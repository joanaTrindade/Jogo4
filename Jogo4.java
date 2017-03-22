import java.io.*;
import java.util.*;
import java.lang.*;

//classe que representa um estado do jogo
//inclui os métodos: jogada; clear; imprime

class Tabuleiro {
    char [][] matriz = new char [6][7];

    public Tabuleiro (){
	for(int i=0; i<6; i++){
	    for(int j=0; j<7; j++){
		matriz[i][j] = '-';
	    }
	}
    }

    public void jogada (int y, char turn){
	for (int i=5; i>=0; i--){
	    if (matriz[i][y]=='-'){
		matriz[i][y]=turn;
		break;
	    }
	}
    }

    public void clear (int j){
	for (int i=0; i<6; i++){
	    if (matriz[i][j]!='-'){
		matriz[i][j]='-';
		break;
	    }
	}
    }

    public void imprime () {
	
	for(int i=0;i<6;i++){
	    System.out.print(i +" ");
	    for(int j=0;j<7;j++){
		//System.out.print(j+" ");
		System.out.print( matriz[i][j]+" ");
	    }
	    System.out.println();
	}
	System.out.println("  0 1 2 3 4 5 6");
	System.out.println();
    }
    public int cheio (){
	int blanck = 0;
	for(int i=0; i<6; i++){
	    for(int j=0; j<7; j++){
		if(matriz[i][j]=='-')
		    blanck++;
	    }
	}
	return blanck;
    }
}

public class Jogo4{
    //Estado do jogo que é atualizado em cada jogada
    private Tabuleiro t;
    //Scanner para ler input
    private Scanner in;
    //coluna escolhida pelo minimax para a proxima jogada do pc
    private int next;
    //profundidade até onde deixamos o minimax iterar
    private int maxDepth;
    //caracter do pc
    private char c;
    //caracter do adversario
    private char pl;

    public Jogo4 (Tabuleiro t){
	this.t = t;
	in = new Scanner (System.in);
    }

    public void check (){
	int res = Final (t);
	if (res == 512){
	    System.out.println ("O computador ganhou!");
	    System.exit(0);
	}
	else if (res == -512){
	    System.out.println ("Parabéns! Ganhou!");
	    System.exit(0);
	}
	else {
	    if (t.cheio()==0){
		System.out.println ("Empate");
		//j.play();
		System.exit(0);
	    }
	}
    }
    
    public int Final (Tabuleiro t){
	//verificação na horizontal
	int pc = 0;
	int player = 0;
	for(int i=0; i<6; i++){
	    for(int j=0; j<7-3; j++){
		if(t.matriz[i][j]==c && t.matriz[i][j+1]==c && t.matriz [i][j+2]==c && t.matriz [i][j+3]==c)
		    pc = 4;
		else if (t.matriz[i][j]==pl && t.matriz[i][j+1]==pl && t.matriz [i][j+2]==pl && t.matriz [i][j+3]==pl)
		    player = 4;
	    }
	}
	if (pc==4) return 512;
	if (player==4) return -512;
	//verificação na vertical
	for (int i=0; i< 6-3; i++){
	    for(int j=0; j<7; j++){
		if (t.matriz[i][j]==c && t.matriz[i+1][j]==c && t.matriz[i+2][j]==c && t.matriz[i+3][j]==c)
		    pc = 4;
		else if (t.matriz[i][j]==pl && t.matriz[i+1][j]==pl && t.matriz[i+2][j]==pl && t.matriz[i+3][j]==pl)
		    player = 4;
	    }
	}
	if (pc==4) return 512;
	if (player==4) return -512;
	//verificação ma diagonal
	//diagonal ascendente
	for(int i = 3; i<6; i++){
	    for(int j=0; j<7-3; j++){
		if (t.matriz[i][j]==c && t.matriz[i-1][j+1]==c && t.matriz[i-2][j+2]==c && t.matriz[i-3][j+3]==c)
		    pc = 4;
		else if (t.matriz[i][j]==pl && t.matriz[i-1][j+1]==pl && t.matriz[i-2][j+2]==pl && t.matriz[i-3][j+3]==pl)
		    player = 4;
	    }
	}
	if (pc==4) return 512;
	if (player==4) return -512;
	//diagonal descendente
	for(int i = 0; i<3; i++){
	    for(int j=0; j<7-3; j++){
		if (t.matriz[i][j]==c && t.matriz[i+1][j+1]==c && t.matriz[i+2][j+2]==c && t.matriz[i+3][j+3]==c)
		    pc = 4;
		else if (t.matriz[i][j]==pl && t.matriz[i+1][j+1]==pl && t.matriz[i+2][j+2]==pl && t.matriz[i+3][j+3]==pl)
		    player = 4;
	    }
	}
	if (pc==4) return 512;
	if (player==4) return -512;
	return 0;
    }
    
    public void Adversario (){
	System.out.println ("Introduza a coluna, de 0 a 6, onde pretende jogar");
	int col = in.nextInt();
	while (col < 0 || col > 7 || t.matriz[0][col]!='-'){
	    System.out.println ("Introduza a coluna, de 0 a 6, onde pretende jogar");
	    col = in.nextInt();
	}
	t.jogada (col,pl);
    }
    
    public void Computador (){
	minimax(0, c);
	t.jogada(next,c);
    }
    public int minimax(int depth, char turn){
        int res = Final(t);
        if(res == 512) return Integer.MAX_VALUE;
        else if(res == -512) return Integer.MIN_VALUE;
	else if (t.cheio() == 0) return 0;
        
        if(depth == maxDepth) return score(t);
        
        int maxScore = Integer.MIN_VALUE;
	int minScore = Integer.MAX_VALUE;
	//System.out.println("score "+ score(t));
        for(int j=0; j<=6; ++j){
            if(t.matriz[0][j]!='-')
		continue;
	    
            if(turn == c){
		t.jogada (j, c);
		int currentScore = minimax(depth+1, pl);
		//System.out.println(currentScore);
		maxScore = Math.max(currentScore, maxScore);
		if(depth==0){
		    System.out.println("Score for location "+j+" = "+currentScore);
		    if(maxScore==currentScore) next = j;
                    
		    if(maxScore==Integer.MAX_VALUE){
			t.clear(j);
			break;
		    }
		}
            }
	    else if(turn==pl){
		t.jogada(j, pl);
		int currentScore = minimax(depth+1, c);
		minScore = Math.min(currentScore, minScore);
            }
            t.clear(j);
        }
        return turn==c ? maxScore : minScore;
    }
    public int score (Tabuleiro t){
	//Identica a função "Final", mas verifica o contador, se estiver a 3, 2, 1 ou 0, atribuindo um pontuação - positiva para o pc e negatva para o player.
	int sum = 0;
	int player = 0;
	int pc = 0;
	//horizontal
	for(int i=0; i<6; i++){
	    for(int j=0; j<7-3; j++){
		for(int h=0; h<4; h++){
		    if(t.matriz[i][j+h]==c) //contador para o pc
			pc++;
		    else if (t.matriz[i][j+h]==pl) //contador para o player
			player++;
		}
		sum += soma(pc,player);
            }
	}
	//vertical
	pc=0;
	player=0;
	for(int j=0; j<7;j++){
	    for(int i=0; i<6-3; i++){
		for(int h=0; h<4; h++){
		    if (t.matriz[i+h][j]==c)
			pc++;
		    else if (t.matriz[i+h][j]==pl){
			player++; 
		    }
		}
		sum += soma(pc,player);
	    }
	}
	//sum += soma (pc, player);
	//diagonal ascendente
	pc=0;
	player=0;
	for(int i = 3; i<6; i++){
	    for(int j=0; j<7-3; j++){
		for(int h=0; h<4; h++){
		    if(t.matriz[i-h][j+h]==c)
			pc++;
		    else if (t.matriz[i-h][j]==pl){
			player++; 
		    }
		}
		sum += soma(pc,player);
	    }
	}
	//sum += soma(pc,player);
	//diagonal descendente
	pc=0;
	player=0;
	for(int i = 3; i<6; i++){
	    for(int j=3; j<7-3; j++){
		for(int h=0; h<4; h++){
		    if(t.matriz[i-h][j-h]==c)
			pc++;
		    else if (t.matriz[i-h][j-h]==pl){
			player++; 
		    }
		}
		sum += soma(pc,player);
	    }
	}
	return sum;
	
    }
    public int soma (int pc, int player){
	int sum = 0;
	if (pc==3 && player==0) sum = 50;
	if (pc==2 && player==0) sum = 10;
	if (pc==1 && player==0) sum = 1;
	if (pc==0 && player==3) sum = -50;
	if (pc==0 && player==2) sum = -10;
	if (pc==0 && player==1) sum = -1;
	return sum;
    }
   
    public void play(){
	in = new Scanner (System.in);
	System.out.println ("Escolha o nível de difculdade com que pretende jogar:");
	System.out.println ("Fácil: 0; Médio: 1; Difícil: 2");
	int d = in.nextInt();
	if (d==0) maxDepth = 2;
	else if (d==1) maxDepth = 5;
	else maxDepth = 8;
	
	System.out.println ("Escolha o caracter que o vai representar, X ou O.");
	pl = in.next().charAt(0);
	if (pl!='O' && pl !='X'){
	    System.out.println("Opção inválida.");
	    System.out.println ("Escolha o caracter que o vai representar, X ou O.");
	    pl = in.next().charAt(0);
	}
	if (pl=='O') c = 'X';
	else c = 'O';
	t.imprime();
	while (true){
	    Adversario ();
	    t.imprime();

	    check();

	    Computador ();
	    t.imprime();

	    check ();
	}
    }
    public static void main (String [] args){
	Tabuleiro t = new Tabuleiro ();
	Jogo4 j = new Jogo4(t);
	j.play();

    }
}
