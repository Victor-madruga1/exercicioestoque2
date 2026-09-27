/**
 * Classe abstrata que representa um produto genérico do estoque.
 * Implementa Vendavel, pois todo produto pode ser vendido.
 * Cada subclasse decide como calcular seu valor total (polimorfismo dinâmico).
 */
public abstract class Product implements Vendavel {

    private String nome;
    private double preco;
    private int quantidade;

    public Product(String nome, double preco, int quantidade) throws QuantidadeInvalidaException {
        if (preco < 0) {
            throw new QuantidadeInvalidaException(
                    "Preço inválido para o produto \"" + nome + "\": " + preco + " (não pode ser negativo).");
        }
        if (quantidade < 0) {
            throw new QuantidadeInvalidaException(
                    "Quantidade inválida para o produto \"" + nome + "\": " + quantidade + " (não pode ser negativa).");
        }
        this.nome = nome;
        this.preco = preco;
        this.quantidade = quantidade;
    }

    // ----- Getters/Setters protegidos para uso nas subclasses -----
    public String getNome() {
        return nome;
    }

    public double getPreco() {
        return preco;
    }

    protected void setPreco(double preco) {
        this.preco = preco;
    }

    public int getQuantidade() {
        return quantidade;
    }

    protected void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    /**
     * Cada subclasse decide como calcular seu valor total (polimorfismo dinâmico).
     */
    public abstract double calcularValorTotal();

    /**
     * Descrição padrão do produto. Subclasses podem sobrescrever para
     * acrescentar informações específicas (ex.: validade).
     */
    public String getDescricao() {
        return String.format("%s | Preço: R$ %.2f | Quantidade: %d", nome, preco, quantidade);
    }

    /**
     * Implementação de Vendavel: comum a todos os produtos.
     */
    @Override
    public void vender(int quantidadeDesejada) throws ProdutoIndisponivelException {
        if (quantidadeDesejada > this.quantidade) {
            throw new ProdutoIndisponivelException(
                    "Estoque insuficiente para \"" + nome + "\". Disponível: " + this.quantidade
                            + ", solicitado: " + quantidadeDesejada + ".");
        }
        this.quantidade -= quantidadeDesejada;
    }

    // ----- Sobrecarga (polimorfismo estático) -----

    /**
     * Aplica um desconto percentual simples sobre o preço.
     */
    public void aplicarDesconto(double percentual) {
        double novoPreco = preco - (preco * percentual / 100.0);
        this.preco = Math.max(novoPreco, 0);
    }

    /**
     * Aplica um desconto percentual, mas limita o valor máximo (em R$) do desconto concedido.
     */
    public void aplicarDesconto(double percentual, double descontoMaximo) {
        double descontoCalculado = preco * percentual / 100.0;
        double descontoAplicado = Math.min(descontoCalculado, descontoMaximo);
        this.preco = Math.max(preco - descontoAplicado, 0);
    }
}
