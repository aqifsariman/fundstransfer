package ibf2022.batch2.paf.serverstub.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ibf2022.batch2.paf.serverstub.repositories.FundsMongoRepository;

@Service
public class FundsMongoService {

    @Autowired
    FundsMongoRepository fundsMongoRepo;

    public String createTransaction(String from, String to, float Amount) {
        return fundsMongoRepo.createTransaction(from, to, Amount);
    }
}
