package br.com.marcoscunha.PokedexApi.config;

import br.com.marcoscunha.PokedexApi.model.Pokemon;
import br.com.marcoscunha.PokedexApi.service.PokemonService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class StartupRunner implements CommandLineRunner {

    private final PokemonService pokemonService;

    public StartupRunner(PokemonService pokemonService) {
        this.pokemonService = pokemonService;
    }

    @Override
    public void run(String... args) throws Exception {
        int totalPokemons = 1302; // ou pegue dinamicamente se quiser
        for (int i = 1; i <= totalPokemons; i++) {
            try {
                Pokemon p = pokemonService.fetchAndSavePokemon(String.valueOf(i));
                if (p != null) {
                    System.out.println("Inserido: " + p.getName());
                } else {
                    System.err.println("Erro ao inserir ID " + i);
                }

                // Delay pra evitar erro 429 (Too Many Requests)
                Thread.sleep(1000);
            } catch (Exception e) {
                System.err.println("Falha ao inserir Pokémon ID " + i + ": " + e.getMessage());
            }
            System.out.println("Iniciando conversão das sprites para Base64...");
            pokemonService.convertAllSpritesToBase64();
            System.out.println("Conversão das sprites concluída.");
        }

        pokemonService.convertAllSpritesToBase64();
    }
}

