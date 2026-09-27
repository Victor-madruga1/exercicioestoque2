/**
 * Contrato para qualquer item que pode ser vendido no estoque.
 */
public interface Vendavel {
    void vender(int quantidadeDesejada) throws ProdutoIndisponivelException;
}
