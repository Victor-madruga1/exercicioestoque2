/**
 * Produto perecível: aplica 20% de desconto automático no valor total
 * quando faltam poucos dias para vencer (diasParaVencer <= 3).
 */
public class ProdutoPerecivel extends Product {

    private int diasParaVencer;

    public ProdutoPerecivel(String nome, double preco, int quantidade, int diasParaVencer)
            throws QuantidadeInvalidaException {
        super(nome, preco, quantidade);
        this.diasParaVencer = diasParaVencer;
    }

    public int getDiasParaVencer() {
        return diasParaVencer;
    }

    @Override
    public double calcularValorTotal() {
        double valor = getPreco() * getQuantidade();
        if (diasParaVencer <= 3) {
            valor *= 0.8; // desconto automático de 20%
        }
        return valor;
    }

    @Override
    public String getDescricao() {
        String situacao = (diasParaVencer <= 3) ? " [PRÓXIMO DO VENCIMENTO - 20% OFF]" : "";
        return String.format("%s | Preço: R$ %.2f | Quantidade: %d | Vence em: %d dia(s)%s",
                getNome(), getPreco(), getQuantidade(), diasParaVencer, situacao);
    }
}
