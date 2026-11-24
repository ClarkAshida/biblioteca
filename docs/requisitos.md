# Sistema de Biblioteca

Sistema de gerenciamento de uma biblioteca com controle de empréstimos, devoluções, reservas e cálculo de multas de livros físicos, além do controle de empréstimos de e-books e reservas de salas de estudo. O sistema será desenvolvido em Java.

## Perfis de usuário

- **Bibliotecário**
- **Leitor** (aluno ou professor)

## Requisitos

- Login e controle de permissões
- Gestão de usuários (CRUD)
- Gestão do acervo físico e digital (CRUD)
- Gestão de salas de estudo (CRUD)
- Consultar disponibilidade de livros e e-books
- Realizar empréstimos de livros físicos e e-books
- Registrar devolução de livros físicos
- Revogar acesso de e-books
- Renovar empréstimos
- Solicitar reserva de livros e e-books (fila de espera)
- Cancelar reserva
- Consultar empréstimos e multas
- Quitar multa
- Consultar status da sala (livre, ocupada ou reservada)
- Consultar equipamentos disponíveis na sala
- Reservar sala de estudo
- Realizar check-in
- Realizar check-out
- Cancelar reserva de sala
- Gerar relatório de atrasos e multas
- Gerar relatório de ocupação das salas
- Gerar relatório de utilização dos e-books

## Regras de negócio

- Livros físicos devolvidos com atraso terão multa diária progressiva.
- E-books terão seu acesso revogado após o vencimento, sem cobrança de multa.
- Cada e-book possui um limite de licenças simultâneas.
- Leitores com multas pendentes não poderão realizar novos empréstimos.
- Alunos e professores terão limites e prazos de empréstimo diferentes.
- Um exemplar físico não poderá ser emprestado para dois leitores simultaneamente.
- As reservas de livros e e-books seguirão uma fila de espera por ordem de solicitação.
- Quando um material for disponibilizado, o primeiro leitor da fila terá N horas para realizar o empréstimo. Caso não realize, a oportunidade passará para o próximo.
- A renovação será bloqueada caso exista outro leitor na fila de espera.
- As salas de estudo terão horários predefinidos e tempo máximo de permanência.
- Uma sala não poderá possuir duas reservas para o mesmo horário.
- O check-in só poderá ser realizado mediante uma reserva válida.
- Uma reserva de sala em andamento não poderá ser cancelada, apenas finalizada pelo check-out.
- Empréstimos encerrados não poderão ser renovados ou encerrados novamente.

## Polimorfismos

- **Livros físicos e e-books:** diferentes comportamentos de empréstimo, disponibilidade, devolução e expiração.
- **Alunos e professores:** diferentes regras e validações de limites e prazos de empréstimo.

## Estados dinâmicos

- **Empréstimo:** ativo, devolvido, expirado ou revogado, conforme o tipo de material.
- **Reserva de material:** aguardando, disponível, concluída, cancelada ou expirada.
- **Reserva de sala:** reservada, em uso, finalizada, cancelada ou expirada.

## Novas funcionalidades

- **Login e controle de permissões:** dois perfis de acesso (Leitor e Bibliotecário).
- **Consulta de disponibilidade:** verificar se existem exemplares ou licenças disponíveis.
- **Cancelamento de reserva de material:** permitir que o leitor saia da fila de espera.
- **Consulta de empréstimos e multas:** acompanhar prazos e pendências.

## Novas regras de negócio e alterações

- Diferentes limites e prazos de empréstimo para alunos e professores.
- Impedir empréstimos simultâneos do mesmo exemplar físico.
- Respeitar a ordem da fila de espera e liberar o material para o próximo leitor caso o prazo expire.
- Impedir reservas de uma mesma sala no mesmo horário.
- Exigir uma reserva válida para realizar check-in.
- Impedir o cancelamento de reservas de salas em utilização.
- Impedir renovações e devoluções de empréstimos já encerrados.

## Alterações para atender aos requisitos do trabalho

- **Perfis de acesso:** Leitor e Bibliotecário terão permissões diferentes. Aluno e Professor serão classes distintas, mas compartilharão o perfil de Leitor.
- **Primeiro polimorfismo:** livros físicos e e-books terão comportamentos diferentes nas operações de empréstimo e encerramento.
- **Segundo polimorfismo:** alunos e professores terão diferentes regras de empréstimo.
- **Estados dinâmicos:** controle dos estados e transições de empréstimos, reservas de materiais e reservas de salas.
