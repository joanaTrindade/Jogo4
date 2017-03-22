import java.io.*;
import java.util.*;
import java.lang.*;

class Node {
    public char [][] matriz;
  
    Node (char [][] m){
	//matriz = m;
	matriz = new char [6][7];
	for (int j=0; j<7; j++)
	    for (int i=0; i<6; i++)
		matriz [i][j] = m[i][j];
    }
}
public class quatrolinha {
    public static char [][] x;
    public static char pl; //jogador
    public static char c; //computador
    public static Scanner in;
    public static int maxDepth;
    public static boolean flag = false; //verificar se houve jogada
    public static int nextMoveLocation;
   
    public static void imprime (char m [][]){
	for(int i=0;i<6;i++){
	    for(int j=0;j<7;j++)
		System.out.print(m[i][j]+" ");
	    System.out.println();
	}
	System.out.println();
    }
    public static int Final (char [][] tab){
	//verificação na horizontal
	int pc = 0;
	int player = 0;
	for(int j=0; j<7-3;j++){
	    for(int i=0; i<6; i++){
		if(tab[i][j]==c && tab[i][j+1]==c && tab [i][j+2]==c && tab [i][j+3]==c)
		    pc = 4;
		else if (tab[i][j]==pl && tab[i][j+1]==pl && tab [i][j+2]==pl && tab [i][j+3]==pl)
		    pl = 4;
	    }
	}
	if (pc==4) return 512;
	if (player==4) return -512;
	//verificação na vertical
	for (int i=0; i< 6-3; i++){
	    for(int j=0; j<7; j++){
		if (tab[i][j]==c && tab[i+1][j]==c && tab[i+2][j]==c && tab[i+3][j]==c)
		    pc = 4;
		else if (tab[i][j]==pl && tab[i+1][j]==pl && tab[i+2][j]==pl && tab[i+3][j]==pl)
		    player = 4;
	    }
	}
	if (pc==4) return 512;
	if (player==4) return -512;
	//verificação ma diagonal
	//diagonal ascendente
	for(int i = 3; i<6; i++){
	    for(int j=0; j<7-3; j++){
		if (tab[i][j]==c && tab[i-1][j+1]==c && tab [i-2][j+2]==c && tab [i-3][j+3]==c)
		    pc = 4;
		else if (tab[i][j]==pl && tab[i-1][j+1]==pl && tab [i-2][j+2]==pl && tab [i-3][j+3]==pl)
		    player = 4;
	    }
	}
	if (pc==4) return 512;
	if (player==4) return -512;
	//diagonal descendente
	for(int i = 3; i<6; i++){
	    for(int j=3; j<7-3; j++){
		if (tab[i][j]==c && tab[i-1][j-1]==c && tab [i-2][j-2]==c && tab [i-3][j-3]==c)
		    pc = 4;
		else if (tab[i][j]==pl && tab[i-1][j-1]==pl && tab [i-2][j-2]==pl && tab [i-3][j-3]==pl)
		    player = 4;
	    }
	}
	if (pc==4) return 512;
	if (player==4) return -512;
	return 0;
    }
    /*
    public static int minimax (int depth, char turn){
	int col = 0;
	int MAX = Integer.MIN_VALUE;
	char [][] aux = new char [6][7];
	for (int i=0; i<6; i++){
	    for (int j=0; j<7; j++){
		aux[i][j]=x[i][j];
	    }
	}
	for (int j = 0; j<7; j++){
	    tab(j, turn, aux);
	    int v = min_value (depth+1,pl,aux);
	    System.out.println(score(x));
	    if (MAX <= v){
		MAX = v;
		col = j;
	    }
	    clear(j,aux);
	}
	return col;
    }
    public static int min_value (int depth, char turn, char [][] aux){
	int util = Final(x);
	if (util!=0) return Final (x);
	if (depth == maxDepth) return score(x);
	int v = Integer.MAX_VALUE;
	for (int j = 0; j<7; j++){
	    tab(j, turn, aux);
	    v = Math.min (v,max_value (depth+1,c,aux));
	    if (flag)
		clear (j,aux);
	    flag = false;
	}
	return v;
    }
    public static int max_value (int depth, char turn, char [][] aux){
	int util = Final(x);
	if (util!=0) return Final (x);
	if (depth == maxDepth) return score(x);
	int v = Integer.MAX_VALUE;
	for (int j = 0; j<7; j++){
	    tab(j, turn,aux);
	    v = Math.max (v,min_value (depth+1,pl,aux));
	    if (flag)
		clear(j,aux);
	    flag = false;
	}
	return v;
    }
    */
     public static int minimax(int depth, char turn){
        int gameResult = Final(x);
        if(gameResult==1)return Integer.MAX_VALUE;
        else if(gameResult==2)return Integer.MIN_VALUE;
        else if(gameResult==0)return 0;
        
        if(depth==maxDepth)return score(x);
        
        int maxScore=Integer.MIN_VALUE, minScore = Integer.MAX_VALUE;
        for(int j=0;j<=6;++j){
            if(x[0][j]=='-') continue;
                
            if(turn==c){
                    tab(j, c);
                    int currentScore = minimax(depth+1, pl);
                    maxScore = Math.max(currentScore, maxScore);
                    if(depth==0){
                        System.out.println("Score for location "+j+" = "+currentScore);
                        if(maxScore==currentScore) nextMoveLocation = j;
                        
			if(maxScore==Integer.MAX_VALUE){
			//We know we're going to win if we play here.
		        //So no need of further evaluations.
			clear(j);break;}
                    }
            }else if(turn==pl){
                    tab(j, pl);
                    int currentScore = minimax(depth+1, c);
                    minScore = Math.min(currentScore, minScore);
            }
            clear(j);
        }
        return turn==c?maxScore:minScore;
     }
    public static void clear (int j){
	for (int i=0; i<6; i++){
	    if (x[i][j]!='-'){
		x[i][j]='-';
		break;
	    }
	}
    }
    public static void tab (int j, char turn){
	for (int i=5; i>=0; i--){
	    if (x[i][j]=='-'){
		x[i][j]=turn;
		flag = true;
	    }
	}
    }
    public static void jogada (int y, char turn){
	if (y<0 || y>6){
	    System.out.println("Jogada impossível!");
	    System.out.println("Introduza a coluna, de 0 a 6, onde pretende jogar.");
	    jogada (in.nextInt(),pl);
	}
	for (int i=5; i>=0; i--){
	    if (x[i][y]=='-'){
		x[i][y]=turn;
		imprime (x);
		break;
	    }
	}
    }
    /*public static int score (char [][] m){
	//Identica a função "Final", mas verifica o contador, se estiver a 3, 2, 1 ou 0, atribuindo um pontuação - positiva para o pc e negatva para o player.
	//??Retorna a soma dos segmentos (vert + hori + diag) mais um bonus de 16, cujo sinal segue a lógica explicada em cima.
	int sum = 0;
	int player = 0;
	int pc = 0;
	//horizontal
	for(int j=0; j<7-3;j++){
	    for(int i=0; i<6; i++){
		for(int h=0; h<4; h++){
		    if(m[i][j+h]==c) //contador para o pc
			pc++;
		    else if (m[i][j+h]==pl){ //contador para o player
			player++;
		    }
		}
	    }
	}
	sum += soma (pc, player);
	//vertical
	pc=0;
	player=0;
	for(int j=0; j<7;j++){
	    for(int i=0; i<6-3; i++){
		for(int h=0; h<4; h++){
		    if (m[i+h][j]==c)
			pc++;
		    else if (m[i+h][j]==pl){
			player++; 
		    }
		}
	    }
	}
	sum += soma (pc, player);
	//diagonal ascendente
	pc=0;
	player=0;
	for(int i = 3; i<6; i++){
	    for(int j=0; j<7-3; j++){
		for(int h=0; h<4; h++){
		    if(m[i-h][j+h]==c)
			pc++;
		    else if (m[i-h][j]==pl){
			player++; 
		    }
		}
	    }
	}
	sum += soma(pc,player);
	//diagonal descendente
	pc=0;
	player=0;
	for(int i = 3; i<6; i++){
	    for(int j=3; j<7-3; j++){
		for(int h=0; h<4; h++){
		    if(m[i-h][j-h]==c)
			pc++;
		    else if (m[i-h][j-h]==pl){
			player++; 
		    }
		}
	    }
	}
	sum += soma(pc,player);
	return sum;
	
    }
    public static int soma (int pc, int player){
	int sum = 0;
	if (pc==3 && player==0) sum = 50;
	if (pc==2 && player==0) sum = 10;
	if (pc==1 && player==0) sum = 1;
	if (pc==0 && player==3) sum = -50;
	if (pc==0 && player==2) sum = -10;
	if (pc==0 && player==1) sum = -1;
	return sum;
	}*/
    public static int calculateScore(int aiScore, int moreMoves){   
        int moveScore = 4 - moreMoves;
        if(aiScore==0)return 0;
        else if(aiScore==1)return 1*moveScore;
        else if(aiScore==2)return 10*moveScore;
        else if(aiScore==3)return 100*moveScore;
        else return 1000;
}
    public static int score (char [][] m){
      
        int aiScore=1;
        int score=0;
        int blanks = 0;
        int k=0, moreMoves=0;
        for(int i=5;i>=0;--i){
            for(int j=0;j<=6;++j){
                
                if(m[i][j]==c || m[i][j]==pl) continue; 
                
                if(j<=3){ 
                    for(k=1;k<4;++k){
                        if(m[i][j+k]==c)aiScore++;
                        else if(m[i][j+k]==pl){aiScore=0;blanks = 0;break;}
                        else blanks++;
                    }
                     
                    moreMoves = 0; 
                    if(blanks>0) 
                        for(int c=1;c<4;++c){
                            int column = j+c;
                            for(int h=i; h<= 5;h++){
                             if(m[h][column]=='-')moreMoves++;
                                else break;
                            } 
                        } 
                    
                    if(moreMoves!=0) score += calculateScore(aiScore, moreMoves);
                    aiScore=1;   
                    blanks = 0;
                } 
                
                if(i>=3){
                    for(k=1;k<4;++k){
                        if(m[i-k][j]==c)aiScore++;
                        else if(m[i-k][j]==pl){aiScore=0;break;} 
                    } 
                    moreMoves = 0; 
                    
                    if(aiScore>0){
                        int column = j;
                        for(int h=i-k+1; h<=i-1;h++){
                         if(m[h][column]=='-')moreMoves++;
                            else break;
                        }  
                    }
                    if(moreMoves!=0) score += calculateScore(aiScore, moreMoves);
                    aiScore=1;  
                    blanks = 0;
                }
                 
                if(j>=3){
                    for(k=1;k<4;++k){
                        if(m[i][j-k]==1)aiScore++;
                        else if(m[i][j-k]==2){aiScore=0; blanks=0;break;}
                        else blanks++;
                    }
                    moreMoves=0;
                    if(blanks>0) 
                        for(int c=1;c<4;++c){
                            int column = j- c;
                            for(int h=i; h<= 5;h++){
                             if(m[h][column]==0)moreMoves++;
                                else break;
                            } 
                        } 
                    
                    if(moreMoves!=0) score += calculateScore(aiScore, moreMoves);
                    aiScore=1; 
                    blanks = 0;
                }
                 
                if(j<=3 && i>=3){
                    for(k=1;k<4;++k){
                        if(m[i-k][j+k]==c)aiScore++;
                        else if(m[i-k][j+k]==pl){aiScore=0;blanks=0;break;}
                        else blanks++;                        
                    }
                    moreMoves=0;
                    if(blanks>0){
                        for(int c=1;c<4;++c){
                            int column = j+c, row = i-c;
                            for(int h=row; h<=5;++h){
                                if(m[h][column]=='-')moreMoves++;
                                else if(m[h][column]==1);
                                else break;
                            }
                        } 
                        if(moreMoves!=0) score += calculateScore(aiScore, moreMoves);
                        aiScore=1;
                        blanks = 0;
                    }
                }
                 
                if(i>=3 && j>=3){
                    for(k=1;k<4;++k){
                        if(m[i-k][j-k]==1)aiScore++;
                        else if(m[i-k][j-k]==2){aiScore=0;blanks=0;break;}
                        else blanks++;                        
                    }
                    moreMoves=0;
                    if(blanks>0){
                        for(int c=1;c<4;++c){
                            int column = j-c, row = i-c;
                            for(int h=row;h<=5;++h){
                                if(m[h][column]==0)moreMoves++;
                                else if(m[h][column]==1);
                                else break;
                            }
                        } 
                        if(moreMoves!=0) score += calculateScore(aiScore, moreMoves);
                        aiScore=1;
                        blanks = 0;
                    }
                } 
            }
        }
        return score;
    } 
    public static int cheia (char [][] in){
	int blanck = 0;
	for(int i=0; i<6; i++){
	    for(int j=0; j<7; j++){
		if(in[i][j]=='-')
		    blanck++;
	    }
	}
	return blanck;
    }
    public static void main (String [] args){
	in = new Scanner (System.in);
	//leMatriz (in);
	x = new char [6][7];
	for(int i=0; i<6; i++){
	    for(int j=0; j<7; j++){
		x [i][j] = '-';
	    }
	}
	System.out.println ("Escolha o nível de difculdade com que pretende jogar:");
	System.out.println ("Fácil: 0; Médio: 1; Difícil: 2");
	int d = in.nextInt();
	if (d==0) maxDepth = 10;
	else if (d==1) maxDepth = 30;
	else maxDepth = 42;
	
	System.out.println ("Escolha o caracter que o vai representar, X ou O.");
	pl = in.next().charAt(0);
	if (pl=='O') c = 'X';
	else c = 'O';
	/*
	System.out.println ("Pretende jogar em primeiro lugar? (s/n)");
	char s = in.next().charAt(0);

	if (s == 's'){
	    imprime (inicial);
	    System.out.println ("Introduza a coluna, de 0 a 6, onde pretende jogar");
	    jogada (inicial, in.nextInt(), 2);
	    }*/
	int res = 0;
	imprime (x);
	while (true){
	    System.out.println ("Introduza a coluna, de 0 a 6, onde pretende jogar");
	    int sc = in.nextInt();
	    jogada (sc, pl);
	    
	    res = Final (x);
	    if(res > 0) {System.out.println ("O computador ganhou!"); break;}
	    else if (res < 0){System.out.println ("Ganhou!"); break;}
	    else{
		if (cheia(x)==0){
		    System.out.println ("Empate");
		    break;
		}
	    }
	    System.out.println ("Cheguei");
	    minimax(0,c);
	    System.out.println(nextMoveLocation);
	    jogada (nextMoveLocation,c);
	    
	    res = Final (x);
	    if(res > 0) {System.out.println ("O computador ganhou!"); break;}
	    else if (res < 0){System.out.println ("Ganhou!"); break;}
	    else{
		if (cheia(x)==0){
		    System.out.println ("Empate");
		    break;
		}
	    }
	}
    }
}
