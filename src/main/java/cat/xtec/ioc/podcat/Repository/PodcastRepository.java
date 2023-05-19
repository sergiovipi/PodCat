package cat.xtec.ioc.podcat.Repository;

import cat.xtec.ioc.podcat.Model.Podcast;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@EnableJpaRepositories
@Repository
public interface PodcastRepository extends JpaRepository<Podcast, Long> {

    List<Podcast> findAllByOrderByIdDesc();

    List<Podcast> findByCanalId(Long id);

    List<Podcast> findByUsuariId(Long id);

    @Query("SELECT DISTINCT p.genere FROM Podcast p")
    List<String> findByGeneres();

    @Query("SELECT p.dataPublicacio FROM Podcast p")
    List<LocalDateTime> findByDataPublicacio();

    @Query("SELECT p FROM Podcast p WHERE p.genere = :genere")
    List<Podcast> findByGenere(@Param("genere") String genere);

    // Filtre.html
    @Query("SELECT DISTINCT p FROM Podcast p JOIN p.canal c WHERE c.titol LIKE %?1% OR p.genere LIKE %?1%")
    List<Podcast> searchByCanalTitol(String keyword);
}
