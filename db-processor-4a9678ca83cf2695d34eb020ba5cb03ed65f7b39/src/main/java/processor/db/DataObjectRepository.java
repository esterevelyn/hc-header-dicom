package processor.db;

import org.springframework.data.mongodb.repository.MongoRepository;
import processor.entity.DataObject;

public interface DataObjectRepository extends MongoRepository<DataObject, String> {
    public DataObject findByIdPatient(String idPatient);
   // public List<DataObject> findByLastName(String lastName);
}