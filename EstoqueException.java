/**
 * Exceção base de todo o sistema de estoque.
 * Todas as exceções específicas do domínio herdam dela.
 */
public class EstoqueException extends Exception {
    public EstoqueException(String mensagem) {
        super(mensagem);
    }
}
