package ibf2022.batch2.paf.serverstub.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ibf2022.batch2.paf.serverstub.models.Account;
import ibf2022.batch2.paf.serverstub.repositories.FundsRepository;

@Service
public class FundsService {

    @Autowired
    FundsRepository fundsRepo;

    @Transactional
    public String postTransfer(String from, String to, Integer amount) {
        try {
            Account accountFrom = fundsRepo.findAccountInfo(from);
            Account accountTo = fundsRepo.findAccountInfo(to);
            // check if balance is enough from the account transferring out
            float accountFromBalance = accountFrom.getBalance();
            if (amount < accountFromBalance) {
                accountFrom.setBalance(accountFromBalance - amount);
                fundsRepo.transactionStatus((accountFromBalance - amount), accountFrom.getFullName());
                accountTo.setBalance(accountTo.getBalance() + amount);
                fundsRepo.transactionStatus(accountTo.getBalance() + amount, accountTo.getFullName());
                return "success";
            }
        } catch (Exception e) {
            System.out.println("Exception: " + e);

            if (e.toString().equals(
                    "org.springframework.dao.EmptyResultDataAccessException: Incorrect result size: expected 1, actual 0"))
                return "Account does not exist.";
        }
        return null;
    }
}
