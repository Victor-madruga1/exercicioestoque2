/**
 * Lançada quando preço ou quantidade informados são inválidos (negativos).
 */
public class QuantidadeInvalidaException extends EstoqueException {
    public QuantidadeInvalidaException(String mensagem) {
        super(mensagem);
    }
}
