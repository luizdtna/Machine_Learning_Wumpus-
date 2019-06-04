
package mundowumpus;

import java.util.ArrayList;
import java.util.Random;


public class AmbienteMundo {

    private Random rand = new Random();
    private ArrayList<Posicao> vetPosicaoProibida = new ArrayList<>();
    private Matriz[][] matriz;

    public Matriz[][] armazenaNaMatriz(Posicao posInic, int numLinhas, int numColunas, int numWumpus, int numPoco) {

        this.matriz = new Matriz[numLinhas][numColunas];
        //Inicializa a matriz
        for (int linha = 0; linha < numLinhas; linha++) {
            for (int colun = 0; colun < numColunas; colun++) {
                //Converte a string do vetor em inteiro, aopós isso insere na matriz
                this.matriz[linha][colun] = new Matriz();
            }
        }
        //Cria um vetor de posições proibidas para inserir wumpus, poco
        posicaoProibida(posInic);

        verificaObstaculo(true, numWumpus, numLinhas, numColunas);
        verificaObstaculo(false, numPoco, numLinhas, numColunas);
        inserirOuro(numLinhas, numColunas);

        return this.matriz;
    }

    private ArrayList<Posicao> posicaoProibida(Posicao inicio) {
        //Nesse via receber a posição inicial. Não pode haver fedor, brisa, Wumpus nem poço nesta posição
        //Adiciona no vetor de Posição a posição proibida, caso seja inválida(fim da matriz) recebe -3000
        
        //Se a posição adjacente for == -3000 quer dizer que é parede
        if (inicio.getLinha() - 1 >= 0) {

            vetPosicaoProibida.add(new Posicao(inicio.getLinha() - 1, inicio.getColuna()));
        } else {
            vetPosicaoProibida.add(new Posicao(-3000, -3000));
        }
        if (inicio.getLinha() + 1 < this.matriz.length) {
            vetPosicaoProibida.add(new Posicao(inicio.getLinha() + 1, inicio.getColuna()));
        } else {
            vetPosicaoProibida.add(new Posicao(-3000, -3000));
        }
        if (inicio.getColuna() - 1 >= 0) {
            vetPosicaoProibida.add(new Posicao(inicio.getLinha(), inicio.getColuna() - 1));
        } else {
            vetPosicaoProibida.add(new Posicao(-3000, -3000));
        }
        if (inicio.getColuna() + 1 < this.matriz[0].length) {
            vetPosicaoProibida.add(new Posicao(inicio.getLinha(), inicio.getColuna() + 1));
        } else {
            vetPosicaoProibida.add(new Posicao(-3000, -3000));
        }
        vetPosicaoProibida.add(new Posicao(inicio.getLinha(), inicio.getColuna()));
        
        //X=1 e Y=1 vai dar um loop infinito, pois o agente não vai achar o ouro 
        vetPosicaoProibida.add(new Posicao(1, 1));
        return vetPosicaoProibida;
    }

    private void verificaObstaculo(boolean tipoObdtaculo, int numObstaculo, int numLinhas, int numColunas) {
        int linhaObstaculo = 0;
        int colunaObstaculo = 0;

        for (int i = 0; i < numObstaculo; i++) {
            
            //Gera a linha e a coluna aleatória para o obstaculo
            linhaObstaculo = rand.nextInt(numLinhas); 
            colunaObstaculo = rand.nextInt(numColunas);
            Posicao obstaculo = new Posicao(linhaObstaculo, colunaObstaculo);
            if (!inserirObstaculo(tipoObdtaculo, obstaculo)) {
                //Posisção do obstaculo bateu com alguma invalida(outro obstaculo, posição inicial)
                i--;
            }
        }
    }

    private boolean inserirObstaculo(boolean tipoObstaculo, Posicao obstaculo) {
        for (int i = 0; i < this.vetPosicaoProibida.size(); i++) {
            //Enquanto tiver posições no vetor de posições proibidas
            if (obstaculo.equals(this.vetPosicaoProibida.get(i))) {
                //Se for a mesma posição vai retornar False
                return false;
            }
        }
        //Não existe a posição atual no 'vetPosicaoProibida', então pode inserir
        //adiciona essa posição do obstaculo no 'vetPosicaoProibida', para não haver a possibilidade
            //De inserir outro na mesma posição
        this.vetPosicaoProibida.add(new Posicao(obstaculo.getLinha(), obstaculo.getColuna()));
        diferenciaObstaculo(tipoObstaculo, obstaculo);
        return true;
    }

    private void diferenciaObstaculo(boolean tipoObstaculo, Posicao obstaculo) {
        //Esse método, vai diferenciar se o obstaculo é wumpus ou poço
        if (tipoObstaculo == true) {
            //true == Wumpus
            this.matriz[obstaculo.getLinha()][obstaculo.getColuna()].setWumpus(true);
            inserirSentidos(tipoObstaculo, obstaculo);
        } else {
            this.matriz[obstaculo.getLinha()][obstaculo.getColuna()].setPoco(true);
            inserirSentidos(tipoObstaculo, obstaculo);
        }

    }

    private void inserirSentidos(boolean tipoObstaculo, Posicao obstaculo) {
        //Nesse vai isserir os sentidos(fedor ou brisa) nas posições adjacentes ao obstaculo 
        if (obstaculo.getLinha() - 1 >= 0) {

            if (tipoObstaculo == true) {
                this.matriz[obstaculo.getLinha() - 1][obstaculo.getColuna()].setFedor(true);
            } else {
                this.matriz[obstaculo.getLinha() - 1][obstaculo.getColuna()].setBrisa(true);
            }
        }
        if (obstaculo.getLinha() + 1 < this.matriz.length) {
            if (tipoObstaculo == true) {
                this.matriz[obstaculo.getLinha() + 1][obstaculo.getColuna()].setFedor(true);
            } else {
                this.matriz[obstaculo.getLinha() + 1][obstaculo.getColuna()].setBrisa(true);
            }
        }
        if (obstaculo.getColuna() - 1 >= 0) {
            if (tipoObstaculo == true) {
                this.matriz[obstaculo.getLinha()][obstaculo.getColuna() - 1].setFedor(true);
            } else {
                this.matriz[obstaculo.getLinha()][obstaculo.getColuna() - 1].setBrisa(true);
            }
        }
        if (obstaculo.getColuna() + 1 < this.matriz[0].length) {
            if (tipoObstaculo == true) {
                this.matriz[obstaculo.getLinha()][obstaculo.getColuna() + 1].setFedor(true);
            } else {
                this.matriz[obstaculo.getLinha()][obstaculo.getColuna() + 1].setBrisa(true);
            }
        }
    }

    private void inserirOuro(int numLinhas, int numColunas) {
        int linhaBrilho = 0;
        int colunaBrilho = 0;
        for (int i = 0; i < this.vetPosicaoProibida.size(); i++) {
            //Verifica no vetor 'vetPosicaoProibida' se pode inserir.
            //Gera linha e coluna aleatoria
            linhaBrilho = rand.nextInt(numLinhas);  
            colunaBrilho = rand.nextInt(numColunas);
            Posicao ouro = new Posicao(linhaBrilho, colunaBrilho);

            if (this.vetPosicaoProibida.contains(ouro)) {
                //Se contem posição proibida == a posição do brilho
                    //Vai continuar em loop até achar uma não proibida
                i--;
            }
        }
        //Posição permitida para inserir o ouro
        this.matriz[linhaBrilho][colunaBrilho].setBrilho(true);
    }

}
