/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mundowumpus;

import java.util.ArrayList;
import java.util.Random;
import javax.swing.JOptionPane;



public class Busca {

    InterfaceMundoW view = new InterfaceMundoW();
    static final int NAO_TEM_OBSTACULO = 1;
    static final int TEM_OBSTACULO = 2;
    private final int QTD_LINHA;
    private final int QTD_COLUN;
    static int pontuacao = 0;
    private int vidas = 2;
    private final Matriz[][] matriz;
    static MatrizAprendizagem[][] matrizAprendizagem;
    private final Posicao posInicio;
    private Wumpus wumpus;
    private Poco poco;
    private ArrayList<Posicao> fila_CaminharPossiveisPosicoes = new ArrayList<>();

    public Busca(Matriz[][] newMatriz, Posicao newPosInicio, int qtdlinha, int qtdColuna) {
        this.QTD_LINHA = qtdlinha;
        this.QTD_COLUN = qtdColuna;

        Busca.matrizAprendizagem = new MatrizAprendizagem[qtdlinha][qtdColuna];
        for (int i = 0; i < qtdlinha; i++) {
            for (int j = 0; j < qtdColuna; j++) {
                //Instacia as posições da matriz de aprendizagem
                matrizAprendizagem[i][j] = new MatrizAprendizagem(new Posicao(i, j));
            }
        }
        this.wumpus = new Wumpus();
        this.poco = new Poco();
        this.matriz = newMatriz;
        this.posInicio = newPosInicio;
    }

    public void BuscarOuro() {
        int contBuscas = 0;
        Posicao atual = posInicio;

        while (true) {
            if (verificaWumps(atual) == false) {
                
                //Não existe wumpus nesta posição 
                Busca.matrizAprendizagem[atual.linha][atual.coluna].setWumpus(Busca.NAO_TEM_OBSTACULO);
            } else {
                //O SISTEMA NÃO ESTÁ IMPLEMENTADO PARA ARRISCAR A IR EM UMA POSIÇÃO QUE PODE TER WUMPUS
                
                //O Wumpus te pegou
                pontuacao = -1000;
                vidas--;
                //Armazena a posição do Wumpus
                matrizAprendizagem[atual.linha][atual.coluna].setWumpus(Busca.TEM_OBSTACULO);
                atual = posInicio;
                if (vidas == 0) {
                    break;
                }
            }

            if (verificaPoco(atual) == false) {
                matrizAprendizagem[atual.linha][atual.coluna].setPoco(Busca.NAO_TEM_OBSTACULO);
            } else {
                //O SISTEMA NÃO ESTÁ IMPLEMENTADO PARA ARRISCAR A IR EM UMA POSIÇÃO QUE PODE TER POÇO
                
                //Caiu no poço
                pontuacao = -1000;
                vidas--;
                //Armazena a posição do poço
                matrizAprendizagem[atual.linha][atual.coluna].setWumpus(Busca.TEM_OBSTACULO);
                atual = posInicio;
                if (vidas == 0) {
                    break;
                }
            }
            if (verificaOuro(atual) == true) {
                Busca.matrizAprendizagem[atual.linha][atual.coluna].setExplorada(true);
                Busca.matrizAprendizagem[atual.linha][atual.coluna].setBrilho(true);
                this.pontuacao += 1000;
                this.view.componentes(matrizAprendizagem, QTD_LINHA, QTD_COLUN, atual,true, false);
                voltarAoPontoDePartida(atual);
                break;

            }

            if (verificaFedor(atual) == false) {
                //Sem fedor
                Busca.matrizAprendizagem[atual.linha][atual.coluna].setFedor(false);
                this.wumpus.pre_EliminacaoDeE_Wumpus(atual);
            } else {
                //Tem fedor nesta posição
                Busca.matrizAprendizagem[atual.linha][atual.coluna].setFedor(true);
                this.wumpus.ProbabilidadeWumpusAdjacentes(atual);
            }
            if (verificaBrisa(atual) == false) {
                //sem brisa
                Busca.matrizAprendizagem[atual.linha][atual.coluna].setBrisa(false);
                this.poco.pre_EliminacaoDeE_Poco(atual);
            } else {
                //Tem brisa nesta posição
                this.poco.ProbabilidadePocoAdjacentes(atual);
                Busca.matrizAprendizagem[atual.linha][atual.coluna].setBrisa(true);
            }
            
            this.view.componentes(matrizAprendizagem, QTD_LINHA, QTD_COLUN, atual,false, false);
            atual = caminhar(atual);
            contBuscas++;
            if(contBuscas == 500){
                JOptionPane.showMessageDialog(null, "Loop infinito, o ouro está cercado de obstaculos","Mundo do Wumpus",vidas);
                System.exit(0);
            }

        }
    }

    private void voltarAoPontoDePartida(Posicao atual) {
        //fila de posições adjacentes a posição atual do agente
        ArrayList<Posicao> lista_ParaVoltar = new ArrayList<>();
        Posicao irPara = null;
        while (true) {
            //remove tudo da lista da posição anterior, para adicionar as posições
                //adjacente da posição atual 
            lista_ParaVoltar.removeAll(lista_ParaVoltar);

            if (atual.getLinha() - 1 >= 0) {
                //Se foi explorada, quer dizer que não tem obstaculo
                if (matrizAprendizagem[atual.linha - 1][atual.coluna].isExplorada() == true) {
                    //Então é seguro adicionar na lista de adjacentes
                    lista_ParaVoltar.add(new Posicao(atual.linha - 1, atual.coluna));
                }
            }
            if (atual.getLinha() + 1 < QTD_LINHA) {
                if (matrizAprendizagem[atual.linha + 1][atual.coluna].isExplorada() == true) {
                    lista_ParaVoltar.add(new Posicao(atual.linha + 1, atual.coluna));
                }
            }
            if (atual.getColuna() - 1 >= 0) {
                if (matrizAprendizagem[atual.linha][atual.coluna - 1].isExplorada() == true) {
                    lista_ParaVoltar.add(new Posicao(atual.linha, atual.coluna - 1));
                }
            }
            if (atual.getColuna() + 1 < QTD_COLUN) {
                if (matrizAprendizagem[atual.linha][atual.coluna + 1].isExplorada() == true) {
                    lista_ParaVoltar.add(new Posicao(atual.linha, atual.coluna + 1));
                }
            }
            irPara = encontrarMelhorAdjacente(lista_ParaVoltar);
            atual = irPara;

            this.view.componentes(matrizAprendizagem, QTD_LINHA, QTD_COLUN, irPara,true, false);
            if ((irPara.linha == posInicio.linha) && (irPara.coluna == posInicio.coluna)) {
                view.componentes(matrizAprendizagem, QTD_LINHA, QTD_COLUN, irPara,true, true);
                break;
            }
        }
    }

    private Posicao encontrarMelhorAdjacente(ArrayList<Posicao> newLista_ParaVoltar) {
        int aux = 9999;
        int aux2 = 0;
        Posicao escolhida = null;
        for (Posicao objPos : newLista_ParaVoltar) {
            //Verifica na heuristica
            aux2 = heuristica(objPos);
            if (aux2 < aux) {
                //de acordo com a heuristica aux2 é melhor(menor peso) que aux 
                aux = aux2;
                //como comparou com a heuristica da posição de 'objPos', ele será escolhido
                escolhida = objPos;
            }
        }
        //A posição escolhida acrescenta 1 em seu peso
        matrizAprendizagem[escolhida.linha][escolhida.coluna].setSomaPeso(1);
        return escolhida;
    }

    private int heuristica(Posicao atual) {
        //Medir a heuristica para voltar ao inicio com a melhor rota,
            //de acordo com posições exploradas 
        int heuristica = 0;
        int aux = atual.linha + atual.coluna;
        int aux2 = this.posInicio.linha + this.posInicio.coluna;

        if (aux > aux2) {
            heuristica = (aux - aux2);

        } else {
            heuristica = (aux2 - aux);

        }
        //Soma a heuristica com o peso da posição atual
        return heuristica + Busca.matrizAprendizagem[atual.linha][atual.coluna].getPeso();
    }

    private Posicao caminhar(Posicao atual) {
        Posicao proxima;
        //Se caminhar perde 2 pontos
        this.pontuacao -= 2;
        Busca.matrizAprendizagem[atual.linha][atual.coluna].setExplorada(true);
        //Adiciona a lista de posições seguras 
        this.fila_CaminharPossiveisPosicoes.add(atual);
        proxima = fronteiraParaCaminhar(atual);

        return proxima;

    }

    private Posicao fronteiraParaCaminhar(Posicao atual) {
        Random r = new Random();
        boolean existePosicoesInexploradas = false;
        ArrayList<Posicao> possiveisPosicoes = new ArrayList<>();
        Posicao escolhida;
        Posicao posAux;
        for (int i = 0; i < 2; i++) {
            //Loop, por que tem que verificar todos as 4 possiveis fronteiras primeiras
                //Se no 1º loop não encontrou possições inexploradas, o 2° vai buscar posições
                //que já foram exploradas
            if (atual.getLinha() - 1 >= 0) {

                posAux = new Posicao(atual.getLinha() - 1, atual.getColuna());
                if (!Busca.matrizAprendizagem[atual.getLinha() - 1][atual.getColuna()].getTalvezWumpus().contains(posAux)) {
                    //Não tem a possibilidade de wumpus
                    if (!Busca.matrizAprendizagem[atual.getLinha() - 1][atual.getColuna()].getTalvezPoco().contains(posAux)) {
                        //Não tem a possibilidade de ter poco
                        if (Busca.matrizAprendizagem[atual.getLinha() - 1][atual.getColuna()].isExplorada() == false) {
                            //Não foi explorada
                            possiveisPosicoes.add(new Posicao(atual.getLinha() - 1, atual.getColuna()));
                            //Idenfica que houve posições inexploradas
                            existePosicoesInexploradas = true;
                        } else if (existePosicoesInexploradas == false) {
                            if (i == 1) {
                                possiveisPosicoes.add(new Posicao(atual.getLinha() - 1, atual.getColuna()));
                            }
                        }
                    }
                }
            }
            if (atual.getLinha() + 1 < this.matriz.length) {

                posAux = new Posicao(atual.getLinha() + 1, atual.getColuna());
                if (!Busca.matrizAprendizagem[atual.getLinha() + 1][atual.getColuna()].getTalvezWumpus().contains(posAux)) {
                    //Essa posicao não é suspeita de ter Wumpus
                    if (!Busca.matrizAprendizagem[atual.getLinha() + 1][atual.getColuna()].getTalvezPoco().contains(posAux)) {
                        if (Busca.matrizAprendizagem[atual.getLinha() + 1][atual.getColuna()].isExplorada() == false) {
                            possiveisPosicoes.add(new Posicao(atual.getLinha() + 1, atual.getColuna()));
                            existePosicoesInexploradas = true;
                        } else if (existePosicoesInexploradas == false) {
                            if (i == 1) {
                                possiveisPosicoes.add(new Posicao(atual.getLinha() + 1, atual.getColuna()));
                            }

                        }
                    }
                }
            }
            if (atual.getColuna() - 1 >= 0) {

                posAux = new Posicao(atual.getLinha(), atual.getColuna() - 1);
                if (!Busca.matrizAprendizagem[atual.getLinha()][atual.getColuna() - 1].getTalvezWumpus().contains(posAux)) {
                    if (!Busca.matrizAprendizagem[atual.getLinha()][atual.getColuna() - 1].getTalvezPoco().contains(posAux)) {
                        if (Busca.matrizAprendizagem[atual.getLinha()][atual.getColuna() - 1].isExplorada() == false) {
                            possiveisPosicoes.add(new Posicao(atual.getLinha(), atual.getColuna() - 1));
                            existePosicoesInexploradas = true;
                        } else if (existePosicoesInexploradas == false) {
                            if (i == 1) {
                                possiveisPosicoes.add(new Posicao(atual.getLinha(), atual.getColuna() - 1));
                            }
                        }
                    }
                }
            }
            if (atual.getColuna() + 1 < this.matriz.length) {

                posAux = new Posicao(atual.getLinha(), atual.getColuna() + 1);
                if (!Busca.matrizAprendizagem[atual.getLinha()][atual.getColuna() + 1].getTalvezWumpus().contains(posAux)) {
                    if (!Busca.matrizAprendizagem[atual.getLinha()][atual.getColuna() + 1].getTalvezPoco().contains(posAux)) {
                        if (Busca.matrizAprendizagem[atual.getLinha()][atual.getColuna() + 1].isExplorada() == false) {
                            possiveisPosicoes.add(new Posicao(atual.getLinha(), atual.getColuna() + 1));
                            existePosicoesInexploradas = true;
                        } else if (existePosicoesInexploradas == false) {
                            if (i == 1) {
                                possiveisPosicoes.add(new Posicao(atual.getLinha(), atual.getColuna() + 1));
                            }
                        }
                    }
                }
            }

        }
        if (existePosicoesInexploradas == true) {
            //tem posições inexploradas
            int aux = r.nextInt(possiveisPosicoes.size());
            //É gerado um numero aleatório que é o indece da proxima posição
            escolhida = possiveisPosicoes.get(aux);
        } else {
            //Todas as posições adjacentes ja foram exploradas 

            int min = 9999;
            for (Posicao obj : possiveisPosicoes) {

                if (fila_CaminharPossiveisPosicoes.indexOf(obj) < min) {
                    //Vai escolher a posição adjacente que tenha o menor index
                    //se o index do 'obj' for menor que 'min'(o menor index), 'obj' pode ser escolhido
                    min = fila_CaminharPossiveisPosicoes.indexOf(obj);
                }
            }
            //Menor index escolhido
            escolhida = this.fila_CaminharPossiveisPosicoes.get(min);
            //É removido a posição escolhida da lista, para a lista ir atualizando
            this.fila_CaminharPossiveisPosicoes.remove(escolhida);

        }
        return escolhida;
    }

    private boolean verificaOuro(Posicao atual) {
        return matriz[atual.linha][atual.coluna].isBrilho() == true;
    }

    private boolean verificaWumps(Posicao atual) {
        return matriz[atual.linha][atual.coluna].isWumpus() == true;
    }

    private boolean verificaPoco(Posicao atual) {
        return matriz[atual.linha][atual.coluna].isPoco() == true;
    }

    private boolean verificaFedor(Posicao atual) {
        return matriz[atual.linha][atual.coluna].isFedor() == true;
    }

    private boolean verificaBrisa(Posicao atual) {
        return matriz[atual.linha][atual.coluna].isBrisa() == true;
    }
}
