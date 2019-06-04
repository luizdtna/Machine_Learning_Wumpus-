/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
 /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mundowumpus;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import sun.swing.ImageIconUIResource;

public final class InterfaceMundoW extends JFrame {

    public JPanel panel;
    public static ArrayList<JPanel> arrayPanel = new ArrayList<>();
    public JButton[][] matrizBotoes;
    public Posicao[] posIJ;
    
    private java.net.URL urlWumpusD = getClass().getResource("/icons/wumpus_duv.png");
    private java.net.URL urlWumpus = getClass().getResource("/icons/wumpus.png");
    private java.net.URL urlagt = getClass().getResource("/icons/agente.png");
    private java.net.URL urlPoco = getClass().getResource("/icons/poco.png");
    private java.net.URL urlBrisaFedorA = getClass().getResource("/icons/brisa_fedorA.jpg");
    private java.net.URL urlFedor = getClass().getResource("/icons/fedor.jpg");
    private java.net.URL urlBrisa = getClass().getResource("/icons/brisa.jpg");
    private java.net.URL urlBrisaA = getClass().getResource("/icons/brisa_agt.png");
    private java.net.URL urlFedorA = getClass().getResource("/icons/fedor_agt.png");
    private java.net.URL urlOuroA = getClass().getResource("/icons/ouro_agt.png");
    private java.net.URL urlPocoD = getClass().getResource("/icons/poco_duv.png");
    private java.net.URL urlBrisaFedor = getClass().getResource("/icons/brisa_fedor.jpg");
    
    private Icon imgWumpus = new ImageIcon(urlWumpus);
    private Icon imgWumpusD = new ImageIcon(urlWumpusD);
    private Icon imgPoco = new ImageIcon(urlPoco);
    private Icon imgPocoD = new ImageIcon(urlPocoD);
    private Icon imgFedor = new ImageIcon(urlFedor);
    private Icon imgBrisa = new ImageIcon(urlBrisa);
    private Icon imgBrisaFedor = new ImageIcon(urlBrisaFedor);
    private Icon imgBrisaFedorA = new ImageIcon(urlBrisaFedorA);
    private Icon imgAgt = new ImageIcon(urlagt);
    private Icon imgBrisaA = new ImageIcon(urlBrisaA);
    private Icon imgFedorA = new ImageIcon(urlFedorA);
    private Icon imgOuroA = new ImageIcon(urlOuroA);

    public InterfaceMundoW() {
      //  img = new ImageIcon("/view/oie_transparent (1).png");
        //Tamanho do Jframe
        this.setSize(500, 500);
        //A tela não pode ser maximizada nem minimizada
        this.setResizable(false);

        //Centraliza a tela
        setLocationRelativeTo(null);
        setDefaultCloseOperation(InterfaceMundoW.DISPOSE_ON_CLOSE);
        

    }

    public void componentes(MatrizAprendizagem matriz[][], int qtdLinha, int qtdCol, Posicao posiAgenteAtual, boolean comOuro, boolean fim) {
        //Matriz de botões vai ser utilizada para preencher a tela 
        matrizBotoes = new JButton[qtdLinha][qtdCol];
        panel = new JPanel();
        //O GridLayout tem a funcionalidade de organizar a matriz, dado as linhas e colunas
        panel.setLayout(new GridLayout(qtdLinha, qtdCol));

        boolean posicaoAgente = false;
        for (int i = 0; i < qtdLinha; i++) {
            for (int j = 0; j < qtdCol; j++) {
                //É instanciado um botão com as posições   
                matrizBotoes[i][j] = new JButton("");
                if ((posiAgenteAtual.linha == i) && (posiAgenteAtual.coluna == j)) {
                    //Compara se a posição atual do agente é a mesma dos indices

                    matrizBotoes[i][j].setBackground(Color.white);
                    if (comOuro == false) {
                        //É adicionado um icone para identificar a posição do agente
                        matrizBotoes[i][j].setIcon(imgAgt);
                    } else {
                        //Agente já com o ouro
                        matrizBotoes[i][j].setIcon(imgOuroA);
                    }
                    posicaoAgente = true;
                    if (matriz[i][j].isFedor() == true) {
                        //Tem fedor
                        matrizBotoes[i][j].setIcon(imgFedorA);
                        if (matriz[i][j].isBrisa() == true) {
                            //Tem fedor e brisa
                            matrizBotoes[i][j].setIcon(imgBrisaFedorA);
                        }
                    } else if (matriz[i][j].isBrisa()) {
                        //tem brisa
                        matrizBotoes[i][j].setIcon(imgBrisaA);
                    }
                    if (matriz[i][j].isBrilho() == true) {
                        //Tem brilho
                        matrizBotoes[i][j].setIcon(imgOuroA);

                    } else if (matriz[i][j].getWumpus() == Busca.TEM_OBSTACULO) {
                        //tem wumpus
                        matrizBotoes[i][j].setBackground(Color.white);
                        matrizBotoes[i][j].setIcon(imgWumpus);
                    } else if (matriz[i][j].getPoco() == Busca.TEM_OBSTACULO) {
                        //tem poco
                        matrizBotoes[i][j].setBackground(Color.white);
                        matrizBotoes[i][j].setIcon(imgPoco);
                    }
                } else {
                    if (matriz[i][j].isExplorada() == false) {
                        //Caso a posição i:j não foi explorada
                        matrizBotoes[i][j].setBackground(Color.gray);

                        if (matriz[i][j].getTalvezWumpus().contains(matriz[i][j].getPosicao())) {
                            //Caso o vetor de possiveis posições desta, conter a própria posição 
                            matrizBotoes[i][j].setBackground(Color.white);
                            matrizBotoes[i][j].setIcon(imgWumpusD);
                            if (matriz[i][j].getTalvezPoco().contains(matriz[i][j].getPosicao())) {
                                //Pode ter wumpus e poco
                                matrizBotoes[i][j].setIcon(imgPocoD);
                            }
                        } else if (matriz[i][j].getTalvezPoco().contains(matriz[i][j].getPosicao())) {
                            //Pode ter poco
                            matrizBotoes[i][j].setBackground(Color.white);
                            matrizBotoes[i][j].setIcon(imgPocoD);
                        }
                        if (matriz[i][j].getWumpus() == Busca.TEM_OBSTACULO) {
                            //Descobiu o obstaculo
                            matrizBotoes[i][j].setBackground(Color.white);
                            matrizBotoes[i][j].setIcon(imgWumpus);
                        }
                        if (matriz[i][j].getPoco() == Busca.TEM_OBSTACULO) {
                            //Descobiu o obstaculo
                            matrizBotoes[i][j].setBackground(Color.white);
                            matrizBotoes[i][j].setIcon(imgPoco);
                        }

                    } else {
                        //Foi explorada
                        matrizBotoes[i][j].setBackground(Color.white);
                        if (matriz[i][j].isFedor() == true) {
                            //Tem fedor
                            matrizBotoes[i][j].setIcon(imgFedor);
                            if (matriz[i][j].isBrisa() == true) {
                                //Tem fedor e brisa
                                matrizBotoes[i][j].setIcon(imgBrisaFedor);
                            }
                        } else if (matriz[i][j].isBrisa() == true) {
                            //Tem Brisa
                            matrizBotoes[i][j].setIcon(imgBrisa);
                        } else if (matriz[i][j].getWumpus() == Busca.TEM_OBSTACULO) {
                            //Se foi explorada e tiver o obstaculo (perde 1000 pontos)
                            matrizBotoes[i][j].setIcon(imgWumpus);
                        } else if (matriz[i][j].getPoco() == Busca.TEM_OBSTACULO) {
                            //Se foi explorada e tiver o obstaculo (perde 1000 pontos)
                            matrizBotoes[i][j].setIcon(imgPoco);
                        }
                    }
                }
                panel.add(matrizBotoes[i][j]);
            }

        }
        //Insere o jPainel no array de paneis
        InterfaceMundoW.arrayPanel.add(panel);

        if (fim == true) {
            exibirView();
        }
    }

    private void exibirView() {

        for (JPanel objPanel : arrayPanel) {
            //Adiciona o jPanel no jFrame para exibir
            this.add(objPanel, BorderLayout.CENTER);
            this.setVisible(true);

            try {
                //pausa um determinado tempo a alteração de jPainel
                Thread.currentThread().sleep(500);
            } catch (InterruptedException ex) {
                Logger.getLogger(InterfaceMundoW.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            if (InterfaceMundoW.arrayPanel.indexOf(objPanel) != InterfaceMundoW.arrayPanel.size() - 1) {
                //Enquanto o objPanel não for o ultimo do array
                objPanel.setVisible(false);
                this.remove(objPanel);
                //objPanel.disable();
            } else {
                JOptionPane.showMessageDialog(null, "Objetivo concluido!!\nA pontuação é: "+Busca.pontuacao);
                System.exit(0);
            }
        }
    }

}
