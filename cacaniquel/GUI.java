import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class GUI extends JFrame{
    //Atributos iniciais
    private static int largura = 1366;
    private static int altura = 768;
    private static int pontos = 0;
    private static int fichas = 100;
    private static int aposta = 1;
    private CaçaNiquel caçaNiquel;
    private JLabel[][] carretelLabel;
    private JTextArea resultadoMensagem;
    private JLabel resultadoLabel;
    private JLabel pontuacaoLabel;
    private JLabel fichasLabel;
    private ArrayList<Premio> ultimosPremios;
    
    //Construtor da interface
    public GUI(){
        caçaNiquel = new CaçaNiquel();
        ultimosPremios = new ArrayList<>();
        inicializar();
    }
    
    //Inicia de fato o caça niquel por meio da interface grafica
    private void inicializar(){
        //Configuração da janela principal
        setTitle("Caça Niquel");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);
        setSize(largura, altura);
        setVisible(true);
        
        //Configuração do Painel Principal e Carretel
        JPanel painelPrincipal = new JPanel(new BorderLayout());
        JPanel paineldoCarretel = new JPanel(new GridLayout(3, 3));
        carretelLabel = new JLabel[3][3];
        
        //Criaçao do Carretel
        for (int i = 0; i < 3; i++){
            for (int j = 0; j < 3; j++){
                carretelLabel[i][j] = new JLabel();
                carretelLabel[i][j].setHorizontalAlignment(JLabel.CENTER);
                paineldoCarretel.add(carretelLabel[i][j]);
            }
        }

        //Configuração dos Botões
        JPanel botaoPainel = new JPanel(new GridLayout(3, 1));
        JButton botaoGirar = new JButton("Girar");
        JButton botaoVerUltimosPremios = new JButton("Ver Últimos Prêmios");
        JButton botaoApostas = new JButton ("Aposta");
        botaoGirar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                girar();
            }
        });
        botaoVerUltimosPremios.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                showUltimosPremios();
            }
        });
        botaoApostas.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                aposta = apostar();
            }
        });
        botaoGirar.setPreferredSize(new Dimension(100, 100));
        botaoVerUltimosPremios.setPreferredSize(new Dimension(200, 100));
        botaoApostas.setPreferredSize(new Dimension(100, 100));
        
        //Adiçao dos Botoes ao Painel Principal de Botoes
        botaoPainel.add(botaoGirar);        
        botaoPainel.add(botaoVerUltimosPremios); 
        botaoPainel.add(botaoApostas);
        
        //Configuração dos Labels de Resultado, Pontuação e Fichas
        resultadoMensagem = new JTextArea();
        resultadoMensagem.setPreferredSize(new Dimension(250, 180));
        resultadoMensagem.setEditable(false);
        resultadoMensagem.setFont(new Font("Arial", Font.PLAIN, 16));
        resultadoLabel = new JLabel();
        resultadoLabel.setHorizontalAlignment(JLabel.CENTER);
        resultadoLabel.setFont(new Font("Arial", Font.BOLD, 18));
        resultadoLabel.setText("\n");
        pontuacaoLabel = new JLabel("Pontuação: " + pontos);
        pontuacaoLabel.setHorizontalAlignment(JLabel.CENTER);
        pontuacaoLabel.setFont(new Font("Arial", Font.BOLD, 18));
        fichasLabel = new JLabel("Fichas: " + fichas);
        fichasLabel.setHorizontalAlignment(JLabel.CENTER);
        fichasLabel.setFont(new Font("Arial", Font.BOLD, 18));
        
        //Configuração dos Painéis Superior, Esquerdo, Inferior e Central
        JPanel topPanel = new JPanel();
        JPanel topPanelLeft = new JPanel();
        JPanel topPanelRight = new JPanel();
        JPanel leftPanel = new JPanel(new BorderLayout());
        JPanel southPanel = new JPanel(new BorderLayout());
        JPanel centerPanel = new JPanel(new BorderLayout()); 
        
        //Configuração da Pontuação e Fichas no Painel Superior
        topPanel.add(topPanelRight, BorderLayout.EAST);
        topPanel.add(topPanelLeft, BorderLayout.WEST);
        topPanelLeft.add(fichasLabel, BorderLayout.WEST);
        topPanelRight.add(pontuacaoLabel, BorderLayout.EAST);
        
        //Configuração do Painel Esquerdo com Botão Girar e uma flecha (->)
        leftPanel.add(topPanel, BorderLayout.NORTH);
        leftPanel.add(botaoGirar, BorderLayout.WEST);
        leftPanel.add(new JLabel("                                                            ->"), BorderLayout.EAST);
        
        //Configuração do Painel Central com Carretel
        centerPanel.add(paineldoCarretel, BorderLayout.CENTER);
        
        //Configuração do Painel Inferior com Resultado e Botões
        southPanel.add(resultadoLabel, BorderLayout.CENTER);
        southPanel.add(botaoVerUltimosPremios, BorderLayout.WEST);
        southPanel.add(botaoApostas, BorderLayout.EAST);
        
        //Configuração do Painel Inferior com Resultado e Botões
        painelPrincipal.add(topPanel, BorderLayout.NORTH);
        painelPrincipal.add(leftPanel, BorderLayout.WEST);
        painelPrincipal.add(southPanel, BorderLayout.SOUTH);
        painelPrincipal.add(centerPanel, BorderLayout.CENTER);
        
        //Adição do Painel Principal à Janela
        add(painelPrincipal);
    }
    
    private void girar(){
        //Gira o caça niquel, atualiza o carretel e avalia o resultado
        if(fichas > 0){
            caçaNiquel.girar();
            updateCarretel();
            Premio premio = caçaNiquel.avaliarResultado();
            updateCarretel(premio);
            fichas -= aposta;
        }
        else{
            //Caso o jogador nao tenha mais ficha ele perdera
            gameOver();
        }
    }
    
    private void updateCarretel(){
        // Atualiza a interface gráfica com os símbolos do carretel
        String[][] carretel = caçaNiquel.getCarretel();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                carretelLabel[i][j].setIcon(carregarImagem(carretel[i][j]));
            }
        }
    }
    
    private void updateCarretel(Premio premio){
        // Atualiza a interface gráfica com base no prêmio obtido
        if (premio != null) {
            resultadoLabel.setText("Parabens! Voce ganhou " + aposta*(premio.getQuantidade()) + " pontos!");
            pontos += aposta*(premio.getQuantidade());
            fichas += aposta*(premio.getQuantidade());
            ultimosPremios.add(premio);
        } else {
            resultadoLabel.setText("\n");
        }
        pontuacaoLabel.setText("Pontuação: " + pontos);
        fichasLabel.setText("Fichas: " + fichas);
        resultadoMensagem.setText("Resultado: " + (premio != null ? premio.getSimbolo() + " - Premio: " + premio.getQuantidade() : "Na vitoria"));
    }
    
    private void showUltimosPremios(){
        // Cria e exibe uma janela com os últimos prêmios obtidos
        JFrame ultimosPremiosFrame = new JFrame("Últimos Prêmios");
        ultimosPremiosFrame.setSize(400, 300);
        ultimosPremiosFrame.setLocationRelativeTo(null);
        JTextArea premiosTextArea = new JTextArea();
        premiosTextArea.setEditable(false);
        for (Premio premio : ultimosPremios) {
            premiosTextArea.append(premio.getSimbolo() + " - Prêmio: " + premio.getQuantidade() + "\n");
        }
        JScrollPane scrollPane = new JScrollPane(premiosTextArea);
        ultimosPremiosFrame.add(scrollPane);
        ultimosPremiosFrame.setVisible(true);
    }
    
    private int apostar(){
        // Exibe uma caixa para o jogador escolher a quantidade de fichas a apostar
        String[] apostas = {"1", "2", "3"};
        String apostaEscolhida = (String) JOptionPane.showInputDialog(
                this,
                "Quantas fichas deseja apostar?\n",
                "",
                JOptionPane.QUESTION_MESSAGE,
                null,
                apostas,
                apostas[0]
        );
        return Integer.parseInt(apostaEscolhida);
    }
    
    private ImageIcon carregarImagem(String simbolo){
        // Carrega uma imagem com base no símbolo fornecido
        String pasta = "images/";
        String pathing = pasta + simbolo.toLowerCase() + ".png";
        return new ImageIcon(getClass().getClassLoader().getResource(pathing));
    }
    
    private void gameOver(){
        // Exibe uma mensagem de jogo encerrado e finaliza o programa
        JOptionPane.showMessageDialog(this, "Fim de jogo.");
        System.exit(1);
    }
}
