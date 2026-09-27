/**
 * Classe principal: demonstra o uso de toda a hierarquia (herança, polimorfismo,
 * composição, interfaces e tratamento de exceções).
 */
public class EstoqueApp {

    public static void main(String[] args) {

        // Garante que acentos sejam impressos corretamente em qualquer console
        System.setOut(new java.io.PrintStream(System.out, true, java.nio.charset.StandardCharsets.UTF_8));

        Estoque estoque = new Estoque();

        System.out.println("===== 1) Cadastrando produtos =====");
        try {
            // 2 produtos comuns
            ProdutoComum arroz = new ProdutoComum("Arroz 5kg", 25.90, 50);
            ProdutoComum detergente = new ProdutoComum("Detergente", 3.50, 100);

            // 2 produtos perecíveis (um deles perto do vencimento)
            ProdutoPerecivel iogurte = new ProdutoPerecivel("Iogurte Natural", 6.00, 30, 2); // <= 3 dias -> desconto
            ProdutoPerecivel queijo = new ProdutoPerecivel("Queijo Minas", 18.00, 15, 10);   // fora do desconto

            estoque.adicionarProduto(arroz);
            estoque.adicionarProduto(detergente);
            estoque.adicionarProduto(iogurte);
            estoque.adicionarProduto(queijo);

            System.out.println("Produtos cadastrados com sucesso:");
            estoque.listarProdutos();

        } catch (QuantidadeInvalidaException e) {
            System.out.println("Erro ao cadastrar produto: " + e.getMessage());
        }

        System.out.println();
        System.out.println("===== 2) Tentando cadastrar produto com quantidade negativa =====");
        try {
            ProdutoComum produtoInvalido = new ProdutoComum("Produto Inválido", 10.00, -5);
            estoque.adicionarProduto(produtoInvalido);
        } catch (QuantidadeInvalidaException e) {
            System.out.println("Exceção capturada corretamente -> " + e.getMessage());
        }

        System.out.println();
        System.out.println("===== 3) Vendendo uma quantidade válida (índice 0 - Arroz) =====");
        try {
            estoque.venderProduto(0, 10);
            System.out.println("Venda realizada com sucesso! Estoque atualizado:");
            estoque.listarProdutos();
        } catch (ProdutoIndisponivelException e) {
            System.out.println("Erro na venda: " + e.getMessage());
        }

        System.out.println();
        System.out.println("===== 4) Tentando vender mais do que o disponível (índice 0 - Arroz) =====");
        try {
            estoque.venderProduto(0, 1000);
        } catch (ProdutoIndisponivelException e) {
            System.out.println("Exceção capturada corretamente -> " + e.getMessage());
        }

        // Observação sobre a ordem dos catches: se um mesmo bloco try capturasse
        // QuantidadeInvalidaException, ProdutoIndisponivelException e EstoqueException,
        // o catch de EstoqueException (o mais genérico) precisaria vir por último —
        // colocá-lo antes tornaria os catches seguintes inalcançáveis (erro de compilação).

        System.out.println();
        System.out.println("===== 5) Valor total do estoque (polimorfismo em ação) =====");
        System.out.printf("Valor total do estoque: R$ %.2f%n", estoque.calcularValorTotalEstoque());

        System.out.println();
        System.out.println("===== 6) Exemplo de sobrecarga (polimorfismo estático) =====");
        ProdutoComum promo = estoque.getProdutos().get(1) instanceof ProdutoComum
                ? (ProdutoComum) estoque.getProdutos().get(1) : null;
        if (promo != null) {
            System.out.println("Preço antes do desconto: R$ " + promo.getPreco());
            promo.aplicarDesconto(10); // versão com 1 parâmetro
            System.out.println("Preço após 10% de desconto: R$ " + promo.getPreco());
            promo.aplicarDesconto(50, 0.20); // versão com desconto máximo limitado
            System.out.println("Preço após tentar 50% (limitado a R$0,20 de desconto): R$ " + promo.getPreco());
        }
    }
}
