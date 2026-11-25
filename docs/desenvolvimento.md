# Guia de desenvolvimento da equipe

Este guia é o ponto de partida para quem vai desenvolver um módulo do Sistema de
Biblioteca. Comece executando o projeto, encontre sua responsabilidade na divisão
abaixo e entregue o primeiro fluxo indicado para sua frente.

As referências do trabalho são os [requisitos](requisitos.md) e o
[diagrama de classes](diagrama_biblioteca.png). Use o diagrama para preservar as
classes, heranças e relações, e os requisitos para implementar o comportamento.

## 1. Entenda o que você está recebendo

A entrega atual é uma **base inicial compilável**. A estrutura das classes está
preparada; a implementação das funcionalidades será feita pela equipe.

| Parte | O que já está preparado | O que falta desenvolver |
| --- | --- | --- |
| Ambiente | Java 21, Maven Wrapper e dependências do projeto | Configurar o JDK na sua máquina |
| Modelos | 22 classes com atributos, associações, heranças, construtores e getters | Validações, cálculos e operações de negócio |
| Enums | Perfis e estados previstos no diagrama | Utilizar os estados e validar suas transições |
| Banco | JDBC, H2 em memória, tabelas e fábrica de conexões | Consultas e gravações de cada módulo |
| Console | Menu principal, submenus e navegação | Conectar as opções às funcionalidades implementadas |
| Testes | Verificação da infraestrutura do banco e da navegação | Testes dos casos de uso e regras de cada módulo |

Nos modelos, os métodos abstratos têm apenas a assinatura. Os métodos concretos
de negócio têm `TODO` e lançam `UnsupportedOperationException`. Essa exceção indica
uma tarefa pendente para o responsável pelo módulo.

Os construtores inicializam dados e referências. Ainda será necessário implementar
o cálculo de datas, a atualização das associações e as alterações de estado.
As camadas de `repository` e `service` serão criadas conforme cada funcionalidade
precisar delas; suas interfaces e implementações ainda não estão prontas.

### Onde encontrar as coisas

Todos os pacotes Java ficam dentro de `src/main/java/com/imd`:

| Local | Conteúdo |
| --- | --- |
| `model/usuario` | Usuario, Leitor, Aluno, Professor e Bibliotecario |
| `model/acervo` | Material, LivroFisico, EBook e Exemplar |
| `model/emprestimo` | Emprestimo, EmprestimoFisico, EmprestimoEbook e Multa |
| `model/reserva` | ReservaMaterial |
| `model/sala` | SalaEstudo, ReservaSala, HorarioSala e Equipamento |
| `model/relatorio` | Relatorio e suas três especializações |
| `enums` | Perfis e estados |
| `infraestrutura/banco` | FabricaConexoes e InicializadorBanco |
| `ui` | ConsoleBiblioteca |
| `Main.java` | Inicialização do banco e abertura do console |

Os scripts e a configuração do banco ficam em `src/main/resources`. Os testes
ficam em `src/test/java/com/imd`.

## 2. Rode o projeto antes de começar

1. Clone o repositório da equipe e abra a pasta do projeto.
2. Instale/configure um JDK 21. No IntelliJ, importe o `pom.xml` como projeto Maven
   e selecione o JDK do projeto.
3. Na raiz do projeto, execute:

```powershell
.\mvnw.cmd test
.\mvnw.cmd compile exec:java
```

O esperado é que os testes passem e o console abra com os módulos da biblioteca.
As opções ainda apresentam a mensagem de funcionalidade em desenvolvimento.

Se o terminal reclamar de `JAVA_HOME`, configure a variável para a pasta do seu
JDK. O [README](../README.md) apresenta os detalhes de configuração. No Linux/macOS,
use `sh ./mvnw` no lugar de `.\mvnw.cmd`.

A aplicação inicia com tabelas vazias. Os cadastros e as consultas serão desenvolvidos
pelos responsáveis pelos módulos.

## 3. Encontre sua frente de trabalho

A divisão abaixo contempla cinco frentes funcionais. O integrante 1 é quem
preparou a base inicial e também desenvolverá o módulo de usuários e acesso.
Substituam os números pelos nomes da equipe quando distribuírem as tarefas.

Cada integrante implementa os modelos, a persistência, as operações do console e
os testes necessários à sua frente. Os relatórios ficam com os responsáveis pelos
dados correspondentes. A integração do projeto é uma atividade compartilhada.

| Integrante | Frente | Primeira entrega |
| --- | --- | --- |
| 1 — Flávio | Usuários e acesso | Cadastrar e consultar um aluno no H2 pelo console; em seguida, permitir login |
| 2 | Acervo físico, empréstimos e multas | Cadastrar um livro com exemplar e consultar sua disponibilidade |
| 3 | Acervo digital e empréstimos de e-books | Cadastrar um e-book com licenças e consultar sua disponibilidade |
| 4 | Reservas de materiais e renovações | Solicitar uma reserva e consultar a fila do material no H2 |
| 5 | Salas de estudo | Cadastrar uma sala com equipamento e horário e consultá-la |

### Integrante 1 — Usuários e acesso

Você assume uma funcionalidade completa do sistema, além da preparação inicial
já realizada. Suas classes principais são `Usuario`, `Leitor`, `Aluno`, `Professor`
e `Bibliotecario`.

- Implementar cadastro, consulta, edição e exclusão de usuários.
- Persistir os dados específicos de aluno, professor e bibliotecário.
- Implementar autenticação, proteção de senhas, login, logout e sessão no console.
- Implementar `temPermissao` e os limites/prazos de empréstimo de aluno e professor.
- Implementar `possuiMultasPendentes`, utilizando as multas fornecidas pelo módulo do integrante 2.
- Conectar as ações do bibliotecário aos módulos dos colegas, mantendo cada operação sob o perfil adequado.

**Comece por:** implementar cadastro e consulta de aluno com JDBC e ligar essas
operações ao menu de usuários. Em seguida, acrescentar professor/bibliotecário e
implementar o login com os dados cadastrados.

**Sua entrega estará demonstrável quando:** for possível cadastrar usuários,
entrar com credenciais válidas, recusar credenciais inválidas e identificar quais
operações o usuário autenticado pode realizar.

**Combine com os integrantes 2, 3 e 4:** como obter o leitor autenticado, os
limites/prazos por perfil e a situação das multas. Esses dados serão usados nos
empréstimos e nas renovações.

### Integrante 2 — Acervo físico, empréstimos e multas

Suas classes principais são `LivroFisico`, `Exemplar`, `EmprestimoFisico` e `Multa`.
Você coordena as alterações compartilhadas de `Material` e `Emprestimo`, além de
implementar `RelatorioAtrasosMultas` e coordenar ajustes na classe base `Relatorio`.
A operação de renovação será desenvolvida pelo integrante 4, com alinhamento prévio.

- Implementar cadastro, consulta, edição e exclusão de livros e exemplares.
- Implementar disponibilidade, empréstimo e devolução de livros físicos.
- Impedir dois empréstimos ativos para o mesmo exemplar.
- Aplicar limites, prazos e bloqueio por multas pendentes às solicitações de empréstimo.
- Implementar cálculo progressivo de multa, consulta de multas e quitação.
- Implementar consultas de empréstimos físicos e o relatório de atrasos/multas por período.

**Comece por:** cadastrar um livro com pelo menos um exemplar, gravar ambos e
consultar a disponibilidade. A próxima entrega deve emprestar esse exemplar e
mostrar que ele deixa de estar disponível.

**Sua entrega estará demonstrável quando:** for possível emprestar um exemplar,
recusar outro empréstimo simultâneo, devolver o material, apurar e quitar uma multa
e consultar o relatório correspondente.

**Combine com o integrante 1:** a consulta das multas pendentes e as regras do
leitor. **Combine com os integrantes 3 e 4:** as operações das classes base e a
atualização da fila quando um exemplar for devolvido.

### Integrante 3 — Acervo digital e empréstimos de e-books

Suas classes principais são `EBook` e `EmprestimoEbook`. Você também implementa
`RelatorioUtilizacaoEbooks`.

- Implementar cadastro, consulta, edição e exclusão de e-books e suas licenças.
- Implementar disponibilidade e empréstimo digital, aplicando as regras do leitor.
- Respeitar o limite de licenças simultâneas.
- Implementar encerramento, revogação e vencimento do acesso digital, sem cobrança de multa.
- Liberar a licença quando o acesso for encerrado e informar a disponibilidade ao módulo de reservas.
- Implementar consultas de empréstimos digitais e o relatório de utilização de e-books por período.

**Comece por:** cadastrar um e-book com licenças e consultar sua disponibilidade.
Depois, realize um empréstimo digital e confira que ele consome uma licença.

**Sua entrega estará demonstrável quando:** o sistema respeitar o limite de
licenças, encerrar o acesso e liberar a licença no encerramento ou vencimento,
e apresentar o relatório de utilização.

**Combine com o integrante 2:** as operações compartilhadas de `Material` e
`Emprestimo`. **Combine com o integrante 4:** a prioridade da fila, a liberação de
licenças para quem aguarda e a renovação de empréstimos digitais.

### Integrante 4 — Reservas de materiais e renovações

Sua classe principal é `ReservaMaterial`. Você implementa a fila de reservas de
**livros físicos e e-books**, além da operação `renovar()` de `Emprestimo`, em
conjunto com os responsáveis pelos empréstimos.

- Implementar solicitação, consulta e cancelamento de reservas de materiais.
- Persistir a fila por material e manter sua ordem de solicitação.
- Disponibilizar o material ao próximo leitor e definir o prazo de retirada.
- Expirar oportunidades não aproveitadas e encaminhá-las ao próximo leitor.
- Concluir a reserva quando o empréstimo correspondente for realizado.
- Implementar a renovação, verificando estado do empréstimo, regras do leitor e existência de outro leitor na fila.

**Comece por:** registrar duas reservas para um material e consultar a fila na
ordem das solicitações. Depois, implemente cancelamento e disponibilização ao
primeiro leitor elegível.

**Sua entrega estará demonstrável quando:** a fila respeitar a ordem de chegada,
tratar cancelamentos e expiração do prazo, e bloquear uma renovação quando houver
outro leitor aguardando o material.

**Combine com os integrantes 2 e 3:** a consulta e a atualização da fila nas
operações de empréstimo, devolução, vencimento e revogação. Concluir a reserva e
registrar o empréstimo correspondente precisam ocorrer na mesma transação.
Antes de editar `Emprestimo`, alinhe a mudança com o integrante 2.

### Integrante 5 — Salas de estudo

Suas classes principais são `SalaEstudo`, `ReservaSala`, `HorarioSala` e `Equipamento`.
Você também implementa `RelatorioOcupacaoSalas`.

- Implementar cadastro, consulta, edição e exclusão de salas, equipamentos e horários.
- Consultar a situação da sala e os equipamentos disponíveis.
- Implementar reserva de sala, check-in, check-out, cancelamento e expiração.
- Impedir duas reservas da mesma sala para o mesmo horário.
- Respeitar o tempo máximo de permanência e exigir reserva válida para check-in.
- Permitir que uma reserva em uso seja finalizada por check-out, preservando seu histórico.
- Implementar o relatório de ocupação das salas por período.

**Comece por:** cadastrar uma sala com equipamento e pelo menos um horário e
consultar esses dados. Depois, implemente a reserva e demonstre a recusa de uma
segunda reserva para o mesmo horário.

**Sua entrega estará demonstrável quando:** for possível reservar, realizar
check-in e check-out, recusar conflitos de horário e cancelamento de reserva em
uso, e consultar o relatório de ocupação.

**Combine com o integrante 1:** a identificação do leitor autenticado e as
permissões de gerenciamento do bibliotecário. Ajustes na base `Relatorio` devem
ser combinados com os integrantes 2 e 3.

## 4. Implemente seu primeiro fluxo completo

Trabalhe em uma funcionalidade pequena de cada vez. Por exemplo: cadastrar um
livro, gravá-lo e consultá-lo. Para cada funcionalidade, siga esta sequência:

1. **Leia o requisito e as classes envolvidas.** Identifique os métodos pendentes,
   os dados necessários e as regras de recusa da operação.
2. **Implemente o comportamento do modelo.** Substitua o `TODO` pelo comportamento
   esperado e preserve os métodos abstratos das classes base.
3. **Implemente a persistência necessária.** Crie um repositório com as consultas
   e gravações que esse fluxo usa.
4. **Coordene o caso de uso.** Crie o serviço quando precisar combinar validações,
   permissões, consultas e alterações de vários objetos.
5. **Conecte o console.** Troque a opção marcada como em desenvolvimento pela
   chamada ao caso de uso e apresente seu resultado ou uma mensagem de erro.
6. **Verifique o fluxo.** Confira o resultado no banco e teste também uma situação
   inválida prevista nos requisitos.

A organização esperada é:

```text
Console → caso de uso/serviço → repositório JDBC → H2
                   ↓
                 modelos
```

O console cuida de entrada e saída. Os modelos representam os objetos e suas
regras. O repositório executa SQL. Crie as classes necessárias ao seu fluxo,
conforme ele for desenvolvido.

### Use a estrutura existente dos modelos

- Construtores e getters já estão implementados. Os demais comportamentos estão pendentes.
- Os atributos são privados e as listas retornadas pelos getters são cópias.
  Para atualizar uma associação, implemente o método adequado na entidade;
  alterar a lista retornada pelo getter não altera a entidade.
- Datas de empréstimos usam `LocalDate`; solicitação/prazo de retirada usam
  `LocalDateTime`; horários usam `LocalTime`. Prazos são em dias e permanência em sala é em minutos.
- Datas como previsão de devolução e prazo de retirada precisam ser calculadas
  na implementação das operações correspondentes.
- Ao implementar a leitura do banco, acrescente os construtores ou fábricas
  necessários para restaurar datas, estados e associações persistidos.
  A leitura deve recuperar o histórico sem realizar um novo empréstimo ou consumir uma licença.
- Preserve as heranças que permitem os comportamentos diferentes de aluno/professor
  e livro físico/e-book. Cada especialização implementa seus próprios métodos.

### Use o banco que já está configurado

`FabricaConexoes.padrao().abrirConexao()` fornece uma conexão JDBC. Use
`PreparedStatement` para valores e `try-with-resources` para fechar os recursos.

O `schema.sql` já define as tabelas de usuários, materiais, exemplares, empréstimos,
multas, reservas de materiais, salas, equipamentos, horários e reservas de salas.
Leia o esquema antes de escrever o SQL do seu módulo. As heranças Java são
representadas no banco por colunas de tipo/perfil.

Uma operação que altera vários registros precisa ser feita na mesma transação.
Exemplo: registrar um empréstimo e marcar o exemplar como emprestado. Utilize
a mesma conexão JDBC para as alterações, configure `setAutoCommit(false)` e
confirme com `commit()`. Se a operação falhar, use `rollback()` para desfazê-la.

O SQL da base contém a estrutura das tabelas, campos obrigatórios, chaves e
relacionamentos. Os módulos Java deverão validar limites de licenças, filas,
estados e conflitos de empréstimos e reservas. Essas regras continuam sendo
requisitos do trabalho e ainda precisam ser implementadas.

O banco atual está em memória: os dados são perdidos quando a aplicação termina.
Para testar o ciclo de cadastro e consulta, mantenha a mesma execução aberta.
O `Main` cria as tabelas automaticamente. Alterações no esquema devem ser combinadas
entre os integrantes que utilizam as tabelas. `CREATE TABLE IF NOT EXISTS` não
atualiza tabelas já existentes.

### Alinhe os valores de negócio

Os requisitos não definem todos os valores numéricos. O planejamento sugeriu os
valores abaixo; eles ainda precisam ser implementados e compartilhados pelos módulos.

| Regra | Valor sugerido |
| --- | --- |
| Aluno | Até 3 empréstimos ativos, prazo de 7 dias |
| Professor | Até 5 empréstimos ativos, prazo de 14 dias |
| Retirada de material reservado | 24 horas após disponibilização |
| Multa progressiva | R$ 1 no primeiro dia, R$ 2 no segundo e assim por diante |
| Salas | Capacidade, horários e permanência máxima definidos no cadastro |

Registrem no projeto qualquer ajuste combinado nesses valores para que todos os
módulos apliquem as mesmas regras.

## 5. Integre seu trabalho com o restante da equipe

Crie uma branch para sua frente a partir da base atualizada da equipe. Sugestões:
`feature/usuarios`, `feature/acervo-fisico`, `feature/acervo-digital`,
`feature/reservas-materiais` e `feature/salas`. Os relatórios entram nas branches
dos respectivos módulos.

Entregue primeiro o fluxo indicado para sua frente. Depois, acrescente os outros
casos de uso em alterações pequenas. Os primeiros testes podem chamar os casos
de uso diretamente com usuários e materiais de teste. A sessão autenticada será
conectada ao console em conjunto com o integrante 1.

`Material` e `Emprestimo` são compartilhados pelos integrantes 2, 3 e 4.
O integrante 2 coordena alterações nessas classes, e o integrante 4 desenvolve
`renovar()` com alinhamento prévio. `Bibliotecario` conecta as operações de vários
módulos por meio do integrante 1. A base `Relatorio` é compartilhada pelos autores
dos três relatórios: integrantes 2, 3 e 5.

O console e o esquema SQL também são compartilhados. Antes de alterar uma
assinatura, enum ou regra que outra frente utiliza, combine a mudança com os
envolvidos. Cada integrante conecta o submenu da própria frente e participa da
integração e da demonstração final do sistema.

Considere sua funcionalidade pronta para revisão quando:

- O projeto compilar e os testes existentes passarem.
- O fluxo funcionar com dados reais do H2 e puder ser demonstrado no console.
- As regras aplicáveis ao fluxo estiverem implementadas e os casos inválidos forem recusados.
- Os estados e associações alterados estiverem coerentes também no banco.
- Os testes de negócio necessários à funcionalidade tiverem sido acrescentados.
- A entrega informar o que foi implementado, como executar e o que ainda depende de outro módulo.

Ao revisar a entrega, outro integrante deve conseguir executar o fluxo usando
essas instruções. Se uma funcionalidade continuar pendente, mantenha isso explícito
no código e no menu até que sua implementação esteja pronta.
