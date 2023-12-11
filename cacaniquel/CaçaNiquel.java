import java.util.ArrayList;
import java.util.Random;

public class CaçaNiquel{
    private String[] simbolos = {"Cereja", "Laranja", "Limao", "Ameixa", "Banana", "Uva", "Melancia", "Sino", "Estrela", "7"};
    private int[] pontos = {10, 20, 30, 40, 50, 60, 75, 100, 150, 200};

    private String[][] carretel;
    private int pontuacao;

    public CaçaNiquel(){
        carretel = new String[3][3];
        pontuacao = 0;
        girar();
    }
    
    public void girar(){
        // Gira o caça-níquel, preenchendo o carretel com símbolos aleatórios
        Random random = new Random();
        for (int i = 0; i < 3; i++){
            for (int j = 0; j < 3; j++){
                carretel[i][j] = selecionarSimboloAleatorio(random);
            }
        }
        Premio premio = avaliarResultado();
        if (premio != null){
            pontuacao += premio.getQuantidade();
        }
    }
    
    private String selecionarSimboloAleatorio(Random random){
        // Seleciona um símbolo aleatório para o carretel
        int indiceAleatorio = random.nextInt(simbolos.length);
        return simbolos[indiceAleatorio];
    }
    
    public Premio avaliarResultado(){
        // Avalia o resultado do giro e retorna um prêmio, se houver
        String simboloCentro = carretel[1][1];
        if (carretel[1][0].equals(simboloCentro) && carretel[1][2].equals(simboloCentro)){
            int quantidadePremio = getQuantidadePremio(simboloCentro);
            Premio premio = new Premio(simboloCentro, quantidadePremio);
            pontuacao += quantidadePremio;
            return premio;
        }
        return null;
    }
    
    public String[][] getCarretel(){
        // Retorna o estado atual do carretel
        return carretel;
    }
    
    public int getPontuacao(){
        // Retorna o estado atual da pontuaçao
        return pontuacao;
    }
    
    private int getQuantidadePremio(String simbolo){
        // Retorna a quantidade de pontos associada a um símbolo
        for (int i = 0; i < simbolos.length; i++){
            if (simbolos[i].equals(simbolo)){
                return pontos[i];
            }
        }
        return 0;
    }
}

