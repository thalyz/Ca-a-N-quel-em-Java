public class Premio{
    private String simbolo;
    private int quantidade;
    public Premio(String simbolo, int quantidade){
        this.simbolo = simbolo;
        this.quantidade = quantidade;
    }
    public String getSimbolo(){
        return simbolo;
    }
    public int getQuantidade(){
        return quantidade;
    }
}
