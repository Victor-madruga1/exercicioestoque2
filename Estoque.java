import java.util.ArrayList;
import java.util.List;

/**
 * Estoque TEM UMA lista de produtos (composição) — não herda de Product.
 */
public class Estoque {

    private List<Product> produtos;

    public Estoque() {
        this.produtos = new ArrayList<>();
    }

    public void adicionarProduto(Product p) {
        produtos.add(p);
    }

    public void venderProduto(int indice, int quantidade) throws ProdutoIndisponivelException {
        validarIndice(indice);
        produtos.get(indice).vender(quantidade);
    }

    /**
     * Soma o valor total do estoque. Cada produto calcula do seu próprio jeito
     * (polimorfismo): o Estoque não precisa saber se é ProdutoComum ou ProdutoPerecivel.
     */
    public double calcularValorTotalEstoque() {
        double total = 0;
        for (Product p : produtos) {
            total += p.calcularValorTotal();
        }
        return total;
    }

    public void listarProdutos() {
        for (int i = 0; i < produtos.size(); i++) {
            System.out.println("[" + i + "] " + produtos.get(i).getDescricao());
        }
    }

    public List<Product> getProdutos() {
        return produtos;
    }

    private void validarIndice(int indice) {
        if (indice < 0 || indice >= produtos.size()) {
            throw new IndexOutOfBoundsException("Índice de produto inválido: " + indice);
        }
    }
}
