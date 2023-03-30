package ibf2022.batch2.paf.serverstub.repositories;

import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

import jakarta.json.Json;
import jakarta.json.JsonObject;

@Repository
public class FundsMongoRepository {

    @Autowired
    MongoTemplate mongoTemplate;

    private final String TRANSACTION = "transaction";

    public String createTransaction(String from, String to, float amount) {
        JsonObject json = Json.createObjectBuilder().add("from", from)
                .add("to", to)
                .add("amount", amount)
                .build();

        Document toInsert = Document.parse(json.toString());

        Document newDoc = mongoTemplate.insert(toInsert, TRANSACTION);
        ObjectId id = newDoc.getObjectId("_id");
        return id.toString();

    }

}