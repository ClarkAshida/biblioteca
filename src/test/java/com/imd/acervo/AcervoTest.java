package com.imd.acervo;

import com.imd.infraestrutura.banco.*;
import com.imd.model.acervo.*;
import com.imd.repository.AcervoRepository;
import com.imd.service.AcervoService;
import com.imd.ui.ConsoleBiblioteca;
import org.junit.jupiter.api.*;
import java.util.*;
import java.io.*;
import static org.junit.jupiter.api.Assertions.*;

class AcervoTest {
    FabricaConexoes fabrica;
    AcervoService service;
    @BeforeEach void preparar() throws Exception {
        fabrica = new FabricaConexoes("jdbc:h2:mem:" + UUID.randomUUID() + ";DB_CLOSE_DELAY=-1", "sa", "");
        new InicializadorBanco(fabrica).inicializar();
        service = new AcervoService(new AcervoRepository(fabrica));
    }
    @Test void crudFisicoEDigital() throws Exception {
        long livro = service.cadastrarLivro("Livro", "Autor", "123", List.of("A"));
        long ebook = service.cadastrarEbook("Digital", "Autor", "456", 2);
        assertTrue(service.listar().stream().allMatch(Material::consultarDisponibilidade));
        service.adicionarExemplar(livro, "B");
        service.excluirExemplar("A");
        assertThrows(IllegalArgumentException.class, () -> service.excluirExemplar("B"));
        service.editar(livro, "Novo título", "Autor", "123", null);
        service.editar(ebook, "Digital", "Autor", "456", 3);
        assertEquals("Novo título", service.listar().getFirst().getTitulo());
        assertEquals(3, ((EBook) service.listar().get(1)).getLicencasTotais());
        service.excluir(livro); service.excluir(ebook);
        assertTrue(service.listar().isEmpty());
    }
    @Test void cadastroAtomicoEValidacoes() throws Exception {
        service.cadastrarLivro("Livro", "Autor", "123", List.of("A"));
        assertThrows(java.sql.SQLException.class, () -> service.cadastrarLivro("Outro", "Autor", "456", List.of("B", "A")));
        assertEquals(1, service.listar().size());
        assertThrows(IllegalArgumentException.class, () -> service.cadastrarEbook("Digital", "Autor", "456", 0));
        assertThrows(IllegalArgumentException.class, () -> service.cadastrarLivro(" ", "Autor", "123", List.of("B")));
        assertThrows(IllegalArgumentException.class, () -> service.cadastrarLivro("Livro", "Autor", "123", List.of()));
        assertThrows(IllegalArgumentException.class, () -> service.cadastrarLivro("Livro", "Autor", "123", List.of("B", " B ")));
    }
    @Test void disponibilidadeRespeitaEstadoPersistido() throws Exception {
        long livro = service.cadastrarLivro("Livro", "Autor", "123", List.of("A"));
        long ebook = service.cadastrarEbook("Digital", "Autor", "456", 2);
        try (var c = fabrica.abrirConexao(); var s = c.createStatement()) {
            s.executeUpdate("UPDATE exemplares SET emprestado=TRUE");
            s.executeUpdate("UPDATE materiais SET licencas_em_uso=2 WHERE tipo='EBOOK'");
        }
        assertTrue(service.listar().stream().noneMatch(Material::consultarDisponibilidade));
        assertThrows(IllegalArgumentException.class, () -> service.editar(ebook,"Digital","Autor","456",1));
        assertThrows(IllegalArgumentException.class, () -> service.excluir(livro));
        assertThrows(IllegalArgumentException.class, () -> service.excluir(ebook));
        assertThrows(IllegalArgumentException.class, () -> service.adicionarExemplar(ebook,"B"));
    }
    @Test void consoleCadastraEConsulta() {
        var buffer = new ByteArrayOutputStream();
        new ConsoleBiblioteca(new Scanner("2\n2\nLivro\nAutor\n123\nA\n1\n0\n0\n"), new PrintStream(buffer), service).executar();
        assertTrue(buffer.toString().contains("Livro cadastrado"));
        assertTrue(buffer.toString().contains("Exemplar A"));
    }
}
