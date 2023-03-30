package ibf2022.batch2.paf.serverstub.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import ibf2022.batch2.paf.serverstub.models.TransferInfo;
import ibf2022.batch2.paf.serverstub.services.FundsMongoService;
import ibf2022.batch2.paf.serverstub.services.FundsService;
import jakarta.json.Json;
import jakarta.json.JsonObject;

@RestController
public class FundsTransferController {

	@Autowired
	FundsService fundsSvc;

	@Autowired
	FundsMongoService fundsMongoSvc;

	@PostMapping("/api/transfer")
	public ResponseEntity<String> postTransfer(@RequestBody TransferInfo transferInfo) {

		String transactionStatus = fundsSvc.postTransfer(transferInfo.getSrcAcct(), transferInfo.getDestAcct(),
				transferInfo.getAmount());
		System.out.println(transferInfo.getSrcAcct());
		System.out.println("Status: " + transactionStatus);
		if (transactionStatus == null) {
			return ResponseEntity.badRequest().body(Json.createObjectBuilder().add("message", "Insufficient amount.")
					.build().toString());
		}
		if (transactionStatus.equals("success")) {
			transactionStatus = fundsMongoSvc
					.createTransaction(transferInfo.getSrcAcct(), transferInfo.getDestAcct(),
							(float) transferInfo.getAmount());

			JsonObject json = Json.createObjectBuilder().add("transactionId", transactionStatus)
					.build();
			return ResponseEntity.ok().body(json.toString());
		} else {
			return ResponseEntity.badRequest().body(Json.createObjectBuilder().add("message", transactionStatus)
					.build().toString());
		}
		// Transfer successful return the following JSON object
		// { "transactionId", "aTransactionId" }
		//
		// Transfer failed return the following JSON object
		// { "message", "Error message" }
	}
}
