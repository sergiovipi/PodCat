package cat.xtec.ioc.podcat.Repository;

import cat.xtec.ioc.podcat.Model.Canal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.util.List;

@EnableJpaRepositories
@Repository
public interface CanalRepository extends JpaRepository<Canal, Long> {

    List<Canal> findByUsuariId(Long id);

    @Query(value = "SELECT * FROM canals WHERE titol LIKE %?1%", nativeQuery = true)
    List<Canal>search(String keyword);
}
