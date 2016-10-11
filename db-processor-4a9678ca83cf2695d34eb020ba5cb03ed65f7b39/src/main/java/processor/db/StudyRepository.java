package processor.db;

import org.springframework.data.mongodb.repository.MongoRepository;
import processor.entity.Study;

/**
 * Created by Ester on 10/10/2016.
 */
public interface StudyRepository extends MongoRepository<Study, String> {
    public Study findByIdStudy(String idStudy);
}
