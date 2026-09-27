# Sistema de Estoque de Produtos

Exercício de implementação em Java — Classes Abstratas, Herança, Interfaces,
Polimorfismo, Composição e Exceções.

## Estrutura do projeto

```
src/
├── EstoqueException.java            // exceção base (checked)
├── QuantidadeInvalidaException.java // extends EstoqueException
├── ProdutoIndisponivelException.java// extends EstoqueException
├── Vendavel.java                    // interface (método vender)
├── Product.java                     // classe abstrata, implements Vendavel
├── ProdutoComum.java                // extends Product
├── ProdutoPerecivel.java            // extends Product
├── Estoque.java                     // composição: tem uma lista de Product
└── EstoqueApp.java                  // main()
saida-programa.txt                   // saída de uma execução de exemplo
```

## Como compilar e rodar

```bash
cd src
javac -encoding UTF-8 *.java
java -Dfile.encoding=UTF-8 EstoqueApp
```

(O `-Dfile.encoding=UTF-8` evita que os acentos apareçam como `?` em alguns
terminais; o programa também força UTF-8 internamente na saída.)

## Como cada requisito do exercício foi atendido

1. **Hierarquia de exceções**: `EstoqueException` (checked, extends `Exception`)
   é a base; `QuantidadeInvalidaException` e `ProdutoIndisponivelException` a
   estendem.
2. **`Product` (abstrata)**: atributos `nome`, `preco`, `quantidade` são
   `private`. O construtor lança `QuantidadeInvalidaException` se `preco` ou
   `quantidade` forem negativos. `calcularValorTotal()` é abstrato.
   `getDescricao()` é concreto. `Product implements Vendavel`.
3. **Subclasses**:
   - `ProdutoComum.calcularValorTotal()` = `preco * quantidade`.
   - `ProdutoPerecivel` tem `diasParaVencer`; sobrescreve
     `calcularValorTotal()` aplicando 20% de desconto quando
     `diasParaVencer <= 3`, e sobrescreve `getDescricao()` incluindo a
     validade.
4. **Interface `Vendavel`**: implementada em `Product.vender(int)`, que lança
   `ProdutoIndisponivelException` se a quantidade desejada for maior que o
   estoque disponível; caso contrário, subtrai do estoque.
5. **Sobrecarga `aplicarDesconto`**: `Product` tem
   `aplicarDesconto(double percentual)` e
   `aplicarDesconto(double percentual, double descontoMaximo)`
   (polimorfismo estático — mesmo nome, assinaturas diferentes).
6. **`Estoque` (composição)**: guarda uma `List<Product>` (não herda de
   `Product`). Tem `adicionarProduto`, `venderProduto` (propaga
   `ProdutoIndisponivelException`) e `calcularValorTotalEstoque()`, que soma
   `calcularValorTotal()` de cada item — é aí que o polimorfismo aparece,
   pois o `Estoque` nunca precisa saber se o item é `ProdutoComum` ou
   `ProdutoPerecivel`.
7. **`EstoqueApp` (main)**: cadastra 2 `ProdutoComum` e 2 `ProdutoPerecivel`
   (um deles com `diasParaVencer <= 3`), tenta cadastrar produto com
   quantidade negativa (captura `QuantidadeInvalidaException`), faz uma
   venda válida e depois uma venda além do estoque disponível (captura
   `ProdutoIndisponivelException`), e imprime o valor total do estoque.

## Sobre a ordem dos catches (pista do enunciado)

Se um mesmo bloco `try` capturasse `QuantidadeInvalidaException`,
`ProdutoIndisponivelException` e `EstoqueException` juntos, o catch de
`EstoqueException` (o mais genérico) precisaria vir **por último** —
colocá-lo antes tornaria os catches seguintes inalcançáveis e o código não
compilaria. No projeto, cada exceção específica é tratada isoladamente no
ponto onde ocorre (mais claro e evita esse problema).

## Como enviar para o GitHub

```bash
git init
git add .
git commit -m "Sistema de estoque: classes abstratas, herança, interfaces, polimorfismo, composição e exceções"
git branch -M main
git remote add origin <URL_DO_SEU_REPOSITORIO>
git push -u origin main
```

Depois é só enviar o link do repositório (ou do commit final) conforme
pedido pelo professor.
