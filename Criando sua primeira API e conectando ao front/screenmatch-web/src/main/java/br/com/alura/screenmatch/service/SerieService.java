package br.com.alura.screenmatch.service;

import br.com.alura.screenmatch.dto.EpisodioDTO;
import br.com.alura.screenmatch.dto.SerieDTO;
import br.com.alura.screenmatch.model.Categoria;
import br.com.alura.screenmatch.model.Serie;
import br.com.alura.screenmatch.repository.SerieRepository;
import java.util.Collections;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SerieService {

    private final SerieRepository serieRepository;

    public SerieService(SerieRepository serieRepository) {
        this.serieRepository = serieRepository;
    }

    public List<SerieDTO> obterTodasAsSeries() {
        return converteDados(serieRepository.findAll());
    }

    public List<SerieDTO> obterTop5Series() {
        return converteDados(serieRepository.findTop5ByOrderByAvaliacaoDesc());
    }
    public List<SerieDTO> obterLancamento() {
        return converteDados(serieRepository.lancamentosMaisRecentes());
    }

    private List<SerieDTO> converteDados(List<Serie> series){
        return series.stream()
                .map(serie -> new SerieDTO(serie.getId(), serie.getTitulo(), serie.getTotalTemporadas(),
                        serie.getAvaliacao(), serie.getGenero(), serie.getAtores(),
                        serie.getPoster(), serie.getSinopse()))
                .toList();
    }

    public SerieDTO obterPorId(Long id) {
        Optional<Serie> serie = serieRepository.findById(id);
        if(serie.isPresent()){
            Serie s = serie.get();
            return new SerieDTO(s.getId(), s.getTitulo(), s.getTotalTemporadas(),
                    s.getAvaliacao(), s.getGenero(), s.getAtores(),
                    s.getPoster(), s.getSinopse());
        }
        return null;
    }

    public List<EpisodioDTO> obterTodasTemporadas(Long id) {
        Optional<Serie> serie = serieRepository.findById(id);
        if(serie.isPresent()){
            Serie s = serie.get();
            return s.getEpisodios().stream()
                    .map(e -> new EpisodioDTO(e.getTemporada(), e.getNumeroEpisodio(), e.getTitulo()))
                    .toList();
        }
        return Collections.emptyList();
    }

    public List<EpisodioDTO> obterTemporadasPorNumero(Long id, Long numero) {
        return serieRepository.obterEpisodiosPorTempoarada(id, numero)
                .stream()
                .map(e -> new EpisodioDTO(e.getTemporada(), e.getNumeroEpisodio(), e.getTitulo()))
                .toList();
    }

    public List<SerieDTO> obterSeriesPorCategoria(String nomeGenero) {
        Categoria categoria = Categoria.fromPortugues(nomeGenero);
        return converteDados(serieRepository.findByGenero(categoria));
    }
}